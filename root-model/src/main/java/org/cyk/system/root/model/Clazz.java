package org.cyk.system.root.model;

import java.beans.Introspector;
import java.io.Serializable;

import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudInheritanceStrategy;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

/*@Entity*/ @Getter @Setter @NoArgsConstructor @ModelBean(crudStrategy=CrudStrategy.INTERNAL)
public class Clazz extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 524804677149804204L;

	@Transient private Class<?> clazz;

	@Transient private ModelBean modelBeanAnnotation;
	private Boolean male;
	
	private String qualifiedName;
	private String runtimeIdentifier;

	private String varName,uiLabelId,uiIconName,uiIconExtension;
	private String uiConsultViewId,uiListViewId,uiEditViewId;
	
	@Transient private String uiLabel;

	public Clazz(Class<?> clazz) {
		super();
		this.clazz = clazz;
		this.qualifiedName = clazz.getName();
		this.runtimeIdentifier = clazz.getSimpleName();
		this.varName = Introspector.decapitalize(clazz.getSimpleName());
		
		modelBeanAnnotation = CommonUtils.getInstance().getAnnotation(clazz,ModelBean.class);
		if(modelBeanAnnotation!=null){
        	uiIconName = modelBeanAnnotation.uiIconName();
        }
		
		if(Identifiable.class.isAssignableFrom(clazz)){
			uiLabelId = "model.entity."+varName;
		}else{
			uiLabelId = StringUtils.replace(getClazz().getName().toLowerCase(), Constant.CHARACTER_DOLLAR.toString(), Constant.CHARACTER_DOT.toString());
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
    
    @Override
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**/
	
}