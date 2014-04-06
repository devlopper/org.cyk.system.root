package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.cyk.utility.common.validation.Client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*lombok*/
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
/*mapping-jpa*/
@MappedSuperclass
public abstract class AbstractEnumeration  extends AbstractIdentifiable  implements Serializable {

	private static final long serialVersionUID = -8639942019354737162L;
	
	@Column(nullable=false,unique=true)
	@NotNull(groups=Client.class)
	protected String code;
	
	@Column(nullable=false)
	@NotNull(groups=Client.class)
	protected String libelle;
	
	protected String abbreviation;
	
	@Column(length=10 * 1024)
	protected String description;
		
	@Override
	public String toString() {
		return libelle;
	}

	
}
