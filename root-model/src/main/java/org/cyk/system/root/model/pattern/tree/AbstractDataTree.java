package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public class AbstractDataTree<TYPE extends AbstractDataTreeType> extends AbstractDataTreeNode implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	@ManyToOne @NotNull
	protected TYPE type;

	public AbstractDataTree(AbstractDataTree<TYPE> parent,String code) {
		super(parent,code);
		setParent(parent);
	}
	
	public AbstractDataTree(AbstractDataTree<TYPE> parent,TYPE type,String code) {
		this(parent,code);
		this.type=type;
	}
	
	public AbstractDataTree<TYPE> setType(TYPE type){
		this.type = type;
		return this;
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
}
