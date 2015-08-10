package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity
@AllArgsConstructor @NoArgsConstructor 
@ModelBean(crudStrategy=CrudStrategy.INTERNAL)
public class Country extends AbstractIdentifiable implements Serializable{

	private static final long serialVersionUID = 8675998527199168142L;
	
	public static final String COTE_DIVOIRE = "CIV";
	
	@OneToOne @JoinColumn(nullable=false) @NotNull 
	private Locality locality;
	
	@Column(nullable=false) @NotNull
	private Integer phoneNumberCode;
	
	/**/
	
	public static final String FIELD_LOCALITY = "locality";
	public static final String FIELD_ORDER_INDEX = "orderIndex";
	
}
