package org.cyk.system.root.model.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.computation.DataReadConfiguration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class AbstractFieldValueSearchCriteriaSet extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 2055293289197179106L;

	private static final Map<Class<?>, Class<? extends AbstractFieldValueSearchCriteriaSet>> MAP = new HashMap<>();
	
	protected DataReadConfiguration readConfig = new DataReadConfiguration();
	protected Collection<AbstractFieldValueSearchCriteria<?>> criterias = new ArrayList<>();
	protected Collection<AbstractIdentifiable> excluded;
	
	protected void setStringSearchCriteria(StringSearchCriteria stringSearchCriteria,String value){
		stringSearchCriteria.setValue(value);
		criterias.add(stringSearchCriteria);
	}
	
	public AbstractFieldValueSearchCriteriaSet(AbstractFieldValueSearchCriteriaSet criteriaSet){
		readConfig = new DataReadConfiguration(criteriaSet.readConfig);
		criterias.addAll(criteriaSet.criterias);
	}
	
	public abstract void set(String value);
	public abstract void set(StringSearchCriteria stringSearchCriteria);
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	public Collection<AbstractIdentifiable> getExcluded(){
		if(excluded==null)
			excluded = new ArrayList<>();
		return excluded;
	}
	
	public Boolean isNull(){
		for(AbstractFieldValueSearchCriteria<?> criteria : criterias)
			if(!Boolean.TRUE.equals(criteria.isNull()))
				return Boolean.FALSE;
		return Boolean.TRUE;
	}
	
	/**/
	
	@Getter @Setter @NoArgsConstructor
	public static abstract class AbstractIdentifiableSearchCriteriaSet extends AbstractFieldValueSearchCriteriaSet implements Serializable {
		private static final long serialVersionUID = 3289886983410287161L;
		
		protected GlobalIdentifier.SearchCriteria globalIdentifier = new GlobalIdentifier.SearchCriteria();
		
		public AbstractIdentifiableSearchCriteriaSet(String name) {
			globalIdentifier.set(name);
		}
		
		@Override
		public void set(String value) {
			globalIdentifier.set(value);
		}
		
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			globalIdentifier.set(stringSearchCriteria);
		}

		public StringSearchCriteria getCode() {
			return globalIdentifier.getCode();
		}

		public StringSearchCriteria getName() {
			return globalIdentifier.getName();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public static Class<? extends AbstractFieldValueSearchCriteriaSet> get(Class<?> aClass){
		Class<? extends AbstractFieldValueSearchCriteriaSet> result = MAP.get(aClass);
		if(result==null){
			try {
				result = (Class<? extends AbstractFieldValueSearchCriteriaSet>) ClassUtils.getClass(aClass.getName()+".SearchCriteria");
			} catch (ClassNotFoundException e) {
				result = AbstractFieldValueSearchCriteriaSet.class;
			}
			if(result!=null)
				set(aClass, result);
		}
		return result;
	}
	
	public static void set(Class<?> aClass,Class<? extends AbstractFieldValueSearchCriteriaSet> searchCriteriaClass){
		MAP.put(aClass, searchCriteriaClass);
		if(!AbstractFieldValueSearchCriteriaSet.class.equals(searchCriteriaClass))
			System.out.println("SearchCriteria class has been registered. "+aClass+" -> "+searchCriteriaClass);
	}
}
