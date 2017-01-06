package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity
@AllArgsConstructor @NoArgsConstructor 
@ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class Country extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;
	
	@OneToOne @JoinColumn(nullable=false) @NotNull 
	private Locality locality;
	
	@Column(nullable=false) @NotNull
	private Integer phoneNumberCode;
	
	@Transient private Locality continent;
	
	public Country(Locality locality, Integer phoneNumberCode) {
		super();
		this.locality = locality;
		setCode(locality.getCode());
		setName(locality.getName());
		this.phoneNumberCode = phoneNumberCode;
	}
	
	/**/
	
	@Override
	public String getUiString() {
		return /*"(+"+phoneNumberCode+") "+*/locality.getUiString();
	}
	
	

	public static final String FIELD_LOCALITY = "locality";
	public static final String FIELD_PHONE_NUMBER_CODE = "phoneNumberCode";
	
}
