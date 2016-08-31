package org.cyk.system.root.model.globalidentification;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.model.time.PeriodReport;
import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.generator.RandomDataProvider;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GlobalIdentifierReport extends AbstractGeneratable<GlobalIdentifierReport> implements Serializable {

	private static final long serialVersionUID = 5692082425509998915L;
	
	private String identifier,creationDate,createdBy,code,name,otherDetails,birthLocation,deathLocation,owner;
	private PeriodReport existencePeriod = new PeriodReport();
	private InputStream image;
	private Boolean generateImage=Boolean.FALSE;
	
	
	@Override
	public void generate() {
		existencePeriod.generate();
		birthLocation = provider.randomWord(RandomDataProvider.WORD_LOCATION, 10, 20);
		identifier = RandomStringUtils.randomAlphanumeric(6);
		code = RandomStringUtils.randomAlphanumeric(6);
		name = RandomStringUtils.randomAlphanumeric(6);
		otherDetails = provider.randomWord(RandomDataProvider.WORD_LOCATION, 10, 20);
	}
	
}
