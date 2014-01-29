package org.cyk.system.root.dao.impl;

import javax.inject.Inject;

import org.cyk.system.root.dao.api.IDataAccess.WhereOperator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PersonDaoTestase {
	
	@Inject PersonDao dao;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(PersonDao.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void service() {
    	System.out.println(dao);
    	Assert.assertNotNull(dao);
		dao.select(); System.out.println(dao.getQueryString() );
		dao.where("matricule", 5, null); System.out.println(dao.getQueryString() );
		dao.where("name", 5, WhereOperator.AND); System.out.println(dao.getQueryString() );
		
    }

}
