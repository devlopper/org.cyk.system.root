package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter /*@NoArgsConstructor @AllArgsConstructor*/ @Entity
public class Metric extends AbstractEnumeration implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	

}