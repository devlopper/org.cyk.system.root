package org.cyk.system.root.service.impl.unit.jasper.samplereport;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.Input;

@Getter @Setter
public class Employee {
	
	@Input
    private Integer empNo;
    
	@Input
	private String name;    
     
	@Input 
	private Integer salary;
    
	@Input
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