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
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.Rud;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.Constant;

@Getter @Setter @Entity @EqualsAndHashCode(callSuper=false,of="identifier")
public class GlobalIdentifier extends AbstractModelElement implements Identifiable<String>, Serializable {

	private static final long serialVersionUID = -8743545996393946779L;

	/**
	 * System informations
	 */
	
	@Id private String identifier;
	
	@Transient private AbstractIdentifiable identifiable;
	
	@Column @Temporal(TemporalType.TIMESTAMP) private Date creationDate;
	
	@ManyToOne private Party createdBy;
	
	/**
	 * Common business informations
	 */
	
	private String code;
	
	private String name;
	
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true) private File image;
	
	@Embedded private Rud rud = new Rud();
	
	@Embedded private Period existencePeriod = new Period();
	
	public GlobalIdentifier() {}
	
	public GlobalIdentifier(AbstractIdentifiable identifiable) {
		this.identifiable = identifiable;
	}
	
	public Rud getRud(){
		if(rud==null)
			rud = new Rud();
		return rud;
	}
	
	public Period getExistencePeriod(){
		if(existencePeriod==null)
			existencePeriod = new Period();
		return existencePeriod;
	}
	
	@Override
	public String getUiString() {
		return toString();
	}

	@Override
	public String toString() {
		return String.format(LOG_FORMAT, identifier,creationDate==null?null:Constant.DATE_TIME_FORMATTER.format(creationDate)
				,createdBy==null?Constant.EMPTY_STRING:createdBy.getGlobalIdentifier().getCode(),rud==null ? null : rud.getUiString());
	}
	
	/**/
	
	public static final String LOG_FORMAT = "GID(%s,%s,%s,%s)";
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_EXISTENCE_PERIOD = "existencePeriod";
}