package org.cyk.system.root.business.impl.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.ThreadPoolExecutor;
import org.cyk.utility.common.cdi.AbstractBean;
import org.junit.Test;

import lombok.AllArgsConstructor;

public class ParallelOperationUsingThreadIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void businesses() {
    	
    }
    
    @Test
    public void serial(){
    	org.cyk.utility.common.CommonUtils.Execution execution = CommonUtils.getInstance().execute("Serial", new Runnable() {
			@Override
			public void run() {
				Integer count = 2000;
				for(int i = 0; i < count; i++)
		    		create(inject(PersonBusiness.class).instanciateOneRandomly());
			}
		});
    	System.out.println(execution);
    	CommonUtils.getInstance().pause(10 * 1000);
    }
    
    @Test
    public void parallel(){
    	ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 1l, TimeUnit.MINUTES, 10000, 1l, TimeUnit.MINUTES, null);
    	
    	Integer count = 1000;
    	
    	Collection<AbstractIdentifiable> c1 = new ArrayList<>();
    	for(int i = 0; i < count; i++)
    		c1.add(inject(PersonBusiness.class).instanciateOneRandomly());
    	
    	
    	Collection<AbstractIdentifiable> c2 = new ArrayList<>();
    	for(int i = 0; i < count; i++)
    		c2.add(inject(PersonBusiness.class).instanciateOneRandomly());
    	
    	threadPoolExecutor.execute(new MyLongRunOperation("C1",c1));
    	threadPoolExecutor.execute(new MyLongRunOperation("C2",c2));
    	
    	threadPoolExecutor.waitTermination();
    	
    	CommonUtils.getInstance().pause(10 * 1000);
    	
    	//assertEquals(2000l, inject(PersonDao.class).countAll());
    }

    @AllArgsConstructor
    public class MyLongRunOperation extends AbstractBean implements Runnable {
		private static final long serialVersionUID = 1L;
		private String name;
    	private Collection<? extends AbstractIdentifiable> identifiables;
    	
		@Override
		public void run() {
			System.out.println("Running "+name);
			org.cyk.utility.common.CommonUtils.Execution execution = commonUtils.execute(name, new Runnable() {
				@Override
				public void run() {
					inject(GenericBusiness.class).create(commonUtils.castCollection(identifiables, AbstractIdentifiable.class));
				}
			});
			System.out.println("Done "+execution);
		}
    	
    }
}
