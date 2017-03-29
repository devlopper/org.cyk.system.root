package org.cyk.system.root.model.globalidentification;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.model.file.FileReport;
import org.cyk.system.root.model.time.PeriodReport;
import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.generator.RandomDataProvider;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GlobalIdentifierReport extends AbstractGeneratable<GlobalIdentifierReport> implements Serializable {

	private static final long serialVersionUID = 5692082425509998915L;
	
	private String identifier,creationDate,createdBy,code,name,otherDetails,birthLocation,deathLocation,owner,weight;
	private PeriodReport existencePeriod = new PeriodReport();
	private FileReport supportingDocument = new FileReport();
	private InputStream image;
	private Boolean generateImage=Boolean.FALSE;
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		identifier = ((GlobalIdentifier)source).getIdentifier();
		code = ((GlobalIdentifier)source).getCode();
		name = ((GlobalIdentifier)source).getName();
		if(((GlobalIdentifier)source).getBirthLocation()!=null){
			birthLocation = ((GlobalIdentifier)source).getBirthLocation().getUiString();
		}
		otherDetails = ((GlobalIdentifier)source).getOtherDetails();
		weight = format(((GlobalIdentifier)source).getWeight());
		creationDate = ((GlobalIdentifier)source).getCreationDate().toString();
		existencePeriod.setSource(((GlobalIdentifier)source).getExistencePeriod());
		supportingDocument.setSource(((GlobalIdentifier)source).getSupportingDocument());
	}
	
	@Override
	public void generate() {
		existencePeriod.generate();
		birthLocation = provider.randomWord(RandomDataProvider.WORD_LOCATION, 10, 20);
		identifier = RandomStringUtils.randomAlphanumeric(6);
		code = RandomStringUtils.randomAlphanumeric(6);
		name = RandomStringUtils.randomAlphanumeric(6);
		weight = RandomStringUtils.randomNumeric(2);
		otherDetails = provider.randomWord(RandomDataProvider.WORD_LOCATION, 10, 20);
		supportingDocument.generate();
	}


	public String getBirthDate() {
		return existencePeriod.getFromDate();
	}


	public String getDeathDate() {
		return existencePeriod.getToDate();
	}
	
	public String getFromDateToDate() {
		return existencePeriod.getFromDateToDate();
	}
	
	
}
