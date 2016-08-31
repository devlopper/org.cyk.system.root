package org.cyk.system.root.business.impl.integration.report;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.unit.jasper.samplereport.Employee;
import org.cyk.utility.common.annotation.user.interfaces.Input;

@Getter @Setter
public class EmployeeLineReport implements Serializable {
	
	private static final long serialVersionUID = -4946585596435850782L;

	@Input private String number,names,salary;
	
	public EmployeeLineReport(Employee employee) {
		number = employee.getEmpNo().toString();
		names = employee.getName();
	}
	
}
