package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor 
public abstract class AbstractDataTreeType extends AbstractDataTreeNode implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
	
	public AbstractDataTreeType(AbstractDataTreeType parent,String code,String label) {
		super(parent,code,label);
	}
	
	public AbstractDataTreeType(AbstractDataTreeType parent,String code) {
		this(parent,code,code);
	}
	
	public static class Filter<T extends AbstractDataTreeType> extends AbstractDataTreeNode.Filter<T> implements Serializable{
		private static final long serialVersionUID = 1L;
    	
		
		
    }
}
