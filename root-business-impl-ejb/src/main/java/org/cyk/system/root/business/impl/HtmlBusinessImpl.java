package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Map.Entry;

import org.cyk.system.root.business.api.HtmlBusiness;
import org.cyk.system.root.model.html.HtmlTag;
import org.cyk.utility.common.cdi.AbstractBean;

public class HtmlBusinessImpl extends AbstractBean implements HtmlBusiness,Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7387144022390774064L;
	private static final String ATTRIBUTE_FORMAT = "%s=\"%s\"";
    private static final String FORMAT = "<%1$s %3$s>%2$s</%1$s>";
    
    @Override
    public String format(HtmlTag tag,String body) {
        StringBuilder attributesBuilder = new StringBuilder();
        for(Entry<String, String> attribute : tag.getAttributes().entrySet())
            attributesBuilder.append(String.format(ATTRIBUTE_FORMAT, attribute.getKey(),attribute.getValue()));
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(FORMAT, tag.getName(),body,attributesBuilder.toString()));
        return builder.toString();
    }

}
