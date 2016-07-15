package org.cyk.system.root.model.information;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.GlobalIdentifier;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class Comment extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 8167875049554197503L;

	@ManyToOne private CommentType type;
	
	@ManyToOne private GlobalIdentifier identifiableGlobalIdentifier;
	
	@Column(nullable=false,length=1024 * 1) @NotNull private String message;
	
}
