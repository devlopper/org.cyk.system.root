package org.cyk.system.root.model.userinterface.style;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Font implements Serializable {

	private static final long serialVersionUID = 3136155998010403919L;
	
	private FontName name = FontName.VERDANA;
	private FontStyle style = FontStyle.BOLD;
	private Color color = new Color();
	private Integer size = 12;
	/**/
	
	public enum FontStyle{BOLD}
	
}
