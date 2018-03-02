package org.cyk.system.root.model.time;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.InstanceHelper;

@Entity
@Getter @Setter
public class IdentifiablePeriodType extends AbstractEnumeration implements Serializable{
	private static final long serialVersionUID = 374208919427476791L;
	
	@ManyToOne @JoinColumn(name=COLUMN_TIME_DIVISION_TYPE) @NotNull private TimeDivisionType timeDivisionType;
	
	private @Accessors(chain=true) Boolean isDisjoint = Boolean.TRUE;
	private @Accessors(chain=true) Boolean isAdjacent = Boolean.TRUE;
	
	public IdentifiablePeriodType setTimeDivisionTypeFromCode(String code){
		this.timeDivisionType = InstanceHelper.getInstance().getByIdentifier(TimeDivisionType.class, code, ClassHelper.Listener.IdentifierType.BUSINESS);
		return this;
	}
	
	public static final String FIELD_TIME_DIVISION_TYPE = "timeDivisionType";
	public static final String FIELD_IS_DISJOINT = "isDisjoint";
	public static final String FIELD_IS_ADJACENT = "isAdjacent";
	
	public static final String COLUMN_TIME_DIVISION_TYPE = FIELD_TIME_DIVISION_TYPE;
}

