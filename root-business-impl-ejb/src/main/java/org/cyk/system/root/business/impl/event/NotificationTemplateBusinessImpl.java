package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Arrays;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.event.NotificationTemplateBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.cyk.utility.common.Constant;

public class NotificationTemplateBusinessImpl extends AbstractEnumerationBusinessImpl<NotificationTemplate, NotificationTemplateDao> implements NotificationTemplateBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public NotificationTemplateBusinessImpl(NotificationTemplateDao dao) {
		super(dao); 
	}   
	
	@Override
	protected NotificationTemplate __instanciateOne__(String[] values,org.cyk.system.root.business.api.TypedBusiness.InstanciateOneListener<NotificationTemplate> listener) {
		NotificationTemplate notificationTemplate = super.__instanciateOne__(values, listener);
        notificationTemplate.setTitle(inject(FileBusiness.class).process(values[10].getBytes(), notificationTemplate.getCode()
        		+Constant.CHARACTER_DOT+(StringUtils.contains(values[10], "<html>")?"html":"txt")));
        notificationTemplate.setMessage(inject(FileBusiness.class).process(values[11].getBytes(), notificationTemplate.getCode()
        		+Constant.CHARACTER_DOT+(StringUtils.contains(values[10], "<html>")?"html":"txt")));
		return notificationTemplate;
	}
	
	@Override
	protected void beforeCreate(NotificationTemplate notificationTemplate) {
		super.beforeCreate(notificationTemplate);
		createIfNotIdentified(notificationTemplate.getTitle());
		createIfNotIdentified(notificationTemplate.getMessage());
	}
	
	@Override
	protected void beforeDelete(NotificationTemplate notificationTemplate) {
		super.beforeDelete(notificationTemplate);
		inject(FileBusiness.class).delete(Arrays.asList(notificationTemplate.getTitle(),notificationTemplate.getMessage()));
	}
}
