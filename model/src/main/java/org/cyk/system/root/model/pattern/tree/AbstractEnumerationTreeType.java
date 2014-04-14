package org.cyk.system.root.model.pattern.tree;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractEnumerationTreeType extends AbstractEnumerationNode implements Serializable {

	private static final long serialVersionUID = 4388503557071277363L;
		
	public AbstractEnumerationTreeType(AbstractEnumerationTreeType parent,String code,String label) {
		super(parent,code,label);
	}
	
	public AbstractEnumerationTreeType(AbstractEnumerationTreeType parent,String code) {
		this(parent,code,code);
	}
	
}
