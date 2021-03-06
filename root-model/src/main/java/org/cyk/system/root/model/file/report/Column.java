package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.userinterface.style.Style;

@Getter @Setter @NoArgsConstructor
public class Column implements Serializable {

	private static final long serialVersionUID = -6465141536112290681L;

	private String fieldName;
	private Class<?> type;
	private String title,footer;
	private Integer width /*= 40*/;
	private Field field;
	
	private Style headerStyle = new Style();
	private Style detailStyle = new Style();
	private Style footerStyle = new Style();
	
	public Column(Field field, String title) {
		super();
		this.field = field;
		this.fieldName = field.getName();
		this.type = field.getType();
		this.title = title;
	}
	
}
