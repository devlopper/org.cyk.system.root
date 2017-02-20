package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;
import org.cyk.utility.common.generator.RandomDataProvider;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovementReport extends AbstractIdentifiableReport<MovementReport> implements Serializable {

	private static final long serialVersionUID = 2972654088041307426L;

	private String value;
	
	private String valueInWords;
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		_setValue(((Movement)source).getValue());
	}
	
	private void  _setValue(Number number){
		this.value = format(number); 
		setValueInWords(formatNumberToWords(number));
	}
	
	@Override
	public void generate() {
		super.generate();
		_setValue(RandomDataProvider.getInstance().randomInt(5, 100));
	}
	
}
