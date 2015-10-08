package org.cyk.system.root.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractFormatter<T> extends AbstractBean implements Serializable{

	private static final long serialVersionUID = 3566948449829233213L;
	
	private ContentType defaultContentType = ContentType.TEXT;
	
	public AbstractFormatter(ContentType defaultContentType) {
		super();
		this.defaultContentType = defaultContentType;
	}
	
	public abstract String format(T object,ContentType contentType);
	
	public String format(T object){
		return format(object, defaultContentType);
	}
	
	/**/
	
	protected static final String TABLE_FORMAT = "<table class='%2$s'>%1$s</table>";
	protected static final String TR_FORMAT = "<tr class='%2$s'>%1$s</tr>";
	protected static final String TD_FORMAT = "<td class='%2$s'>%1$s</td>";
	
	protected void appendHtmlTag(StringBuilder stringBuilder,String tagFormat,String body,Object...arguments){
		stringBuilder.append(htmlTag(tagFormat, body, arguments));
	}
	
	protected String htmlTag(String tagFormat,String body,Object...arguments){
		Object[] parguments = new Object[1+(arguments==null?0:arguments.length)];
		parguments[0] = body;
		for(int i = 0; i < arguments.length; i++)
			parguments[i+1] = arguments[i];
		return String.format(tagFormat, parguments);
	}
	
	protected String htmlTag(String tagFormat,StringBuilder body,Object...arguments){
		return htmlTag(tagFormat, body.toString(), arguments);
	}

}