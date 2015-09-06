package org.cyk.system.root.model.network;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Service implements Serializable {

	private static final long serialVersionUID = -1273937760925881644L;

	private Computer host = new Computer();
	
	private Integer port;
	
}
