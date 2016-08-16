package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.NotificationTemplateBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;

public class NotificationTemplateBusinessImpl extends AbstractEnumerationBusinessImpl<NotificationTemplate, NotificationTemplateDao> implements NotificationTemplateBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public NotificationTemplateBusinessImpl(NotificationTemplateDao dao) {
		super(dao); 
	}   
	
}
