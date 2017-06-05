package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.time.PeriodDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter 
public abstract class AbstractOutputDetails<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractModelElementOutputDetails<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 7439361240545541931L;

	protected String identifier;
	
	@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) protected File image;
	@Input @InputText protected String code,name,abbreviation,description,weight,orderNumber,otherDetails,activated,defaulted;
	@IncludeInputs(layout=Layout.VERTICAL) protected PeriodDetails existencePeriod;
	//@IncludeInputs(layout=Layout.VERTICAL) protected FileDetails supportingDocument;
	
	/**
	 * The following fields are used to dynamically extend
	 */
	@Input @InputText protected String 
		__f00__,__f01__,__f02__,__f03__,__f04__,__f05__,__f06__,__f07__,__f08__,__f09__,__f10__,__f11__,__f12__,__f13__,__f14__,__f15__,
		__f16__,__f17__,__f18__,__f19__,__f20__,__f21__,__f22__,__f23__,__f24__,__f25__,__f26__,__f27__,__f28__,__f29__,__f30__,__f31__;
	
	public AbstractOutputDetails(IDENTIFIABLE master) {
		super(master);
	}
	
	public void setMaster(IDENTIFIABLE master){
		super.setMaster(master);
		if(this.master==null){
			
		}else if(this.master.getGlobalIdentifier()!=null) {
			image = this.master.getImage();
			code = this.master.getCode();
			name = this.master.getName();
			abbreviation = this.master.getAbbreviation();
			description = this.master.getDescription();
			getExistencePeriod().set(this.master.getExistencePeriod());
			weight = formatNumber(this.master.getWeight());
			orderNumber = formatNumber(this.master.getOrderNumber());
			otherDetails = this.master.getOtherDetails();
			activated = formatResponse(this.master.getGlobalIdentifier().getActivated());
			defaulted = formatResponse(this.master.getGlobalIdentifier().getDefaulted());
			//getSupportingDocument().setMaster(this.master.getSupportingDocument());
		}
	}
	
	public PeriodDetails getExistencePeriod(){
		if(existencePeriod == null)
			existencePeriod = new PeriodDetails();
		return existencePeriod;
	}
	
	/*public FileDetails getSupportingDocument(){
		if(supportingDocument == null)
			supportingDocument = new FileDetails();
		return supportingDocument;
	}*/
	
	/**/
	
	public static final String FIELD_IMAGE = "image";
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_ABBREVIATION = "abbreviation";
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_EXISTENCE_PERIOD = "existencePeriod";
	public static final String FIELD_WEIGHT = "weight";
	public static final String FIELD_DEFAULTED = "defaulted";
	public static final String FIELD_ORDER_NUMBER = "orderNumber";
	public static final String FIELD_OTHER_DETAILS = "otherDetails";
	
	public static String[] getFieldNames(Boolean isBusinessIdentified,Boolean hasImage,Boolean hasExistenceStart,Boolean hasExistenceEnd){
		Set<String> fields = new HashSet<>();
		if(Boolean.TRUE.equals(isBusinessIdentified))
			fields.addAll(Arrays.asList(FIELD_CODE,FIELD_NAME));
		if(Boolean.TRUE.equals(hasImage))
			fields.addAll(Arrays.asList(FIELD_IMAGE));
		if(Boolean.TRUE.equals(hasExistenceStart))
			fields.addAll(Arrays.asList(FIELD_EXISTENCE_PERIOD,PeriodDetails.FIELD_FROM_DATE));
		if(Boolean.TRUE.equals(hasExistenceEnd))
			fields.addAll(Arrays.asList(FIELD_EXISTENCE_PERIOD,PeriodDetails.FIELD_TO_DATE));
		return fields.toArray(new String[]{});
	}
	
	public static final String IDENTIFIER_1 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_2 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_3 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_4 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_5 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_6 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_7 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_8 = RandomStringUtils.randomAlphanumeric(10);
	public static final String IDENTIFIER_9 = RandomStringUtils.randomAlphanumeric(10);
	
	private static final String __F__PREFIX = "__f";
	private static final String __F__SUFFIX = "__";
	
	public static Boolean isExtendedFieldName(String name){
		return StringUtils.isNotBlank(StringUtils.substringBetween(name,__F__PREFIX, __F__SUFFIX));
	}
	
	protected static String getExtendedFieldName(Integer index){
		return __F__PREFIX+index+__F__SUFFIX;
	}
	
	public static Integer getExtendedFieldNameIndex(String name){
		return Integer.valueOf(StringUtils.substringBetween(name, __F__PREFIX, __F__SUFFIX));
	}
}
