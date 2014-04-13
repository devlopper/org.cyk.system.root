package org.cyk.system.root.dao.impl.unit;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.cyk.system.root.dao.api.pattern.tree.NestedSetDao;
import org.cyk.system.root.dao.impl.Utils;
import org.cyk.system.root.dao.impl.pattern.tree.NestedSetDaoImpl;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.utility.common.test.AbstractUnitTest;

public class UtilsUT extends AbstractUnitTest {

	Utils utils = new Utils();
	
	@Override
	protected void _execute_() {
		super._execute_();
		Set<String> set = utils.componentNames(NestedSet.class) ;
		assertTrue("Model",set.contains(NestedSet.class.getName()));
		assertTrue("Dao Api",set.contains(NestedSetDao.class.getName()));
		assertTrue("Dao Impl",set.contains(NestedSetDaoImpl.class.getName()));
	}
	
}
