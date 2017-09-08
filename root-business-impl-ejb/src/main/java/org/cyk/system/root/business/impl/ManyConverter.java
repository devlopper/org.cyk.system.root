package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.LogMessage.Builder;

@Deprecated
public class ManyConverter<T,R> extends org.cyk.utility.common.converter.ManyConverter.Adapter.Default<T,R> implements Serializable {

	private static final long serialVersionUID = 1L;

	public ManyConverter(Collection<T> instances, Class<R> resultClass, Builder logMessageBuilder) {
		super(instances, resultClass, logMessageBuilder);
	}
	@Deprecated
	public static class ConverterToArray<T,R> extends org.cyk.utility.common.converter.ManyConverter.ManyConverterToArray.Adapter.Default<T,R> implements Serializable {
		private static final long serialVersionUID = 1L;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public ConverterToArray(Collection<T> instances, Class<R> resultClass,OneConverter.ConverterToArray oneConverterToArray, Builder logMessageBuilder) {
			super(instances, resultClass, logMessageBuilder);
			if(String[][].class.equals(resultClass))
				setOneConverter(oneConverterToArray);
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public ConverterToArray(Collection<T> instances, Class<R> resultClass, Builder logMessageBuilder) {
			this(instances, resultClass,new OneConverter.ConverterToArray((instances==null || instances.isEmpty()) ? null : instances.iterator().next().getClass(),null, String[].class, logMessageBuilder)
					, logMessageBuilder);
		}
	
	}
	
}
