package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.impl.file.report.AbstractReportRepository;
import org.cyk.system.root.business.impl.file.report.DefaultReportBasedOnDynamicBuilder;
import org.cyk.system.root.business.impl.file.report.jasper.DefaultJasperReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderIdentifiableConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;

import lombok.Getter;

@Singleton //@Deployment(initialisationType=InitialisationType.EAGER,order=RootBusinessLayer.DEPLOYMENT_ORDER+1)
public class RootReportRepository extends AbstractReportRepository implements Serializable {

	private static final long serialVersionUID = -2638067951038200976L;

	@Inject private GenericBusiness genericBusiness;
	
	@Getter private final String parameterGenericReportBasedOnDynamicBuilder = "grbodb"; 
	
	@Override
	public void build() {
		registerConfiguration(new ReportBasedOnDynamicBuilderConfiguration<Object, ReportBasedOnDynamicBuilder<Object>>(parameterGenericReportBasedOnDynamicBuilder) {        	
			@SuppressWarnings("unchecked")
			@Override
			public ReportBasedOnDynamicBuilder<Object> build(ReportBasedOnDynamicBuilderParameters<Object> parameters) {
				logTrace("Building report based on dynamic builder for Identifier={} IdentifiableClass={}",parameterGenericReportBasedOnDynamicBuilder,parameters.getIdentifiableClass());
				parameters.setDatas(new ArrayList<Object>());
				if(parameters.getDatas()==null || parameters.getDatas().isEmpty()){
					if(parameters.getIdentifiableClass()==null){
						logError("Identifiable class is null");
					}else{
						logTrace("Looping through identifiable configurations. count is {}",ReportBasedOnDynamicBuilderListener.IDENTIFIABLE_CONFIGURATIONS.size());
						ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable, Object> identifiableConfiguration = null;
						for(ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable, Object> ic : ReportBasedOnDynamicBuilderListener.IDENTIFIABLE_CONFIGURATIONS){
							logTrace("Comparing with {} , {}", ic.getReportBasedOnDynamicBuilderIdentifier(),ic.getIdentifiableClass());
							if(parameterGenericReportBasedOnDynamicBuilder.equals(ic.getReportBasedOnDynamicBuilderIdentifier()) 
									&& ic.getIdentifiableClass().equals(parameters.getIdentifiableClass())
									//&& ic.getModelClass().equals(parameters.getModelClass())
									){
								identifiableConfiguration = ic;
								if(parameters.getModelClass()==null)
									parameters.setModelClass((Class<Object>) ic.getModelClass());
								logTrace("Identifiable configuration found. {}",identifiableConfiguration);
								break;
							}
						}
						Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
						
						if(identifiableConfiguration==null || !Boolean.TRUE.equals(identifiableConfiguration.useCustomIdentifiableCollection())){
							identifiables.addAll(genericBusiness.use(parameters.getIdentifiableClass()).find().all());
						}else{
							identifiables.addAll(identifiableConfiguration.identifiables(parameters));	
						}
						logTrace("Data count is {}",identifiables.size());
						for(AbstractIdentifiable identifiable : identifiables){
				        	Object value = identifiableConfiguration == null ? identifiable:identifiableConfiguration.model(identifiable);
							parameters.getDatas().add(value);	
						}
						
						if(identifiableConfiguration!=null)
							identifiableConfiguration.beforeBuild(parameters);
					}
				}
				
		        parameters.getReportBasedOnDynamicBuilderListeners().add(new DefaultReportBasedOnDynamicBuilder());
		        parameters.getReportBasedOnDynamicBuilderListeners().add(new DefaultJasperReportBasedOnDynamicBuilder());
				return (ReportBasedOnDynamicBuilder<Object>) reportBusiness.build(parameters);
			}
		});
	}

}
