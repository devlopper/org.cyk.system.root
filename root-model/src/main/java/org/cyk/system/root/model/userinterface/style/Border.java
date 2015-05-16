package org.cyk.system.root.model.userinterface.style;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Border implements Serializable {

	private static final long serialVersionUID = 3136155998010403919L;

	private Side left,top,right,bottom,all;
	
	/**/
	
	@Getter @Setter
	public static class Side implements Serializable {

		private static final long serialVersionUID = 3136155998010403919L;

		public enum Style{NONE,SOLID,DOTTED}
		
		private Style style = Style.NONE;
		private Color color = new Color();
		private Integer size = 1;
	}

}
