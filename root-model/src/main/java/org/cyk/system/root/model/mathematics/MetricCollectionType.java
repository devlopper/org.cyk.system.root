package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractEnumeration;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class MetricCollectionType extends AbstractEnumeration implements Serializable{
	
	private static final long serialVersionUID = 374208919427476791L;
	
	public static final String BEHAVIOUR = "BEHAVIOUR";
	public static final String ATTENDANCE = "ATTENDANCE";
	
	public MetricCollectionType() {}

	public MetricCollectionType(String code,String name, String abbreviation) {
		super(code,name, abbreviation,null);
	}

}

