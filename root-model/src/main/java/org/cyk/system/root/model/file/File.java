package org.cyk.system.root.model.file;

import java.io.Serializable;
import java.net.URI;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;


@Getter @Setter @Entity @NoArgsConstructor
public class File extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 129506142716551683L;
	
	/*
	 * Database storage
	 */
	
	private String extension;//in case we do not have the URI we need to know which kind of data we have
	
	@Lob @Basic(fetch=FetchType.LAZY)
    private byte[] bytes;//in case we need to point a file inside the database
	
	/*
	 * Outside storage
	 */
	
	private URI uri;//in case we need to point to a file outside the database
	
	

}
