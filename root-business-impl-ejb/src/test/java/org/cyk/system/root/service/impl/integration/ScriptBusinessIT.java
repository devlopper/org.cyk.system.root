package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.geography.ContactBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.PersonBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.GenericBusinessImpl;
import org.cyk.system.root.business.impl.file.ScriptBusinessImpl;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.business.impl.party.PersonBusinessImpl;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeBusinessImpl;
import org.cyk.system.root.business.impl.security.UserAccountBusinessImpl;
import org.cyk.system.root.business.impl.validation.ValidationPolicyImpl;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.file.ScriptDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.language.LanguageDao;
import org.cyk.system.root.persistence.api.party.PersonDao;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeDao;
import org.cyk.system.root.persistence.api.security.UserAccountDao;
import org.cyk.system.root.persistence.impl.QueryStringBuilder;
import org.cyk.system.root.persistence.impl.file.ScriptDaoImpl;
import org.cyk.system.root.persistence.impl.geography.ContactDaoImpl;
import org.cyk.system.root.persistence.impl.language.LanguageDaoImpl;
import org.cyk.system.root.persistence.impl.party.PersonDaoImpl;
import org.cyk.system.root.persistence.impl.pattern.tree.AbstractDataTreeDaoImpl;
import org.cyk.system.root.persistence.impl.security.UserAccountDaoImpl;
import org.cyk.utility.common.test.ArchiveBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class ScriptBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;

    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    	/*
    	return  
                new ArchiveBuilder().create().getArchive()
                    //.addClasses(BusinessIntegrationTestHelper.classes())
                    .addPackages(Boolean.FALSE, Script.class.getPackage(),AbstractDataTree.class.getPackage(),Language.class.getPackage()
                    		,ScriptDao.class.getPackage(),ScriptDaoImpl.class.getPackage(),AbstractDataTreeDao.class.getPackage(),AbstractDataTreeDaoImpl.class.getPackage(),LanguageDao.class.getPackage(),LanguageDaoImpl.class.getPackage()
                    		,ScriptBusiness.class.getPackage(),ScriptBusinessImpl.class.getPackage(),AbstractDataTreeBusiness.class.getPackage(),AbstractDataTreeBusinessImpl.class.getPackage(),LanguageBusiness.class.getPackage(),LanguageBusinessImpl.class.getPackage()
                    		,QueryStringBuilder.class.getPackage(),ValidationPolicy.class.getPackage(),ValidationPolicyImpl.class.getPackage(),GenericBusiness.class.getPackage(),
                    		GenericBusinessImpl.class.getPackage(),PersonBusiness.class.getPackage(),PersonBusinessImpl.class.getPackage(),PersonDao.class.getPackage(),PersonDaoImpl.class.getPackage(),
                    		UserAccountDao.class.getPackage(),UserAccountDaoImpl.class.getPackage(),UserAccount.class.getPackage(),Party.class.getPackage(),
                    		UserAccountBusiness.class.getPackage(),UserAccountBusinessImpl.class.getPackage(),Contact.class.getPackage(),ContactDao.class.getPackage(),ContactDaoImpl.class.getPackage(),
                    		ContactBusiness.class.getPackage()
                    		)
                    ;*/
    }  
     
    @Inject private ScriptBusiness scriptBusiness;
    private Script script1,script2; 
    
    @Override
    protected void populate() {
    	System.out.println("ScriptBusinessIT.populate()");
        //create(script1 = script("print('THIS IS A IT')"));
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
        System.out.println("ScriptBusinessIT._execute_()");
        //scriptBusiness.evaluate(script1, null);
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
