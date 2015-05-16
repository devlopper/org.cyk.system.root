package org.cyk.system.root.service.impl.integration.report;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.report.ReportBusiness.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.business.impl.file.report.jasper.JasperReportBusinessImpl;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.service.impl.integration.AbstractBusinessIT;
import org.cyk.system.root.service.impl.unit.jasper.samplereport.Employee;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class ReportBasedOnDynamicBuilderIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }  
     
    @Inject private JasperReportBusinessImpl reportBusiness;
    
    @Override
    protected void _execute_() {
        super._execute_();
        
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
        
        ReportBasedOnDynamicBuilderParameters<Employee> parameters = new ReportBasedOnDynamicBuilderParameters<Employee>();
        parameters.setClazz(Employee.class);
        parameters.setDatas(list);
        parameters.setFileExtension("pdf");
        parameters.setPrint(Boolean.FALSE);
        
        //ReportBasedOnDynamicBuilder<Employee> report =  reportBusiness.build(Employee.class, list, "pdf", Boolean.FALSE);
        ReportBasedOnDynamicBuilder<Employee> report =  reportBusiness.build(parameters);
        
        reportBusiness.write(new java.io.File("h:\\"),report);
        
    }

    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
        
    }

    @Override
    protected void create() {
        
    }

    @Override
    protected void delete() {
        
    }

    

    @Override
    protected void read() {
        
    }

    @Override
    protected void update() {
        
    }
    
    

}
