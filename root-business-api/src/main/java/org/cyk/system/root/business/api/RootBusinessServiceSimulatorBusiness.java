package org.cyk.system.root.business.api;

import org.cyk.utility.common.computation.ExecutionProgress;

public interface RootBusinessServiceSimulatorBusiness {
	
	void simulateExecutionProgress(ExecutionProgress.Listener executionProgressListener);
	
}
