package org.cyk.system.root.persistence.impl.party;

import java.io.Serializable;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.persistence.api.party.AbstractPartyDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

public abstract class AbstractPartyDaoImpl<PARTY extends Party> extends AbstractTypedDao<PARTY> implements AbstractPartyDao<PARTY>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	protected String readByEmail;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByEmail, "SELECT party FROM "+clazz.getSimpleName()+" party WHERE EXISTS("
				+ " SELECT email FROM ElectronicMailAddress email WHERE email.address = :"+ElectronicMailAddress.FIELD_ADDRESS+" AND email.collection = party.contactCollection"
				+ ")");
	}
	
	@Override
	public PARTY readByEmail(String email) {
		return namedQuery(readByEmail).parameter(ElectronicMailAddress.FIELD_ADDRESS, email).ignoreThrowable(NoResultException.class).resultOne();
	}
	
	@Override
	protected void processReadByFilterQueryBuilderWhereConditions(JavaPersistenceQueryLanguage jpql) {
		super.processReadByFilterQueryBuilderWhereConditions(jpql);
		jpql.getWhere().or().exists(new StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage(ElectronicMailAddress.class, "email")
				.where().lk("email."+ElectronicMailAddress.FIELD_ADDRESS).and().addTokens("email.collection = t.contactCollection").getParent());	
	}
	
	@Override
	protected void listenBeforeFilter(QueryWrapper<?> queryWrapper, Filter<PARTY> filter,DataReadConfiguration dataReadConfiguration) {
		super.listenBeforeFilter(queryWrapper, filter, dataReadConfiguration);
		queryWrapper.parameterLike(ElectronicMailAddress.FIELD_ADDRESS,((Party.Filter<PARTY>)filter).getContactCollection().getElectronicMailAddress().getAddress());
	}
	
}
 