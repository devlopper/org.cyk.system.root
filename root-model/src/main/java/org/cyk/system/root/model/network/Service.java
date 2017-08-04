package org.cyk.system.root.model.network;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
public class Service extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -1273937760925881644L;

	@ManyToOne @NotNull private Computer computer = new Computer();
	
	@NotNull @Column(nullable=false) private Integer port;
	
	@Column(nullable=false) @NotNull 
	private Boolean authenticated = Boolean.TRUE;
	
	@Column(nullable=false) @NotNull 
	private Boolean secured = Boolean.FALSE;
	
	@Override
	public String toString() {
		return computer.getCode()+Constant.CHARACTER_COLON+port;
	}
	
	@Override
	public String getUiString() {
		return StringUtils.defaultIfBlank(StringUtils.defaultIfBlank(getCode(),getName()),computer.getUiString())+Constant.CHARACTER_COLON+port;
	}
	
	public static final String FIELD_COMPUTER = "computer";
	public static final String FIELD_PORT = "port";
	public static final String FIELD_AUTHENTICATED = "authenticated";
	public static final String FIELD_SECURED = "secured";
}
