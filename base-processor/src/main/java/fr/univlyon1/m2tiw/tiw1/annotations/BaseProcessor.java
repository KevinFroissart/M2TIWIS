package fr.univlyon1.m2tiw.tiw1.annotations;

import com.squareup.javapoet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("fr.univlyon1.m2tiw.tiw1.annotations.Component")
public class BaseProcessor extends AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(AbstractProcessor.class);
    MethodSpec startMethod, stopMethod;
    FieldSpec loggerField;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.startMethod = MethodSpec
                .methodBuilder("start")
                .addModifiers(Modifier.PUBLIC)
                .addCode("logger.info(\"Composant \" + this.toString() + \" démarré.\");")
                .build();

        this.stopMethod = MethodSpec
                .methodBuilder("stop")
                .addModifiers(Modifier.PUBLIC)
                .addCode("logger.info(\"Composant \" + this.toString() + \" arrêté.\");")
                .build();

        this.loggerField = FieldSpec
                .builder(Logger.class, "logger")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("org.slf4j.LoggerFactory.getLogger(this.getClass())")
                .build();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            logger.info("Annotation : " + annotation.getSimpleName());
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                logger.info("Element annote : " + element.toString());

                // Création d'un sous-composant
                TypeSpec subComponent = TypeSpec
                        .classBuilder(ClassName.bestGuess(element.toString() + "_Component"))
                        .superclass(element.asType())
                        .addField(this.loggerField)
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(this.startMethod)
                        .addMethod(this.stopMethod)
                        .build();

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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
