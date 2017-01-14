package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.event.ExecutedMethod;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExecutedMethodDetails extends AbstractOutputDetails<ExecutedMethod> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	
	public ExecutedMethodDetails(ExecutedMethod executedMethod) {
		super(executedMethod);
		
	}
	
	/**/
	
	
}