package org.cyk.system.root.business.impl;
import org.cyk.system.root.business.impl.integration.IdentifiablePeriodBusinessIT;
import org.cyk.system.root.business.impl.integration.MovementBusinessIT;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

public class TestRunnerForTestSuite {

	public static void main(String[] args) {
		System.out.println("TestRunnerForTestSuite.main() IdentifiablePeriodBusinessIT");
		org.junit.runner.Result result = JUnitCore.runClasses(IdentifiablePeriodBusinessIT.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
		
		System.out.println("TestRunnerForTestSuite.main() MovementBusinessIT");
		result = JUnitCore.runClasses(MovementBusinessIT.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}

}