package org.cyk.system.root.business.impl;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractLog;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public abstract class AbstractLogDetails<LOG extends AbstractLog> extends AbstractOutputDetails<LOG> implements Serializable{
	
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText protected String date,party;
	
	public AbstractLogDetails(LOG log) {
		super(log);
		date = formatDate(log.getDate());
		party = formatUsingBusiness(log.getParty());
	}
	
	public static final String FIELD_DATE = "date";
	public static final String FIELD_PARTY = "party";
}