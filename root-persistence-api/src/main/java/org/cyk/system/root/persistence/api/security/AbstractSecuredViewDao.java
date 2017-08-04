package org.cyk.system.root.persistence.api.security;

import org.cyk.system.root.model.security.AbstractSecuredView;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractSecuredViewDao<VIEW extends AbstractSecuredView<ACCESSOR>,ACCESSOR> extends TypedDao<VIEW> {


}
