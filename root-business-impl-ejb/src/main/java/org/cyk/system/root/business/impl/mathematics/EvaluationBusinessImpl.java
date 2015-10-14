package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.EvaluationBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.mathematics.Evaluation;
import org.cyk.system.root.model.mathematics.EvaluationItem;
import org.cyk.system.root.persistence.api.mathematics.EvaluationDao;
import org.cyk.system.root.persistence.api.mathematics.EvaluationItemDao;

@Stateless
public class EvaluationBusinessImpl extends AbstractEnumerationBusinessImpl<Evaluation, EvaluationDao> implements EvaluationBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private EvaluationItemDao evaluationItemDao;
	
	@Inject
	public EvaluationBusinessImpl(EvaluationDao dao) {
		super(dao); 
	}   
	
	@Override
	public Evaluation create(Evaluation evaluation) {
		evaluation = super.create(evaluation);
		if(evaluation.getItems()!=null){
			for(EvaluationItem item : evaluation.getItems()){
				item.setEvaluation(evaluation);
				evaluationItemDao.create(item);
			}
		}
		return evaluation;
	}
	
	@Override
	protected void __load__(Evaluation evaluation) {
		super.__load__(evaluation);
		evaluation.setItems(evaluationItemDao.readByEvaluation(evaluation));
	}
}
