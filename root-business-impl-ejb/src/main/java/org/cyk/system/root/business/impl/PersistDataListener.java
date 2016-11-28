package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.BeanAdapter;

public interface PersistDataListener {

	Collection<PersistDataListener> COLLECTION = new ArrayList<>();
	
	/**/
	
	<T> T processPropertyValue(Class<?> instanceClass,String instanceCode,String name,T value);
	
	<T extends AbstractIdentifiable> T processIdentifiable(T identifiable);
	
	/**/
	
	public static class Adapter extends BeanAdapter implements PersistDataListener,Serializable{

		private static final long serialVersionUID = 9097732902832939276L;

		/**/
		
		@Override
		public <T> T processPropertyValue(Class<?> instanceClass,String instanceCode,String name,T value) {
			return null;
		}
		
		@Override
		public <T extends AbstractIdentifiable> T processIdentifiable(T identifiable) {
			return null;
		}
		
		/**/
		
		@SuppressWarnings("unchecked")
		public static <T> T process(final Class<?> instanceClass,final String instanceCode,final String name,final T value){
			return ListenerUtils.getInstance().getValue((Class<T>) instanceClass,PersistDataListener.COLLECTION, new ListenerUtils.ResultMethod<PersistDataListener,T>() {

				@Override
				public T execute(PersistDataListener listener) {
					return listener.processPropertyValue(instanceClass,instanceCode,name,value);
				}

				@Override
				public T getNullValue() {
					return value;
				}
			});
		}
		
		@SuppressWarnings("unchecked")
		public static <T extends AbstractIdentifiable> T process(final T identifiable){
			return ListenerUtils.getInstance().getValue((Class<T>) identifiable.getClass(),PersistDataListener.COLLECTION, new ListenerUtils.ResultMethod<PersistDataListener,T>() {

				@Override
				public T execute(PersistDataListener listener) {
					return listener.processIdentifiable(identifiable);
				}

				@Override
				public T getNullValue() {
					return identifiable;
				}
			});
		}
		
		/**/
		
		public static class Default extends Adapter implements Serializable {

			private static final long serialVersionUID = -7147026540295720232L;

			@Override
			public <T> T processPropertyValue(Class<?> aClass,String instanceCode, String name,T value) {
				return value;
			}
			
			@Override
			public <T extends AbstractIdentifiable> T processIdentifiable(T identifiable) {
				return identifiable;
			}
		}
	}
	
	/**/
	
	public static final String RELATIVE_PATH = "RELATIVE_PATH";
	public static final String BASE_PACKAGE = "BASE_PACKAGE";
	
}
