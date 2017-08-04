package org.cyk.system.root.business.impl.network;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.network.ComputerBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.network.Computer;
import org.cyk.system.root.persistence.api.network.ComputerDao;

public class ComputerBusinessImpl extends AbstractTypedBusinessService<Computer,ComputerDao> implements ComputerBusiness , Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	public ComputerBusinessImpl(ComputerDao dao) {
		super(dao);
	}

	@Override
	protected Computer __instanciateOne__(String[] values,InstanciateOneListener<Computer> listener) {
		listener.getInstance().getGlobalIdentifierCreateIfNull();
		set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE);
		set(listener.getSetListener(), AbstractEnumeration.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_NAME);
    	set(listener.getSetListener(), Computer.FIELD_IP_ADDRESS);
    	set(listener.getSetListener(), Computer.FIELD_IP_ADDRESS_NAME);
    	return listener.getInstance();
	}
}
