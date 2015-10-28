package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.validation.Client;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.ENUMERATION)
public class EventMissedReason extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public static final String LATE = "late";
	public static final String DISEASE = "disease";
	
	@NotNull(groups=Client.class) @Column(nullable=false) private Boolean acceptable = Boolean.TRUE;
	
	public EventMissedReason(String code, String libelle, String description) {
		super(code, libelle,null, description);
	}
	
	
}
