package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.Map.Entry;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*lombok*/

/*mapping - jpa*/
@MappedSuperclass
public abstract class AbstractIdentifiable extends AbstractModelElement implements Identifiable<Long>, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	@Id @GeneratedValue // Generation is customizable using mapping file
	protected Long identifier;

	//private Long index;
	
	//TODO any object can have its description or more extended by a collection of additional (external) informations 
	
	//@Transient private String __id__;
	
	/*private String __identifier__(){
		if(__id__==null)
			__id__ = getClass().getSimpleName()+"/"+(identifier==null?"?":identifier);
		return __id__;
	}*/
	
	private String __identifier__(){
		return getClass().getSimpleName()+"/"+(identifier==null?"?":identifier);
	}
	
	@Override
	public int hashCode() {
		String id = __identifier__();
		return id==null?HashCodeBuilder.reflectionHashCode(this, false):id.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof AbstractIdentifiable))
			return Boolean.FALSE;
		String id1 = __identifier__() , id2 = ((AbstractIdentifiable) object).__identifier__();
		if(id1==null || id2==null)
			return Boolean.FALSE;
		return id1.equals(id2);
	}
	
	@Override
	public String toString() {
		return __identifier__();
	}
	
	@Override
	public String getUiString() {
		return "?? NO UI STRING SPECIFIED ??";
	}
	
	/**/
	
	@PrePersist
	private void onPrePersist() {
		for(Entry<Class<? extends AbstractIdentifiable>, AbstractIdentifiableLifeCyleEventListener> entry : AbstractIdentifiableLifeCyleEventListener.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				getLogger().trace("Pre persist called for {}",this);
				entry.getValue().onPrePersist(this);
			}
		}
	}

	@PostPersist
	private void onPostPersist() {
		for(Entry<Class<? extends AbstractIdentifiable>, AbstractIdentifiableLifeCyleEventListener> entry : AbstractIdentifiableLifeCyleEventListener.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				getLogger().trace("Post persist called for {}",this);
				entry.getValue().onPostPersist(this);
			}
		}
	}

	@PostLoad
	private void onPostLoad() {
		for(Entry<Class<? extends AbstractIdentifiable>, AbstractIdentifiableLifeCyleEventListener> entry : AbstractIdentifiableLifeCyleEventListener.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				entry.getValue().onPostLoad(this);
			}
		}
	}

	@PreUpdate
	private void onPreUpdate() {
		for(Entry<Class<? extends AbstractIdentifiable>, AbstractIdentifiableLifeCyleEventListener> entry : AbstractIdentifiableLifeCyleEventListener.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				entry.getValue().onPreUpdate(this);
			}
		}
	}

	@PostUpdate
	private void onPostUpdate() {
		for(Entry<Class<? extends AbstractIdentifiable>, AbstractIdentifiableLifeCyleEventListener> entry : AbstractIdentifiableLifeCyleEventListener.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				entry.getValue().onPostUpdate(this);
			}
		}
	}

	@PreRemove
	private void onPreRemove() {
		for(Entry<Class<? extends AbstractIdentifiable>, AbstractIdentifiableLifeCyleEventListener> entry : AbstractIdentifiableLifeCyleEventListener.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				entry.getValue().onPreRemove(this);
			}
		}
	}

	@PostRemove
	private void onPostRemove() {
		for(Entry<Class<? extends AbstractIdentifiable>, AbstractIdentifiableLifeCyleEventListener> entry : AbstractIdentifiableLifeCyleEventListener.MAP.entrySet()){
			if(entry.getKey().equals(getClass())){
				entry.getValue().onPostRemove(this);
			}
		}
	}
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
	
	protected Logger getLogger(){
		return LoggerFactory.getLogger(getClass());
	}
 
}