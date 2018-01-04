package org.cyk.system;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;

public class FilterHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class ClassLocator extends org.cyk.utility.common.helper.FilterHelper.Filter.ClassLocator implements Serializable {
		private static final long serialVersionUID = 1L;
			
		@Override
		protected Class<?> getDefault(Class<?> aClass) {
			if(ClassHelper.getInstance().isInstanceOf(AbstractDataTree.class, aClass))
				return AbstractDataTree.Filter.class;
			if(ClassHelper.getInstance().isInstanceOf(AbstractDataTreeType.class, aClass))
				return AbstractDataTreeType.Filter.class;
			if(ClassHelper.getInstance().isInstanceOf(AbstractDataTreeNode.class, aClass))
				return AbstractDataTreeNode.Filter.class;
			if(ClassHelper.getInstance().isInstanceOf(AbstractEnumeration.class, aClass))
				return AbstractEnumeration.Filter.class;
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
				return AbstractIdentifiable.Filter.class;
			return super.getDefault(aClass);
		}
		
	}
	
}
