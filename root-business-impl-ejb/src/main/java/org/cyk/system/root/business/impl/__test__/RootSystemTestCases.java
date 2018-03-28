package org.cyk.system.root.business.impl.__test__;

import java.io.Serializable;

public class RootSystemTestCases extends AbstractTestCases implements Serializable {
	private static final long serialVersionUID = 1L;

	protected MovementTestCases movementTestCases = new MovementTestCases();

	public void crudOneMovementCollectionType() {
		movementTestCases.crudOneMovementCollectionType();
	}

	public void crudOneMovementCollection() {
		movementTestCases.crudOneMovementCollection();
	}

	public void instanciateOneMovement() {
		movementTestCases.instanciateOneMovement();
	}

	public void instanciateOneMovementInIdentifiablePeriod() {
		movementTestCases.instanciateOneMovementInIdentifiablePeriod();
	}

	public void crudOneMovement() {
		movementTestCases.crudOneMovement();
	}

	public void crudOneMovementWhereIdentifiablePeriodIsSetToNotClosed() {
		movementTestCases.crudOneMovementWhereIdentifiablePeriodIsSetToNotClosed();
	}

	public void crudTwoMovementsWhereIdentifiablePeriodIsSetToNotClosed() {
		movementTestCases.crudTwoMovementsWhereIdentifiablePeriodIsSetToNotClosed();
	}

	public void assertComputedMovementBirthDateIsIncrementedWhenSetBySystem() {
		movementTestCases.assertComputedMovementBirthDateIsIncrementedWhenSetBySystem();
	}

	public void assertComputedMovementBirthDateIsNotChangedBySystemWhenSetByUser() {
		movementTestCases.assertComputedMovementBirthDateIsNotChangedBySystemWhenSetByUser();
	}

	public void createCreateMovementsWithDateSetBySystem() {
		movementTestCases.createCreateMovementsWithDateSetBySystem();
	}

	public void createCreateMovementsWithDateSetByUserAscending() {
		movementTestCases.createCreateMovementsWithDateSetByUserAscending();
	}

	public void createCreateMovementsWithDateSetByUserDescending() {
		movementTestCases.createCreateMovementsWithDateSetByUserDescending();
	}

	public void createCreateMovementsWithDateFirstSpecifiedAncestorSecondNotSpecifiedNewest() {
		movementTestCases.createCreateMovementsWithDateFirstSpecifiedAncestorSecondNotSpecifiedNewest();
	}

	public void createCreateMovementsWithDateFirstSpecifiedNewestSecondNotSpecifiedAncestor() {
		movementTestCases.createCreateMovementsWithDateFirstSpecifiedNewestSecondNotSpecifiedAncestor();
	}

	public void createCreateMovementsWithDateFirstNotSpecifiedAncestorSecondSpecifiedNewest() {
		movementTestCases.createCreateMovementsWithDateFirstNotSpecifiedAncestorSecondSpecifiedNewest();
	}

	public void createCreateMovementsWithDateFirstNotSpecifiedNewestSecondSpecifiedAncestor() {
		movementTestCases.createCreateMovementsWithDateFirstNotSpecifiedNewestSecondSpecifiedAncestor();
	}

	public void crudOneMovementWithSpecifiedDate() {
		movementTestCases.crudOneMovementWithSpecifiedDate();
	}

	public void crudOneMovementWithoutSpecifiedDate() {
		movementTestCases.crudOneMovementWithoutSpecifiedDate();
	}

	public void crudOneMovementCollectionAndMovements() {
		movementTestCases.crudOneMovementCollectionAndMovements();
	}

	public void useValueAbsolute() {
		movementTestCases.useValueAbsolute();
	}

	public void useNullAction() {
		movementTestCases.useNullAction();
	}

	public void addSequenceAscending() {
		movementTestCases.addSequenceAscending();
	}

	public void addSequenceDescending() {
		movementTestCases.addSequenceDescending();
	}

	public void findPrevious() {
		movementTestCases.findPrevious();
	}

	public void doMovementsAndUpdates() {
		movementTestCases.doMovementsAndUpdates();
	}

	public void filter() {
		movementTestCases.filter();
	}

	public void crudOneMovementCollectionIdentifiableGlobalIdentifier() {
		movementTestCases.crudOneMovementCollectionIdentifiableGlobalIdentifier();
	}

	public void computeChanges() {
		movementTestCases.computeChanges();
	}

	public void crudMovementsWithDestination() {
		movementTestCases.crudMovementsWithDestination();
	}

	public void createOneMovementAndOneChildrenWithDateSetByUser() {
		movementTestCases.createOneMovementAndOneChildrenWithDateSetByUser();
	}

	public void createOneMovementAndOneChildrenWithDateSetBySystem() {
		movementTestCases.createOneMovementAndOneChildrenWithDateSetBySystem();
	}

	public void createOneMovementAndOneChildren() {
		movementTestCases.createOneMovementAndOneChildren();
	}

	public void createOneMovementAndManyChildren() {
		movementTestCases.createOneMovementAndManyChildren();
	}

	public void throwCollectionIsNull() {
		movementTestCases.throwCollectionIsNull();
	}

	public void throwValueIsNull() {
		movementTestCases.throwValueIsNull();
	}

	public void throwIdentifiableCollectionIsNull() {
		movementTestCases.throwIdentifiableCollectionIsNull();
	}

	public void throwDoesNotBelongsToIdentifiablePeriod() {
		movementTestCases.throwDoesNotBelongsToIdentifiablePeriod();
	}

	public void throwValueIsZero() {
		movementTestCases.throwValueIsZero();
	}
	
}
