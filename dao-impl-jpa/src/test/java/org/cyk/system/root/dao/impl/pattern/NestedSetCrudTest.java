package org.cyk.system.root.dao.impl.pattern;

import org.cyk.system.root.dao.impl.AbstractCrudTest;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class NestedSetCrudTest extends AbstractCrudTest<NestedSet> {

	@Deployment
	public static Archive<?> createDeployment() {
		return createCrudDeployment(NestedSet.class.getPackage());
	}
	
	@Override
	protected NestedSet sampleOne() {
		return new NestedSet();
	}
	
	@Override
	protected NestedSet _create_() {
		return new NestedSet();
	}

	@Override
	protected void _update_(NestedSet model) {
		model.setRoot(new NestedSetNode());
	}
	

}
