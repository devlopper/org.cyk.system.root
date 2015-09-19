package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractCollection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor
public class BusinessServiceCollection extends AbstractCollection<BusinessService> implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;

}
