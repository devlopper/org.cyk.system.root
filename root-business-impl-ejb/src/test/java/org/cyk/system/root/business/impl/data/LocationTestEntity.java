package org.cyk.system.root.business.impl.data;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
//@Entity 
@AllArgsConstructor @NoArgsConstructor
public class LocationTestEntity extends AbstractIdentifiable implements Serializable {
	
	private static final long serialVersionUID = -7061794989292809428L;
	
	private String address;

	
	
}
