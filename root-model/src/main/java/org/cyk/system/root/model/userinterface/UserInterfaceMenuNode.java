package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name=UserInterfaceMenuNode.FIELD_TYPE,type=UserInterfaceMenuNodeType.class)
@ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.ENUMERATION)
public class UserInterfaceMenuNode extends AbstractDataTree<UserInterfaceMenuNodeType> implements Serializable  {

	private static final long serialVersionUID = -6128937819261060725L;

	@ManyToOne private UserInterfaceCommand command;
	
	@Transient private Boolean automaticallyCreateCommand;
	
	public UserInterfaceMenuNode(UserInterfaceMenuNode parent, UserInterfaceMenuNodeType type, String code,String name) {
		super(parent, type, code);
		setName(name);
	}
	
	public UserInterfaceMenuNode(UserInterfaceMenuNode parent, UserInterfaceMenuNodeType type, String code) {
		super(parent,type,code);
	}
	
	public static final String FIELD_COMMAND = "command";
}
