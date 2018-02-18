package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity 
@ModelBean(crudStrategy=CrudStrategy.ENUMERATION,genderType=GenderType.FEMALE)
@FieldOverrides(value={
		@FieldOverride(name=AbstractDataTree.FIELD_TYPE,type=LocalityType.class)
		,@FieldOverride(name=AbstractDataTreeType.FIELD___PARENT__,type=Locality.class)
})
public class Locality extends AbstractDataTree<LocalityType> implements Serializable  {

	private static final long serialVersionUID = -6128937819261060725L;
	
	private String residentName;
	
	@Embedded private GlobalPosition globalPosition;
	
	public Locality(Locality parent, LocalityType type, String code,String name) {
		super(parent, type, code);
		setName(name);
	}
	
	public Locality(Locality parent, LocalityType type, String code) {
		super(parent,type,code);
	}
	
	@Override
	public Locality setCode(String code) {
		return (Locality) super.setCode(code);
	}
	
	@Override
	public Locality set__parent__(AbstractIdentifiable parent) {
		return (Locality) super.set__parent__(parent);
	}
	
	public GlobalPosition getGlobalPosition(){
		if(globalPosition==null)
			globalPosition = new GlobalPosition();
		return globalPosition;
	}
	
	public static final String FIELD_RESIDENT_NAME = "residentName";
	public static final String FIELD_GLOBAL_POSITION = "globalPosition";
}
