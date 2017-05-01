package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.hibernate.validator.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @AllArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class ElectronicMail extends Contact implements Serializable {

	private static final long serialVersionUID = 923076998880521464L;

	@Email @NotNull @Column(unique=true,nullable=false)
	private String address;
	
	public ElectronicMail() {}

	public ElectronicMail(ContactCollection collection,String address) {
		super(collection,null);
		this.address = address;
	}
		
	@Override
	public String toString() {
		return address;
	}
	
	@Override
	public String getUiString() {
		return address;
	}
	
	public static final String FIELD_ADDRESS = "address";
	
	/**/
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractFieldValueSearchCriteriaSet.AbstractIdentifiableSearchCriteriaSet implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		protected StringSearchCriteria address = new StringSearchCriteria();
		
		public SearchCriteria(){ 
			this(null);
		}
		
		public SearchCriteria(String name) {
			super(name);
		}
		
		@Override
		public void set(StringSearchCriteria stringSearchCriteria) {
			super.set(stringSearchCriteria);
			address.set(stringSearchCriteria);
		}
		
		@Override
		public void set(String value) {
			super.set(value);
			address.setValue(value);
		}
		
	}
}
