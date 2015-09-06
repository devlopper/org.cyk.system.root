package org.cyk.system.root.model.generator;

public interface ValueGenerator<INPUT,OUTPUT>{
	
	void setIdentifier(String value);
	String getIdentifier();
	
	void setDescription(String value);
	String getDescription();
	
	void setMethod(GenerateMethod<INPUT, OUTPUT> value);
	GenerateMethod<INPUT, OUTPUT> getMethod();
	
	Class<INPUT> getInputClass();
	
	Class<OUTPUT> getOutputClass();
	
	OUTPUT generate(INPUT input);
	
	/**/
	
	public static interface GenerateMethod<INPUT, OUTPUT>{
		OUTPUT execute(INPUT input);
	}
	
	String PARTY_CODE_IDENTIFIER = "PARTY_CODE_IDENTIFIER";
	String PARTY_CODE_DESCRIPTION = "generate a party code";
	
	String ACTOR_REGISTRATION_CODE_IDENTIFIER = "ACTOR_REGISTRATION_CODE";
	String ACTOR_REGISTRATION_CODE_DESCRIPTION = "generate an actor registration code";
}