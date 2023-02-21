package fr.univlyon1.m2tiw.tiw1.annotations;

import java.lang.annotation.*;

// S'applique aux classes, interfaces et enums
@Target(ElementType.TYPE)

// Sera traitée par un annotation processor au niveau des sources (pas sur les fichiers compilés ni à l'exécution)
@Retention(RetentionPolicy.SOURCE)

// S'applique aussi aux sous-classes de celles annotées
// @Inherited

/**
 * Annotation qui permettra de définir déclarativement les composants du framework
 */
public @interface Component {
    COMPONENT_TYPE type() default COMPONENT_TYPE.BUSINESS;
}
