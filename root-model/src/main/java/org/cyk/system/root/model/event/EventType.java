package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.ENUMERATION)
public class EventType extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	public static final String ANNIVERSARY = "ANNIVERSARY";
	public static final String MEETING = "MEETING";
	public static final String APPOINTMENT = "APPOINTMENT";
	public static final String REMINDER = "REMINDER";
	
	public EventType(String code, String libelle, String description) {
		super(code, libelle,null, description);
	}
	
	
}
