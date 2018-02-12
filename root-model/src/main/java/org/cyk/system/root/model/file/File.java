package org.cyk.system.root.model.file;

import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
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
	
	private String uri;//in case we need to point to a file outside the database
	
	/**
	 * Text representation of the bytes. This enable lookup into text
	 */
	private String text;
	
	/**
	 * Multipurpose Internet Mail Extension
	 */
	private String mime;
	
	@ManyToOne @JoinColumn(name=COLUMN_REPRESENTATION_TYPE) private FileRepresentationType representationType;
	
	/** Others GED informations **/
	
	private String generator;
	private String sender;
	private String contentWriter;
	
	/****/
	
	@Transient transient private InputStream inputStream;
	
	@Override
	public String toString() {
		return identifier==null?super.toString()
				:StringUtils.isNotBlank(getName()) ? getName() : ((uri==null?(extension+Constant.CHARACTER_LEFT_PARENTHESIS+mime+Constant.CHARACTER_RIGHT_PARENTHESIS):(uri.toString()))+"("+identifier+")");
	}
	
	public static final String FIELD_EXTENSION = "extension";
	public static final String FIELD_BYTES = "bytes";
	public static final String FIELD_URI = "uri";
	public static final String FIELD_TEXT = "text";
	public static final String FIELD_MIME = "mime";
	public static final String FIELD_REPRESENTATION_TYPE = "representationType";
	public static final String FIELD_GENERATOR = "generator";
	public static final String FIELD_SENDER = "sender";
	public static final String FIELD_CONTENT_WRITER = "contentWriter";
	
	public static final String COLUMN_REPRESENTATION_TYPE = FIELD_REPRESENTATION_TYPE;
}
