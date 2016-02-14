package org.cyk.system.root.business.api.markuplanguage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.markuplanguage.MarkupLanguageDocument;
import org.cyk.system.root.model.markuplanguage.MarkupLanguageTag;


public interface MarkupLanguageBusiness {

	MarkupLanguageDocument build(String text,BuildArguments arguments);
	
	@Getter @Setter
	public static class BuildArguments implements Serializable{
		private static final long serialVersionUID = 1935935174208525017L;
		
	}
	
	MarkupLanguageTag findTag(String text,FindTagArguments arguments);
	
	@Getter @Setter
	public static class TagArguments implements Serializable{
		private static final long serialVersionUID = 6782959448219692933L;
		
		private String name,space;
		private Map<String,String> attributes = new HashMap<>();
		private Integer index;
		
		public TagArguments setAttributes(String...values){
			for(int i = 0; i< values.length ; i=i+2)
				attributes.put(values[i], values[i+1]);
			return this;
		}
	}
	
	@Getter @Setter
	public static class FindTagArguments extends AbstractTagArguments implements Serializable{
		private static final long serialVersionUID = 1935935174208525017L;
		
		private List<TagArguments> tagArguments = new ArrayList<>();
		
		public FindTagArguments addTag(String name,String space,Integer index,String...attributes){
			TagArguments tag = new TagArguments();
			tag.setName(name);
			tag.setSpace(space);
			tag.setIndex(index);
			tag.setAttributes(attributes);
			tagArguments.add(tag);
			return this;
		}
		
		public FindTagArguments addTag(String name,String space,String[] attributes){
			return addTag(name,space, null, attributes);
		}
		public FindTagArguments addTag(String name,Integer index,String[] attributes){
			return addTag(name, null,index, attributes);
		}
		public FindTagArguments addTag(String name,Integer index){
			return addTag(name, null,index, new String[]{});
		}
		public FindTagArguments addTag(String name,String[] attributes){
			return addTag(name, null,null, attributes);
		}
		public FindTagArguments addTag(String name){
			return addTag(name, null,null, new String[]{});
		}
	}
	
	String updateTag(String text,UpdateTagArguments arguments);
	
	@Getter @Setter
	public static class UpdateTagArguments extends AbstractTagArguments implements Serializable{
		private static final long serialVersionUID = 1935935174208525017L;
	
		private FindTagArguments findTagArguments = new FindTagArguments();
		
		private String text;
		
	}
	
	@Getter @Setter
	public static class AbstractTagArguments implements Serializable{
		private static final long serialVersionUID = 1935935174208525017L;
		
		protected Map<String,String> attributes = new HashMap<>();
		
		public AbstractTagArguments setAttributes(String...values){
			for(int i = 0; i< values.length ; i=i+2)
				attributes.put(values[i], values[i+1]);
			return this;
		}
	}
	
	/*
	 * 
	 * @Getter @Setter
	public static class AbstractTagArguments implements Serializable{
		private static final long serialVersionUID = 1935935174208525017L;
		
		protected Map<String,Map<String,String>> attributes = new HashMap<>();
		
		public AbstractTagArguments setAttributes(String...values){
			Map<String,String> map = attributes.get(values[0]);
			if(map==null)
				attributes.put(values[0], map = new HashMap<>());
			for(int i = 1; i< values.length ; i=i+2)
				map.put(values[i], values[i+1]);
			return this;
		}
	}
	 * 
	 * 
	 */
	
}
