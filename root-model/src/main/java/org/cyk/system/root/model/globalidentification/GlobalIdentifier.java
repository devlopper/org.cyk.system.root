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
import javax.persistence.JoinColumn;
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
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputEditor;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CriteriaHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @EqualsAndHashCode(callSuper=false,of="identifier")
public class GlobalIdentifier extends AbstractModelElement implements Identifiable<String>, Serializable {
	private static final long serialVersionUID = -8743545996393946779L;

	static {
		ClassHelper.IDENTIFIABLE_BASE_CLASSES.add(GlobalIdentifier.class);
		ClassHelper.IDENTIFIABLE_BASE_CLASSES.add(AbstractIdentifiable.class);
	}
	
	/**
	 * System informations
	 */
	
	@Id private String identifier;
	//private String identifierOfOwner;
	//private Boolean deleteWhenOwnerIsDeleted;
	
	@Transient private AbstractIdentifiable identifiable;
	@Column @Temporal(TemporalType.TIMESTAMP) private Date creationDate;
	@ManyToOne @JoinColumn(name=COLUMN_CREATED_BY) private Party createdBy;
	
	/**
	 * The one in charge of
	 */
	@ManyToOne @JoinColumn(name=COLUMN_OWNER) private Party owner;
	
	@Transient protected Processing processing;
	
	/**
	 * Common business informations
	 */
	
	@ManyToOne @JoinColumn(name=COLUMN_IMAGE) private File image; //an image can be shared for better space management
	@ManyToOne @JoinColumn(name=COLUMN_SUPPORTING_DOCUMENT) private File supportingDocument; //a file can be shared for better space management
	
	private String code;
	
	private String name;
	private String nameI18nId;
	
	private String abbreviation;
	private String abbreviationI18nId;
	
	@Column(length=1024 * 1)
	private String description;
	private String descriptionI18nId;

	@Column(precision=COEFFICIENT_PRECISION,scale=FLOAT_SCALE) private BigDecimal weight;
	private Long orderNumber; //TODO remove all index properties declare in other class
	
	/**
	 * To capture non structured data
	 */
	@Column(length=1024 * 8)
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
	 * True if object is required by business process, False otherwise.
	 */
	private Boolean required;
	
	/**
	 * True if must be only update by application , False otherwise.
	 */
	@Column(name="f_constant") private Boolean constant;
	
	/**
	 * True if default value, False otherwise.
	 */
	private Boolean defaulted;
	
	@Embedded private Period existencePeriod = new Period();
	@OneToOne @JoinColumn(name=COLUMN_BIRTH_LOCATION) private Location birthLocation;
	@OneToOne @JoinColumn(name=COLUMN_DEATH_LOCATION) private Location deathLocation;
	
	
	
	@Embedded private CascadeStyleSheet cascadeStyleSheet;
	
	/**
	 * External system identifier. Used to link to another system
	 */
	@Column private String externalIdentifier;
	
	/**
	 * instance which creation has created this instance
	 */
	private @JoinColumn(name=COLUMN_SOURCE) GlobalIdentifier source;
	/**
	 * instance created by the creation of this instance
	 */
	private @JoinColumn(name=COLUMN_DESTINATION) GlobalIdentifier destination;
	
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
	
	@Override
	public String toString() {
		return identifier;
	}

	/*@Override
	public String toString() {
		return String.format(LOG_FORMAT, StringUtils.isBlank(code)?identifier:code,creationDate==null?null:Constant.DATE_TIME_FORMATTER.format(creationDate)
				,createdBy==null?Constant.EMPTY_STRING:createdBy.getGlobalIdentifier().getCode(),rud==null ? null : rud.getUiString());
	}*/
	
	/**/
	
	public static final String LOG_FORMAT = "GID(%s,%s,%s,%s)";
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_CREATION_DATE = "creationDate";
	public static final String FIELD_CREATED_BY = "createdBy";
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
	public static final String FIELD_ACTIVATED = "activated";
	public static final String FIELD_CLOSED = "closed";
	public static final String FIELD_USABLE = "usable";
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_EXTERNAL_IDENTIFIER = "externalIdentifier";
	public static final String FIELD_REQUIRED = "required";
	public static final String FIELD_RUD = "rud";
	public static final String FIELD_MALE = "male";
	public static final String FIELD_INITIALIZED = "initialized";
	public static final String FIELD_CONSTANT = "constant";
	public static final String FIELD_OWNER = "owner";
	public static final String FIELD_CASCADE_STYLE_SHEET = "cascadeStyleSheet";
	public static final String FIELD_SOURCE = "source";
	public static final String FIELD_DESTINATION = "destination";
	
	public static final String COLUMN_CREATED_BY = "createdby";
	public static final String COLUMN_OWNER = "owner";
	public static final String COLUMN_IMAGE = "image";
	public static final String COLUMN_SUPPORTING_DOCUMENT = "supportingdocument";
	public static final String COLUMN_BIRTH_LOCATION = "birthlocation";
	public static final String COLUMN_DEATH_LOCATION = "deathlocation";
	public static final String COLUMN_SOURCE = FIELD_SOURCE;
	public static final String COLUMN_DESTINATION = FIELD_DESTINATION;
	
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
			if(NumberHelper.getInstance().isNumber(value))
				orderNumber.setValue(NumberUtils.createLong(value));
		}
		
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			code.set(stringSearchCriteria);
			name.set(stringSearchCriteria);
			if(NumberHelper.getInstance().isNumber(stringSearchCriteria.getValue()))
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
	public static class Filter extends FilterHelper.Filter<GlobalIdentifier> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;

		protected CriteriaHelper.Criteria.String code,name;
		protected CriteriaHelper.Criteria.Number.Long orderNumber;
		protected CriteriaHelper.Criteria.Boolean closed;
		
		public Filter(){
			code = instanciateCriteria(CriteriaHelper.Criteria.String.class).setLocation(StringHelper.Location.INSIDE);
			name = instanciateCriteria(CriteriaHelper.Criteria.String.class).setLocation(StringHelper.Location.INSIDE);
			closed = instanciateCriteria(CriteriaHelper.Criteria.Boolean.class);
			//orderNumber = instanciateCriteria(CriteriaHelper.Criteria.Number.Long.class);
		}
				
		public Filter(Filter criterias) {
			super(criterias);
		}

		@Override
		public String toString() {
			return "code = "+code+" , name = "+name+" , orderNumber = "+orderNumber;
		}
	}
	
	/**/
	
	public static class Inputs extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Input @InputText private String code;
		@Input @InputText private String name;
		@Input @InputText private String abbreviation;
		@Input @InputNumber private BigDecimal weight;
		@Input @InputNumber private Long orderNumber;
		@Input @InputEditor private String otherDetails;
		@Input @InputFile private FileHelper.File image;
		@Input @InputBooleanButton private Boolean defaulted;
		@Input @InputBooleanButton private Boolean derived;
		@Input @InputBooleanButton private Boolean usable;
		@Input @InputTextarea private String description;
		@Input @InputText private String externalIdentifier;
		@Input @InputBooleanButton private Boolean required;
		
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
		public static final String FIELD_EXTERNAL_IDENTIFIER = "externalIdentifier";
		public static final String FIELD_REQUIRED = "required";
	}
	
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
	
	/**/
	
	public Collection<GlobalIdentifier> get(Collection<AbstractIdentifiable> identifiables){
		return MethodHelper.getInstance().callGet(identifiables, GlobalIdentifier.class, AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER);
	}
}
