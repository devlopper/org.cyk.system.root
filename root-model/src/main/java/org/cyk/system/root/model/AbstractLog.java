package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.Party;

@Getter @Setter @MappedSuperclass
public abstract class AbstractLog extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2576023570217657424L;

	@ManyToOne @NotNull private Party party;
	
	@Temporal(TemporalType.TIMESTAMP) @Column(name="thedate",nullable=false) @NotNull private Date date;
	
	/**/
	
	public static final String FIELD_PARTY = "party";
	public static final String FIELD_DATE = "date";
}
