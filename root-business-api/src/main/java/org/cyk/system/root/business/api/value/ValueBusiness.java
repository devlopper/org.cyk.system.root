package org.cyk.system.root.business.api.value;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.value.Value;

import lombok.Getter;
import lombok.Setter;

public interface ValueBusiness extends TypedBusiness<Value> {

	void setRandomly(Collection<Value> values);
	
	Object derive(Value value,DeriveArguments arguments);
	void derive(Collection<Value> values,DeriveArguments arguments);
	
	Collection<Value> deriveByCodes(Collection<String> valueCodes,DeriveArguments arguments);
	Value deriveByCode(String valueCode,DeriveArguments arguments);
	
	/**/
	
	@Getter @Setter
	public static class DeriveArguments implements Serializable {
		private static final long serialVersionUID = 9078040654840071139L;
		
		private Map<String,Object> inputs;
		
		public Map<String,Object> getInputs(){
			if(inputs == null)
				inputs = new HashMap<>();
			return inputs;
		}
	}
}
