package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @ModelBean(genderType=GenderType.MALE,crudStrategy=CrudStrategy.BUSINESS)
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {MovementsTransferItemCollectionItemIdentifiableGlobalIdentifier.FIELD_MOVEMENTS_TRANSFER_ITEM_COLLECTION_ITEM
		,MovementsTransferItemCollectionItemIdentifiableGlobalIdentifier.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER})})
public class MovementsTransferItemCollectionItemIdentifiableGlobalIdentifier extends AbstractJoinGlobalIdentifier implements Serializable {
	private static final long serialVersionUID = 8167875049554197503L;

	@ManyToOne @NotNull @JoinColumn(name=COLUMN_MOVEMENTS_TRANSFER_ITEM_COLLECTION_ITEM) private MovementsTransferItemCollectionItem movementsTransferItemCollectionItem;
	
	public static final String FIELD_MOVEMENTS_TRANSFER_ITEM_COLLECTION_ITEM = "movementsTransferItemCollectionItem";
	
	public static final String COLUMN_MOVEMENTS_TRANSFER_ITEM_COLLECTION_ITEM = FIELD_MOVEMENTS_TRANSFER_ITEM_COLLECTION_ITEM;
	
	@Getter @Setter
	public static class SearchCriteria extends AbstractJoinGlobalIdentifier.AbstractSearchCriteria implements Serializable {

		private static final long serialVersionUID = 6796076474234170332L;
		
		public SearchCriteria addGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers){
			return (SearchCriteria) super.addGlobalIdentifiers(globalIdentifiers);
		}
		public SearchCriteria addGlobalIdentifier(GlobalIdentifier globalIdentifier){
			return (SearchCriteria) super.addGlobalIdentifier(globalIdentifier);
		}
		
	}
}
