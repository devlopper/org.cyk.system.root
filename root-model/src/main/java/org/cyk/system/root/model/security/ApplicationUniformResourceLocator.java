package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //@Entity
public class ApplicationUniformResourceLocator extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 5164454356106670454L;

	@ManyToOne private UniformResourceLocator uniformResourceLocator;
	
}
