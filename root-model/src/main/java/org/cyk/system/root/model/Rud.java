package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.Setter;

@Embeddable @Getter @Setter
public class Rud extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = -2272776792804730789L;

	private Boolean readable;
	
	private Boolean updatable;
	
	private Boolean deletable;

	@Override
	public String getUiString() {
		return Constant.EMPTY_STRING+readable+Constant.CHARACTER_HYPHEN+updatable+Constant.CHARACTER_HYPHEN+deletable;
	}
	
}
