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
	
	Object derive(Value value,Derive listener);
	void derive(Collection<Value> values,Derive listener);
	
	Collection<Value> deriveByCodes(Collection<String> valueCodes,Derive listener);
	Value deriveByCode(String valueCode,Derive listener);
	
	/**/
	
	public static interface Derive {
		
		Map<String,Object> getInputs();
		Derive setInputs(Map<String,Object> inputs);
		
		Derive addInput(String name,Object instance);
		Derive addInputs(Object...instances);
		
		void processInputs(Value value,Map<String,Object> inputs);
		
		@Getter @Setter
		public static class Adapter extends BeanAdapter implements Derive,Serializable {
			private static final long serialVersionUID = 1L;

			protected Map<String,Object> inputs;

			@Override
			public Derive setInputs(Map<String, Object> inputs) {
				return null;
			}

			@Override
			public Derive addInput(String name, Object instance) {
				return null;
			}

			@Override
			public Derive addInputs(Object... instances) {
				return null;
			}
			
			@Override
			public void processInputs(Value value, Map<String, Object> inputs) {
				
			}
			
			protected String getInstanceVariableName(Object instance){
				Class<?> aClass = instance.getClass(); //instance instanceof AbstractIdentifiable ? instance.getClass() : ((AbstractIdentifiableReport<?>)instance).getSource().getClass();
				String name = Introspector.decapitalize(aClass.getSimpleName());
				if(instance instanceof AbstractIdentifiableReport || instance instanceof AbstractGeneratable)
					if(StringUtils.endsWith(name, "Report"))
						name = StringUtils.substringBefore(name, "Report");
				return name;
			}
			
			protected Derive setGlobalIdentifier(Object identifiable){
				return addInputs(identifiable instanceof AbstractIdentifiable ? ((AbstractIdentifiable)identifiable).getGlobalIdentifier() 
						: ((AbstractIdentifiableReport<?>)identifiable).getGlobalIdentifier());
			}
			
			
			
			/**/
			
			public static class Default extends Derive.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Map<String,Object> getInputs(){
					if(inputs == null)
						inputs = new HashMap<>();
					return inputs;
				}
				
				@Override
				public Derive addInput(String name, Object instance) {
					getInputs().put(name, instance);
					return this;
				}
				
				private void addInstance(Object instance){
					addInput(getInstanceVariableName(instance), instance);
				}
				
				@Override
				public Derive addInputs(Object... instances) {
					if(instances!=null)
						for(Object instance : instances){
							addInstance(instance);
							if(instance instanceof Person){
								
							}else if(instance instanceof AbstractActor){
								addInstance(((AbstractActor)instance).getPerson());
							}else if(instance instanceof AbstractActorReport<?>){
								addInstance(((AbstractActorReport<?>)instance).getPerson());
							}
						}
					return this;
				}
				
			}
		}
	}
	
	@Getter @Setter @Deprecated
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
