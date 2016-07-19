package org.cyk.system.root.business.impl.mathematics.machine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineStateLogBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.BusinessLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog.IdentifiablesSearchCriteria;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog.SearchCriteria;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateLogDao;
import org.cyk.utility.common.ListenerUtils;
import org.joda.time.DateTime;

@Stateless
public class FiniteStateMachineStateLogBusinessImpl extends AbstractTypedBusinessService<FiniteStateMachineStateLog, FiniteStateMachineStateLogDao> implements FiniteStateMachineStateLogBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public FiniteStateMachineStateLogBusinessImpl(FiniteStateMachineStateLogDao dao) {
		super(dao); 
	}

	@Override
	public FiniteStateMachineStateLog create(FiniteStateMachineStateLog finiteStateMachineStateLog) {
		if(finiteStateMachineStateLog.getDate()==null)
			finiteStateMachineStateLog.setDate(timeBusiness.findUniversalTimeCoordinated());
		return super.create(finiteStateMachineStateLog);
	}

	@Override
	public FiniteStateMachineStateLog create(AbstractIdentifiable identifiable,FiniteStateMachineState finiteStateMachineState) {
		return create(Arrays.asList(identifiable),finiteStateMachineState).iterator().next();
	}

	@Override
	public Collection<FiniteStateMachineStateLog> create(Collection<AbstractIdentifiable> identifiables,FiniteStateMachineState finiteStateMachineState) {
		Collection<FiniteStateMachineStateLog> finiteStateMachineStateLogs = new ArrayList<>();
		for(AbstractIdentifiable identifiable : identifiables){
			FiniteStateMachineStateLog finiteStateMachineStateLog = new FiniteStateMachineStateLog();
			finiteStateMachineStateLog.setState(finiteStateMachineState);
			finiteStateMachineStateLog.setParty(finiteStateMachineState.getProcessing().getParty());
			finiteStateMachineStateLog.setDate(finiteStateMachineState.getProcessing().getDate());
			finiteStateMachineStateLog.setIdentifiableGlobalIdentifier(identifiable.getGlobalIdentifier());
			finiteStateMachineStateLogs.add(finiteStateMachineStateLog);
		}
		create(finiteStateMachineStateLogs);
		return finiteStateMachineStateLogs;
	}

	@SuppressWarnings("unchecked")
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public <IDENTIFIABLE extends AbstractIdentifiable> Collection<IDENTIFIABLE> findIdentifiablesByCriteria(final IdentifiablesSearchCriteria<IDENTIFIABLE> criteria) {
		Collection<GlobalIdentifier> globalIdentifiers = new ArrayList<>();
		Collection<FiniteStateMachineStateLog> finiteStateMachineStateLogs = new ArrayList<>();
		if(criteria.getFiniteStateMachineStateLogs()!=null)
			finiteStateMachineStateLogs.addAll(criteria.getFiniteStateMachineStateLogs());
		finiteStateMachineStateLogs.addAll(dao.readByCriteria(criteria.getFiniteStateMachineStateLog()));	
		
		for(FiniteStateMachineStateLog finiteStateMachineStateLog : finiteStateMachineStateLogs)
			globalIdentifiers.add(finiteStateMachineStateLog.getIdentifiableGlobalIdentifier());
		
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>(){
			@Override
			public void execute(Listener listener) {
				listener.beforeSearchIdentifiablesFind(criteria);
			}
			
		});
		final Collection<IDENTIFIABLE> identifiables = (Collection<IDENTIFIABLE>) BusinessLocator.getInstance().locate(criteria.getIdentifiableClass()).findByGlobalIdentifiers(globalIdentifiers);
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
	public Collection<FiniteStateMachineStateLog> findByCriteria(final SearchCriteria searchCriteria) {
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>(){
			@Override
			public void execute(Listener listener) {
				listener.beforeSearchFind(searchCriteria);
			}
			
		});
		final Collection<FiniteStateMachineStateLog> finiteStateMachineStateLogs = dao.readByCriteria(searchCriteria);
		listenerUtils.execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>(){
			@Override
			public void execute(Listener listener) {
				listener.afterSearchFind(searchCriteria,finiteStateMachineStateLogs);
			}
		});
		
		for(FiniteStateMachineStateLog finiteStateMachineStateLog : finiteStateMachineStateLogs){
			DateTime dateTime = new DateTime(finiteStateMachineStateLog.getDate().getTime());
			Date date = null;
			if(StringUtils.isBlank(searchCriteria.getTimeDivisionTypeCode())){
				date = dateTime.toDate();
			}else{
				switch(searchCriteria.getTimeDivisionTypeCode()){
				case TimeDivisionType.DAY:
					date = dateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate();
					break;
				}	
			}
			finiteStateMachineStateLog.setDate(date);
		}
		return finiteStateMachineStateLogs;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(SearchCriteria searchCriteria) {
		return dao.countByCriteria(searchCriteria);
	}
	
	/**/
	
	public static interface Listener extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.SearchCriteria<FiniteStateMachineStateLog,FiniteStateMachineStateLog.SearchCriteria> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();

		void beforeSearchIdentifiablesFind(FiniteStateMachineStateLog.IdentifiablesSearchCriteria<? extends AbstractIdentifiable> searchCriteria);
		<T extends AbstractIdentifiable> void afterSearchIdentifiablesFind(FiniteStateMachineStateLog.IdentifiablesSearchCriteria<T> searchCriteria,Collection<T> identifiables);
		
		/**/
		
		public static class Adapter extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.SearchCriteria.Adapter<FiniteStateMachineStateLog,FiniteStateMachineStateLog.SearchCriteria> implements Listener,Serializable{
			private static final long serialVersionUID = 8213436661982661753L;

			@Override
			public void beforeSearchIdentifiablesFind(IdentifiablesSearchCriteria<? extends AbstractIdentifiable> searchCriteria) {}

			@Override
			public <T extends AbstractIdentifiable> void afterSearchIdentifiablesFind(IdentifiablesSearchCriteria<T> searchCriteria,Collection<T> identifiables) {}
			
		}
		
	}
}
