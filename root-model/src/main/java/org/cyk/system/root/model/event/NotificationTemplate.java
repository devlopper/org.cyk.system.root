package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.INTERNAL)
public class NotificationTemplate extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;
	
	public static final String ALARM_EMAIL = "ALARM_MAIL_NT";
	public static final String ALARM_SMS = "ALARM_SMS_NT";
	public static final String ALARM_USER_INTERFACE = "ALARM_USER_INTERFACE_NT";
	
	@OneToOne(cascade=CascadeType.ALL)
	private File title;
	
	@OneToOne(cascade=CascadeType.ALL)
	private File message;
	
	/**/
	
	@Transient private Map<String, Object> titleParametersMap = new HashMap<String, Object>();
	
	@Transient private Map<String,Object> messageParametersMap = new HashMap<String, Object>();

}
