package org.cyk.system.root.business.impl.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
//@Entity 
@AllArgsConstructor @NoArgsConstructor
public class ValidationTestEntity extends AbstractIdentifiable implements Serializable {
	
	private static final long serialVersionUID = -7061794989292809428L;
	
	@NotNull private String myField1;
	
	

	
	
}
