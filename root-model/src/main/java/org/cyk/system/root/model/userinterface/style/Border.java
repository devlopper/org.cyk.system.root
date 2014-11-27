package org.cyk.system.root.model.userinterface.style;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Border implements Serializable {

	private static final long serialVersionUID = 3136155998010403919L;

	public enum Style{SOLID,DOTTED}
	
	private Style style = Style.SOLID;
	private Color color = new Color();
	private Integer size = 1;
}
