package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.InstanceHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class IdentifiablePeriodCollection extends AbstractCollection<IdentifiablePeriod> implements Serializable {
	private static final long serialVersionUID = -4946585596435850782L;
	
	@ManyToOne @JoinColumn(name=COLUMN_TYPE) @Accessors(chain=true) private IdentifiablePeriodCollectionType type;
	
	@Override
	public IdentifiablePeriodCollection setCode(String code) {
		return (IdentifiablePeriodCollection) super.setCode(code);
	}
	
	public IdentifiablePeriodCollection setTypeFromCode(String code){
		this.type = InstanceHelper.getInstance().getByIdentifier(IdentifiablePeriodCollectionType.class, code, ClassHelper.Listener.IdentifierType.BUSINESS);
		return this;
	}
	
	public static final String FIELD_TYPE = "type";
	
	public static final String COLUMN_TYPE = COLUMN_NAME_UNKEYWORD+FIELD_TYPE;
	
}
