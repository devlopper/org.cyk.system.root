package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.pattern.tree.DataTreeType;

@Getter @Setter @NoArgsConstructor @Entity 
public class MenuItem extends DataTreeType implements Serializable  {

	private static final long serialVersionUID = -6838401709866343401L;

	@ManyToOne private UniformResourceLocator uniformResourceLocator;
	
	public MenuItem(DataTreeType parent, String code,String label) {
		super(parent, code,label);
	}

	
	
}
