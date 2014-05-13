package org.cyk.system.root.business.api;

import java.beans.Introspector;
import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.Identifiable;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.Model;
import org.cyk.utility.common.annotation.Model.CrudInheritanceStrategy;
import org.cyk.utility.common.annotation.Model.CrudStrategy;

@EqualsAndHashCode(of="clazz")
public class BusinessEntityInfos implements Serializable {
    
    @Getter @Setter private Class<? extends Identifiable<?>> clazz;
    private Model modelAnnotation;
    @Getter @Setter private String uiLabelId,identifier;
    
    public BusinessEntityInfos(Class<? extends Identifiable<?>> clazz,LanguageBusiness languageBusiness) {
        super();
        this.clazz = clazz;
        this.identifier=clazz.getSimpleName();
        modelAnnotation = CommonUtils.getInstance().getAnnotation(clazz,Model.class);
        uiLabelId = "entity."+Introspector.decapitalize(clazz.getSimpleName());
    }

    public CrudStrategy getCrudStrategy() {
        if(modelAnnotation==null)
            return null;
        if(modelAnnotation.crudInheritanceStrategy().equals(CrudInheritanceStrategy.CHILDREN_ONLY)){
            if(modelAnnotation.equals(clazz.getAnnotation(Model.class)))
                return null;
            return modelAnnotation.crudStrategy();
        }
        return modelAnnotation.crudStrategy();
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
                +"\r\nCrud Strategy : "+getCrudStrategy()+"\r\n"
                ;
    }
    
    

}
