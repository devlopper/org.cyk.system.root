package org.cyk.system.root.business.impl.unit.jasper.samplereport;

import java.util.ArrayList;
import java.util.Collection;

public class DynaReportApp {
 
    public static void main(String[] args) {
 
    	//JasperReportBusinessImpl reportBusiness = new JasperReportBusinessImpl();
    	
        Collection<Employee> list = new ArrayList<>();
        list.add(new Employee(101, "Ravinder Shah",  67000, (float) 2.5));
        list.add(new Employee(102, "John Smith",  921436, (float) 9.5));
        list.add(new Employee(103, "Kenneth Johnson",  73545, (float) 1.5));
        list.add(new Employee(104, "John Travolta",  43988, (float) 0.5));
        list.add(new Employee(105, "Peter Parker",  93877, (float) 3.5));
        list.add(new Employee(106, "Leonhard Euler",  72000, (float) 2.3));
        list.add(new Employee(107, "William Shakespeare",  33000, (float) 1.4));
        list.add(new Employee(108, "Arup Bindal",  92000, (float) 6.2));
        list.add(new Employee(109, "Arin Kohfman",  55000, (float) 8.5));
        list.add(new Employee(110, "Albert Einstein",  89000, (float) 8.2));
        
        try {
            //JasperViewer jasperViewer = new JasperViewer(new ByteArrayInputStream(reportBusiness.build(Employee.class, list)),Boolean.TRUE);
            //jasperViewer.setVisible(true);
 
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }
    
}