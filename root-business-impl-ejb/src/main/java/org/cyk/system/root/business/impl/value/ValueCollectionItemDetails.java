package org.cyk.system.root.business.impl.value;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionItemDetails;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ValueCollectionItemDetails extends AbstractCollectionItemDetails.AbstractDefault<ValueCollectionItem,ValueCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private FieldValue value;
	
	public ValueCollectionItemDetails(ValueCollectionItem valueCollectionItem) {
		super(valueCollectionItem);
	}
	
	@Override
	public void setMaster(ValueCollectionItem valueCollectionItem) {
		super.setMaster(valueCollectionItem);
		if(valueCollectionItem!=null){
			if(valueCollectionItem.getValue()!=null)
				value = new FieldValue(valueCollectionItem.getValue());
		}
	}
	
	public static final String FIELD_VALUE = "value";
	
}