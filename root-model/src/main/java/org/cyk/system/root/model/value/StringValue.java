package org.cyk.system.root.model.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.cyk.system.root.model.value.AbstractValue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable @Getter @Setter @NoArgsConstructor 
public class StringValue extends AbstractValue<String> implements Serializable {

	private static final long serialVersionUID = 6459524990626259467L;
	
	/*
	 * The field is used to extend the type of the string value. this enable the support of any value type
	 * which can be define by the business
	 */
	@Column(name="valueType")
	private String type;
	
	public StringValue(String user) {
		super(user);
	}
	
}
