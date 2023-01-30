package fr.univlyon1.m2tiw.tiw1.annotations;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("fr.univlyon1.m2tiw.tiw1.annotations.Service")
public class ServiceProcessor extends BaseProcessor {
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return super.process(annotations, roundEnv);
    }
}
