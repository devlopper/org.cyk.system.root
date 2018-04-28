package org.cyk.system.root.model;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;

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
	private Boolean itemAggregationApplied = Boolean.TRUE;
	
	@Transient protected IdentifiableRuntimeCollection<ITEM> items = new IdentifiableRuntimeCollection<>();
	@Transient protected IdentifiableRuntimeCollection<ITEM> itemsDeletable = new IdentifiableRuntimeCollection<>();
	
	{
		getItems().setElementObjectClass(ClassHelper.getInstance().getParameterAt(getClass(), 0, Object.class));
	}
	
	public AbstractCollection(String code, String name, String abbreviation,String description) {
		super(code, name, abbreviation, description);
	}
	
	public AbstractCollection<ITEM> setItemsSynchonizationEnabled(Boolean synchonizationEnabled){
		items.setSynchonizationEnabled(synchonizationEnabled);
		return this;
	}

	public ITEM addItem(String name){
		ITEM item = addItem(null,name);
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public <T> AbstractCollection<ITEM> add(Class<T> itemClass,Collection<T> items) {
		for(T item : items)
			add((ITEM) item);
		return this;
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
	
	public AbstractCollection<ITEM> addItemsByName(Collection<String> names){
		if(CollectionHelper.getInstance().isNotEmpty(names))
			for(String index : names)
				addItem(index, index);
		return this;
	}
	
	public AbstractCollection<ITEM> addItemsByName(String...names){
		if(ArrayHelper.getInstance().isNotEmpty(names))
			addItemsByName(Arrays.asList(names));
		return this;
	}
	
	public AbstractCollection<ITEM> add(Collection<ITEM> items){
		new CollectionHelper.Iterator.Adapter.Default<ITEM>(items) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			protected void __executeForEach__(ITEM item) {
				((AbstractCollectionItem<AbstractIdentifiable>)item).setCollection(AbstractCollection.this);
				AbstractCollection.this.items.addOne(item);
			};
		}.execute();
		return this;
	}
	
	public AbstractCollection<ITEM> add(@SuppressWarnings("unchecked") ITEM...items){
		if(ArrayHelper.getInstance().isNotEmpty(items))
			add(Arrays.asList(items));
		return this;
	}
	
	/*@SuppressWarnings("unchecked")
	public AbstractCollection<ITEM> add(ITEM item){
		((AbstractCollectionItem<AbstractIdentifiable>)item).setCollection(this);
		items.addOne(item);
		return this;
	}*/
	
	public <CLASS> CLASS getItemAt(Class<CLASS> aClass,Integer index){
		return items == null ? null : items.getOneAt(aClass, index);
	}
	
	public <CLASS> void removeItem(ITEM item){
		if(items!=null)
			items.removeOne(item);
	}
	
	public <CLASS> void removeItemAt(Class<CLASS> aClass,Integer index){
		if(items!=null)
			items.removeOneAt(aClass, index);
	}
	
	public static final String FIELD_ITEM_CODE_SEPARATOR = "itemCodeSeparator"; 
	public static final String FIELD_ITEM_AGGREGATION_APPLIED = "itemAggregationApplied";
	public static final String FIELD_ITEMS = "items";
	
}
