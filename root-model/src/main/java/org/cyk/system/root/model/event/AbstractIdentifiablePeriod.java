package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.time.Period;

/**
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractIdentifiablePeriod extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
    /**
     * The period
     */
    @Embedded protected Period period = new Period();
    
    
    @Override
    public String toString() {
    	return period.toString();
    }

}
