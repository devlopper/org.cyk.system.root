package org.cyk.system.root.service.impl.unit;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.cyk.system.root.business.impl.generator.StringGeneratorBusinessImpl;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.system.root.model.generator.StringValueGeneratorConfiguration;
import org.cyk.system.root.model.generator.StringValueGeneratorPadding;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

public class StringGeneratorBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private StringGeneratorBusinessImpl stringGeneratorBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(stringGeneratorBusiness);
	}
	
	@Test
	public void generate() {
		StringGenerator stringGenerator = new StringGenerator();
		stringGenerator.setConfiguration(new StringValueGeneratorConfiguration());
		stringGenerator.getConfiguration().setLeftPadding(padding("FACT", "0", 8l, null));
		stringGenerator.getConfiguration().setRightPadding(padding(null, null, null, null));
		stringGenerator.getConfiguration().setLenght(8l);
		
	    Assert.assertEquals("FACT00000001",stringGeneratorBusiness.generate(stringGenerator, "1"));
	    Assert.assertEquals("FACT00000751",stringGeneratorBusiness.generate(stringGenerator, "751"));
	    Assert.assertEquals("FACT12345678",stringGeneratorBusiness.generate(stringGenerator, "12345678"));
	}
	
	private StringValueGeneratorPadding padding(String prefix, String pattern, Long lenght, String suffix){
		StringValueGeneratorPadding padding = new StringValueGeneratorPadding();
		padding.setPrefix(prefix);
		padding.setPattern(pattern);
		padding.setLenght(lenght);
		padding.setSuffix(suffix);
		return padding;
	}
	
	private void configuration(){
		
	}
	
}
