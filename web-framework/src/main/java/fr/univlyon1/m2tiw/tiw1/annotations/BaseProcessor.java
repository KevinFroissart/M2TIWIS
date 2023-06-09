package fr.univlyon1.m2tiw.tiw1.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

@SupportedAnnotationTypes("fr.univlyon1.m2tiw.tiw1.annotations.Component")
public class BaseProcessor extends AbstractProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(BaseProcessor.class);
	MethodSpec startMethod;
	MethodSpec stopMethod;
	FieldSpec loggerField;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		this.startMethod = MethodSpec
				.methodBuilder("start")
				.addModifiers(Modifier.PUBLIC)
				.addCode("LOG.info(\"Composant {} démarré.\", this.toString());")
				.build();

		this.stopMethod = MethodSpec
				.methodBuilder("stop")
				.addModifiers(Modifier.PUBLIC)
				.addCode("LOG.info(\"Composant {} arrêté.\", this.toString());")
				.build();

		this.loggerField = FieldSpec
				.builder(Logger.class, "LOG")
				.addModifiers(Modifier.PRIVATE, Modifier.FINAL)
				.initializer("org.slf4j.LoggerFactory.getLogger(this.getClass())")
				.build();
	}

	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (TypeElement annotation : annotations) {
			LOG.info("Annotation : {}", annotation.getSimpleName());
			for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
				LOG.info("Element annote : {}", element);

				TypeSpec.Builder subComponent = buildSubComponent(element, annotation);
				subComponent.addMethod(buildConstructor(element));

				generateClass(element, subComponent.build());
			}
		}
		return true;
	}

	public TypeSpec.Builder buildSubComponent(Element element, TypeElement annotation) {
		return TypeSpec
				.classBuilder(ClassName.bestGuess(element.toString().concat("_" + annotation.getSimpleName().toString())))
				.addSuperinterface(Startable.class)
				.superclass(ClassName.bestGuess(element.asType().toString()))
				.addField(this.loggerField)
				.addModifiers(Modifier.PUBLIC)
				.addMethod(this.startMethod)
				.addMethod(this.stopMethod);
	}

	public MethodSpec buildConstructor(Element element) {
		MethodSpec.Builder constructor = MethodSpec
				.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.addException(Exception.class);

		element.getEnclosedElements().forEach(elem -> {
			if (elem.getKind() == ElementKind.CONSTRUCTOR) {
				String params = ((ExecutableElement) elem).getParameters().stream().map(param -> {
					constructor.addParameter(TypeName.get(param.asType()), param.getSimpleName().toString());
					return param.getSimpleName().toString();
				}).reduce("", (a, b) -> a + ", " + b);

				constructor.addStatement("super(" + (params.length() > 0 ? params.substring(2) : "") + ")");
				constructor.addStatement("this.start()");
			}
		});

		return constructor.build();
	}

	public void generateClass(Element element, TypeSpec subComponent) {
		String packageName = element.toString();
		int separator = packageName.lastIndexOf(".");
		packageName = packageName.substring(0, separator);
		// Création du fichier source Java
		JavaFile javaFile = JavaFile
				.builder(packageName, subComponent)
				.build();
		try {
			// Utilisation de l'interface Filer pour récupérer un PrintWriter
			// vers le répertoire GeneratedSources indiqué dans le pom
			JavaFileObject builderFile = processingEnv
					.getFiler()
					.createSourceFile(subComponent.name);
			try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
				// Ecriture du fichier
				javaFile.writeTo(out);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
