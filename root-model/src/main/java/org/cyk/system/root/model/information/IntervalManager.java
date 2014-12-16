package org.cyk.system.root.model.information;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter
@Entity 
public class IntervalManager  extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;

	
}