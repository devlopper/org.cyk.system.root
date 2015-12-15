package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
	public Object findParentOf(Class<?> rootClass, Object object) {
		if(object==null || object.getClass().equals(rootClass))
			return null;
		Object value = null;
		for(ClazzBusinessListener listener : LISTENERS){
			Object v = listener.getParentOf(object); 
			if(v!=null)
				value = v;
		}
		return value;
	}
	
	private void findParentsOf(Class<?> rootClass, Object object,List<Object> list) {
		list.add(object);
		Object parent = findParentOf(rootClass, object);
		if(parent==null)
			;
		else
			findParentsOf(rootClass, parent,list);
	}
	
	@Override
	public List<Object> findParentsOf(Class<?> rootClass, Object object) {
		List<Object> list = findPathOf(rootClass,object);
		if(list.size()>0)
			list.remove(list.size()-1);
		return list;
	}
	
	@Override
	public List<Object> findPathOf(Class<?> rootClass, Object object) {
		List<Object> list = new ArrayList<>();
		findParentsOf(rootClass,object,list);
		Collections.reverse(list);
		return list;
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
