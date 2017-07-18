package org.cyk.system.root.model.globalidentification;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.math.NumberUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.Rud;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.LongSearchCriteria;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.system.root.model.search.StringSearchCriteria.LocationType;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;

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
	
	@Transient protected Processing processing;
	
	/**
	 * Common business informations
	 */
	
	@OneToOne private File image;
	@OneToOne private File supportingDocument;
	
	private String code;
	
	private String name;
	private String nameI18nId;
	
	private String abbreviation;
	private String abbreviationI18nId;
	
	private String description;
	private String descriptionI18nId;

	@Column(precision=COEFFICIENT_PRECISION,scale=FLOAT_SCALE) private BigDecimal weight;
	private Long orderNumber; //TODO remove all index properties declare in other class
	
	/**
	 * To capture non structured data
	 */
	private String otherDetails;
	
	@Embedded private Rud rud = new Rud();
	
	/**
	 * True if activated by business services , False otherwise
	 */
	private Boolean activated;
	
	/**
	 * True if usable in business services , False otherwise.
	 */
	private Boolean usable;
	
	/**
	 * True if it has finish its business process , False otherwise.
	 */
	private Boolean closed;
	
	/**
	 * True if is masculine concept , False otherwise.
	 */
	private Boolean male;
	
	/**
	 * True if object initialization match business rules , False otherwise.
	 */
	private Boolean initialized;
	
	/**
	 * True if object has been derived by business process, False otherwise.
	 */
	private Boolean derived;
	
	/**
	 * True if must be only update by application , False otherwise.
	 */
	@Column(name="f_constant") private Boolean constant;
	
	/**
	 * True if default value, False otherwise.
	 */
	private Boolean defaulted;
	
	@Embedded private Period existencePeriod = new Period();
	@OneToOne private Location birthLocation;
	@OneToOne private Location deathLocation;
	
	@ManyToOne private Party owner;
	
	@Embedded private CascadeStyleSheet cascadeStyleSheet;
	
	/**
	 * External system identifier. Used to link to another system
	 */
	@Column private String externalIdentifier;
	
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
	
	public CascadeStyleSheet getCascadeStyleSheet(){
		if(cascadeStyleSheet==null)
			cascadeStyleSheet = new CascadeStyleSheet();
		return cascadeStyleSheet;
	}
	
	public Processing getProcessing(){
		if(processing==null)
			processing = new Processing();
		return  processing;
	}
	
	@Override
	public String getUiString() {
		return toString();
	}

	/*@Override
	public String toString() {
		return String.format(LOG_FORMAT, StringUtils.isBlank(code)?identifier:code,creationDate==null?null:Constant.DATE_TIME_FORMATTER.format(creationDate)
				,createdBy==null?Constant.EMPTY_STRING:createdBy.getGlobalIdentifier().getCode(),rud==null ? null : rud.getUiString());
	}*/
	
	/**/
	
	public static final String LOG_FORMAT = "GID(%s,%s,%s,%s)";
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_ABBREVIATION = "abbreviation";
	public static final String FIELD_WEIGHT = "weight";
	public static final String FIELD_EXISTENCE_PERIOD = "existencePeriod";
	public static final String FIELD_BIRTH_LOCATION = "birthLocation";
	public static final String FIELD_DEATH_LOCATION = "deathLocation";
	public static final String FIELD_ORDER_NUMBER = "orderNumber";
	public static final String FIELD_OTHER_DETAILS = "otherDetails";
	public static final String FIELD_IMAGE = "image";
	public static final String FIELD_DEFAULTED = "defaulted";
	public static final String FIELD_SUPPORTING_DOCUMENT = "supportingDocument";
	public static final String FIELD_DERIVED = "derived";
	public static final String FIELD_USABLE = "usable";
	public static final String FIELD_DESCRIPTION = "description";
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected StringSearchCriteria code=new StringSearchCriteria(),name=new StringSearchCriteria();
		protected LongSearchCriteria orderNumber = new LongSearchCriteria();
		
		public SearchCriteria(){
			code.setLocationType(LocationType.INSIDE);
			criterias.add(code);
			name.setLocationType(LocationType.INSIDE);
			criterias.add(name);
			
			criterias.add(orderNumber);
		}
		
		public void set(String value){
			code.setValue(value);
			name.setValue(value);
			if(NumberUtils.isNumber(value))
				orderNumber.setValue(NumberUtils.createLong(value));
		}
		
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			code.set(stringSearchCriteria);
			name.set(stringSearchCriteria);
			if(NumberUtils.isNumber(stringSearchCriteria.getValue()))
				orderNumber.setValue(NumberUtils.createLong(stringSearchCriteria.getValue()));
		}
		
		@Override
		public String toString() {
			return "code : <<"+code+">> , name : <<"+name+">> , "+readConfig;
		}
	}
	
	/**/
	
	public static final Collection<Class<?>> EXCLUDED = new ArrayList<>();
	
	/**/
	
	@Getter @Setter
	/**
	 * Informations about client processing
	 * @author Christian Yao Komenan
	 *
	 */
	public static class Processing implements Serializable {
		private static final long serialVersionUID = -6123968511493504593L;
		
		private String identifier;
		private Party party;
		private Date date;
	}
}
