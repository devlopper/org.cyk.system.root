package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.Entity;
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

	@ManyToOne//(cascade=CascadeType.PERSIST)
	private NestedSetNode root;
	
	public NestedSet() {}

}
