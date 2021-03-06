package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;
import org.cyk.utility.common.annotation.FieldOverride;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity @FieldOverride(name=AbstractDataTreeType.FIELD___PARENT__,type=MovementCollectionType.class) @Accessors(chain=true)
public class MovementCollectionType extends AbstractDataTreeType implements Serializable  {
	private static final long serialVersionUID = -6838401709866343401L;

	@ManyToOne @JoinColumn(name=COLUMN_INTERVAL) private Interval interval;
	@ManyToOne @JoinColumn(name=COLUMN_INCREMENT_ACTION) private MovementAction incrementAction;
	@ManyToOne @JoinColumn(name=COLUMN_DECREMENT_ACTION) private MovementAction decrementAction;
	private Boolean movementParentable;
	private Boolean supportDocumentIdentifier;
	@ManyToOne @JoinColumn(name=COLUMN_DOCUMENT_IDENTIFIER_COUNT_INTERVAL) private Interval documentIdentifierCountInterval;
	@ManyToOne @JoinColumn(name=COLUMN_IDENTIFIABLE_PERIOD_COLLECTION_TYPE) private IdentifiablePeriodCollectionType identifiablePeriodCollectionType;
	private Boolean automaticallyJoinIdentifiablePeriodCollection;
	private Boolean valueIsAggregated;
	
	//TODO to be model using a class which can be called MovementCollectionAlert or something like that. really i do not know so to think about
	//@Column(precision=10,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal minimalQuantityAlert = BigDecimal.ZERO;
	//@Column(precision=10,scale=FLOAT_SCALE,nullable=false) @NotNull private BigDecimal minimalQuantityBlock = BigDecimal.ZERO;

	/**/
	
	@Override
	public MovementCollectionType setCode(String code) {
		return (MovementCollectionType) super.setCode(code);
	}

	@Override
	public MovementCollectionType set__parent__(AbstractIdentifiable __parent__) {
		return (MovementCollectionType) super.set__parent__(__parent__);
	}
	
	/**/
	
	public static final String FIELD_INTERVAL = "interval";
	public static final String FIELD_INCREMENT_ACTION = "incrementAction";
	public static final String FIELD_DECREMENT_ACTION = "decrementAction";
	public static final String FIELD_MOVEMENT_PARENTABLE = "movementParentable";
	public static final String FIELD_SUPPORT_DOCUMENT_IDENTIFIER = "supportDocumentIdentifier";
	public static final String FIELD_DOCUMENT_IDENTIFIER_COUNT_INTERVAL = "documentIdentifierCountInterval";
	public static final String FIELD_IDENTIFIABLE_PERIOD_COLLECTION_TYPE = "identifiablePeriodCollectionType";
	public static final String FIELD_AUTOMATICALLY_JOIN_IDENTIFIABLE_PERIOD_COLLECTION = "automaticallyJoinIdentifiablePeriodCollection";
	public static final String FIELD_VALUE_IS_AGGREGATED = "valueIsAggregated";
	
	public static final String COLUMN_INTERVAL = COLUMN_NAME_UNKEYWORD+FIELD_INTERVAL;
	public static final String COLUMN_INCREMENT_ACTION = FIELD_INCREMENT_ACTION;
	public static final String COLUMN_DECREMENT_ACTION = FIELD_DECREMENT_ACTION;
	public static final String COLUMN_SUPPORT_DOCUMENT_IDENTIFIER = FIELD_SUPPORT_DOCUMENT_IDENTIFIER;
	public static final String COLUMN_DOCUMENT_IDENTIFIER_COUNT_INTERVAL = FIELD_DOCUMENT_IDENTIFIER_COUNT_INTERVAL;
	public static final String COLUMN_IDENTIFIABLE_PERIOD_COLLECTION_TYPE = FIELD_IDENTIFIABLE_PERIOD_COLLECTION_TYPE;
}
