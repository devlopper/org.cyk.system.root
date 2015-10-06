package org.cyk.system.root.model.file.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractModelElement;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LabelValueCollection extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 9095448953171778448L;

	private String name;
	private List<LabelValue> collection = new ArrayList<>(); 
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	public void add(String labelId,String label,String value){
		collection.add(new LabelValue(labelId,label, value));
	}
	
	public LabelValue getById(String labelId){
		for(LabelValue labelValue : collection)
			if(labelValue.getLabelId().equals(labelId))
				return labelValue;
		return null;
	}
	
	@Override
	public String toString() {
		return StringUtils.join(collection,",");
	}

}
