package org.cyk.system.root.model.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Entity @Getter @Setter @NoArgsConstructor @ModelBean(genderType=GenderType.FEMALE,crudStrategy=CrudStrategy.BUSINESS) 
public class UserAccountLock extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = -4449969354459932246L;

	@ManyToOne @NotNull(groups=org.cyk.utility.common.validation.System.class)
	private UserAccount userAccount;
	
	@ManyToOne @NotNull(groups=org.cyk.utility.common.validation.System.class)
	private LockCause lockCause;
	
	@Column(name="lock_date",nullable=false) @NotNull(groups=org.cyk.utility.common.validation.System.class)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	/**
	 * Unlocking code
	 */
	@Column(nullable=false)
	@NotNull(groups=org.cyk.utility.common.validation.System.class)
	private String unlockCode;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date unlockDate;
	
}
