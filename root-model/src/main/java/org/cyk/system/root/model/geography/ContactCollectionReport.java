package org.cyk.system.root.model.geography;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.helper.CollectionHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContactCollectionReport extends AbstractIdentifiableReport<ContactCollectionReport> implements Serializable {

	private static final long serialVersionUID = -5092036698872705383L;

	private String phoneNumbers,emails,postalBoxs,locations,websites,all,separatror=Constant.CHARACTER_SLASH.toString();

	@Override
	public void setSource(Object source) {
		super.setSource(source);
		if(source==null){
			
		}else{
			phoneNumbers = CollectionHelper.getInstance().concatenate(((ContactCollection)source).getItems().filter(PhoneNumber.class),Constant.CHARACTER_COMA);
			emails = CollectionHelper.getInstance().concatenate(((ContactCollection)source).getItems().filter(ElectronicMail.class),Constant.CHARACTER_COMA);
			postalBoxs = CollectionHelper.getInstance().concatenate(((ContactCollection)source).getItems().filter(PostalBox.class),Constant.CHARACTER_COMA);
			locations = CollectionHelper.getInstance().concatenate(((ContactCollection)source).getItems().filter(Location.class),Constant.CHARACTER_COMA);
			websites = CollectionHelper.getInstance().concatenate(((ContactCollection)source).getItems().filter(Website.class),Constant.CHARACTER_COMA);
		}
	}
	
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
