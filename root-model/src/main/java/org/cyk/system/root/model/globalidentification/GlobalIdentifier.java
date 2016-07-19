package org.cyk.system.root.model.globalidentification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.utility.common.Constant;

@Getter @Setter @Entity @EqualsAndHashCode(callSuper=false,of="identifier")
public class GlobalIdentifier extends AbstractModelElement implements Identifiable<String>, Serializable {

	private static final long serialVersionUID = -8743545996393946779L;

	@Id private String identifier;
	
	@Column @Temporal(TemporalType.TIMESTAMP)// @NotNull 
	//@Transient
	private Date creationDate;
	
	@ManyToOne /*@NotNull*/ 
	//@Transient
	private Party createdBy;
	
	@Column private Boolean readable;
	
	@Column private Boolean updatable;
	
	@Column private Boolean deletable;
	
	@Override
	public String getUiString() {
		return toString();
	}

	@Override
	public String toString() {
		return String.format(LOG_FORMAT, identifier,creationDate==null?null:Constant.DATE_TIME_FORMATTER.format(creationDate)
				,createdBy==null?Constant.EMPTY_STRING:createdBy.getCode(),readable,updatable,deletable);
	}
	
	/**/
	
	public static final String LOG_FORMAT = "GID(%s,%s,%s,%s,%s,%s)";
	
	public static final String FIELD_IDENTIFIER = "identifier";
}
