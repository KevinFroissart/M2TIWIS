package fr.univlyon1.m2tiw.tiw1.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

@SupportedAnnotationTypes("fr.univlyon1.m2tiw.tiw1.annotations.Service")
public class ConfigurationProcessor extends AbstractProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationProcessor.class);

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            logger.info("Annotation : {}", annotation.getSimpleName());
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                logger.info("Element annote : {}", element);

                // Création d'un sous-composant
                TypeSpec subComponent = TypeSpec
                        .classBuilder(ClassName.bestGuess(element.toString().concat("_" + annotation.getSimpleName().toString())))
                        .superclass(element.asType())
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(AnnotationSpec.builder(Component.class)
                                .addMember("type", "$S", COMPONENT_TYPE.CONFIGURATION)
                                .build()
                        ).build();

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
        return true;
    }

}
