package org.cyk.system.root.model.party;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(crudStrategy=CrudStrategy.BUSINESS,genderType=GenderType.MALE)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {PartyBusinessRole.FIELD_PARTY,PartyBusinessRole.FIELD_BUSINESS_ROLE})})
public class PartyBusinessRole extends AbstractIdentifiable implements Serializable {
	private static final long serialVersionUID = -4946585596435850782L;
	
	private @ManyToOne @JoinColumn(name=COLUMN_PARTY) @NotNull Party party;
	private @ManyToOne @JoinColumn(name=COLUMN_BUSINESS_ROLE) @NotNull BusinessRole businessRole;
	
	/**/
	
	public static final String FIELD_PARTY = "party";
	public static final String FIELD_BUSINESS_ROLE = "businessRole";
	
	public static final String COLUMN_PARTY = FIELD_PARTY;
	public static final String COLUMN_BUSINESS_ROLE = FIELD_BUSINESS_ROLE;
}
