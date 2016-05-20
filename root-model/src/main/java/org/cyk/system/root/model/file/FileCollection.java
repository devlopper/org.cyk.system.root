package org.cyk.system.root.model.file;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter @Entity  @NoArgsConstructor
public class FileCollection extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	
}