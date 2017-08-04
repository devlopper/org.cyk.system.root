package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.FEMALE)
public class NotificationTemplate extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;
	
	@OneToOne private File title;
	
	@OneToOne private File message;
	
	/**/
	
	@Transient private Map<String, Object> titleParametersMap = new HashMap<String, Object>();
	
	@Transient private Map<String,Object> messageParametersMap = new HashMap<String, Object>();

	/**/
	
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_MESSAGE = "message";
}
