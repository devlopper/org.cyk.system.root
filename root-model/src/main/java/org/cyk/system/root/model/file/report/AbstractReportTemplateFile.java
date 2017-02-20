package org.cyk.system.root.model.file.report;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cyk.system.root.model.party.person.PersonReport;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.generator.RandomDataProvider.RandomPerson;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractReportTemplateFile<TEMPLATE> extends AbstractGeneratable<TEMPLATE> implements Serializable {
	  
	private static final long serialVersionUID = 5632592320990657808L;

	protected String name,header,footer,title,creationDate,createdBy;
	protected Boolean isDraft = Boolean.FALSE;
	protected InputStream headerImage,backgroundImage,footerImage/*,signature*/;
	protected PersonReport signer = new PersonReport();
	protected Boolean generateHeaderImage=Boolean.TRUE,generateBackgroundImage=Boolean.TRUE,generateFooterImage=Boolean.TRUE,generateSigner=Boolean.TRUE;
	
	protected List<LabelValueCollectionReport> labelValueCollections = new ArrayList<>();

	@Override
	public void generate() {
		header = provider.randomLine(1, 2);
		footer = provider.randomLine(1, 2);
		title = provider.randomLine(1, 2);
		name = provider.randomLine(1, 2);
		creationDate = formatDate(new Date(),Constant.Date.Part.DATE_AND_TIME,Constant.Date.Length.LONG);
		RandomPerson randomPerson = provider.getMale();
		createdBy = randomPerson.firstName()+Constant.CHARACTER_SPACE+randomPerson.lastName();
		
		if(Boolean.TRUE.equals(generateHeaderImage))
			headerImage = generateHeaderImage();
		
		if(Boolean.TRUE.equals(generateBackgroundImage)) 
			backgroundImage = generateBackgroundImage();
		
		if(Boolean.TRUE.equals(generateFooterImage)) 
			footerImage = generateFooterImage();
		
		if(Boolean.TRUE.equals(generateSigner)) {
			signer.setGenerateSignatureSpecimen(Boolean.TRUE);
			signer.generate();
		}
	}
	
	protected InputStream generateHeaderImage(){
		return inputStream(provider.documentHeader().getBytes());
	}
	
	protected InputStream generateBackgroundImage(){
		return inputStream(provider.documentBackground().getBytes());
	}
	
	protected InputStream generateFooterImage(){
		return inputStream(provider.documentFooter().getBytes());
	}
	
	protected InputStream generateSignature(){
		return inputStream(provider.signatureSpecimen().getBytes());
	}
	
	public LabelValueCollectionReport getCurrentLabelValueCollection(){
		if(labelValueCollections.isEmpty())
			return null;
		return labelValueCollections.get(labelValueCollections.size()-1);
	}
	
	public AbstractReportTemplateFile<TEMPLATE> addLabelValueCollection(LabelValueCollectionReport labelValueCollectionReport){
		labelValueCollections.add(labelValueCollectionReport);
		return this;
	}
	
	public AbstractReportTemplateFile<TEMPLATE> addLabelValueCollection(String name){
		LabelValueCollectionReport labelValueCollectionReport = new LabelValueCollectionReport();
		labelValueCollectionReport.setName(name);
		return addLabelValueCollection(labelValueCollectionReport);
	}

	public AbstractReportTemplateFile<TEMPLATE> addLabelValues(String name,String[][] values){
		addLabelValueCollection(name);
		addLabelValues(values);
		return this;
	}
	
	public AbstractReportTemplateFile<TEMPLATE> addLabelValues(String[][] values){
		getCurrentLabelValueCollection().addLabelValues(values);
		return this;
	}
	
	public AbstractReportTemplateFile<TEMPLATE> addLabelValue(String label,String value){
		getCurrentLabelValueCollection().add(label,value);
		return this;
	}
	
	public LabelValueCollectionReport addNotRenderedLabelValueCollection(){
		LabelValueCollectionReport labelValueCollectionReport = null;//addLabelValueCollection("NOT_RENDERED", null,null);
		//labelValueCollectionReport.setRendered(Boolean.FALSE);
		return labelValueCollectionReport;
	}
	
	/**/
	
	public LabelValueCollectionReport getLabelValueCollectionAtIndex(Integer index){
		//return labelValueCollections.get(index.intValue());
		return index < labelValueCollections.size() ? labelValueCollections.get(index.intValue()) : null;//NULL_LABEL_VALUE_COLLECTION;
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
	
	/**/
	
	private static final LabelValueCollectionReport NULL_LABEL_VALUE_COLLECTION = new LabelValueCollectionReport();
	static {
		NULL_LABEL_VALUE_COLLECTION.setName("??NULL??");
		NULL_LABEL_VALUE_COLLECTION.addLabelValues(new String[][]{{"??NULL??","??NULL??"}});
	}
}
