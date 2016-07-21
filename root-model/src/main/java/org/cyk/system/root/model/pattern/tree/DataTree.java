package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name=AbstractDataTree.FIELD_TYPE,type=DataTreeType.class)
@ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class DataTree extends AbstractDataTree<DataTreeType> implements Serializable  {

	private static final long serialVersionUID = -6128937819261060725L;

	public DataTree(AbstractDataTree<DataTreeType> parent, DataTreeType type, String code,String name) {
		super(parent, type, code);
		this.name = name;
	}
	
	public DataTree(AbstractDataTree<DataTreeType> parent, DataTreeType type, String code) {
		super(parent,type,code);
	}
}
