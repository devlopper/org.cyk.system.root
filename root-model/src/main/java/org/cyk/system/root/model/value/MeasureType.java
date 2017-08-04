package org.cyk.system.root.model.value;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Komenan.Christian
 *
 */
@Entity @Getter @Setter @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.ENUMERATION)
public class MeasureType extends AbstractEnumeration implements Serializable{
	
	private static final long serialVersionUID = 374208919427476791L;
	
	
}

