package org.cyk.system.root.business.impl.markuplanguage;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.markuplanguage.MarkupLanguageBusiness;
import org.cyk.system.root.model.markuplanguage.MarkupLanguageDocument;
import org.cyk.system.root.model.markuplanguage.MarkupLanguageTag;


public abstract class AbstractMarkupLanguageBusinessImpl<NAMESPACE,BUILDER,DOCUMENT,ELEMENT,ATTRIBUTE> implements MarkupLanguageBusiness,Serializable {

	private static final long serialVersionUID = -1477124794923780532L;

	@Override
	public MarkupLanguageDocument build(String text, BuildArguments arguments) {
		MarkupLanguageDocument markupLanguageDocument = new MarkupLanguageDocument();
		markupLanguageDocument.setText(text);
		markupLanguageDocument.setNamespace(StringUtils.substringBetween(text, "xmlns=\"", "\"")); //xmlns="http://jboss.org/schema/arquillian"
		return markupLanguageDocument;
	}
	
	protected ELEMENT getElement(MarkupLanguageDocument markupLanguageDocument,ELEMENT element,List<TagArguments> tagArgumentsList){
		for(int i = 0; i<tagArgumentsList.size(); i++){
			TagArguments tagArguments = tagArgumentsList.get(i);
			List<ELEMENT> children = getElementChildren(markupLanguageDocument,element,tagArguments.getName(),tagArguments.getSpace());
			List<ELEMENT> matches = new ArrayList<>();
			for(int j = 0; j< children.size(); j++){
				ELEMENT child = children.get(j);
				Boolean match = Boolean.TRUE;
				
				if(tagArguments.getAttributes()!=null){
					for(Entry<String, String> entry : tagArguments.getAttributes().entrySet()){
						for(ATTRIBUTE attribute : getElementAttributes(child))
							if(entry.getKey().equals(getAttributeName(attribute)) ){
								if( (match = entry.getValue().equals(getAttributeValue(attribute))) == Boolean.FALSE )
									break;
							}
						if(Boolean.FALSE.equals(match))
							break;
					}
				}
				
				if(Boolean.TRUE.equals(match))
					matches.add(child);
			}
			
			ELEMENT found = null;
			if(tagArguments.getIndex()==null){
				if(matches.size()==1)
					found = matches.get(0);
				else
					throw new RuntimeException("Only one child expected but "+matches.size()+" found");
			}else {
				found = matches.get(tagArguments.getIndex().intValue());
			}
			
			if(found==null)
				throw new RuntimeException("No match found");
			else
				if(i == tagArgumentsList.size()-1)
					return found;
				else
					element = found;
		}
		throw new RuntimeException("No match found");
	}
	
	@Override
	public MarkupLanguageTag findTag(String text, FindTagArguments arguments) {
		try {
			BUILDER builder = createBuilder();
			
			BuildArguments buildArguments = new BuildArguments();
			MarkupLanguageDocument markupLanguageDocument = build(text, buildArguments);
			
			DOCUMENT document = createDocument(builder,markupLanguageDocument);
			ELEMENT element = getElement(markupLanguageDocument, getRoot(document), arguments.getTagArguments());
			MarkupLanguageTag tag = new MarkupLanguageTag();
			tag.setName(getElementName(element));
			tag.setText(getElementText(element));
			for(ATTRIBUTE attribute : getElementAttributes(element))
				tag.getAttributes().put(getAttributeName(attribute), getAttributeValue(attribute));
			return tag;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String updateTag(String text,UpdateTagArguments updateTagArguments) {
		try {
			BUILDER builder = createBuilder();
			 
			BuildArguments buildArguments = new BuildArguments();
			MarkupLanguageDocument markupLanguageDocument = build(text, buildArguments);
			
			DOCUMENT document = createDocument(builder,markupLanguageDocument);
			ELEMENT element = getElement(markupLanguageDocument, getRoot(document), updateTagArguments.getFindTagArguments().getTagArguments());
			
			tagToUpdateFound(element);
			
			for(Entry<String, String> entry : updateTagArguments.getAttributes().entrySet())
				for(ATTRIBUTE attribute : getElementAttributes(element))
					if(entry.getKey().equals(getAttributeName(attribute)))
						setAttributeValue(attribute, entry.getValue());
				
			if(updateTagArguments.getText()!=null)
				setElementText(element, updateTagArguments.getText());
			
			return getDocumentAsString(document);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	protected void tagToUpdateFound(ELEMENT element) {}

	protected abstract BUILDER createBuilder();
	protected abstract DOCUMENT createDocument(BUILDER builder,MarkupLanguageDocument markupLanguageDocument) throws Exception;
	protected abstract String getDocumentAsString(DOCUMENT document) throws IOException;
	protected abstract ELEMENT getRoot(DOCUMENT document);
	protected abstract List<ELEMENT> getElementChildren(MarkupLanguageDocument markupLanguageDocument,ELEMENT element);
	protected abstract List<ELEMENT> getElementChildren(MarkupLanguageDocument markupLanguageDocument,ELEMENT element,String name,String space);
	
	protected abstract String getElementName(ELEMENT element);
	protected abstract String getElementText(ELEMENT element);
	protected abstract void setElementText(ELEMENT element,String text);
	protected abstract List<ATTRIBUTE> getElementAttributes(ELEMENT element);
	
	protected abstract String getAttributeName(ATTRIBUTE attribute);
	protected abstract String getAttributeValue(ATTRIBUTE attribute);
	protected abstract void setAttributeValue(ATTRIBUTE attribute,String value);

	/*
	protected static void readXmlNode(String file,Namespace namespace,String[][] valuePaths){
		File arquillianFile = testSourceFile(file);
		SAXBuilder builder = new SAXBuilder();
		Document document;
		try {
			document = builder.build(arquillianFile);
			Element element = null;
			for(String[] valuePath : valuePaths){
				element = document.getRootElement();
				for(int i=0;i<valuePath.length-1;i++)
					element = element.getChild(valuePath[i],namespace);	
				valuePath[valuePath.length-1] = element.getText();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	

}
