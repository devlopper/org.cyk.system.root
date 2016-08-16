package org.cyk.system.root.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class Movement extends AbstractCollectionItem<MovementCollection> implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @JoinColumn(name="maction") /*@NotNull*/ private MovementAction action;	
	@Column(name="mvalue",precision=20,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal value;
	
	@Column private String supportingDocumentIdentifier;
	
	/**/
	
	/*@Override
	public String getLogMessage() {
		return String.format(LOG_FORMAT,action==null ? Constant.EMPTY_STRING:action.getLogMessage(),value,collection.getLogMessage());
	}*/
	
	public static final String LOG_FORMAT = Movement.class.getSimpleName()+"(DATE=%s %s VALUE=%s %s)";
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_SUPPORTING_DOCUMENT_IDENTIFIER = "supportingDocumentIdentifier";
		
}
