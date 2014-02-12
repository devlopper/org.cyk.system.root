package org.cyk.system.root.model.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Metadata of model
 * @author Komenan Y .Christian
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Model {
	
	public static enum GenderType{UNSET,MALE,FEMALE}
	
	GenderType genderType() default GenderType.UNSET;
	
}