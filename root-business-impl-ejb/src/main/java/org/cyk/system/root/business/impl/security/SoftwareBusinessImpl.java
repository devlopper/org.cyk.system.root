package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.security.SoftwareBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.persistence.api.security.SoftwareDao;
import org.cyk.utility.common.helper.FieldHelper;

public class SoftwareBusinessImpl extends AbstractEnumerationBusinessImpl<Software, SoftwareDao> implements SoftwareBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public SoftwareBusinessImpl(SoftwareDao dao) {
		super(dao); 
	}
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<Software> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Software.class);
			addParameterArrayElementString(5,FieldHelper.getInstance().buildPath(SmtpProperties.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_DEFAULTED));
		}
		
		@Override
		protected Software __execute__() {
			// TODO Auto-generated method stub
			Software software =  super.__execute__();
			System.out
					.println("SoftwareBusinessImpl.BuilderOneDimensionArray.__execute__() : "+software.getCode()+" : " +software.getDefaulted());
			return software;
		}
		
	}
	
}
