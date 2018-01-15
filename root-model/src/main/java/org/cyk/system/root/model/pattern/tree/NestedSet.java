package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter
@Entity @ModelBean(crudStrategy=CrudStrategy.INTERNAL,genderType=GenderType.MALE)
public class NestedSet extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 9135086950442356103L;

	@ManyToOne @JoinColumn(name=COLUMN_ROOT) private NestedSetNode root;
	
	@Column(name=COLUMN_NUMBER_OF_ELEMENT)
	private Integer numberOfElement; 
	
	@Override
	public NestedSet setCode(String code) {
		return (NestedSet) super.setCode(code);
	}

	public static final String FIELD_ROOT = "root";
	public static final String FIELD_NUMBER_OF_ELEMENT = "numberOfElement";
	
	public static final String COLUMN_ROOT = "root";
	public static final String COLUMN_NUMBER_OF_ELEMENT = "numberOfElement";
}
