package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractOutputDetails<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7439361240545541931L;

	protected RootBusinessLayer rootBusinessLayer = RootBusinessLayer.getInstance();
	protected NumberBusiness numberBusiness = rootBusinessLayer.getNumberBusiness();
	protected TimeBusiness timeBusiness = rootBusinessLayer.getTimeBusiness();
	
	@Getter @Setter protected String identifier;
	@Getter @Setter protected IDENTIFIABLE master;
	
	@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) protected File image;
	@Input @InputText protected String code,name;
	
	public AbstractOutputDetails(IDENTIFIABLE master) {
		super();
		this.master = master;
		if(this.master==null){
			
		}else if(this.master.getGlobalIdentifier()!=null) {
			image = this.master.getImage();
			code = this.master.getCode();
			name = this.master.getName();
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
		return rootBusinessLayer.getLanguageBusiness().findResponseText(value);
	}
	
	protected String formatUsingBusiness(Object object) {
		return rootBusinessLayer.getFormatterBusiness().format(object);
	}
	
	/**/
	
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_IMAGE = "image";
	
	public static final String IDENTIFIER_1 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_2 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_3 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_4 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_5 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_6 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_7 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_8 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_9 = RandomStringUtils.randomAlphanumeric(10);
}
