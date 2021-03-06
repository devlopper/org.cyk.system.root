package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity
@FieldOverrides(value={
		@FieldOverride(name=AbstractCollectionItem.FIELD_COLLECTION,type=IdentifiablePeriodCollection.class)
})
@Accessors(chain=true)
public class IdentifiablePeriod extends AbstractCollectionItem<IdentifiablePeriodCollection> implements Serializable {
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @JoinColumn(name=COLUMN_TYPE)  private IdentifiablePeriodType type;

	/**/
	
	@Override
	public IdentifiablePeriod setCollection(IdentifiablePeriodCollection collection) {
		return (IdentifiablePeriod) super.setCollection(collection);
	}
	
	@Override
	public IdentifiablePeriod setCollectionFromCode(String code) {
		return (IdentifiablePeriod) super.setCollectionFromCode(code);
	}
	
	public IdentifiablePeriod setTypeFromCode(String code){
		this.type = InstanceHelper.getInstance().getByIdentifier(IdentifiablePeriodType.class, code, ClassHelper.Listener.IdentifierType.BUSINESS);
		return this;
	}
	
	@Override
	public IdentifiablePeriod setBirthDate(Date date) {
		return (IdentifiablePeriod) super.setBirthDate(date);
	}
	
	@Override
	public IdentifiablePeriod setBirthDateFromString(String date) {
		return (IdentifiablePeriod) super.setBirthDateFromString(date);
	}
	
	@Override
	public IdentifiablePeriod setDeathDate(Date date) {
		return (IdentifiablePeriod) super.setDeathDate(date);
	}
	
	@Override
	public IdentifiablePeriod setDeathDateFromString(String date) {
		return (IdentifiablePeriod) super.setDeathDateFromString(date);
	}
	
	@Override
	public IdentifiablePeriod setClosed(Boolean closed) {
		return (IdentifiablePeriod) super.setClosed(closed);
	}
	
	/**/
	
	public static class Filter extends AbstractIdentifiable.Filter<IdentifiablePeriod> implements Serializable{
		private static final long serialVersionUID = 1L;
    	
		@Override
		public Filter addMaster(Object master) {
			return (Filter) super.addMaster(master);
		}
		
		@Override
		public Filter setClosed(Boolean... values) {
			return (Filter) super.setClosed(values);
		}
		
		@Override
		public Filter addMaster(Class<?> aClass,Object identifier) {
			return (Filter) super.addMaster(aClass, identifier);
		}
    }
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	
	public static final String COLUMN_TYPE = FIELD_TYPE;
	
	/**/
	
	public static final Set<Class<?>> MANAGED_CLASSES = new HashSet<>();
	
	public static void manage(Collection<Class<?>> classes){
		if(CollectionHelper.getInstance().isNotEmpty(classes))
			MANAGED_CLASSES.addAll(classes);
	}
	
	public static void manage(Class<?>...classes){
		if(ArrayHelper.getInstance().isNotEmpty(classes))
			manage(Arrays.asList(classes));
	}
	
	public static Boolean isManaged(Class<?> aClass){
		return aClass == null ? Boolean.FALSE : MANAGED_CLASSES.contains(aClass);
	}
	
	public static Boolean isManaged(AbstractIdentifiable identifiable){
		return isManaged(identifiable == null ? null : identifiable.getClass());
	}
}
