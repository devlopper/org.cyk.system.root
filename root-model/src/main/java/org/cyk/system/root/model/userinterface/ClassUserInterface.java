package org.cyk.system.root.model.userinterface;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModelElement;

@Getter @Setter @EqualsAndHashCode(of="clazz",callSuper=false)
public class ClassUserInterface extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = -2596683791977022898L;

	@Getter @Setter private Class<?> clazz,hierarchyHighestAncestorClass,detailsClass;
	@Getter @Setter private String labelId,label,iconName,iconExtension;
    @Getter @Setter private String consultViewId,listViewId,editViewId,createManyViewId,selectOneViewId,selectManyViewId,processManyViewId,printViewId;
	
	@Override
	public String getUiString() {
		return labelId;
	}

}
