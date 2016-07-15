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
	protected String nameI18nId;
	
	protected String abbreviation;
	protected String abbreviationI18nId;
	
	@Column(length=10 * 1024)
	protected String description;
	protected String descriptionI18nId;

	@Column(name="f_constant")
	protected Boolean constant;
	
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

    /**/
    
    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";
	
}
