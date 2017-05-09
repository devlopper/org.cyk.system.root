package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS) @Accessors(chain=true)
public class Movement extends AbstractCollectionItem<MovementCollection> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @JoinColumn(name=COLUMN_ACTION) private MovementAction action;	
	@Column(name=COLUMN_VALUE,precision=20,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal value;
	
	/**
	 * The person to whom value goes or from whom value comes
	 */
	@ManyToOne @JoinColumn(name=COLUMN_SENDER_OR_RECEIVER_PERSON) private Person senderOrReceiverPerson;
	
	private String senderOrReceiverPersonAsString;
	
	
	
	/**/
	
	/*@Override
	public String getLogMessage() {
		return String.format(LOG_FORMAT,action==null ? Constant.EMPTY_STRING:action.getLogMessage(),value,collection.getLogMessage());
	}*/
	
	public static final String LOG_FORMAT = Movement.class.getSimpleName()+"(DATE=%s %s VALUE=%s %s)";
	
	/**/
	
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_SENDER_OR_RECEIVER_PERSON = "senderOrReceiverPerson";
	public static final String FIELD_SENDER_OR_RECEIVER_PERSON_AS_STRING = "senderOrReceiverPersonAsString";
	
	public static final String COLUMN_ACTION = FIELD_ACTION;
	public static final String COLUMN_VALUE = FIELD_VALUE;
	public static final String COLUMN_SENDER_OR_RECEIVER_PERSON = FIELD_SENDER_OR_RECEIVER_PERSON;
		
}
