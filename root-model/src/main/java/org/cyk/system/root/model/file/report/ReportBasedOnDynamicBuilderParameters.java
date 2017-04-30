package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ReportBasedOnDynamicBuilderParameters<MODEL> implements Serializable{

	private static final long serialVersionUID = -8188598097968268568L;
	
	private ReportBasedOnDynamicBuilder<?> report;
	private Class<? extends AbstractIdentifiable> identifiableClass;
	private Class<MODEL> /*clazz,*/modelClass;
	private Collection<MODEL> datas;
	private String title,subTitle,creationDate,createdBy,ownerNameImagePath;
	private Party owner;
	private String fileExtension = "pdf";
	private Boolean print;
	private Map<String,String[]> extendedParameterMap = new HashMap<>();
	private Set<String> columnNamesToExclude = new HashSet<>();
	private Collection<ReportBasedOnDynamicBuilderListener> reportBasedOnDynamicBuilderListeners = new ArrayList<>();
	
	public void addParameter(String name,Object value){
		if(value==null)
			return;
		String stringValue = null;
		if(value instanceof Date)
			stringValue = ((Date)value).getTime()+"";
		else
			stringValue = value.toString();
		extendedParameterMap.put(name, new String[]{stringValue});
	}
	
	@SuppressWarnings("unchecked")
	public <T> T parameterAs(Class<T> aClass,String name){
		if(extendedParameterMap.get(name)==null || extendedParameterMap.get(name).length==0)
			return null;
		String stringValue = extendedParameterMap.get(name)[0];
		if(StringUtils.isBlank(stringValue))
			return null;
		if(Date.class.isAssignableFrom(aClass))
			return (T) new Date(Long.parseLong(stringValue));
		else if(Long.class.isAssignableFrom(aClass))
			return (T) new Long(Long.parseLong(stringValue));
		return null;
	}
	
}