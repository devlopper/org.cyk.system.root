package org.cyk.system.root.business.api.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Series implements Serializable {

	private static final long serialVersionUID = 8380472434376469139L;

	private Long identifier;
	
	private String label;
	
	private Collection<SeriesItem> items = new ArrayList<>();

	public Series(String label) {
		super();
		this.label = label;
	}	
	
	@Override
	public String toString() {
		return label+" , "+items;
	}

}
