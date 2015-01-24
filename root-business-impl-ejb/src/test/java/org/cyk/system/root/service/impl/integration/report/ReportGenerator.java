package org.cyk.system.root.service.impl.integration.report;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.impl.file.JasperReportBusinessImpl;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.Report;
import org.cyk.system.root.service.impl.integration.AbstractBusinessIT;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class ReportGenerator extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }  
     
    @Inject private JasperReportBusinessImpl reportBusiness;
    private File model1,model2; 
    
    @Override
    protected void populate() {
    	model1 = template("model1.jrxml");
    	model2 = template("model2.jrxml");
    }
    
    private File template(String filePath){
    	File file = new File();
        try {
			file.setBytes(IOUtils.toByteArray(getClass().getResourceAsStream(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
        create(file);
        return file;
    }

    
    @Override
    protected void _execute_() {
        super._execute_();
        Report<Object> report = new Report<>();
        report.getDataSource().add(new Object());
        report.getDataSource().add(new Object());
        report.setFileExtension("pdf");
        
        report.setTemplateFile(model1);
        reportBusiness.build(report, Boolean.FALSE);
        
        report.setTemplateFile(model2);
        reportBusiness.build(report, Boolean.FALSE);
        
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
