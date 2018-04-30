package org.cyk.system.root.model.metadata;

import java.io.Serializable;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @javax.persistence.Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE) @Accessors(chain=true)
@Table(name=Property.TABLE_NAME)
public class Property extends AbstractEnumeration implements Serializable  {
	private static final long serialVersionUID = -4876159772208660975L;

	private @NotNull @Size(min=1) String path;
	
	/**/
	
	public static final String FIELD_PATH = "path";
	
	/**/
	
	public static final String TABLE_NAME = "tproperty";
	
}

