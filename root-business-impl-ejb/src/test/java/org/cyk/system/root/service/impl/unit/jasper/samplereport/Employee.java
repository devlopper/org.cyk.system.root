package org.cyk.system.root.service.impl.unit.jasper.samplereport;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.ReportColumn;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Employee {
	
	@Input
    private Integer empNo;
    
	@Input
	private String name;    
     
	@Input 
	private Integer salary;
    
	@ReportColumn
	private Float commission;
 
    public Employee() {
    }
 
    public Employee(int empNo, String name, int salary, float commission) {
        this.empNo = empNo;
        this.name = name;        
        this.salary = salary;
        this.commission = salary*commission;
    }
 
   
}