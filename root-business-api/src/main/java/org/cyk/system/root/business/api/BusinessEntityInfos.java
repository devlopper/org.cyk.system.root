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
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudInheritanceStrategy;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@EqualsAndHashCode(of="clazz")
public class BusinessEntityInfos implements Serializable {
    
    @Getter @Setter private Class<? extends Identifiable<?>> clazz;
    private ModelBean modelBeanAnnotation;
    @Getter @Setter private String uiLabelId,identifier;
    
    public BusinessEntityInfos(Class<? extends Identifiable<?>> clazz,LanguageBusiness languageBusiness) {
        super();
        this.clazz = clazz;
        this.identifier=clazz.getSimpleName();
        modelBeanAnnotation = CommonUtils.getInstance().getAnnotation(clazz,ModelBean.class);
        uiLabelId = "model.entity."+Introspector.decapitalize(clazz.getSimpleName());
    }

    public CrudStrategy getCrudStrategy() {
        if(modelBeanAnnotation==null)
            return null;
        if(modelBeanAnnotation.crudInheritanceStrategy().equals(CrudInheritanceStrategy.CHILDREN_ONLY)){
            if(modelBeanAnnotation.equals(clazz.getAnnotation(ModelBean.class)))
                return null;
            return modelBeanAnnotation.crudStrategy();
        }
        return modelBeanAnnotation.crudStrategy();
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
                +"\r\nCrud Strategy : "+getCrudStrategy()+"\r\n"
                ;
    }
    
    

}
