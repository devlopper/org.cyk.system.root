package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractEnumeration;

import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class DurationType extends AbstractEnumeration implements Serializable{
	private static final long serialVersionUID = 374208919427476791L;
	
}

