package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {Credentials.FIELD_SOFTWARE,Credentials.FIELD_USERNAME})})
@EqualsAndHashCode(callSuper=false,of={Credentials.FIELD_SOFTWARE,Credentials.FIELD_USERNAME,Credentials.FIELD_PASSWORD})
//@ToString(of={Credentials.FIELD_SOFTWARE,Credentials.FIELD_USERNAME,Credentials.FIELD_PASSWORD})
public class Credentials extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;
	
	@ManyToOne @JoinColumn(name=COLUMN_SOFTWARE) @NotNull private Software software;
	
	@Column(name=COLUMN_USERNAME,nullable=false) @NotNull private String username;
	
	@Column(name=COLUMN_PASSWORD,nullable=false) @NotNull private String password;
	
	public Credentials(Software software, String username, String password) {
		super();
		this.software = software;
		this.username = username;
		this.password = password;
	}
	/*
	@Override
	public String toString() {
		return software+"/"+username;
	}
	*/
	/**/
	
	public static final String FIELD_SOFTWARE = "software";
	public static final String FIELD_USERNAME = "username";
	public static final String FIELD_PASSWORD = "password";

	public static final String COLUMN_SOFTWARE = "software";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASSWORD = "password";
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;

		protected StringSearchCriteria username = new StringSearchCriteria();
		
		public SearchCriteria(){ 
			this(null);
		}
		
		public SearchCriteria(String name) {
			super(name);
			username.setValue(name);
		}
		
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			super.set(stringSearchCriteria);
			username.set(stringSearchCriteria);
		}

	}

	
}
