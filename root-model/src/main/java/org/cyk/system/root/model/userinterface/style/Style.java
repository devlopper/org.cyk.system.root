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
    private Text text = new Text();
    private Align horizontal = Align.LEFT;
    private Align vertical = Align.MIDDLE;
    private Padding padding = new Padding();       
	
}
