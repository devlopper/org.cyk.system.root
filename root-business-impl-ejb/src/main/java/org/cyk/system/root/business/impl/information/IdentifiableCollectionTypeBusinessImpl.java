package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.cyk.system.root.business.api.information.IdentifiableCollectionTypeBusiness;
import org.cyk.system.root.business.impl.pattern.tree.AbstractDataTreeTypeBusinessImpl;
import org.cyk.system.root.model.information.IdentifiableCollectionType;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionTypeDao;

@Stateless
public class IdentifiableCollectionTypeBusinessImpl extends AbstractDataTreeTypeBusinessImpl<IdentifiableCollectionType,IdentifiableCollectionTypeDao> implements IdentifiableCollectionTypeBusiness,Serializable {

	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public IdentifiableCollectionTypeBusinessImpl(IdentifiableCollectionTypeDao dao) {
        super(dao);
    } 

}
