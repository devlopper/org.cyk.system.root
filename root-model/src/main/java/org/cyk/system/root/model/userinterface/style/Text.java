package org.cyk.system.root.model.userinterface.style;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Text implements Serializable {

	private static final long serialVersionUID = 326984617334717322L;

	public enum HorizontalAlignment{LEFT,MIDDLE,RIGHT}
	public enum VerticalAlignment{TOP,MIDDLE,BOTTOM}
	
	private Color color = new Color();
	private Alignment alignment = new Alignment();
	
}
