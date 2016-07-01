package org.cyk.system.root.business.impl.file;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.persistence.api.file.ScriptDao;

public class ScriptBusinessImpl extends AbstractTypedBusinessService<Script, ScriptDao> implements ScriptBusiness, Serializable {
	private static final long serialVersionUID = 8072220305781523624L;
	
	private static final String DEFAULT_ENGINE_NAME = "javascript";
	
	@Inject private FileBusiness fileBusiness;
	
	@Inject
	public ScriptBusinessImpl(ScriptDao dao) { 
		super(dao);
	} 

	@Override
	public Map<String, Object> evaluate(Script script,Map<String, Object> inputs) {
		Map<String, Object> variablesValue = new HashMap<String, Object>();
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName(engineName(script));

		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.clear();
		
		if(inputs!=null)
			for (Entry<String, Object> entry : inputs.entrySet())
				bindings.put(entry.getKey(), entry.getValue());

		try {
			engine.eval(IOUtils.toString(fileBusiness.findInputStream(script.getFile())), bindings);
		} catch (Exception e) {
			exceptionUtils().exception(e);
		}
		
		for (String variable : script.getVariables())
			variablesValue.put(variable, bindings.get(variable));
		return variablesValue;
	}

	private String engineName(Script script) {
		return DEFAULT_ENGINE_NAME;
	}

}
