package org.cyk.system.root.model.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE) @Accessors(chain=true)
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
	
	private String uniformResourceIdentifier;//in case we need to point to a file outside the database
	
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
	@Transient private Boolean getTextFromBytesAutomatically;
	
	public File setBytesFromInputStream(InputStream inputStream){
		try {
			setBytes(IOUtils.toByteArray(inputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public File setBytesFromString(String string){
		if(string!=null)
			setBytes(string.getBytes());
		return this;
	}
	
	public File setBytes(byte[] bytes){
		this.bytes = bytes;
		if(Boolean.TRUE.equals(getTextFromBytesAutomatically))
			setTextFromBytes();
		return this;
	}
	
	public File setTextFromBytes(){
		setText(FileHelper.getInstance().getText(this.bytes, Boolean.TRUE, Boolean.TRUE));
		return this;
	}
	
	@Override
	public String toString() {
		return identifier==null?super.toString()
				:StringUtils.isNotBlank(getName()) ? getName() : ((StringHelper.getInstance().isBlank(uniformResourceIdentifier)?(extension+Constant.CHARACTER_LEFT_PARENTHESIS+mime+Constant.CHARACTER_RIGHT_PARENTHESIS):(uniformResourceIdentifier))+"("+identifier+")");
	}
	
	public static final String FIELD_EXTENSION = "extension";
	public static final String FIELD_BYTES = "bytes";
	public static final String FIELD_UNIFORM_RESOURCE_IDENTIFIER = "uniformResourceIdentifier";
	public static final String FIELD_MIME = "mime";
	public static final String FIELD_REPRESENTATION_TYPE = "representationType";
	public static final String FIELD_GENERATOR = "generator";
	public static final String FIELD_SENDER = "sender";
	public static final String FIELD_CONTENT_WRITER = "contentWriter";
	
	public static final String COLUMN_REPRESENTATION_TYPE = FIELD_REPRESENTATION_TYPE;
}
