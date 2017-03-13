package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;
import org.cyk.system.root.model.party.person.PersonReport;
import org.cyk.utility.common.generator.RandomDataProvider;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovementReport extends AbstractIdentifiableReport<MovementReport> implements Serializable {

	private static final long serialVersionUID = 2972654088041307426L;

	private String value,action,supportingDocumentIdentifier,supportingDocumentProvider;	
	private String valueInWords;
	private PersonReport senderOrReceiverPerson = new PersonReport();
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		_setValue(((Movement)source).getValue());
		senderOrReceiverPerson.setSource(((Movement)source).getSenderOrReceiverPerson());
	}
	
	private void  _setValue(Number number){
		this.value = format(number); 
		setValueInWords(formatNumberToWords(number));
	}
	
	@Override
	public void generate() {
		super.generate();
		_setValue(RandomDataProvider.getInstance().randomInt(5, 100));
		senderOrReceiverPerson.generate();
	}
	
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_VALUE_IN_WORDS = "valueInWords";
	public static final String FIELD_SUPPORTING_DOCUMENT_PROVIDER = "supportingDocumentProvider";
	public static final String FIELD_SUPPORTING_DOCUMENT_IDENTIFIER = "supportingDocumentIdentifier";
	public static final String FIELD_SENDER_OR_RECEIVER_PERSON = "senderOrReceiverPerson";
	
}
