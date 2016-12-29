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
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.file.ScriptVariableBusiness;
import org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.persistence.api.file.ScriptDao;
import org.cyk.system.root.persistence.api.file.ScriptEvaluationEngineDao;
import org.cyk.system.root.persistence.api.file.ScriptVariableDao;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.LogMessage;

public class ScriptBusinessImpl extends AbstractTypedBusinessService<Script, ScriptDao> implements ScriptBusiness, Serializable {
	private static final long serialVersionUID = 8072220305781523624L;
	
	@Inject
	public ScriptBusinessImpl(ScriptDao dao) { 
		super(dao);
	}
	
	@Override
	protected void beforeCreate(Script script) {
		super.beforeCreate(script);
		createIfNotIdentified(script.getFile());
	}
	
	@Override
	protected void afterCreate(Script script) {
		super.afterCreate(script);
		if(script.getVariables().isSynchonizationEnabled())
			inject(ScriptVariableBusiness.class).create(script.getVariables().getCollection());
	}
	
	@Override
	protected void afterUpdate(Script script) {
		super.afterUpdate(script);
		
	}
	
	@Override
	public Script instanciateOne(String[] values) {
		Script script = instanciateOne();
		Integer index = 0;
		script.setCode(values[index++]);
		script.setEvaluationEngine(inject(ScriptEvaluationEngineDao.class).read(values[index++]));
		script.setFile(new File());
		script.getFile().setBytes(values[index++].getBytes());
		return script;
	}

	@Override
	public Object evaluate(final Script script) {
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Evaluate", "script");
		ScriptEngineManager manager = new ScriptEngineManager();
		String engineName = script.getEvaluationEngine().getCode();
		logMessageBuilder.addParameters("Engine",engineName);
		ScriptEngine engine = manager.getEngineByName(engineName);
		
		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.clear();
		
		bindings.put(RootConstant.Configuration.Script.GENERIC_BUSINESS, inject(GenericBusiness.class));
		
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.processInputs(script);
			}
		});
		
		if(!script.getInputs().isEmpty())
			logMessageBuilder.addParameters("Inputs",script.getInputs());
		for (Entry<String, Object> entry : script.getInputs().entrySet())
			bindings.put(entry.getKey(), entry.getValue());
		
		try {
			String string = IOUtils.toString(inject(FileBusiness.class).findInputStream(script.getFile()));
			logMessageBuilder.addParameters("Text",string);
			script.setReturned(engine.eval(string, bindings));
			logMessageBuilder.addParameters("Returned",script.getReturned());
		} catch (Exception e) {
			exceptionUtils().exception(e);
		}
		
		script.getOutputs().clear();
		for (ScriptVariable scriptVariable : inject(ScriptVariableDao.class).readByScript(script))
			script.getOutputs().put(scriptVariable.getCode(), bindings.get(scriptVariable.getCode()));
		if(!script.getOutputs().containsKey(RootConstant.Configuration.ScriptVariable.RETURNED))
			script.getOutputs().put(RootConstant.Configuration.ScriptVariable.RETURNED, script.getReturned());
		logMessageBuilder.addParameters("Outputs",script.getOutputs());
		logTrace(logMessageBuilder);
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

}
