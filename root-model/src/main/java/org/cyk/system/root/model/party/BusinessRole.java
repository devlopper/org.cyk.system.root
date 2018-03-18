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
@FieldOverride(name=BusinessRole.FIELD___PARENT__,type=BusinessRole.class)
/**
 * It is a role that can be taken by a party in a business activity
 * @author Christian
 *
 */
public class BusinessRole extends AbstractDataTreeType implements Serializable {
	private static final long serialVersionUID = -4946585596435850782L;
	
	private Integer minimumNumberOfOccurrence;
	private Integer maximumNumberOfOccurrence;
	
	@Override
	public BusinessRole setCode(String code) {
		return (BusinessRole) super.setCode(code);
	}
	
	@Override
	public BusinessRole set__parent__(AbstractIdentifiable parent) {
		return (BusinessRole) super.set__parent__(parent);
	}
	
}
