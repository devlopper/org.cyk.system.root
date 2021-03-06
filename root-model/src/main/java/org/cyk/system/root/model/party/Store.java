package org.cyk.system.root.model.party;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.annotation.user.interfaces.Text;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Entity 
@ModelBean(crudStrategy=CrudStrategy.ENUMERATION,genderType=GenderType.FEMALE)
@FieldOverrides(value={
		@FieldOverride(name=AbstractDataTree.FIELD_TYPE,type=StoreType.class)
		,@FieldOverride(name=AbstractDataTreeType.FIELD___PARENT__,type=Store.class)
}) @Accessors(chain=true)
/**
 * a stall, room, floor, or building housing or suitable for housing a retail business
 * @author Christian Yao Komenan
 *
 */
public class Store extends AbstractDataTree<StoreType> implements Serializable  {
	private static final long serialVersionUID = -6128937819261060725L;
	
	private Boolean hasPartyAsCompany;
	@Transient @Text(value="company") private Party partyCompany;
	
	public Store(Store parent, StoreType type, String code,String name) {
		super(parent, type, code);
		setName(name);
	}
	
	public Store(Store parent, StoreType type, String code) {
		super(parent,type,code);
	}
	
	@Override
	public Store setCode(String code) {
		return (Store) super.setCode(code);
	}
	
	@Override
	public Store set__parent__(AbstractIdentifiable parent) {
		return (Store) super.set__parent__(parent);
	}
	
	public Store setTypeFromCode(String code) {
		this.type = getFromCode(StoreType.class, code);
		return this;
	}
	
	public Store setPartyCompanyFromCode(String code) {
		this.partyCompany = getFromCode(Party.class, code);
		return this;
	}
	
	@Override
	public Store addCascadeOperationToMasterFieldNames(String... fieldNames) {
		return (Store) super.addCascadeOperationToMasterFieldNames(fieldNames);
	}
	
	/**/
	
	public static final String FIELD_HAS_PARTY_AS_COMPANY = "hasPartyAsCompany";
	public static final String FIELD_PARTY_COMPANY = "partyCompany";
}
