package org.cyk.system.root.model.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.generator.RandomDataProvider;

@Getter @Setter
public class ContactReport extends AbstractGeneratable<ContactReport> implements Serializable {

	private static final long serialVersionUID = -5092036698872705383L;

	private String phoneNumbers,emails,postalBoxs,locations,websites;

	@Override
	public void generate() {
		emails = provider.randomWord(RandomDataProvider.WORD_EMAIL, 0, 1);
		locations = provider.randomWord(RandomDataProvider.WORD_LOCATION, 0, 1);
		phoneNumbers = provider.randomWord(RandomDataProvider.WORD_PHONE_NUMBER, 0, 1);
		postalBoxs = provider.randomWord(RandomDataProvider.WORD_POSTALBOX, 0, 1);
		locations = provider.randomWord(RandomDataProvider.WORD_WEBSITE, 0, 1);
	}
	
}
