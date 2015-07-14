package org.cyk.system.root.business.impl.file;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.TemplateEngineBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.cdi.AbstractBean;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

@Singleton
public class FreeMarkerTemplateEngineImpl extends AbstractBean implements TemplateEngineBusiness {

	private static final long serialVersionUID = 3246660523691208345L;
	@Inject private FileBusiness fileBusiness;
	private Configuration configuration;

	@Override
	protected void initialisation() {
	    super.initialisation();
	    configuration = new Configuration();
        configuration.setTemplateLoader(new FileTemplateLoader());
	    // Some other recommended settings:
        configuration.setIncompatibleImprovements(new Version(2, 3, 20));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.FRENCH);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
	}

	@Override
	public String process(File file, Map<String, Object> parameters) {
		Template template = null;
		Writer contentWriter = new StringWriter();
		try {
		    template = configuration.getTemplate(file.getIdentifier().toString());
			template.setOutputEncoding("UTF-8");
			template.process(parameters, contentWriter);
		} catch (Exception e) {
			__logger__().error(e.toString(), e);
		}
		return contentWriter.toString();
	}
	
	private class FileTemplateLoader implements TemplateLoader ,Serializable {

		private static final long serialVersionUID = 1589849417249108L;

		@Override
        public Object findTemplateSource(String identifier) throws IOException {
            return StringUtils.substring(identifier, 0, StringUtils.lastIndexOf(identifier, "_"));
        }
	    
	    @Override
        public long getLastModified(Object templateSource) {
            return 0;
        }
	    
	    @Override
        public Reader getReader(Object identifier, String encoding) throws IOException {
	        File file = fileBusiness.find(Long.parseLong((String) identifier));
	        if(file==null)
	            ExceptionUtils.getInstance().resourceNotFound();
	        InputStream inputStream = fileBusiness.findInputStream(file);
            return new InputStreamReader(inputStream);
        }
	    
        @Override
        public void closeTemplateSource(Object anObject) throws IOException {}

	}

}
