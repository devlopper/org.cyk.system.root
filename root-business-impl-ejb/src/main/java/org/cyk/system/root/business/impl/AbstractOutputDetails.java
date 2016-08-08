package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractOutputDetails<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractModelElementOutputDetails<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 7439361240545541931L;

	@Getter @Setter protected String identifier;
	
	@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) protected File image;
	@Input @InputText protected String code,name,abbreviation,description;
	
	public AbstractOutputDetails(IDENTIFIABLE master) {
		super(master);
		this.master = master;
		if(this.master==null){
			
		}else if(this.master.getGlobalIdentifier()!=null) {
			image = this.master.getImage();
			code = this.master.getCode();
			name = this.master.getName();
			abbreviation = this.master.getAbbreviation();
			description = this.master.getDescription();
		}
	}
	
	/**/
	
	public static final String FIELD_IMAGE = "image";
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_ABBREVIATION = "abbreviation";
	public static final String FIELD_DESCRIPTION = "description";
	
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
