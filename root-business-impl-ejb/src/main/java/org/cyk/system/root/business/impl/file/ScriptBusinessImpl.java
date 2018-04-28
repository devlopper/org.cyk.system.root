package org.cyk.system.root.business.impl.file;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.file.ScriptVariableCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness;
import org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptEvaluationEngine;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.persistence.api.file.ScriptDao;
import org.cyk.system.root.persistence.api.file.ScriptVariableDao;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ThrowableHelper;

public class ScriptBusinessImpl extends AbstractTypedBusinessService<Script, ScriptDao> implements ScriptBusiness, Serializable {
	private static final long serialVersionUID = 8072220305781523624L;
	
	@Inject
	public ScriptBusinessImpl(ScriptDao dao) { 
		super(dao);
	}
	
	@Override
	public Script instanciateOne() {
		return super.instanciateOne().setEvaluationEngine(InstanceHelper.getInstance().getDefaultUsingBusinessIdentifier(ScriptEvaluationEngine.class));
	}
	
	@Override
	public Collection<String> findRelatedInstanceFieldNames(Script identifiable) {
		return CollectionHelper.getInstance().add(super.findRelatedInstanceFieldNames(identifiable),Script.FIELD_FILE/*,Script.FIELD_VARIABLE_COLLECTION*/);
	}
	
	@Override
	protected void beforeCrud(Script script, Crud crud) {
		super.beforeCrud(script, crud);
		if(Crud.isCreateOrUpdate(crud)){
			createIfNotIdentified(script.getVariableCollection());
		}
	}
	
	@Override
	protected void afterDelete(Script script) {
		super.afterDelete(script);
		if(script.getVariableCollection()!=null)
			inject(ScriptVariableCollectionBusiness.class).delete(script.getVariableCollection());
	}
		
	@Override
	public Object evaluate(final Script script) {
		LoggingHelper.Logger<?,?,?> loggingMessageBuilder = LoggingHelper.getInstance().getLogger();
		loggingMessageBuilder.addNamedParameters("Evaluate", "script");
		ScriptEngineManager manager = new ScriptEngineManager();
		String engineName = script.getEvaluationEngine().getCode();
		loggingMessageBuilder.addNamedParameters("Engine",engineName);
		ScriptEngine engine = manager.getEngineByName(engineName);
		
		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.clear();
		
		bindings.put(RootConstant.Configuration.Script.STRING_HELPER, StringHelper.getInstance());
		bindings.put(RootConstant.Configuration.Script.FIELD_HELPER, FieldHelper.getInstance());
		
		bindings.put(RootConstant.Configuration.Script.GENERIC_BUSINESS, inject(GenericBusiness.class));
		bindings.put(RootConstant.Configuration.Script.NUMBER_BUSINESS, inject(NumberBusiness.class));
		bindings.put(RootConstant.Configuration.Script.TIME_BUSINESS, inject(TimeBusiness.class));
		bindings.put(RootConstant.Configuration.Script.METRIC_BUSINESS, inject(MetricBusiness.class));
		bindings.put(RootConstant.Configuration.Script.VALUE_BUSINESS, inject(ValueBusiness.class));
		bindings.put(RootConstant.Configuration.Script.METRIC_VALUE_BUSINESS, inject(MetricValueBusiness.class));
		
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.processInputs(script);
			}
		});
		
		if(!script.getInputs().isEmpty())
			loggingMessageBuilder.addNamedParameters("Inputs",script.getInputs());
		for (Entry<String, Object> entry : script.getInputs().entrySet())
			bindings.put(entry.getKey(), entry.getValue());
		
		try {
			String string = IOUtils.toString(inject(FileBusiness.class).findInputStream(script.getFile()));
			loggingMessageBuilder.addNamedParameters("Text",string);
			script.setReturned(engine.eval(string, bindings));
			loggingMessageBuilder.addNamedParameters("Returned",script.getReturned());
			
			script.getOutputs().clear();
			if(script.getVariableCollection()!=null)
				for (ScriptVariable scriptVariable : inject(ScriptVariableDao.class).readByCollection(script.getVariableCollection()))
					script.getOutputs().put(scriptVariable.getCode(), bindings.get(scriptVariable.getCode()));
			if(!script.getOutputs().containsKey(RootConstant.Configuration.ScriptVariable.RETURNED))
				script.getOutputs().put(RootConstant.Configuration.ScriptVariable.RETURNED, script.getReturned());
			loggingMessageBuilder.addNamedParameters("Outputs",script.getOutputs());
		} catch (Exception exception) {
			ThrowableHelper.getInstance().throw_(exception);
		} finally {
			logTrace(loggingMessageBuilder);
		}
		return script.getReturned();
	}
	
	/**/
	
	public static interface Listener extends AbstractIdentifiableBusinessServiceImpl.Listener<Script> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/
		
		void processInputs(Script script);
		
		/**/
		
		public static class Adapter extends AbstractIdentifiableBusinessServiceImpl.Listener.Adapter<Script> implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void processInputs(Script script) {}
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				/**/
			
				public static class EnterpriseResourcePlanning extends Default implements Serializable {
					private static final long serialVersionUID = 1L;
					
					/**/
					
				}
				
			}
			
		}
	}

	/**/
	
	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<Script> {

		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Script.class);
			addFieldCodeName();
			addParameterArrayElementString(Script.FIELD_EVALUATION_ENGINE);
		}
		
		@Override
		protected Script __execute__() {
			Script script = super.__execute__();
			script.setFile(new File());
			script.getFile().setBytes( ((java.lang.String)getInput()[3]).getBytes());
			return script;
		}
		
	}

}
