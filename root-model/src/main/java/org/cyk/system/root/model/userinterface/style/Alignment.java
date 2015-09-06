package org.cyk.system.root.model.userinterface.style;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.style.Alignment.Horizontal;
import org.cyk.utility.common.annotation.user.interfaces.style.Alignment.Vertical;

@Getter @Setter
public class Alignment implements Serializable {

	private static final long serialVersionUID = 326984617334717322L;

	//public enum HorizontalAlignment{LEFT,MIDDLE,RIGHT,JUSTIFY}
	//public enum VerticalAlignment{TOP,MIDDLE,BOTTOM,JUSTIFY}
	
	private Horizontal horizontal = Horizontal.MIDDLE;
	private Vertical vertical = Vertical.MIDDLE;
	
}
