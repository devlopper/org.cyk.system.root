package org.cyk.system.root.service.impl.unit.jasper.samplereport;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.ReportColumn;
import org.cyk.utility.common.annotation.user.interfaces.style.Alignment;
import org.cyk.utility.common.annotation.user.interfaces.style.Style;
import org.cyk.utility.common.annotation.user.interfaces.style.Alignment.Horizontal;

@Getter @Setter
public class Employee extends AbstractIdentifiable {
	
	private static final long serialVersionUID = 3982682654111692983L;

	@Input @ReportColumn(style=@Style(alignment=@Alignment(horizontal=Horizontal.LEFT))) 
    private Integer empNo;
    
	@Input
	private String name;    
     
	@Input //@ReportColumn(style=@Style(alignment=@Alignment())) 
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