package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.model.AbstractFormatter;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.userinterface.style.Style;
import org.cyk.system.root.model.userinterface.style.Text;
import org.cyk.utility.common.generator.AbstractGeneratable;

@Getter @Setter
public class LabelValueCollectionReport extends AbstractGeneratable<LabelValueCollectionReport> implements Serializable {

	private static final long serialVersionUID = -3815250939177148339L;

	private String name,html;
	private List<LabelValueReport> collection = new ArrayList<>();
	private LabelValueItemStyle labelStyle,valueStyle;
	
	@Override
	public void generate() {
		name = RandomStringUtils.randomAlphabetic(5);
		for(int i=0;i<provider.randomInt(3, 6);i++){
			LabelValueReport labelValueReport = new LabelValueReport();
			labelValueReport.generate();
			collection.add(labelValueReport);
		}
	}
	
	public void add(String identifier,String label,String value){
		collection.add(new LabelValueReport(identifier, label, value));
	}
	public void add(String label,String value){
		add(null, label, value);
	}
	public void add(String label){
		add(null, label, null);
	}
	
	/**/
	
	public String getHtml(){
		if(html==null){
			StringBuilder htmlBuilder = new StringBuilder("<table>");
			
			html = htmlBuilder.toString();
		}
		return html;
	}
	
	@Getter @Setter
	public static class Formatter extends AbstractFormatter<LabelValueCollectionReport> implements Serializable{

		private static final long serialVersionUID = 3557901053791630451L;
		
		
		private String tableClass="lvcTableClass",rowClass="lvcRowClass",labelColumnClass="lvcLabelClass",valueColumnClass="lvcValueClass";
		
		@Override
		public String format(LabelValueCollectionReport labelValueCollectionReport,ContentType contentType) {
			StringBuilder contentBuilder = new StringBuilder();
			switch(contentType){
			case HTML:
				StringBuilder rowsBuilder = new StringBuilder();
				for(LabelValueReport labelValueReport : labelValueCollectionReport.getCollection()){
					StringBuilder tdsBuilder = new StringBuilder();
					appendHtmlTag(tdsBuilder,TD_FORMAT, labelValueReport.getLabel(),labelColumnClass);
					appendHtmlTag(tdsBuilder,TD_FORMAT, labelValueReport.getValue(),valueColumnClass);
					
					appendHtmlTag(rowsBuilder,TR_FORMAT, tdsBuilder.toString(),rowClass);
				}
				
				appendHtmlTag(contentBuilder, TABLE_FORMAT, rowsBuilder.toString(),tableClass);
				break;
			default:
				
				break;
			}
			return contentBuilder.toString();
		}
		
		
		
	}
	
	/**/
	@Getter
	public static class LabelValueItemStyle implements Serializable{
		private static final long serialVersionUID = -6678121839387110910L;
		
		private Style style = new Style();
		private Text text = new Text();
	}

}
