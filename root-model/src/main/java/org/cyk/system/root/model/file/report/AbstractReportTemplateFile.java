package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.generator.AbstractGeneratable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractReportTemplateFile<TEMPLATE> extends AbstractGeneratable<TEMPLATE> implements Serializable {
	  
	private static final long serialVersionUID = 5632592320990657808L;

	protected String header,footer,title;
	protected Boolean provisional = Boolean.FALSE;
	
	protected LabelValueCollectionReport currentLabelValueCollection;
	protected List<LabelValueCollectionReport> labelValueCollections = new ArrayList<>();

	@Override
	public void generate() {
		header = provider.randomLine(1, 2);
		footer = provider.randomLine(1, 2);
		title = provider.randomLine(1, 2);
	}
	
	public LabelValueCollectionReport addLabelValueCollection(LabelValueCollectionReport labelValueCollectionReport){
		currentLabelValueCollection = labelValueCollectionReport;
		labelValueCollections.add(labelValueCollectionReport);
		return labelValueCollectionReport;
	}
	
	public LabelValueCollectionReport addLabelValueCollection(String name){
		LabelValueCollectionReport labelValueCollectionReport = new LabelValueCollectionReport();
		labelValueCollectionReport.setName(name);
		return addLabelValueCollection(labelValueCollectionReport);
	}
	
	public void labelValue(LabelValueCollectionReport collection,String labelId,String labelValue,String value,Boolean condition){
		if(!Boolean.TRUE.equals(condition))
			return;
		currentLabelValueCollection = collection;
		currentLabelValueCollection.add(labelId,labelValue, value);
	}
	
	public void labelValue(LabelValueCollectionReport collection,String labelId,String value,Boolean condition){
		labelValue(collection, labelId,null, value, condition);
	}
	
	public void labelValue(String id,String value,Boolean condition){
		labelValue(currentLabelValueCollection, id, value,condition);
	}
	
	public void labelValue(LabelValueCollectionReport collection,String id,String value){
		labelValue(collection, id, value,Boolean.TRUE);
	}
	
	public void labelValue(String id,String value){
		labelValue(currentLabelValueCollection,id, value);
	}
	
	public LabelValueReport getLabelValue(String id){
		return currentLabelValueCollection.getById(id);
	}
	
	public LabelValueCollectionReport randomLabelValueCollection(Integer count){
		LabelValueCollectionReport labelValueCollection = new LabelValueCollectionReport();
		labelValueCollection.generate();
		return labelValueCollection;
	}
	
	public LabelValueCollectionReport randomLabelValueCollection(){
		return randomLabelValueCollection(5);
	}
	
	
	
	public void addLabelValues(LabelValueCollectionReport labelValueCollection,String[][] values,String nullValue){
		if(values!=null)
			for(String[] array : values){
				if(array[1]==null)
					array[1] = nullValue;
				if(array[1]==null)
					;
				else{
					LabelValueReport labelValue = labelValueCollection.add(array[0], array[1]);
					if(array.length>2)
						labelValue.setExtendedValues(ArrayUtils.subarray(array, 2, array.length));
				}
			}
		//addLabelValueCollection(labelValueCollectionReport);
	}
	
	public LabelValueCollectionReport addLabelValueCollection(String name,String[][] values,String nullValue){
		LabelValueCollectionReport labelValueCollectionReport = addLabelValueCollection(name);
		addLabelValues(labelValueCollectionReport,values,nullValue);
		return labelValueCollectionReport;
	}
	
	public LabelValueCollectionReport addLabelValueCollection(String name,String[][] values){
		return addLabelValueCollection(name, values,null);
	}
	
	public LabelValueCollectionReport addNotRenderedLabelValueCollection(){
		LabelValueCollectionReport labelValueCollectionReport = addLabelValueCollection("NOT_RENDERED", null,null);
		labelValueCollectionReport.setRendered(Boolean.FALSE);
		return labelValueCollectionReport;
	}
	
	/**/
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex(Integer index){
		return labelValueCollections.get(index.intValue());
		//return index < labelValueCollections.size() ? labelValueCollections.get(index.intValue()) : null;
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex0(){
		return getLabelValueCollectionAtIndex(0);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex1(){
		return getLabelValueCollectionAtIndex(1);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex2(){
		return getLabelValueCollectionAtIndex(2);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex3(){
		return getLabelValueCollectionAtIndex(3);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex4(){
		return getLabelValueCollectionAtIndex(4);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex5(){
		return getLabelValueCollectionAtIndex(5);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex6(){
		return getLabelValueCollectionAtIndex(6);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex7(){
		return getLabelValueCollectionAtIndex(7);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex8(){
		return getLabelValueCollectionAtIndex(8);
	}
	public LabelValueCollectionReport getLabelValueCollectionAtIndex9(){
		return getLabelValueCollectionAtIndex(9);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex10(){
		return getLabelValueCollectionAtIndex(10);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex11(){
		return getLabelValueCollectionAtIndex(11);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex12(){
		return getLabelValueCollectionAtIndex(12);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex13(){
		return getLabelValueCollectionAtIndex(13);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex14(){
		return getLabelValueCollectionAtIndex(14);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex15(){
		return getLabelValueCollectionAtIndex(15);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex16(){
		return getLabelValueCollectionAtIndex(16);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex17(){
		return getLabelValueCollectionAtIndex(17);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex18(){
		return getLabelValueCollectionAtIndex(18);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex19(){
		return getLabelValueCollectionAtIndex(19);
	}
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex20(){
		return getLabelValueCollectionAtIndex(20);
	}
}
