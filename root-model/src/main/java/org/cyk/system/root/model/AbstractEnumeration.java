package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.validation.Client;

/*lombok*/
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
/*mapping-jpa*/
@MappedSuperclass @ModelBean(crudStrategy=CrudStrategy.ENUMERATION)
public abstract class AbstractEnumeration  extends AbstractIdentifiable  implements Serializable {

	private static final long serialVersionUID = -8639942019354737162L;
	
	@UIField
	@Column(nullable=false,unique=true)
	@NotNull(groups=Client.class)
	protected String code;
	
	@UIField
	@Column(nullable=false)
	@NotNull(groups=Client.class)
	protected String name;
	
	@UIField
	protected String abbreviation;
	
	@UIField(textArea=true,tableColumnIgnore=true)
	@Column(length=10 * 1024)
	protected String description;
		
	@Override
	public String toString() {
		return name;
	}

	
}
