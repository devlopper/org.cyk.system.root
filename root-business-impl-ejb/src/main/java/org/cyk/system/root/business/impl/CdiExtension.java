package org.cyk.system.root.business.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.event.ExecutedMethodBusinessImpl;
import org.cyk.system.root.business.impl.globalidentification.GlobalIdentifierBusinessImpl;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;

public class CdiExtension implements Extension {

	public <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> processAnnotatedType) {

		AnnotatedType<T> annotatedType = processAnnotatedType.getAnnotatedType();

		if(StringUtils.startsWith(annotatedType.getJavaClass().getName(), "org.cyk.") && StringUtils.contains(annotatedType.getJavaClass().getName(), ".business.impl")
				&&  annotatedType.getJavaClass().getName().endsWith("BusinessImpl")) {
			AnnotatedTypeWrapper<T> wrapper = new AnnotatedTypeWrapper<T>(annotatedType,annotatedType.getAnnotations());
			if(ArrayUtils.contains(new Class[]{GlobalIdentifierBusinessImpl.class,ApplicationBusinessImpl.class,LanguageBusinessImpl.class
					,ExecutedMethodBusinessImpl.class}, annotatedType.getJavaClass())){
				
			}else{
				wrapper.addAnnotation(new javax.interceptor.Interceptors(){

					@Override
					public Class<? extends Annotation> annotationType() {
						return javax.interceptor.Interceptors.class;
					}

					@Override
					public Class<?>[] value() {
						return new Class[]{org.cyk.utility.common.cdi.annotation.Log.Interceptor.class};
					}
					
				});
				wrapper.addAnnotation(new Annotation() {
					@Override
					public Class<? extends Annotation> annotationType() {
						return org.cyk.utility.common.cdi.annotation.Log.class;
					}
				});	
			}
			processAnnotatedType.setAnnotatedType(wrapper);
		}

	}

	/**/

	public static class AnnotatedTypeWrapper<T> implements AnnotatedType<T> {

		private final AnnotatedType<T> wrapped;
		private final Set<Annotation> annotations;

		public AnnotatedTypeWrapper(AnnotatedType<T> wrapped, Set<Annotation> annotations) {
			this.wrapped = wrapped;
			this.annotations = new HashSet<>(annotations);
		}

		public void addAnnotation(Annotation annotation) {
			annotations.add(annotation);
		}

		@Override
		public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
			return wrapped.getAnnotation(annotationType);
		}

		@Override
		public Set<Annotation> getAnnotations() {
			return annotations;
		}

		@Override
		public Type getBaseType() {
			return wrapped.getBaseType();
		}

		@Override
		public Set<Type> getTypeClosure() {
			return wrapped.getTypeClosure();
		}

		@Override
		public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
			for (Annotation annotation : annotations) {
				if (annotationType.isInstance(annotation)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public Set<AnnotatedConstructor<T>> getConstructors() {
			return wrapped.getConstructors();
		}

		@Override
		public Set<AnnotatedField<? super T>> getFields() {
			return wrapped.getFields();
		}

		@Override
		public Class<T> getJavaClass() {
			return wrapped.getJavaClass();
		}

		@Override
		public Set<AnnotatedMethod<? super T>> getMethods() {
			return wrapped.getMethods();
		}

	}

}