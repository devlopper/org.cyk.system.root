package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class MovementCollection extends AbstractCollection<Movement> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;
	
	@Column(name=COLUMN_VALUE,precision=20,scale=FLOAT_SCALE) private BigDecimal value;
 
	@ManyToOne @JoinColumn(name=COLUMN_TYPE) @Accessors(chain=true) private MovementCollectionType type;
	
	@Override
	public MovementCollection setCode(String code) {
		return (MovementCollection) super.setCode(code);
	}
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_TYPE = "type";
	
	public static final String COLUMN_VALUE = COLUMN_NAME_UNKEYWORD+FIELD_VALUE;
	public static final String COLUMN_TYPE = COLUMN_NAME_UNKEYWORD+FIELD_TYPE;
	
}
