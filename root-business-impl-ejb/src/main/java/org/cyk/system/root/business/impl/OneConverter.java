package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.LogMessage.Builder;

@Deprecated
public class OneConverter<T,R> extends org.cyk.utility.common.converter.OneConverter.Adapter.Default<T,R> implements Serializable {

	private static final long serialVersionUID = 1L;

	public OneConverter(Class<T> instanceClass,T instance, Class<R> resultClass, Builder logMessageBuilder) {
		super(instanceClass,instance, resultClass, logMessageBuilder);
	}
	@Deprecated
	public static class ConverterToArray<T,R> extends org.cyk.utility.common.converter.OneConverter.OneConverterToArray.Adapter.Default<T,R> implements Serializable {
		private static final long serialVersionUID = 1L;

		public ConverterToArray(Class<T> instanceClass,T instance, Class<R> resultClass, Builder logMessageBuilder) {
			super(instanceClass,instance, resultClass, logMessageBuilder);
		}
	
		@Override
		public Object getValueAt(Integer index) {
			if(index==0)
				return instance instanceof AbstractIdentifiable ? ((AbstractIdentifiable)instance).getName() : super.getValueAt(index);
			if(index==1)
				inject(FormatterBusiness.class).format(instance);
			return super.getValueAt(index);
		}
		
	}
	
}
