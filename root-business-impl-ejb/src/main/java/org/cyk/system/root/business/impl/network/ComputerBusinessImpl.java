package org.cyk.system.root.business.impl.network;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.network.ComputerBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.network.Computer;
import org.cyk.system.root.persistence.api.network.ComputerDao;

public class ComputerBusinessImpl extends AbstractTypedBusinessService<Computer,ComputerDao> implements ComputerBusiness , Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	public ComputerBusinessImpl(ComputerDao dao) {
		super(dao);
	}

	public static class BuilderOneDimensionArray extends org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<Computer> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Computer.class);
			addFieldCodeName();
			addParameterArrayElementString(Computer.FIELD_IP_ADDRESS,Computer.FIELD_IP_ADDRESS_NAME);
		}
		
	}
}
