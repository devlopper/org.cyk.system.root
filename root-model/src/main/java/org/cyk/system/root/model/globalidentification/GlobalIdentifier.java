package org.cyk.system.root.model.globalidentification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.Rud;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.Constant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @EqualsAndHashCode(callSuper=false,of="identifier")
public class GlobalIdentifier extends AbstractModelElement implements Identifiable<String>, Serializable {

	private static final long serialVersionUID = -8743545996393946779L;

	@Id private String identifier;
	
	@Column @Temporal(TemporalType.TIMESTAMP) private Date creationDate;
	
	@ManyToOne private Party createdBy;
	
	/**
	 * Business code
	 */
	private String code;
	
	/**
	 * Business name
	 */
	private String name;
	
	/**
	 * Business image representation
	 */
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
    private File image;
	
	@Embedded private Rud rud = new Rud();
	
	public Rud getRud(){
		if(rud==null)
			rud = new Rud();
		return rud;
	}
	
	@Override
	public String getUiString() {
		return toString();
	}

	@Override
	public String toString() {
		//return String.format(LOG_FORMAT, identifier,creationDate==null?null:Constant.DATE_TIME_FORMATTER.format(creationDate)
		//		,createdBy==null?Constant.EMPTY_STRING:createdBy.getGlobalIdentifier().getCode(),rud==null ? null : rud.getUiString());

		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	/**/
	
	public static final String LOG_FORMAT = "GID(%s,%s,%s,%s)";
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
}
