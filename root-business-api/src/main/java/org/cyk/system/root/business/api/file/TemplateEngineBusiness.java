package org.cyk.system.root.business.api.file;
import java.util.Map;

import org.cyk.system.root.model.file.File;

public interface TemplateEngineBusiness {
	
    /**
     * Process the given template by applying the real values
     * @param templateId
     * @param parameters
     * @return
     */
	String process(File file,Map<String,Object> parameters);

}
