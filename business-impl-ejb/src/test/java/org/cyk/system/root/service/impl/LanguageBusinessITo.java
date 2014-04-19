package org.cyk.system.root.service.impl;

import java.util.Locale;

import javax.inject.Inject;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.language.LanguageEntry;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class LanguageBusinessITo extends AbstractBusinessIT {
	
	@Inject private LanguageBusiness service;

	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{Language.class,LanguageEntry.class}).getArchive();
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
