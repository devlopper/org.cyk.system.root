package org.cyk.system.root.model.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @MappedSuperclass @ModelBean(crudStrategy=CrudStrategy.INTERNAL) 
public abstract class AbstractSecuredView<ACCESSOR> extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = 5908328682512231058L;

	@ManyToOne @NotNull protected ACCESSOR accessor;
	
	@Column(nullable=false) @NotNull protected String viewId;
	
	public AbstractSecuredView(ACCESSOR accessor,String viewId,String code, String name) {
		super(code, name, null, null);
		this.accessor = accessor;
		this.viewId = viewId;
	}

}