package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE) @Accessors(chain=true)
public class MovementGroup extends AbstractMovementCollections<MovementGroupItem> implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	@ManyToOne @JoinColumn(name=COLUMN_TYPE) @NotNull private MovementGroupType type;
	
	public MovementGroup setPartyFromCode(String code){
		return (MovementGroup) super.setPartyFromCode(code);
	}
	
	@Override
	public MovementGroup setCode(String code) {
		return (MovementGroup) super.setCode(code);
	}
	
	public MovementGroup setTypeFromCode(String code){
		this.type = getFromCode(MovementGroupType.class, code);
		return this;
	}
	
	@Override
	public MovementGroup __setBirthDateComputedByUser__(Boolean value) {
		return (MovementGroup) super.__setBirthDateComputedByUser__(value);
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	
	public static final String COLUMN_TYPE = FIELD_TYPE;
}

