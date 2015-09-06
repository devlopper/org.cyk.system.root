package org.cyk.system.root.business.api.mathematics;

import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public class ValueComparator<T> extends AbstractBean implements Comparator<T>{

	private static final long serialVersionUID = -3725697838258934790L;

	private ValueReader<T> valueReader;
	private Boolean ascending;
	
	public ValueComparator(ValueReader<T> valueReader,Boolean ascending) {
		super();
		this.valueReader = valueReader;
		this.ascending = ascending;
	}
	
	public ValueComparator(ValueReader<T> valueReader) {
		this(valueReader,Boolean.TRUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compare(T t1, T t2) {
		Comparable<Object> v1 = (Comparable<Object>) valueReader.read(t1,0),v2 = (Comparable<Object>) valueReader.read(t2,0);
		int result = 0;
		if(v1==null)
			if(v2==null)
				result = 0;
			else
				result = -1;
		else
			if(v2==null)
				result = 1;
			else
				result = v1.compareTo(v2);
		
		if(ascending!=null && Boolean.FALSE.equals(ascending))
			result = result * -1;
		
		return result;
	}
	
	/**/
	
	public static interface ValueReader<T>{	
		Object read(T entity,Integer level);
	}
		
}