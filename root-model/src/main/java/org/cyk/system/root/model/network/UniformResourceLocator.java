package org.cyk.system.root.model.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractEnumeration;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
public class UniformResourceLocator extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -4633680454658548588L;

	@Column(nullable=false) private String path;
	//private Boolean parametersRequired = Boolean.TRUE;
	
	@Transient private Collection<UniformResourceLocatorParameter> parameters = new ArrayList<>();

	public UniformResourceLocator() {
		super();
	}

	public UniformResourceLocator(String path) {
		super(path,path,null,null);
		this.path = path;
	}
	
	public void addParameter(String name,String value){
		parameters.add(new UniformResourceLocatorParameter(this, name, value));
	}
	public void addParameter(String name){
		addParameter(name, null);
	}
	
	@Override
	public String toString() {
		return path+" , "+parameters;
	}
	
	/**/
	
	public static final String FIELD_PATH = "path";
	public static final String FIELD_PARAMETERS = "parameters";
	
}
