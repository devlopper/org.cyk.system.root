package org.cyk.system.root.model;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass @Getter @Setter @NoArgsConstructor
public abstract class AbstractCollection<ITEM extends AbstractEnumeration> extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;
	
	public static String ITEM_CODE_SEPARATOR = Constant.CHARACTER_UNDESCORE.toString();
	
	protected String itemCodeSeparator = ITEM_CODE_SEPARATOR; 
	
	/**
	 * True if aggregated attributes values must be an aggregation of item value specific attribute
	 */
	private Boolean itemAggregationApplied;
	
	@Transient protected IdentifiableRuntimeCollection<ITEM> items = new IdentifiableRuntimeCollection<>();
	
	@Transient protected Collection<ITEM> collectionToDelete;

	public AbstractCollection(String code, String name, String abbreviation,String description) {
		super(code, name, abbreviation, description);
	}
	
	public Boolean isItemAggregationApplied(){
		return itemAggregationApplied == null || Boolean.TRUE.equals(itemAggregationApplied);
	}

	/*public Collection<ITEM> getCollection(){
		if(collection==null)
			collection = new ArrayList<>();
		return collection;
	}*/
	
	public ITEM addItem(String name){
		ITEM item = addItem(null,name);
		//item.setCode( (StringUtils.isBlank(itemCodeSeparator) ? Constant.EMPTY_STRING : (getCode()+itemCodeSeparator))+code);
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public ITEM addItem(String code,String name){
		Class<ITEM> clazz = (Class<ITEM>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		try {
			ITEM item = clazz.newInstance();
			item.setCode(code);
			item.setName(name);
			((AbstractCollectionItem<AbstractCollection<ITEM>>)item).setCollection(this);
			add(item);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ITEM add(ITEM item){
		items.getCollection().add(item);
		((AbstractCollectionItem<AbstractIdentifiable>)item).setCollection(this);
		return item;
	}
	
	public ITEM addToDelete(ITEM item){
		if(collectionToDelete==null)
			collectionToDelete = new ArrayList<>();
		collectionToDelete.add(item);
		return item;
	}
	
	@Override
	public String getLogMessage() {
		return String.format(LOG_MESSAGE, getClass().getSimpleName(),getCode(),itemCodeSeparator);
	}
	
	private static final String LOG_MESSAGE = "%s(C=%s SEP=%s)";
	
}
