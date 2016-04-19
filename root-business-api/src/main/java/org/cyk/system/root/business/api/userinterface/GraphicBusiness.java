package org.cyk.system.root.business.api.userinterface;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

public interface GraphicBusiness {

	byte[] createFromText(String text,CreateFromTextOptions options);

	byte[] createFromText(String text);
	
	/**/
	
	@Getter @Setter
	public static class CreateFromTextOptions implements Serializable{

		private static final long serialVersionUID = 5907015660583077021L;
		
		private String type = "png";
		private Font font;
		private Color color = Color.BLACK;
		private Collection<CreateFromTextListener> listeners = new ArrayList<>();
		
		public CreateFromTextOptions() {
			listeners.add(new DefaultCreateFromText());
			font = new Font("Arial", Font.PLAIN, 48);
		}
	}
	
	public static interface CreateFromTextListener{
		
	}
	
	@Getter @Setter
	public static class CreateFromTextAdapter implements CreateFromTextListener,Serializable{

		private static final long serialVersionUID = 4756454698537546985L;
		
	}
	
	@Getter @Setter
	public static class DefaultCreateFromText implements CreateFromTextListener,Serializable{

		private static final long serialVersionUID = 4756454698537546985L;
		
	}
	
}
