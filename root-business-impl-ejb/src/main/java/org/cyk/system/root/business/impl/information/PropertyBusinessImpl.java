package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.information.PropertyBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.information.Property;
import org.cyk.system.root.persistence.api.information.PropertyDao;

public class PropertyBusinessImpl extends AbstractEnumerationBusinessImpl<Property,PropertyDao> implements PropertyBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public PropertyBusinessImpl(PropertyDao dao) {
        super(dao);
    } 

	/**/
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<Property> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Property.class);
		}
	}
}
