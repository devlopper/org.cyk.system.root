package org.cyk.system.root.persistence.impl.globalidentification;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;

public class GlobalIdentifierDaoImpl implements GlobalIdentifierDao {

	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public GlobalIdentifier read(String identifier,Map<String,Object> hints) {
		return hints == null ? entityManager.find(GlobalIdentifier.class, identifier) : entityManager.find(GlobalIdentifier.class, identifier,hints);
	}
	
	@Override
	public GlobalIdentifier read(String identifier) {
		return read(identifier,null);
	}
	
	@Override
	public GlobalIdentifier readByCode(String code,Map<String,Object> hints) {
		TypedQuery<GlobalIdentifier> query = entityManager.createQuery("SELECT gid FROM GlobalIdentifier gid WHERE gid.code = :code", GlobalIdentifier.class)
				.setParameter("code", code);
		if(hints!=null)
			for(Entry<String, Object> entry : hints.entrySet())
				query.setHint(entry.getKey(), entry.getValue());
		try {
			return query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	@Override
	public GlobalIdentifier readByCode(String code) {
		return readByCode(code,null);
	}
	
	@Override
	public GlobalIdentifier create(GlobalIdentifier globalIdentifier) {
		entityManager.persist(globalIdentifier);
		return globalIdentifier;
	}
	
	@Override
	public GlobalIdentifier update(GlobalIdentifier globalIdentifier) {
		return entityManager.merge(globalIdentifier);
	}

	@Override
	public GlobalIdentifier delete(GlobalIdentifier globalIdentifier) {
		entityManager.remove(entityManager.merge(globalIdentifier));
		return globalIdentifier;
	}

	@Override
	public Collection<GlobalIdentifier> readAll() {
		return entityManager.createQuery("SELECT r FROM GlobalIdentifier r",GlobalIdentifier.class).getResultList();
	}

	@Override
	public Long countAll() {
		return entityManager.createQuery("SELECT COUNT(r) FROM GlobalIdentifier r",Long.class).getSingleResult();
	}
}
