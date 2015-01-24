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
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanCheck;
import org.cyk.utility.common.validation.Client;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.ENUMERATION)
public class EventMissedReason extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@Input
	@InputBooleanCheck
	@NotNull(groups=Client.class)
	@Column(nullable=false)
	private Boolean acceptable;
	
	public EventMissedReason(String code, String libelle, String description) {
		super(code, libelle,null, description);
	}
	
	
}
