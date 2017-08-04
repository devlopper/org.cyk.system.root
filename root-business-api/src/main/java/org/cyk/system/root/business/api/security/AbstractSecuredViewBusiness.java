package org.cyk.system.root.business.api.security;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.security.AbstractSecuredView;

public interface AbstractSecuredViewBusiness<VIEW extends AbstractSecuredView<ACCESSOR>,ACCESSOR> extends TypedBusiness<VIEW> {

}
