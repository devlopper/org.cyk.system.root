package org.cyk.system.root.persistence.impl.mathematics;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.mathematics.Evaluation;
import org.cyk.system.root.model.mathematics.EvaluationItem;
import org.cyk.system.root.persistence.api.mathematics.EvaluationItemDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class EvaluationItemDaoImpl extends AbstractEnumerationDaoImpl<EvaluationItem> implements EvaluationItemDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByEvaluation;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByEvaluation, _select().where(EvaluationItem.FIELD_EVALUATION));
	}
	
	@Override
	public Collection<EvaluationItem> readByEvaluation(Evaluation evaluation) {
		return namedQuery(readByEvaluation).parameter(EvaluationItem.FIELD_EVALUATION, evaluation).resultMany();
	}

}
 