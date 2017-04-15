package org.cyk.system.root.persistence.impl.network;

import java.io.Serializable;

import org.cyk.system.root.model.network.Computer;
import org.cyk.system.root.persistence.api.network.ComputerDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class ComputerDaoImpl extends AbstractTypedDao<Computer> implements ComputerDao , Serializable {

	private static final long serialVersionUID = 1L;

}
