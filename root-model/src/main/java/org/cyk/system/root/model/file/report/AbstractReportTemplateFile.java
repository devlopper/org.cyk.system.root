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
	protected List<LabelValueCollectionReport> labelValueCollections = new ArrayList<>();
	
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
	
	/**/
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex0(){
		return labelValueCollections.get(0);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex1(){
		return labelValueCollections.get(1);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex2(){
		return labelValueCollections.get(2);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex3(){
		return labelValueCollections.get(3);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex4(){
		return labelValueCollections.get(4);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex5(){
		return labelValueCollections.get(5);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex6(){
		return labelValueCollections.get(6);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex7(){
		return labelValueCollections.get(7);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex8(){
		return labelValueCollections.get(8);
	}
	public LabelValueCollectionReport getLabelValueCollectionAtIndex9(){
		return labelValueCollections.get(9);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex10(){
		return labelValueCollections.get(10);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex11(){
		return labelValueCollections.get(11);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex12(){
		return labelValueCollections.get(12);
	}
}
