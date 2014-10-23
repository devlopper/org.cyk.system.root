package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;
import org.cyk.utility.common.validation.Client;

/*lombok*/
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
/*mapping-jpa*/
@MappedSuperclass @ModelBean(crudStrategy=CrudStrategy.ENUMERATION)
public abstract class AbstractEnumeration  extends AbstractIdentifiable  implements Serializable,Comparable<AbstractEnumeration> {

	private static final long serialVersionUID = -8639942019354737162L;
	
	@Input
	@InputText
	@Column(nullable=false,unique=true)
	@NotNull(groups=Client.class)
	protected String code;
	
	@Input
	@InputText
	@Column(nullable=false)
	@NotNull(groups=Client.class)
	protected String name;
	
	private String nameI18nId;
	
	@Input
	@InputText
	protected String abbreviation;
	
	private String abbreviationI18nId;
	
	@Input
	@InputTextarea
	@Column(length=10 * 1024)
	protected String description;
	
	private String descriptionI18nId;
	
	public AbstractEnumeration(String code, String name, String abbreviation, String description) {
        super();
        this.code = code;
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
    }
	
	@Override
	public int compareTo(AbstractEnumeration o) {
	    if(code==null)
	        if(o.code==null)
	            return 0;
	        else
	            return -1;
	    else
	        if(o.code==null)
                return 1;
            else
                return code.compareTo(o.code);
	}
	
	@Override
	public String toString() {
		return name;
	}

    @Override
    public String getUiString() {
    	return name;
    }

	
}
