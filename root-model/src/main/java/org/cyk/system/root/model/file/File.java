package org.cyk.system.root.model.file;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;


@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.INTERNAL)
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
	
	/**
	 * Multipurpose Internet Mail Extension
	 */
	private String mime;
	
	/*
	 * File external (business) description / informations
	 */
	
	@OneToMany(cascade=CascadeType.ALL)
    private Collection<Tag> tags = new HashSet<>();

	/**
	 * User specific data for reading 
	 */
	private String description;
	
	@ManyToOne private FileCollection collection;
	
	private String groupIdentifier;
	
	@Override
	public String toString() {
		return identifier==null?super.toString():((uri==null?(extension):(uri.toString()))+"("+identifier+")");
	}
	
}
