package org.cyk.system.root.model.network;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor //@Entity
public class UniformResourceLocatorParameter extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 5164454356106670454L;

	@ManyToOne private UniformResourceLocator uniformResourceLocator;
	
	@Column(nullable=false) @NotNull private String name;
	
	private String value;
	
	public UniformResourceLocatorParameter(UniformResourceLocator uniformResourceLocator, String name, String value) {
		super();
		this.uniformResourceLocator = uniformResourceLocator;
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return name+"="+value;
	}
	
	/**/

	public static final String FIELD_NAME = "name";
	public static final String FIELD_VALUE = "value";
}
