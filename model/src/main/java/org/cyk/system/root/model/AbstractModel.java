package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/*lombok*/
@Getter @Setter
/*mapping - jpa*/
@MappedSuperclass
public abstract class AbstractModel implements IModel<Long>, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	protected Long identifier;
	
	@Transient
	private String __id__;
	
	private String __identifier__(){
		if(__id__==null)
			__id__ = identifier==null?null:getClass().getSimpleName()+"/"+identifier;
		return __id__;
	}
	
	@Override
	public int hashCode() {
		String id = __identifier__();
		return id==null?HashCodeBuilder.reflectionHashCode(this, false):id.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof AbstractModel))
			return Boolean.FALSE;
		String id1 = __identifier__() , id2 = ((AbstractModel) object).__identifier__();
		if(id1==null || id2==null)
			return Boolean.FALSE;
		return id1.equals(id2);
	}
	
	@Override
	public String toString() {
		return __identifier__();
	}
 
}