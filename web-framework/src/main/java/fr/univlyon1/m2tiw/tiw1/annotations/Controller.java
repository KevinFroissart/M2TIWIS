package fr.univlyon1.m2tiw.tiw1.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Controller {
	COMPONENT_TYPE type() default COMPONENT_TYPE.CONTROLLER;
}
