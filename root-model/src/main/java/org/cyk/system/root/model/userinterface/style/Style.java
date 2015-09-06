package org.cyk.system.root.model.userinterface.style;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Style implements Serializable {

	private static final long serialVersionUID = -8144099694492131629L;
	public enum Align{LEFT,MIDDLE,RIGHT}
	
	private Font font = new Font();
	private Border border = new Border();  
	private Background background = new Background();  
    private Text text = new Text();
    private Alignment alignment = new Alignment();
    private Padding padding = new Padding();       
	
}
