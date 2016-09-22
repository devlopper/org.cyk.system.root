package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Christian Yao Komenan
 *
 */
@Getter @Setter @NoArgsConstructor @MappedSuperclass
public abstract class AbstractIdentifiablePeriod extends AbstractIdentifiable implements Serializable  {

	private static final long serialVersionUID = 4094533140633110556L;
	
}
