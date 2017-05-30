package org.cyk.system.root.model.file.report;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.FileReport;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifierReport;
import org.cyk.system.root.model.time.PeriodReport;
import org.cyk.utility.common.generator.AbstractGeneratable;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractIdentifiableReport<MODEL> extends AbstractGeneratable<MODEL> implements Serializable {
	  
	private static final long serialVersionUID = 5632592320990657808L;

	protected GlobalIdentifierReport globalIdentifier = new GlobalIdentifierReport();
	protected List<LabelValueCollectionReport> labelValueCollections = new ArrayList<>();//TODO should be replaced by table in super class
	
	public void setSource(Object source){
		super.setSource(source);
		if(source!=null){
			globalIdentifier.setSource(((AbstractIdentifiable)source).getGlobalIdentifier());
		}
	}
	
	@Override
	public void generate() {
		globalIdentifier.generate();
		globalIdentifier.setName(getFieldRandomValue(String.class, commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_NAME)));
		
		text = RandomStringUtils.randomAlphanumeric(20);
	}

	public String getBirthLocation() {
		return globalIdentifier.getBirthLocation();
	}

	public String getCode() {
		return globalIdentifier.getCode();
	}

	public String getCreatedBy() {
		return globalIdentifier.getCreatedBy();
	}

	public String getCreationDate() {
		return globalIdentifier.getCreationDate();
	}

	public String getDeathLocation() {
		return globalIdentifier.getDeathLocation();
	}

	public PeriodReport getExistencePeriod() {
		return globalIdentifier.getExistencePeriod();
	}

	public Boolean getGenerateImage() {
		return globalIdentifier.getGenerateImage();
	}

	public InputStream getImage() {
		return globalIdentifier.getImage();
	}

	public String getName() {
		return globalIdentifier.getName();
	}

	public String getOtherDetails() {
		return globalIdentifier.getOtherDetails();
	}

	public String getBirthDate() {
		return globalIdentifier.getBirthDate();
	}

	public String getDeathDate() {
		return globalIdentifier.getDeathDate();
	}
	
	public FileReport getSupportingDocument() {
		return globalIdentifier.getSupportingDocument();
	}
	
	public String getFromDateToDate(){
		return globalIdentifier.getFromDateToDate();
	}
	
	public String getFromYearToYear(){
		return globalIdentifier.getFromYearToYear();
	}
	
	public void setFromDateToDate(String fromDateToDate){
		globalIdentifier.getExistencePeriod().setFromDateToDate(fromDateToDate);
	}
	
	
	
}
