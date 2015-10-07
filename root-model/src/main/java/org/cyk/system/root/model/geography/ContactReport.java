package org.cyk.system.root.model.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.generator.RandomDataProvider;

@Getter @Setter
public class ContactReport extends AbstractGeneratable<ContactReport> implements Serializable {

	private static final long serialVersionUID = -5092036698872705383L;

	private String phoneNumbers,emails,postalBoxs,locations,websites,all;

	@Override
	public void generate() {
		StringBuilder allBuilder = new StringBuilder();
		if(StringUtils.isNotBlank(locations = provider.randomWord(RandomDataProvider.WORD_LOCATION, 0, 1)))
			allBuilder.append(locations);
		if(StringUtils.isNotBlank(phoneNumbers = provider.randomWord(RandomDataProvider.WORD_PHONE_NUMBER, 0, 1)))
			allBuilder.append(phoneNumbers);
		if(StringUtils.isNotBlank(postalBoxs = provider.randomWord(RandomDataProvider.WORD_POSTALBOX, 0, 1)))
			allBuilder.append(postalBoxs);
		if(StringUtils.isNotBlank(emails = provider.randomWord(RandomDataProvider.WORD_EMAIL, 0, 1)))
			allBuilder.append(emails);
		if(StringUtils.isNotBlank(websites = provider.randomWord(RandomDataProvider.WORD_WEBSITE, 0, 1)))
			allBuilder.append(websites);
		
		all = allBuilder.toString();
	}
	
}
