package org.cyk.system.root.model.unit;


import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class ClassHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = 8008545189557409317L;
	
	//@Test
	public void getClasses(){
		System.out.println("ClassHelperUnitTest.getClasses() : "+Person.class);
		Package package_ = Package.getPackage("org.cyk.system.root.model.party.person");
		Collection<Class<?>> classes = new ClassHelper.Get.Adapter.Default(package_).setBaseClass(AbstractIdentifiable.class).execute();
		
		System.out.println("ClassHelperUnitTest.getClasses() : "+classes.size());
	}
	
	@Test
	public void getReflections(){
		Reflections reflections = new Reflections(new ConfigurationBuilder()
	    	.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("org.cyk.system.")))
	        .setUrls(ClasspathHelper.forPackage("org.cyk.system."))
	        .setScanners(new TypeAnnotationsScanner()));
		
	    //Set<Class<? extends AbstractIdentifiable>> modules = reflections.getSubTypesOf(AbstractIdentifiable.class);
		Set<Class<?>> modules = reflections.getTypesAnnotatedWith(Entity.class);
		
		assertEquals(modules.size(), ClassHelper.getInstance().getByAnnotation("org.cyk.system.", Entity.class).size());
		
	    System.out.println("ClassHelperUnitTest.getReflections() : "+modules.size());
		for(Class<?> clazz : ClassHelper.getInstance().filterByPackageName(modules, "org.cyk.system.root.model.party.person", StringHelper.Location.START))
	    	System.out.println(clazz);
	}
	
}
