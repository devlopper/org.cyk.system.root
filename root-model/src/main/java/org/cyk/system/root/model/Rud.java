package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.cyk.system.root.model.AbstractModelElement;

import lombok.Getter;
import lombok.Setter;

@Embeddable @Getter @Setter
public class Rud extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	@Column(name="rud_readable")
	private Boolean readable = Boolean.TRUE;
	
	@Column(name="rud_updatable")
	private Boolean updatable = Boolean.TRUE;
	
	@Column(name="rud_deletable")
	private Boolean deletable = Boolean.TRUE;
	
	@Override
	public String getUiString() {
		return " R="+readable+" , U="+updatable+" , D="+deletable;
	}
	
}
