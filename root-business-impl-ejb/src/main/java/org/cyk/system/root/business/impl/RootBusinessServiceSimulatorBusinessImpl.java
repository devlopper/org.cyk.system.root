package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.system.root.business.api.RootBusinessServiceSimulatorBusiness;
import org.cyk.utility.common.computation.ExecutionProgress;
import org.cyk.utility.common.computation.ExecutionProgressListener;

@Singleton
public class RootBusinessServiceSimulatorBusinessImpl extends AbstractBusinessServiceImpl implements RootBusinessServiceSimulatorBusiness,Serializable {

	private static final long serialVersionUID = -146387231230323629L;

	@Override
	public void simulateExecutionProgress(ExecutionProgressListener executionProgressListener) {
		ExecutionProgress executionProgress = new ExecutionProgress("Check execution progresse!!!", 100d);
		executionProgress.getExecutionProgressListeners().add(executionProgressListener);
		
		pause(1000);executionProgress.addWorkDoneByStep(5);
		pause(1000);executionProgress.addWorkDoneByStep(10);
		pause(1000);executionProgress.addWorkDoneByStep(10);
		pause(1000);executionProgress.addWorkDoneByStep(30);
		pause(1000);executionProgress.addWorkDoneByStep(5);
		pause(1000);executionProgress.addWorkDoneByStep(5);
		pause(1000);executionProgress.addWorkDoneByStep(25);
		pause(1000);executionProgress.addWorkDoneByStep(10);
	}

	

}
