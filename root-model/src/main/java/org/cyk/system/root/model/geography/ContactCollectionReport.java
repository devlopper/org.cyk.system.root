package org.cyk.system.root.model.geography;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.generator.RandomDataProvider;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContactCollectionReport extends AbstractIdentifiableReport<ContactCollectionReport> implements Serializable {

	private static final long serialVersionUID = -5092036698872705383L;

	private String phoneNumbers,emails,postalBoxs,locations,websites,all,separatror=Constant.CHARACTER_SLASH.toString();

	@Override
	public void generate() {
		locations = provider.randomWord(RandomDataProvider.WORD_LOCATION, 0, 1);
		phoneNumbers = provider.randomWord(RandomDataProvider.WORD_PHONE_NUMBER, 0, 1);
		postalBoxs = provider.randomWord(RandomDataProvider.WORD_POSTALBOX, 0, 1);
		emails = provider.randomWord(RandomDataProvider.WORD_EMAIL, 0, 1);
		websites = provider.randomWord(RandomDataProvider.WORD_WEBSITE, 0, 1);
	}
	
	public String getAll(){
		if(all==null){
			StringBuilder allBuilder = new StringBuilder();
			addAll(allBuilder,locations);
			addAll(allBuilder,phoneNumbers);
			addAll(allBuilder,postalBoxs);
			addAll(allBuilder,emails);
			addAll(allBuilder,websites);
			all = allBuilder.toString();
		}
		return all;
	}
	
	private void addAll(StringBuilder allBuilder,String value){
		if(StringUtils.isNotBlank(value)){
			if(!allBuilder.toString().isEmpty())
				allBuilder.append(separatror);
			allBuilder.append(value);
		}
	}
	
}
