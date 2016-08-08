package org.cyk.system.root.service.impl.integration.report;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.file.report.DefaultReportBasedOnDynamicBuilder;
import org.cyk.system.root.business.impl.file.report.ReportBasedOnDynamicBuilderAdapter;
import org.cyk.system.root.business.impl.file.report.jasper.DefaultJasperReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderIdentifiableConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.service.impl.integration.AbstractBusinessIT;
import org.cyk.system.root.service.impl.unit.jasper.samplereport.Employee;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.generator.RandomDataProvider.RandomFile;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

import lombok.Getter;
import lombok.Setter;

public class ReportBasedOnDynamicBuilderIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }  
     
    @Inject private RootBusinessLayer rootBusinessLayer;
    @Inject private FileBusiness fileBusiness;
    
    @Override
    protected void _execute_() {
        super._execute_();
    }

    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	installApplication();
        final Party owner = new Party();
        owner.setName("Effi-Dis");
        RandomFile randomFile = RandomDataProvider.getInstance().companyLogo();
        owner.setImage(fileBusiness.process(randomFile.getBytes(), "image."+randomFile.getExtension()));
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber("00112233");
        owner.getContactCollection().setPhoneNumbers(new ArrayList<PhoneNumber>());
        owner.getContactCollection().getPhoneNumbers().add(phoneNumber);
        phoneNumber = new PhoneNumber();
        phoneNumber.setNumber("99887744");
        owner.getContactCollection().getPhoneNumbers().add(phoneNumber);
        
        ReportBasedOnDynamicBuilderListener.GLOBALS.add(new ReportBasedOnDynamicBuilderAdapter(){
        	@Override
        	public void report(ReportBasedOnDynamicBuilder<?> report,ReportBasedOnDynamicBuilderParameters<?> parameters) {
        		parameters.setOwner(owner);
        	}
        });
        ReportBasedOnDynamicBuilderListener.IDENTIFIABLE_CONFIGURATIONS.add(new ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable, Object>(
        		rootBusinessLayer.getParameterGenericReportBasedOnDynamicBuilder(),EventMissedReason.class,CustomEventTypeReportModel.class) {
			private static final long serialVersionUID = -1966207854828857772L;

			@Override
			public Object model(AbstractIdentifiable eventType) {
				return new CustomEventTypeReportModel((EventMissedReason) eventType);
			}
			@Override
			public Boolean useCustomIdentifiableCollection() {
				return Boolean.TRUE;
			}
			@Override 
			public Collection<? extends AbstractIdentifiable> identifiables(ReportBasedOnDynamicBuilderParameters<Object> parameters) {		
				Collection<AbstractIdentifiable> r = new ArrayList<>();
				r.addAll(rootBusinessLayer.getGenericBusiness().use(EventMissedReason.class).find().all());
				return r;
			}
		});
        
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
        parameters.setModelClass(Employee.class);
        parameters.setDatas(list);
        parameters.setTitle("Liste des employes");
        parameters.setFileExtension("pdf");
        parameters.setPrint(Boolean.FALSE);
        parameters.setCreatedBy("User048");
        parameters.getReportBasedOnDynamicBuilderListeners().add(new DefaultReportBasedOnDynamicBuilder());
        parameters.getReportBasedOnDynamicBuilderListeners().add(new DefaultJasperReportBasedOnDynamicBuilder());
        
        rootBusinessTestHelper.reportBasedOnDynamicBuilderParameters(parameters);
        
        Collection<Class<? extends AbstractIdentifiable>> classes = new ArrayList<>();
        classes.add(EventMissedReason.class);
        classes.add(TimeDivisionType.class);
        for(Object clazz : classes)
        	rootBusinessTestHelper.reportBasedOnDynamicBuilderParameters((Class<?>) clazz);
        
        //---------------------------
        
        ReportBasedOnDynamicBuilderParameters<EmployeeLineReport> parameters2 = new ReportBasedOnDynamicBuilderParameters<EmployeeLineReport>();
        parameters2.setIdentifiableClass(Employee.class);
        parameters2.setModelClass(EmployeeLineReport.class);
        
        //rootTestHelper.reportBasedOnDynamicBuilderParameters(parameters2);
        
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
    
    @Getter @Setter
    public static class CustomEventTypeReportModel{
    	@Input private String f1;
    	@Input private String f2;
    	@Input private String f3;
    	public CustomEventTypeReportModel(EventMissedReason eventType) {
			f1 = eventType.getCode();
			f2 = eventType.getName();
			f3 = f1+" and the "+f2;
		}
    }

}
