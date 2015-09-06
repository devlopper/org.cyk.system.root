package org.cyk.system.root.model.html;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HtmlTag implements Serializable {
	
	private static final long serialVersionUID = -6056328070972266554L;

	private String name/*,body*/;
	private Map<String,String> attributes = new HashMap<>();
	

}
