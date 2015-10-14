package org.cyk.system.root.persistence.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.model.mathematics.Evaluation;
import org.cyk.system.root.model.mathematics.EvaluationItem;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public interface EvaluationItemDao extends AbstractEnumerationDao<EvaluationItem> {

	Collection<EvaluationItem> readByEvaluation(Evaluation evaluation);

    
}
