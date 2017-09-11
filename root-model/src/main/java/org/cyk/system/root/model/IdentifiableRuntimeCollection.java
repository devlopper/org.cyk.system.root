package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class IdentifiableRuntimeCollection<T> implements Serializable {

	private static final long serialVersionUID = -130189077130420874L;

	private Collection<T> collection;
	private Boolean synchonizationEnabled;
	private Boolean isOrderNumberComputeEnabled;
	private Collection<String> fieldNames;
	
	public IdentifiableRuntimeCollection<T> addOne(T item){
		getCollection().add(item);
		return this;
	}
	
	public IdentifiableRuntimeCollection<T> addMany(Collection<T> items){
		getCollection().addAll(items);
		return this;
	}
	
	public Collection<T> getCollection(){
		if(collection==null)
			collection=new ArrayList<>();
		return collection;
	}
	
	public Collection<String> getFieldNames(){
		if(fieldNames==null)
			fieldNames=new LinkedHashSet<>();
		return fieldNames;
	}
	
	public IdentifiableRuntimeCollection<T> addOneFieldName(String fieldName){
		getFieldNames().add(fieldName);
		return this;
	}
	
	public IdentifiableRuntimeCollection<T> addManyFieldName(Collection<String> fieldNames){
		getFieldNames().addAll(fieldNames);
		return this;
	}
	
	public IdentifiableRuntimeCollection<T> addManyFieldName(String...fieldNames){
		return addManyFieldName(Arrays.asList(fieldNames));
	}
	
	public Boolean isSynchonizationEnabled(){
		return Boolean.TRUE.equals(synchonizationEnabled);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
