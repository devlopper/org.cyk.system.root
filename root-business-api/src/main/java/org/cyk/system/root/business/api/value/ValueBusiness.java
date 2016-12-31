package org.cyk.system.root.business.api.value;

import java.beans.Introspector;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractIdentifiableReport;
import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.AbstractActorReport;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonReport;
import org.cyk.system.root.model.value.Value;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.generator.AbstractGeneratable;

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
		private Listener listener;
		
		public Map<String,Object> getInputs(){
			if(inputs == null)
				inputs = new HashMap<>();
			return inputs;
		}
		
		public DeriveArguments addInput(String name,Object instance){
			getInputs().put(name, instance);
			return this;
		}
		
		public DeriveArguments addInput(Object instance){
			if(instance==null)
				return this;
			Class<?> aClass = instance.getClass(); //instance instanceof AbstractIdentifiable ? instance.getClass() : ((AbstractIdentifiableReport<?>)instance).getSource().getClass();
			String name = Introspector.decapitalize(aClass.getSimpleName());
			if(instance instanceof AbstractIdentifiableReport || instance instanceof AbstractGeneratable)
				name = StringUtils.substringBefore(name, "Report");
			return addInput(name, instance);
		}
		
		public DeriveArguments addInputs(Object...instances){
			for(Object instance : instances){
				if(instance instanceof AbstractActor || instance instanceof AbstractActorReport<?>)
					setActor(instance);
				else if(instance instanceof Person || instance instanceof PersonReport)
					setPerson(instance,Boolean.TRUE);
				addInput(instance);
				if(instance instanceof AbstractReportTemplateFile)
					addInput(((AbstractReportTemplateFile<?>)instance).getSource());
			}
			return this;
		}
		
		public DeriveArguments setGlobalIdentifier(Object identifiable){
			return addInput(identifiable instanceof AbstractIdentifiable ? ((AbstractIdentifiable)identifiable).getGlobalIdentifier() 
					: ((AbstractIdentifiableReport<?>)identifiable).getGlobalIdentifier());
		}
		
		public DeriveArguments setPerson(Object person,Boolean cascade){
			addInput(person);
			if(Boolean.TRUE.equals(cascade)){
				setGlobalIdentifier(person);
			}
			return this;
		}
		
		public DeriveArguments setActor(Object actor,Boolean cascade){
			addInput(actor);
			addInput("actor", actor);
			if(Boolean.TRUE.equals(cascade)){
				setPerson(actor instanceof AbstractActor ? ((AbstractActor)actor).getPerson() : ((AbstractActorReport<?>)actor).getPerson(),cascade);	
			}
			return this;
		}
		
		public DeriveArguments setActor(Object actor){
			return setActor(actor, Boolean.TRUE);
		}
		
		/**/
		
		public static interface Listener {
			
			void processInputs(Value value,Map<String,Object> inputs);
			
			/**/
			
			public static class Adapter extends BeanAdapter implements Listener,Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void processInputs(Value value,Map<String, Object> inputs) {}
				
				/**/
				
				public static class Default extends Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					/**/
					
				}
				
			}
		}
	}
	
	
}
