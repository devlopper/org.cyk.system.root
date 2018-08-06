package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @Entity @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
public class MovementCollectionInventoryType extends AbstractDataTreeType implements Serializable {
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @JoinColumn(name=COLUMN_IDENTIFIABLE_PERIOD_COLLECTION_TYPE) private IdentifiablePeriodCollectionType identifiablePeriodCollectionType;
	private Boolean automaticallyJoinIdentifiablePeriodCollection;
	private Boolean valueIsAggregated;
	
	public static final String FIELD_IDENTIFIABLE_PERIOD_COLLECTION_TYPE = "identifiablePeriodCollectionType";
	public static final String FIELD_AUTOMATICALLY_JOIN_IDENTIFIABLE_PERIOD_COLLECTION = "automaticallyJoinIdentifiablePeriodCollection";
	public static final String FIELD_VALUE_IS_AGGREGATED = "valueIsAggregated";
	
	public static final String COLUMN_IDENTIFIABLE_PERIOD_COLLECTION_TYPE = FIELD_IDENTIFIABLE_PERIOD_COLLECTION_TYPE;
}
