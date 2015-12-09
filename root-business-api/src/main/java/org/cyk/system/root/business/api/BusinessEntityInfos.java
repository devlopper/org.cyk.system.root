package org.cyk.system.root.business.api;

import java.beans.Introspector;
import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.Identifiable;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudInheritanceStrategy;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@EqualsAndHashCode(of="clazz") //FIXME should be replaced by Clazz //@Deprecated
public class BusinessEntityInfos implements Serializable {
    
	private static final long serialVersionUID = -8725167267186070601L;
	@Getter @Setter private Class<? extends Identifiable<?>> clazz;
	@Getter  private ModelBean modelBeanAnnotation;
	@Getter @Setter private Boolean male;
    
    @Getter @Setter private String varName,uiLabelId,uiLabel,identifier,uiIconName,uiIconExtension;
    @Getter @Setter private String uiConsultViewId,uiListViewId,uiEditViewId,uiCreateManyViewId;
    
    public BusinessEntityInfos(Class<? extends Identifiable<?>> clazz,LanguageBusiness languageBusiness) {
        super();
        this.clazz = clazz;
        this.identifier=clazz.getSimpleName();
        this.varName = Introspector.decapitalize(clazz.getSimpleName());
        modelBeanAnnotation = CommonUtils.getInstance().getAnnotation(clazz,ModelBean.class);
        
        uiLabelId = "model.entity."+varName;
        uiLabel = languageBusiness.findText(uiLabelId);
        if(modelBeanAnnotation!=null){
        	uiIconName = modelBeanAnnotation.uiIconName();
        }
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
    
    public GenderType getGenderType(){
    	if(modelBeanAnnotation==null)
            return null;
    	return modelBeanAnnotation.genderType();
    }

}
