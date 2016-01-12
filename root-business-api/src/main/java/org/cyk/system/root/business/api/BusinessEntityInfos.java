package org.cyk.system.root.business.api;

import java.beans.Introspector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.userinterface.ClassUserInterface;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudInheritanceStrategy;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @EqualsAndHashCode(of="clazz")//FIXME should be replaced by Clazz //@Deprecated
public class BusinessEntityInfos implements Serializable {
    
	private static final long serialVersionUID = -8725167267186070601L;
	
	private Class<? extends Identifiable<?>> clazz;
	@Setter private Collection<Class<? extends Identifiable<?>>> manyToOneClasses,oneToOneClasses;
	private ModelBean modelBeanAnnotation;
	private Boolean male;
    
    private String varName,identifier;
    
    private ClassUserInterface userInterface = new ClassUserInterface();
    
    public BusinessEntityInfos(Class<? extends Identifiable<?>> clazz,LanguageBusiness languageBusiness) {
        super();
        this.clazz = clazz;
        this.identifier=clazz.getSimpleName();
        this.varName = Introspector.decapitalize(clazz.getSimpleName());
        modelBeanAnnotation = CommonUtils.getInstance().getAnnotation(clazz,ModelBean.class);
        
        userInterface.setLabelId("model.entity."+varName);
        userInterface.setLabel(languageBusiness.findText(userInterface.getLabelId()));
        if(modelBeanAnnotation!=null){
        	userInterface.setIconName(modelBeanAnnotation.uiIconName());
        }
    }

    public Collection<Class<? extends Identifiable<?>>> getManyToOneClasses(){
    	if(manyToOneClasses==null)
    		manyToOneClasses = new ArrayList<>();
    	return manyToOneClasses;
    }
    
    public Collection<Class<? extends Identifiable<?>>> getOneToOneClasses(){
    	if(oneToOneClasses==null)
    		oneToOneClasses = new ArrayList<>();
    	return oneToOneClasses;
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
