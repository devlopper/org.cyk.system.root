package org.cyk.system.root.model.file.report;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.userinterface.style.Style;

@Getter @Setter @NoArgsConstructor
public class Column implements Serializable {

	private static final long serialVersionUID = -6465141536112290681L;

	private String fieldName;
	private Class<?> type;
	private String title;
	private Integer width = 40;
	
	private Style headerStyle = new Style();
	private Style detailStyle = new Style();
	
	public Column(String fieldName, Class<?> type, String title) {
		super();
		this.fieldName = fieldName;
		this.type = type;
		this.title = title;
	}
	
}
