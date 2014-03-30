package org.cyk.system.root.dao.impl;

import java.io.File;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.cyk.system.root.dao.api.GenericIdentifiableQuery;
import org.cyk.utility.common.AbstractTest;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public abstract class AbstractDataAccessTest extends AbstractTest {
	
	@Inject protected GenericIdentifiableQuery genericDao;
	@Inject protected UserTransaction transaction;
	
	public static Archive<?> createDeployment(Package...packages){
		String metainf = System.getProperty("user.dir")+"/src/main/resources/META-INF/";
		return ShrinkWrap
				.create(JavaArchive.class)
				.addPackage(AbstractQueryable.class.getPackage())
				.addPackages(false,packages)
				.addAsResource("test-persistence.xml","META-INF/persistence.xml")
				.addAsResource(new File(metainf+"org/cyk/system/root/dao/query/nestedsetnode.xml"),"META-INF/org/cyk/system/root/dao/query/nestedsetnode.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
}
