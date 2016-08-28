package org.cyk.system.root.model.file;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.ENUMERATION,genderType=GenderType.FEMALE)
public class FileRepresentationType extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public static final String IDENTITY_IMAGE = "IDENTITY_IMAGE";
	public static final String POINT_OF_SALE = "POINT_OF_SALE";
	
	public FileRepresentationType(String code, String libelle, String description) {
		super(code, libelle,null, description);
	}
	
	
}
