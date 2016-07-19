package org.cyk.system.root.model.party;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.validation.Client;

@Getter @Setter //@ModelBean(crudStrategy=CrudStrategy.ENUMERATION)
@Entity @NoArgsConstructor @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) 
public class Party extends AbstractIdentifiable  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Business code
	 */
	@NotNull(groups=org.cyk.utility.common.validation.System.class)
	@Column(nullable=false,unique=true)
	protected String code;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@NotNull(groups={org.cyk.utility.common.validation.System.class})
	protected Date creationDate;
	
	@NotNull(groups=Client.class)
	@Column(nullable=false)
	protected String name;
	 
	/**
	 * This is an image which visually represent this party
	 */
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
    protected File image;
	
	@Temporal(TemporalType.DATE) protected Date birthDate;
	
	@OneToOne protected ContactCollection contactCollection;
	
	public Party(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String getUiString() {
		return name;
	}

	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_BIRTH_DATE = "birthDate";
	public static final String FIELD_IMAGE = "image";
	
	/**/
	
	@Getter @Setter
	public static class PartySearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected StringSearchCriteria name = new StringSearchCriteria();
		
		public PartySearchCriteria(){
			this(null);
		}
		
		public PartySearchCriteria(String name) {
			setStringSearchCriteria(this.name, name);
			
		}
		
	}

	
}