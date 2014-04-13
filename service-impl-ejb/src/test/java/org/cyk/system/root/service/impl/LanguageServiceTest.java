package org.cyk.system.root.service.impl;

import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.root.business.api.IGenericModelService;
import org.cyk.system.root.business.api.IGenericService;
import org.cyk.system.root.business.api.IModelService;
import org.cyk.system.root.business.api.language.LanguageService;
import org.cyk.system.root.business.impl.AbstractService;
import org.cyk.system.root.business.impl.GenericService;
import org.cyk.system.root.business.impl.language.LanguageServiceImpl;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.persistence.api.language.LanguageDao;
import org.cyk.system.root.persistence.impl.AbstractPersistenceService;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;
import org.cyk.system.root.persistence.impl.language.LanguageDaoImpl;
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
	
	@Inject
	private LanguageService service;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(AbstractBean.class,AbstractService.class,GenericService.class,Language.class,LanguageService.class,LanguageServiceImpl.class,
						LanguageDao.class,LanguageDaoImpl.class,IModelService.class,
						IGenericService.class,IGenericModelService.class,AbstractPersistenceService.class, AbstractTypedDao.class,GenericDaoImpl.class)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Override
	protected Boolean initData() {
		return Boolean.FALSE;
	}
	
	@Test
	public void frenchGoodMorning() {
		Assert.assertTrue("bonjour".equals(service.findText("good.morning", Locale.FRENCH)));
	}
	
	@Test
	public void englishGoodMorning() {
		Assert.assertTrue("good morning".equals(service.findText("good.morning", Locale.ENGLISH)));
	}
	
	@Test
	public void frenchUnknown() {
		Assert.assertTrue("##hello##".equals(service.findText("hello", Locale.FRENCH)));
	}
	
	@Test
	public void englishUnknown() {
		Assert.assertTrue("##hello##".equals(service.findText("hello", Locale.ENGLISH)));
	}

}
