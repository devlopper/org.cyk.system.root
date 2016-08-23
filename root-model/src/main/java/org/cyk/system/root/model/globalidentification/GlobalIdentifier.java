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

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.Rud;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.system.root.model.search.StringSearchCriteria.LocationType;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.Constant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
	
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true) private File image;
	
	private String code;
	
	private String name;
	private String nameI18nId;
	
	private String abbreviation;
	private String abbreviationI18nId;
	
	private String description;
	private String descriptionI18nId;
	
	/**
	 * To capture non structured data
	 */
	private String otherDetails;
	
	@Embedded private Rud rud = new Rud();
	
	private Boolean usable;
	@Column(name="f_constant",nullable=false,updatable=false)
	private Boolean constant = Boolean.FALSE;
	
	@Embedded private Period existencePeriod = new Period();
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true) private Location birthLocation;
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true) private Location deathLocation;
	
	@ManyToOne private Party owner;
	
	//private String htmlStyle;//TODO how to use it???
	
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
	public static final String FIELD_BIRTH_LOCATION = "birthLocation";
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected StringSearchCriteria code=new StringSearchCriteria(),name=new StringSearchCriteria();
		
		public SearchCriteria(){
			code.setLocationType(LocationType.INSIDE);
			name.setLocationType(LocationType.INSIDE);
		}
		
	}
}
