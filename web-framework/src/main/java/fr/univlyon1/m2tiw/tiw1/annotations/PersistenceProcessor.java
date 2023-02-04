package fr.univlyon1.m2tiw.tiw1.annotations;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

@SupportedAnnotationTypes("fr.univlyon1.m2tiw.tiw1.annotations.Persistence")
public class PersistenceProcessor extends BaseProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(PersistenceProcessor.class);

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (TypeElement annotation : annotations) {
			LOG.info("Annotation : {}", annotation.getSimpleName());
			for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
				LOG.info("Element annote : {}", element);

				TypeSpec.Builder subComponent = buildSubComponent(element, annotation);

				if (ClassName.bestGuess(element.toString()).simpleName().equals("DBAccess")) {
					subComponent.addMethod(initConstructorParams(element));
				}
				else {
					subComponent.addMethod(buildConstructor(element));
				}

				generateClass(element, subComponent.build());
			}
		}
		return true;
	}

	private MethodSpec initConstructorParams(Element element) {
		return MethodSpec
				.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.addException(Exception.class)
				.addStatement("super($S, $S, $S)",
						element.getAnnotation(Persistence.class).url(),
						element.getAnnotation(Persistence.class).username(),
						element.getAnnotation(Persistence.class).password()
				)
				.addStatement("this.start()")
				.build();
	}

}
