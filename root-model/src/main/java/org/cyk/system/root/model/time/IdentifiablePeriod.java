package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.helper.ClassHelper;
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
    	
    }
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	
	public static final String COLUMN_TYPE = FIELD_TYPE;
}
