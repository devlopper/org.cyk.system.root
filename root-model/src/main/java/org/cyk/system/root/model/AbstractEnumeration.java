package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*lombok*/
@Getter @Setter @NoArgsConstructor //@AllArgsConstructor
/*mapping-jpa*/
@MappedSuperclass @ModelBean(crudStrategy=CrudStrategy.ENUMERATION)
public abstract class AbstractEnumeration  extends AbstractIdentifiable  implements Serializable,Comparable<AbstractEnumeration> {

	private static final long serialVersionUID = -8639942019354737162L;

	public AbstractEnumeration(String code, String name, String abbreviation, String description) {
        super();
        setCode(code);
        setName(name);
        setAbbreviation(abbreviation);
        setDescription(description);
    }
	
	@Override
	public int compareTo(AbstractEnumeration o) {
	    if(getCode()==null)
	        if(o.getCode()==null)
	            return 0;
	        else
	            return -1;
	    else
	        if(o.getCode()==null)
                return 1;
            else
                return getCode().compareTo(o.getCode());
	}
	
	@Override
	public String toString() {
		return getName();
	}

    @Override
    public String getUiString() {
    	return getName();
    }

    /**/
	
}
