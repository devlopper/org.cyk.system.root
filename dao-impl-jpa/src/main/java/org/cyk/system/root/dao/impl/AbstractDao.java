package org.cyk.system.root.dao.impl;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.Getter;

import org.cyk.system.root.model.AbstractModel;
import org.cyk.utility.cdi.AbstractBean;

public abstract class AbstractDao<IDENTIFIABLE extends AbstractModel> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8198334103295401293L;
	
	@PersistenceContext @Getter
	protected EntityManager entityManager;
	protected Class<IDENTIFIABLE> clazz;

	protected StringBuilder __buildingQueryString__;
	protected Map<String, Object> parameters;

}
