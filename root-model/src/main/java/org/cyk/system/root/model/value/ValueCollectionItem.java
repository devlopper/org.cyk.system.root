package org.cyk.system.root.model.value;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.utility.common.annotation.FieldOverride;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @FieldOverride(name=AbstractCollectionItem.FIELD_COLLECTION,type=ValueCollection.class)
public class ValueCollectionItem extends AbstractCollectionItem<ValueCollection> implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @NotNull @JoinColumn(name="thevalue") private Value value;
	
	public ValueCollectionItem(ValueCollection collection, String code, String name) {
		super(collection, code, name);
	}
	
	public ValueCollectionItem(Value value) {
		this.value = value;
	}

	public static final String FIELD_VALUE = "value";
	
}