package org.cyk.system.root.business.api.chart;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PieModel extends ProportionModel implements Serializable {

	private static final long serialVersionUID = 5062440475941734842L;
 
	private Integer diameter; 
		
	public PieModel(String title){
		super(title);
	}
	
}
