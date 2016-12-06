package org.cyk.system.root.business.impl.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.persistence.api.party.person.SexDao;
import org.cyk.utility.common.ThreadPoolExecutor;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.AllArgsConstructor;

public class ParallelOperationUsingThreadIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void populate() {}
    
    @Override
    protected void businesses() {
    	ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 1l, TimeUnit.MINUTES, 10, 1l, TimeUnit.MINUTES, null);
    	Collection<AbstractIdentifiable> c1 = new ArrayList<>();
    	c1.add(new Sex("sex1", "Masculin"));
    	threadPoolExecutor.execute(new MyLongRunOperation("C1",c1));
    	
    	Collection<AbstractIdentifiable> c2 = new ArrayList<>();
    	c2.add(new Sex("sex2", "Feminin"));
    	threadPoolExecutor.execute(new MyLongRunOperation("C2",c2));
    	
    	threadPoolExecutor.waitTermination();
    	
    	assertEquals(2l, inject(SexDao.class).countAll());
    }

    @AllArgsConstructor
    public class MyLongRunOperation extends AbstractBean implements Runnable {
    	
    	private String name;
    	private Collection<? extends AbstractIdentifiable> identifiables;
    	
		@Override
		public void run() {
			System.out.println("Running "+name);
			pause(1000);
			inject(GenericBusiness.class).create(commonUtils.castCollection(identifiables, AbstractIdentifiable.class));
			System.out.println("Done "+name);
		}
    	
    }
}
