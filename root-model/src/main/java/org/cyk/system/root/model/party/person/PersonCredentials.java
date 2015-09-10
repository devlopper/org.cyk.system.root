package org.cyk.system.root.model.party.person;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter @Entity //TODO to be modeled as a document provided by some authority or something like that
public class PersonCredentials extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 96367120888825867L;
		
	@Input
	@InputChoice
	@InputOneChoice
	@InputOneCombo
	@ManyToOne
	@NotNull
	private PersonCredentialsType type;
	
	@Input
	@InputText
	@NotNull
	private String number;
	
	@Input
	@InputCalendar
	@Temporal(TemporalType.DATE)
	private Date validityDate;
	
	@Input
	@InputCalendar
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date expirationDate;
	
	@Override
	public String getUiString() {
		return type.getName()+" "+number;
	}
	
	
}
