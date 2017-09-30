package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity
@AllArgsConstructor @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class PostalBox extends Contact implements Serializable {

	private static final long serialVersionUID = 923076998880521464L;
	
	@NotNull @Column(unique=true,nullable=false) private String value;


	@Override
	public String toString() {
		return value;
	}
	
	public static final String FIELD_VALUE = "value";
}
