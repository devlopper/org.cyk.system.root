package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.model.file.report.LabelValueCollectionReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractReportProducer extends AbstractRootBusinessBean implements Serializable {

	private static final long serialVersionUID = 7126711234011563710L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractReportProducer.class);
	
	protected LabelValueCollectionReport currentLabelValueCollection;
	
	protected LabelValueCollectionReport labelValueCollection(String labelId){
		currentLabelValueCollection = new LabelValueCollectionReport();
		currentLabelValueCollection.setName(rootBusinessLayer.getLanguageBusiness().findText(labelId));
		return currentLabelValueCollection;
	}
	
	protected void labelValue(LabelValueCollectionReport collection,String id,String value,Boolean condition){
		if(!Boolean.TRUE.equals(condition))
			return;
		currentLabelValueCollection = collection;
		currentLabelValueCollection.add(id,languageBusiness.findText(id), value);
	}
	protected void labelValue(String labelId,String value,Boolean condition){
		labelValue(currentLabelValueCollection, labelId, value,condition);
	}
	
	protected void labelValue(LabelValueCollectionReport collection,String labelId,String value){
		labelValue(collection, labelId, value,Boolean.TRUE);
	}
	protected void labelValue(String labelId,String value){
		labelValue(currentLabelValueCollection,labelId, value);
	}

	@Override
	protected Logger __logger__() {
		return LOGGER;
	}

}
