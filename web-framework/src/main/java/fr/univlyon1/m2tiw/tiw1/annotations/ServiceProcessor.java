package fr.univlyon1.m2tiw.tiw1.annotations;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;

@SupportedAnnotationTypes("fr.univlyon1.m2tiw.tiw1.annotations.Service")
public class ServiceProcessor extends BaseProcessor {

	@Override
	public synchronized void init(ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);
	}

}
