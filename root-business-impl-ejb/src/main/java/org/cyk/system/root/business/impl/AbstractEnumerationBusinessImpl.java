package org.cyk.system.root.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;
import org.cyk.utility.common.Constant;

public abstract class AbstractEnumerationBusinessImpl<ENUMERATION extends AbstractEnumeration,DAO extends AbstractEnumerationDao<ENUMERATION>> 
    extends AbstractTypedBusinessService<ENUMERATION, DAO> implements AbstractEnumerationBusiness<ENUMERATION> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2383681421917333298L;

	public AbstractEnumerationBusinessImpl(DAO dao) {
        super(dao);
    }

    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ENUMERATION instanciate(String name) {
		return instanciate(computeCode(name), name);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ENUMERATION instanciate(String code, String name) {
		ENUMERATION enumeration = instanciate();
		enumeration.setCode(code);
		enumeration.setName(name);
		return enumeration;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ENUMERATION instanciate(List<String> arguments) {
		if(arguments.size()==1)
			return instanciate(arguments.get(0));
		if(arguments.size()==2)
			return instanciate(arguments.get(0),arguments.get(1));
		exceptionUtils().exception("instanciate.toomucharguments");
		return null;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ENUMERATION instanciate(String[] arguments) {
		return instanciate(Arrays.asList(arguments));
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ENUMERATION> instanciateMany(List<List<String>> arguments) {
		List<ENUMERATION> r = new ArrayList<>();
		for(List<String> list : arguments)
			r.add(instanciate(list));
		return r;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ENUMERATION> instanciateMany(String[][] arguments) {
		List<ENUMERATION> r = new ArrayList<>();
		for(String[] list : arguments)
			r.add(instanciate(list));
		return r;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public ENUMERATION find(String code){
        return dao.read(code);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public ENUMERATION load(String code) {
    	ENUMERATION enumeration = find(code);
    	load(enumeration);
    	return enumeration;
    }
    
    protected String computeCode(String string){
    	return StringUtils.remove(string, Constant.CHARACTER_SPACE);
    }

}
