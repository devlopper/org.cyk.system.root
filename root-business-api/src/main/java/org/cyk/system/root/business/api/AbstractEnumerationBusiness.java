package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.cdi.BeanAdapter;

public interface AbstractEnumerationBusiness<ENUMERATION extends AbstractEnumeration> extends TypedBusiness<ENUMERATION> {

	ENUMERATION instanciateOne(String[] values,InstanciateOneListener listener);
	ENUMERATION instanciateOne(String name);
	ENUMERATION instanciateOne(String code,String name);
	
	List<ENUMERATION> instanciateMany(List<List<String>> strings);
	List<ENUMERATION> instanciateMany(String[][] strings);
	
    ENUMERATION find(String code);
    ENUMERATION load(String code);
    
    /**/
    
    public static interface InstanciateOneListener{
    	Integer getLastProcessedIndex();
    	void setLastProcessedIndex(Integer value);
    	/**/
    	@Getter @Setter
    	public static class Adapter extends BeanAdapter implements Serializable,InstanciateOneListener{
			private static final long serialVersionUID = -2973740411677441059L;
    		private Integer lastProcessedIndex;
			/**/
			
			public static class Default extends Adapter implements Serializable{
				private static final long serialVersionUID = -2973740411677441059L;
	    		
				/**/
	    	}
    	}
    }
   
}
