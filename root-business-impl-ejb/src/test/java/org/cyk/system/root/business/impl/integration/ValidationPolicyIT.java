package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.Runnable;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.junit.Test;

public class ValidationPolicyIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;
    
    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    @Test
    public void validateFieldValueMustBeNotNull(){
    	FieldHelper.Field.get(Sex.class, FieldHelper.getInstance().buildPath(Sex.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE))
    	.getConstraints().setIsNullable(Boolean.FALSE);
    	
    	TestCase testCase = instanciateTestCase();
    	testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {
				inject(ValidationPolicy.class).validateCreate(instanciateOne(Sex.class));
			}
    	}, FieldHelper.Field.get(Sex.class,Sex.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE).getIdentifier(ConditionHelper.Condition.Builder.Null.class)
    			, "La valeur de l'attribut <<code>> de l'entité <<sexe>> doit être non nulle.");
    	
    	testCase.clean();
    }
    
    @Test
    public void validateFieldValueMustBeUnique(){
    	FieldHelper.Field.get(Sex.class, FieldHelper.getInstance().buildPath(Sex.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE))
    	.getConstraints().setIsUnique(Boolean.TRUE);
    	
    	TestCase testCase = instanciateTestCase();
    	final String sexCode01 = testCase.getRandomHelper().getAlphabetic(5);
    	testCase.create(testCase.instanciateOne(Sex.class,sexCode01));
    	testCase.assertNotNullByBusinessIdentifier(Sex.class, sexCode01);
    	
    	testCase.assertThrowable(new Runnable(testCase) {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {
				inject(ValidationPolicy.class).validateCreate(instanciateOne(Sex.class,sexCode01));
			}
    	}, FieldHelper.Field.get(Sex.class,Sex.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE).getIdentifier(ConditionHelper.Condition.Builder.Comparison.Count.class)
    			, "Le nombre d'enregistrement de l'entité <<sexe>> où la valeur de l'attribut <<code>> est "+sexCode01+" doit être inférieure à 1.");
    	
    	testCase.clean();
    }
    
    /**/
    
    @SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList();
		}
		
    }
}
