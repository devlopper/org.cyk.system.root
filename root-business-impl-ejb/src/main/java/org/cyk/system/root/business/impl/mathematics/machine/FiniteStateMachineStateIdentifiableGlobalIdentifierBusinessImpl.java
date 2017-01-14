package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifier.IdentifiablesSearchCriteria;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifierDao;
import org.cyk.utility.common.ListenerUtils;

public class FiniteStateMachineStateIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<FiniteStateMachineStateIdentifiableGlobalIdentifier
	, FiniteStateMachineStateIdentifiableGlobalIdentifierDao,FiniteStateMachineStateIdentifiableGlobalIdentifier.SearchCriteria> implements FiniteStateMachineStateIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public FiniteStateMachineStateIdentifiableGlobalIdentifierBusinessImpl(FiniteStateMachineStateIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}

	@Override
	public FiniteStateMachineStateIdentifiableGlobalIdentifier create(AbstractIdentifiable identifiable,FiniteStateMachineState finiteStateMachineState) {
		return create(Arrays.asList(identifiable),finiteStateMachineState).iterator().next();
	}

	@Override
	public Collection<FiniteStateMachineStateIdentifiableGlobalIdentifier> create(Collection<AbstractIdentifiable> identifiables,FiniteStateMachineState finiteStateMachineState) {
		Collection<FiniteStateMachineStateIdentifiableGlobalIdentifier> finiteStateMachineStateIdentifiableGlobalIdentifiers = new ArrayList<>();
		for(AbstractIdentifiable identifiable : identifiables){
			FiniteStateMachineStateIdentifiableGlobalIdentifier finiteStateMachineStateIdentifiableGlobalIdentifier = new FiniteStateMachineStateIdentifiableGlobalIdentifier();
			//finiteStateMachineStateIdentifiableGlobalIdentifier.setState(finiteStateMachineState);
			finiteStateMachineStateIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(identifiable.getGlobalIdentifier());
			finiteStateMachineStateIdentifiableGlobalIdentifiers.add(finiteStateMachineStateIdentifiableGlobalIdentifier);
		}
		create(finiteStateMachineStateIdentifiableGlobalIdentifiers);
		return finiteStateMachineStateIdentifiableGlobalIdentifiers;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <IDENTIFIABLE extends AbstractIdentifiable> Collection<IDENTIFIABLE> findIdentifiablesByCriteria(final IdentifiablesSearchCriteria<IDENTIFIABLE> criteria) {
		Collection<GlobalIdentifier> globalIdentifiers = new ArrayList<>();
		Collection<FiniteStateMachineStateIdentifiableGlobalIdentifier> finiteStateMachineStateIdentifiableGlobalIdentifiers = new ArrayList<>();
		if(criteria.getFiniteStateMachineStateIdentifiableGlobalIdentifiers()!=null)
			finiteStateMachineStateIdentifiableGlobalIdentifiers.addAll(criteria.getFiniteStateMachineStateIdentifiableGlobalIdentifiers());
		finiteStateMachineStateIdentifiableGlobalIdentifiers.addAll(dao.readByCriteria(criteria.getFiniteStateMachineStateIdentifiableGlobalIdentifier()));	
		
		for(FiniteStateMachineStateIdentifiableGlobalIdentifier finiteStateMachineStateIdentifiableGlobalIdentifier : finiteStateMachineStateIdentifiableGlobalIdentifiers)
			globalIdentifiers.add(finiteStateMachineStateIdentifiableGlobalIdentifier.getIdentifiableGlobalIdentifier());
		
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>(){
			@Override
			public void execute(Listener listener) {
				listener.beforeSearchIdentifiablesFind(criteria);
			}
			
		});
		final Collection<IDENTIFIABLE> identifiables = (Collection<IDENTIFIABLE>) BusinessInterfaceLocator.getInstance().injectTyped(criteria.getIdentifiableClass()).findByGlobalIdentifiers(globalIdentifiers);
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>(){
			@Override
			public void execute(Listener listener) {
				listener.afterSearchIdentifiablesFind(criteria,identifiables);
			}
			
		});
		
		return identifiables;
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <IDENTIFIABLE extends AbstractIdentifiable> Long countIdentifiablesByCriteria(IdentifiablesSearchCriteria<IDENTIFIABLE> criteria) {
		return null;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<FiniteStateMachineStateIdentifiableGlobalIdentifier> findByCriteria(final SearchCriteria searchCriteria) {
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>(){
			@Override
			public void execute(Listener listener) {
				listener.beforeSearchFind(searchCriteria);
			}
			
		});
		final Collection<FiniteStateMachineStateIdentifiableGlobalIdentifier> finiteStateMachineStateIdentifiableGlobalIdentifiers = dao.readByCriteria(searchCriteria);
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>(){
			@Override
			public void execute(Listener listener) {
				listener.afterSearchFind(searchCriteria,finiteStateMachineStateIdentifiableGlobalIdentifiers);
			}
		});
		
		for(FiniteStateMachineStateIdentifiableGlobalIdentifier finiteStateMachineStateIdentifiableGlobalIdentifier : finiteStateMachineStateIdentifiableGlobalIdentifiers){
			//DateTime dateTime = new DateTime(finiteStateMachineStateIdentifiableGlobalIdentifier.getGlobalIdentifier().getCreationDate().getTime());
			Date date = null;
			/*if(StringUtils.isBlank(searchCriteria.getTimeDivisionTypeCode())){
				date = dateTime.toDate();
			}else{
				if(RootConstant.Code.TimeDivisionType.DAY.equals(searchCriteria.getTimeDivisionTypeCode())){
					date = dateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate();
				}
			}*/
			finiteStateMachineStateIdentifiableGlobalIdentifier.getGlobalIdentifier().setCreationDate(date);
		}
		return finiteStateMachineStateIdentifiableGlobalIdentifiers;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(SearchCriteria searchCriteria) {
		return dao.countByCriteria(searchCriteria);
	}
	
	/**/
	
	public static interface Listener extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.SearchCriteria<FiniteStateMachineStateIdentifiableGlobalIdentifier,FiniteStateMachineStateIdentifiableGlobalIdentifier.SearchCriteria> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();

		void beforeSearchIdentifiablesFind(FiniteStateMachineStateIdentifiableGlobalIdentifier.IdentifiablesSearchCriteria<? extends AbstractIdentifiable> searchCriteria);
		<T extends AbstractIdentifiable> void afterSearchIdentifiablesFind(FiniteStateMachineStateIdentifiableGlobalIdentifier.IdentifiablesSearchCriteria<T> searchCriteria,Collection<T> identifiables);
		
		
		
		public static class Adapter extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.SearchCriteria.Adapter<FiniteStateMachineStateIdentifiableGlobalIdentifier,FiniteStateMachineStateIdentifiableGlobalIdentifier.SearchCriteria> implements Listener,Serializable{
			private static final long serialVersionUID = 8213436661982661753L;

			@Override
			public void beforeSearchIdentifiablesFind(IdentifiablesSearchCriteria<? extends AbstractIdentifiable> searchCriteria) {}

			@Override
			public <T extends AbstractIdentifiable> void afterSearchIdentifiablesFind(IdentifiablesSearchCriteria<T> searchCriteria,Collection<T> identifiables) {}
			
		}
		
	}
}
