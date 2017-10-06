package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;

import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class AbstractModelElementOutputDetails<MODEL_ELEMENT extends AbstractModelElement> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7439361240545541931L;

	protected Class<MODEL_ELEMENT> clazz;
	protected RootBusinessLayer rootBusinessLayer = RootBusinessLayer.getInstance();
	protected NumberBusiness numberBusiness = inject(NumberBusiness.class);
	protected TimeBusiness timeBusiness = inject(TimeBusiness.class);
	
	@Getter protected MODEL_ELEMENT master;
	
	@Getter @Setter @Input @InputText protected String text;
	
	public AbstractModelElementOutputDetails(MODEL_ELEMENT master) {
		super();
		//clazz = getClassParameter();
		setMaster(master);
	}
	
	@SuppressWarnings("unchecked")
	public Class<MODEL_ELEMENT> getClazz(){
		if(clazz==null){
			clazz = getClassParameter();
			if(clazz==null){
				if(master!=null)
					clazz = (Class<MODEL_ELEMENT>) master.getClass();
			}
		}
		return clazz;
	}
	
	@SuppressWarnings("unchecked")
	protected Class<MODEL_ELEMENT> getClassParameter(){
		return (Class<MODEL_ELEMENT>)  ClassHelper.getInstance().getParameterAt(getClass(), 0,AbstractModelElement.class);
	}
	
	public void setMaster(MODEL_ELEMENT master){
		this.master = master;
		if(this.master==null){
			
		}else{
			text = this.master.getUiString();
		}
	}
	
	protected String formatNumber(Number number) {
		return numberBusiness.format(number);
	}
	protected String formatDate(Date date) {
		return timeBusiness.formatDate(date);
	}
	protected String formatDateTime(Date date) {
		return timeBusiness.formatDateTime(date);
	}
	
	protected String formatResponse(Boolean value){
		return  inject(LanguageBusiness.class).findResponseText(value);
	}
	
	protected String formatUsingBusiness(Object object) {
		return rootBusinessLayer.getFormatterBusiness().format(object);
	}
	
	protected FieldValue createFieldValue(Object object){
		if(object==null)
			return null;
		return new FieldValue(object);
	}
	
	/**/
	@Getter @Setter
	public static class FieldValue implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Object object;
		private String value;
		private String url;
		
		private void init(String value,String url) {
			this.value = value;
			this.url = url;
		}
		
		public FieldValue(String value,String url) {
			init(value, url);
		}
		
		public FieldValue(String value) {
			init(value,null);
		}
		
		public FieldValue(Object object) {
			init(inject(FormatterBusiness.class).format(object),new UniformResourceLocatorHelper.Stringifier.Adapter.Default()
				.addQueryParameterAction(Constant.Action.CONSULT).addQueryParameterIdentifiable(object).execute());
		}
		
		@Override
		public String toString() {
			return value;
		}
	}
	
	public static final String FIELD_TEXT = "text";
	
}
