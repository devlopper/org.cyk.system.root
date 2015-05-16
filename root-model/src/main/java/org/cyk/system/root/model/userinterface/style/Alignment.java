package org.cyk.system.root.model.userinterface.style;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Alignment implements Serializable {

	private static final long serialVersionUID = 326984617334717322L;

	public enum HorizontalAlignment{LEFT,MIDDLE,RIGHT,JUSTIFY}
	public enum VerticalAlignment{TOP,MIDDLE,BOTTOM,JUSTIFY}
	
	private HorizontalAlignment horizontal = HorizontalAlignment.MIDDLE;
	private VerticalAlignment vertical = VerticalAlignment.MIDDLE;
	
}
