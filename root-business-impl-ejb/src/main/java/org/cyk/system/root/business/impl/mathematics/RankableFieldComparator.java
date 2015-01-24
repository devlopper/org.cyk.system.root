package org.cyk.system.root.business.impl.mathematics;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.mathematics.Rankable;

public class RankableFieldComparator<RANKABLE extends Rankable> implements Comparator<RANKABLE> {

	protected LinkedHashSet<String> fieldNames = new LinkedHashSet<>();
	
	public RankableFieldComparator(String...fieldNames) {
		if(fieldNames==null || fieldNames.length==0)
			throw new IllegalArgumentException("Number of field names must be greater than 0");
		this.fieldNames.addAll(Arrays.asList(fieldNames));
	}
	
	@Override
	public int compare(RANKABLE o1, RANKABLE o2) {
		for(String fieldName : fieldNames){
			Object v1 = readField(o1, fieldName);
			Object v2 = readField(o2, fieldName);
			if(v1==null)
				if(v2==null)
					continue;
				else
					return -1;
			else
				if(v2==null)
					return 1;
				else{
					@SuppressWarnings("unchecked")
					Integer c = ((Comparable<Object>)v1).compareTo(v2);
					if(c==0)
						continue;
					return c;
				}
		}
		return 0;
	}
	
	private Object readField(RANKABLE object,String fieldName){
		try {
			return FieldUtils.readField(object, fieldName, Boolean.TRUE);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}
