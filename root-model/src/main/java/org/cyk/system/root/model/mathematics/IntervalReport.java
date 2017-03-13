package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IntervalReport extends AbstractIdentifiableReport<IntervalReport> implements Serializable {

	private static final long serialVersionUID = -3815250939177148339L;
	
	private String value;
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		if(source==null){
			
		}else{
			this.value = format(((Interval)source).getValue());
		}
	}
	
	@Override
	public void generate() {
		super.generate();
		value = provider.randomInt(1000, 900000)+"";
	}
	
}
