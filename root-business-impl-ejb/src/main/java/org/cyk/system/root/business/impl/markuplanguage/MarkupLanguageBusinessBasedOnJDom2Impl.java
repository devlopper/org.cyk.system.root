package org.cyk.system.root.business.impl.markuplanguage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.markuplanguage.MarkupLanguageBusiness;
import org.cyk.system.root.model.markuplanguage.MarkupLanguageDocument;
import org.cyk.utility.common.Constant;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class MarkupLanguageBusinessBasedOnJDom2Impl extends AbstractMarkupLanguageBusinessImpl<Namespace,SAXBuilder,Document,Element,Attribute> implements MarkupLanguageBusiness,Serializable {

	private static final long serialVersionUID = -1477124794923780532L;
	
	@Override
	protected SAXBuilder createBuilder() {
		return new SAXBuilder();
	}

	@Override
	protected Document createDocument(SAXBuilder builder,MarkupLanguageDocument markupLanguageDocument) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(markupLanguageDocument.getText().getBytes(Constant.ENCODING_UTF8));
		return builder.build(bais);
	}

	@Override
	protected Element getRoot(Document document) {
		return document.getRootElement();
	}

	@Override
	protected List<Element> getElementChildren(MarkupLanguageDocument markupLanguageDocument,Element element) {
		return element.getChildren();
	}
	
	@Override
	protected List<Element> getElementChildren(MarkupLanguageDocument markupLanguageDocument,Element element, String name,String space) {
		//System.out.println(element.getContent(0));
		//return element.getChildren();
		//System.out.println(Namespace.getNamespace(StringUtils.isBlank(space)?markupLanguageDocument.getNamespace():space));
		return element.getChildren(name,Namespace.getNamespace(StringUtils.isBlank(space)?"":""
				,StringUtils.isBlank(space)?markupLanguageDocument.getNamespace():space));
		//return element.getChildren(name,Namespace.NO_NAMESPACE);
	}

	@Override
	protected String getElementName(Element element) {
		return element.getName();
	}

	@Override
	protected String getElementText(Element element) {
		return element.getText();
	}

	@Override
	protected List<Attribute> getElementAttributes(Element element) {
		return element.getAttributes();
	}

	@Override
	protected String getAttributeName(Attribute attribute) {
		return attribute.getName();
	}

	@Override
	protected String getAttributeValue(Attribute attribute) {
		return attribute.getValue();
	}

	@Override
	protected String getDocumentAsString(Document document) throws IOException {
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		StringWriter writer = new StringWriter();
		xmlOutput.output(document, writer);
		return new String(writer.toString());
	}

	@Override
	protected void setElementText(Element element, String text) {
		element.setText(text);
	}

	@Override
	protected void setAttributeValue(Attribute attribute, String value) {
		attribute.setValue(value);
	}
	
}
