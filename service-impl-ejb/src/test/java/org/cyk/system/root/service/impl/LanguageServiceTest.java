package org.cyk.system.root.service.impl;

import java.util.Locale;

import javax.ejb.EJB;

import org.cyk.system.root.dao.api.language.LanguageDao;
import org.cyk.system.root.dao.impl.AbstractDao;
import org.cyk.system.root.dao.impl.AbstractTypedDao;
import org.cyk.system.root.dao.impl.GenericDao;
import org.cyk.system.root.dao.impl.language.LanguageDaoImpl;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.service.api.IGenericModelService;
import org.cyk.system.root.service.api.IGenericService;
import org.cyk.system.root.service.api.IModelService;
import org.cyk.system.root.service.api.language.LanguageService;
import org.cyk.system.root.service.impl.language.LanguageServiceImpl;
import org.cyk.utility.common.cdi.AbstractBean;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class LanguageServiceTest extends AbstractServiceTest {
	
	@EJB
	private LanguageService service;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(AbstractBean.class,AbstractService.class,GenericService.class,Language.class,LanguageService.class,LanguageServiceImpl.class,
						LanguageDao.class,LanguageDaoImpl.class,IModelService.class,
						IGenericService.class,IGenericModelService.class,AbstractDao.class, AbstractTypedDao.class,GenericDao.class)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void frenchGoodMorning() {
		Assert.assertTrue("bonjour".equals(service.i18n("good.morning", Locale.FRENCH)));
	}
	
	@Test
	public void englishGoodMorning() {
		Assert.assertTrue("good morning".equals(service.i18n("good.morning", Locale.ENGLISH)));
	}
	
	@Test
	public void frenchUnknown() {
		Assert.assertTrue("##hello##".equals(service.i18n("hello", Locale.FRENCH)));
	}
	
	@Test
	public void englishUnknown() {
		Assert.assertTrue("##hello##".equals(service.i18n("hello", Locale.ENGLISH)));
	}

}
