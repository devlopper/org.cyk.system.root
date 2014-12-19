package org.cyk.system.root.model.file;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;


@Getter @Setter @Entity @NoArgsConstructor
public class Script extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 129506142716551683L;
	
	@OneToOne(cascade=CascadeType.ALL)
	private File file;
	
	private Set<String> variables = new HashSet<String>();

}
