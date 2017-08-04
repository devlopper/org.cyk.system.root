package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.system.root.business.api.RootBusinessServiceSimulatorBusiness;
import org.cyk.utility.common.computation.ExecutionProgress;

@Singleton
public class RootBusinessServiceSimulatorBusinessImpl extends AbstractBusinessServiceImpl implements RootBusinessServiceSimulatorBusiness,Serializable {

	private static final long serialVersionUID = -146387231230323629L;

	@Override
	public void simulateExecutionProgress(ExecutionProgress executionProgress) {
		executionProgress.setCurrentExecutionStep("Tache 1");pause(2000); executionProgress.addWorkDoneByStep(5);
		executionProgress.setCurrentExecutionStep("Tache deux");pause(3000); executionProgress.addWorkDoneByStep(10);
		executionProgress.setCurrentExecutionStep("Tache 3");pause(3000);executionProgress.addWorkDoneByStep(10,new RuntimeException());
		executionProgress.setCurrentExecutionStep("Tache 4");pause(5000);executionProgress.addWorkDoneByStep(30);
		executionProgress.setCurrentExecutionStep("Tache Cinq");pause(2000);executionProgress.addWorkDoneByStep(5);
		executionProgress.setCurrentExecutionStep("Tache Six");pause(4000);executionProgress.addWorkDoneByStep(5,new RuntimeException());
		executionProgress.setCurrentExecutionStep("UNE Tache 777");pause(3000);executionProgress.addWorkDoneByStep(25);
		executionProgress.setCurrentExecutionStep("ANNathoer TaCHe 8");pause(4000);executionProgress.addWorkDoneByStep(10);
	}

	

}
