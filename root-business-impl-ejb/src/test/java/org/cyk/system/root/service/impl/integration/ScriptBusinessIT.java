package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.file.ScriptBusinessImpl;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.business.impl.validation.ValidationPolicyImpl;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.persistence.api.file.ScriptDao;
import org.cyk.system.root.persistence.api.language.LanguageDao;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.system.root.persistence.impl.file.ScriptDaoImpl;
import org.cyk.system.root.persistence.impl.language.LanguageDaoImpl;
import org.cyk.system.root.persistence.impl.pattern.tree.AbstractDataTreeDaoImpl;
import org.cyk.utility.common.test.ArchiveBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class ScriptBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        //return createRootDeployment();
    	
    	return  
                new ArchiveBuilder().create().getArchive()
                    //.addClasses(BusinessIntegrationTestHelper.classes())
                    .addPackages(Boolean.FALSE, Script.class.getPackage(),AbstractDataTree.class.getPackage(),Language.class.getPackage()
                    		,ScriptDao.class.getPackage(),ScriptDaoImpl.class.getPackage(),AbstractDataTreeDao.class.getPackage(),AbstractDataTreeDaoImpl.class.getPackage(),LanguageDao.class.getPackage(),LanguageDaoImpl.class.getPackage()
                    		,ScriptBusiness.class.getPackage(),ScriptBusinessImpl.class.getPackage(),AbstractDataTreeBusiness.class.getPackage(),AbstractDataTreeBusinessImpl.class.getPackage(),LanguageBusiness.class.getPackage(),LanguageBusinessImpl.class.getPackage()
                    		,QueryStringBuilder.class.getPackage(),ValidationPolicy.class.getPackage(),ValidationPolicyImpl.class.getPackage()
                    		)
                    ;
    }  
     
    @Inject private ScriptBusiness scriptBusiness;
    private Script script1,script2; 
    
    @Override
    protected void populate() {
        create(script1 = script("print('THIS IS A IT')"));
    }
    
    private Script script(String text){
        Script script = new Script();
        script.setFile(new File());
        script.getFile().setBytes(text.getBytes());
        return script;
    }

    
    @Override
    protected void _execute_() {
        super._execute_();
        scriptBusiness.evaluate(script1, null);
    }

    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
        
    }

    @Override
    protected void create() {
        
    }

    @Override
    protected void delete() {
        
    }

    

    @Override
    protected void read() {
        
    }

    @Override
    protected void update() {
        
    }

}
