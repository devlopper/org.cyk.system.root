package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.impl.__data__.DataSet;
import org.cyk.system.root.business.impl.__test__.RootSystemTestCases;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.value.Value;
import org.cyk.utility.common.helper.ClassHelper;
import org.junit.Test;

public class RootSystemIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;

    static {
    	ClassHelper.getInstance().map(DataSet.Listener.class, Data.class);
    }
    
    protected RootSystemTestCases rootSystemTestCases = new RootSystemTestCases();
    
    /**/
    
	/**/
	@Test
    public void crudOneMovementCollectionType() {
		rootSystemTestCases.crudOneMovementCollectionType();
	}
	
	@Test
	public void crudOneMovementCollection() {
		rootSystemTestCases.crudOneMovementCollection();
	}

	@Test
	public void instanciateOneMovement() {
		rootSystemTestCases.instanciateOneMovement();
	}

	@Test
	public void instanciateOneMovementInIdentifiablePeriod() {
		rootSystemTestCases.instanciateOneMovementInIdentifiablePeriod();
	}

	@Test
	public void crudOneMovement() {
		rootSystemTestCases.crudOneMovement();
	}

	@Test
	public void crudOneMovementWhereIdentifiablePeriodIsSetToNotClosed() {
		rootSystemTestCases.crudOneMovementWhereIdentifiablePeriodIsSetToNotClosed();
	}

	@Test
	public void crudTwoMovementsWhereIdentifiablePeriodIsSetToNotClosed() {
		rootSystemTestCases.crudTwoMovementsWhereIdentifiablePeriodIsSetToNotClosed();
	}

	@Test
	public void assertComputedMovementBirthDateIsIncrementedWhenSetBySystem() {
		rootSystemTestCases.assertComputedMovementBirthDateIsIncrementedWhenSetBySystem();
	}

	@Test
	public void assertComputedMovementBirthDateIsNotChangedBySystemWhenSetByUser() {
		rootSystemTestCases.assertComputedMovementBirthDateIsNotChangedBySystemWhenSetByUser();
	}

	@Test
	public void createCreateMovementsWithDateSetBySystem() {
		rootSystemTestCases.createCreateMovementsWithDateSetBySystem();
	}

	@Test
	public void createCreateMovementsWithDateSetByUserAscending() {
		rootSystemTestCases.createCreateMovementsWithDateSetByUserAscending();
	}

	@Test
	public void createCreateMovementsWithDateSetByUserDescending() {
		rootSystemTestCases.createCreateMovementsWithDateSetByUserDescending();
	}

	@Test
	public void createCreateMovementsWithDateFirstSpecifiedAncestorSecondNotSpecifiedNewest() {
		rootSystemTestCases.createCreateMovementsWithDateFirstSpecifiedAncestorSecondNotSpecifiedNewest();
	}

	@Test
	public void createCreateMovementsWithDateFirstSpecifiedNewestSecondNotSpecifiedAncestor() {
		rootSystemTestCases.createCreateMovementsWithDateFirstSpecifiedNewestSecondNotSpecifiedAncestor();
	}

	@Test
	public void createCreateMovementsWithDateFirstNotSpecifiedAncestorSecondSpecifiedNewest() {
		rootSystemTestCases.createCreateMovementsWithDateFirstNotSpecifiedAncestorSecondSpecifiedNewest();
	}

	@Test
	public void createCreateMovementsWithDateFirstNotSpecifiedNewestSecondSpecifiedAncestor() {
		rootSystemTestCases.createCreateMovementsWithDateFirstNotSpecifiedNewestSecondSpecifiedAncestor();
	}

	@Test
	public void crudOneMovementWithSpecifiedDate() {
		rootSystemTestCases.crudOneMovementWithSpecifiedDate();
	}

	@Test
	public void crudOneMovementWithoutSpecifiedDate() {
		rootSystemTestCases.crudOneMovementWithoutSpecifiedDate();
	}

	@Test
	public void crudOneMovementCollectionAndMovements() {
		rootSystemTestCases.crudOneMovementCollectionAndMovements();
	}

	@Test
	public void useValueAbsolute() {
		rootSystemTestCases.useValueAbsolute();
	}

	@Test
	public void useNullAction() {
		rootSystemTestCases.useNullAction();
	}

	@Test
	public void addSequenceAscending() {
		rootSystemTestCases.addSequenceAscending();
	}

	@Test
	public void addSequenceDescending() {
		rootSystemTestCases.addSequenceDescending();
	}

	@Test
	public void findPrevious() {
		rootSystemTestCases.findPrevious();
	}

	@Test
	public void doMovementsAndUpdates() {
		rootSystemTestCases.doMovementsAndUpdates();
	}

	@Test
	public void filter() {
		rootSystemTestCases.filter();
	}

	@Test
	public void crudOneMovementCollectionIdentifiableGlobalIdentifier() {
		rootSystemTestCases.crudOneMovementCollectionIdentifiableGlobalIdentifier();
	}

	@Test
	public void computeChanges() {
		rootSystemTestCases.computeChanges();
	}

	@Test
	public void crudMovementsWithDestination() {
		rootSystemTestCases.crudMovementsWithDestination();
	}

	@Test
	public void createOneMovementAndOneChildrenWithDateSetByUser() {
		rootSystemTestCases.createOneMovementAndOneChildrenWithDateSetByUser();
	}

	@Test
	public void createOneMovementAndOneChildrenWithDateSetBySystem() {
		rootSystemTestCases.createOneMovementAndOneChildrenWithDateSetBySystem();
	}

	@Test
	public void createOneMovementAndOneChildren() {
		rootSystemTestCases.createOneMovementAndOneChildren();
	}

	@Test
	public void createOneMovementAndManyChildren() {
		rootSystemTestCases.createOneMovementAndManyChildren();
	}

	@Test
	public void throwCollectionIsNull() {
		rootSystemTestCases.throwCollectionIsNull();
	}

	@Test
	public void throwValueIsNull() {
		rootSystemTestCases.throwValueIsNull();
	}

	@Test
	public void throwIdentifiableCollectionIsNull() {
		rootSystemTestCases.throwIdentifiableCollectionIsNull();
	}

	@Test
	public void throwDoesNotBelongsToIdentifiablePeriod() {
		rootSystemTestCases.throwDoesNotBelongsToIdentifiablePeriod();
	}

	@Test
	public void throwValueIsZero() {
		rootSystemTestCases.throwValueIsZero();
	}

	@SuppressWarnings("unchecked")
	public static class Data extends DataSet.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Collection getClasses() {
			return Arrays.asList(Movement.class,IdentifiablePeriod.class,Value.class);
		}
		
    }
}
