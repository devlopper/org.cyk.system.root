package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
@FieldOverride(name=PartyBusinessRole.FIELD_PARENT,type=PartyBusinessRole.class)
/**
 * It is a role that can be taken by a person in a business activity
 * @author Christian
 *
 */
public class PartyBusinessRole extends AbstractDataTreeType implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;
	
	@Override
	public PartyBusinessRole setCode(String code) {
		return (PartyBusinessRole) super.setCode(code);
	}
	
	@Override
	public PartyBusinessRole set__parent__(AbstractIdentifiable parent) {
		return (PartyBusinessRole) super.set__parent__(parent);
	}
	
}
