package org.cyk.system.root.business.impl.userinterface;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuNodeTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNodeType;
import org.cyk.system.root.persistence.api.userinterface.UserInterfaceMenuNodeTypeDao;

public class UserInterfaceMenuNodeTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<UserInterfaceMenuNodeType,UserInterfaceMenuNodeTypeDao> implements UserInterfaceMenuNodeTypeBusiness {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public UserInterfaceMenuNodeTypeBusinessImpl(UserInterfaceMenuNodeTypeDao dao) {
        super(dao);
    } 

	public static class BuilderOneDimensionArray extends AbstractDataTreeTypeBusinessImpl.BuilderOneDimensionArray<UserInterfaceMenuNodeType> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(UserInterfaceMenuNodeType.class);
		}
		
	}
}
