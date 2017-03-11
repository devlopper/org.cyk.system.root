package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IdentifiableRuntimeCollection<T> implements Serializable {

	private static final long serialVersionUID = -130189077130420874L;

	private Collection<T> collection;
	private Boolean synchonizationEnabled;
	
	public Collection<T> getCollection(){
		if(collection==null)
			collection=new ArrayList<>();
		return collection;
	}
	
	public Boolean isSynchonizationEnabled(){
		return Boolean.TRUE.equals(synchonizationEnabled);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
