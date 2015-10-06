package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.generator.AbstractGeneratable;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractReportTemplateFile<TEMPLATE> extends AbstractGeneratable<TEMPLATE> implements Serializable {
	  
	private static final long serialVersionUID = 5632592320990657808L;

	protected LabelValueCollection currentLabelValueCollection;
	protected List<LabelValueCollection> labelValueCollections = new ArrayList<>();
	
	protected void labelValue(LabelValueCollection collection,String labelId,String labelValue,String value,Boolean condition){
		if(!Boolean.TRUE.equals(condition))
			return;
		currentLabelValueCollection = collection;
		currentLabelValueCollection.add(labelId,labelValue, value);
	}
	protected void labelValue(LabelValueCollection collection,String labelId,String value,Boolean condition){
		labelValue(collection, labelId,null, value, condition);
	}
	
	protected void labelValue(String id,String value,Boolean condition){
		labelValue(currentLabelValueCollection, id, value,condition);
	}
	
	protected void labelValue(LabelValueCollection collection,String id,String value){
		labelValue(collection, id, value,Boolean.TRUE);
	}
	protected void labelValue(String id,String value){
		labelValue(currentLabelValueCollection,id, value);
	}
	
	protected LabelValue getLabelValue(String id){
		return currentLabelValueCollection.getById(id);
	}
	
	protected LabelValueCollectionReport randomLabelValueCollection(Integer count){
		LabelValueCollectionReport labelValueCollection = new LabelValueCollectionReport();
		labelValueCollection.generate();
		return labelValueCollection;
	}
	
	protected LabelValueCollectionReport randomLabelValueCollection(){
		return randomLabelValueCollection(5);
	}
	
}
