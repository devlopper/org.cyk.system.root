package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;

import org.cyk.utility.common.AbstractBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class EntityGraphBuilder<T> extends AbstractBuilder<EntityGraph<T>> implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager entityManager;
	private Class<T> resultClass;
	private Set<String> attributes;

	public EntityGraphBuilder<T> setAttributes(String...attributes){
		if(attributes!=null){
			if(this.attributes==null)
				this.attributes = new HashSet<>();	
			for(String attribute : attributes)
				this.attributes.add(attribute);
		}
		return this;
	}
	
	@Override
	public EntityGraph<T> build() {
		EntityGraph<T> entityGraph = entityManager.createEntityGraph(resultClass);
		entityGraph.addAttributeNodes(attributes.toArray(new String[]{}));
		
		return entityGraph;
	}

}
