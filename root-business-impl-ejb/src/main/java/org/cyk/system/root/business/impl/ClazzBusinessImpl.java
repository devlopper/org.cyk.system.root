package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Singleton;

import org.cyk.system.root.business.api.ClazzBusiness;
import org.cyk.system.root.model.Clazz;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton
public class ClazzBusinessImpl extends AbstractBean implements ClazzBusiness,Serializable {

	private static final long serialVersionUID = 4720122035285323813L;

	private static final Map<Class<?>,Clazz> MAP = new HashMap<>();
	
	@Override
	public Clazz find(Class<?> aClass) {
		return find(aClass, Boolean.TRUE);
	}

	@Override
	public Clazz find(String identifier) {
		for(Entry<Class<?>,Clazz> entry : MAP.entrySet())
			if(entry.getValue().getRuntimeIdentifier().equals(identifier))
				return entry.getValue();
		logWarning("No Clazz found for {}", identifier);
		return null;
	}
	
	@Override
	public Clazz register(Clazz aClazz) {
		for(ClazzBusinessListener listener : LISTENERS)
			listener.register(aClazz); 
		for(ClazzBusinessListener listener : LISTENERS)
			listener.doSetUiLabel(aClazz); 
		return MAP.put(aClazz.getClazz(), aClazz);
	}

	@Override
	public Clazz register(Class<?> aClass) {
		Clazz clazz = new Clazz(aClass);
		return register(clazz);
	}

	@Override
	public Clazz find(Class<?> aClass, Boolean registerWhenNull) {
		Clazz clazz = MAP.get(aClass);
		if(clazz == null)
			if(Boolean.TRUE.equals(registerWhenNull))
				clazz = register(aClass);
		return clazz;
	}

	@Override
	public void resetUiLabels() {
		for(Entry<Class<?>,Clazz> entry : MAP.entrySet())
			for(ClazzBusinessListener listener : LISTENERS)
				listener.doSetUiLabel(entry.getValue()); 
	}
	
	/**/
	/*
	static{
    	LISTENERS.add(new ClazzBusinessListener() {
			@Override
			public void register(Clazz clazz) {
				if(Identifiable.class.isAssignableFrom(clazz.getClazz())){
					clazz.setUiLabelId("model.entity."+clazz.getVarName());
				}else{
					clazz.setUiLabelId(clazz.getClazz().getName());
				}
			}
		});
    }
	 */
}
