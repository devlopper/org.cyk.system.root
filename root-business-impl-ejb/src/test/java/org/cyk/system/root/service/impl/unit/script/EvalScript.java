package org.cyk.system.root.service.impl.unit.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EvalScript {
	
	public static void main(String[] args) throws Exception {
		// create a script engine manager
		ScriptEngineManager factory = new ScriptEngineManager();
		// create a JavaScript engine
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		// evaluate JavaScript code from String
		engine.eval("print('Hello, World')");
	}
}