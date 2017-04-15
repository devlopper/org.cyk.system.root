package org.cyk.system.root.model.network;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class Computer extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1273937760925881644L;

	@Column(unique=true) private String ipAddress;
	@Column(unique=true) private String ipAddressName;
	
	public static final String FIELD_IP_ADDRESS = "ipAddress";
	public static final String FIELD_IP_ADDRESS_NAME = "ipAddressName";
	
}
