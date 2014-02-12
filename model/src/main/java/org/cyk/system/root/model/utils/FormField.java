package org.cyk.system.root.model.utils;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a field to be generated at runtime in the form build
 * @author Komenan Y .Christian
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormField {
	
	public static enum CompositionRelationshipInputType{AUTO,COMBOBOX,AUTOCOMPLETE,FIELDS,FORM}
	public static enum LabelValueType{I18N_ID,I18N_VALUE,VALUE}
	public static enum SelectOneInputType{COMBOBOX,RADIO}
	
	String label() default "";
	LabelValueType labelValueType() default LabelValueType.I18N_ID;
	boolean applicationId() default false;
	boolean required() default false;
	String inputType() default "";
	SelectOneInputType selectOneInputType() default SelectOneInputType.COMBOBOX;
	boolean textArea() default false;
	boolean ignore() default false;
	boolean showCreateButton() default true;
	CompositionRelationshipInputType compositionRelationshipInputType() default CompositionRelationshipInputType.AUTO;
}