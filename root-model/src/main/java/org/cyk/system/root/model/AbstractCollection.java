package org.cyk.system.root.model;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass @Getter @Setter @NoArgsConstructor
public abstract class AbstractCollection<ITEM extends AbstractEnumeration> extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -3099832512046879464L;
	
	protected String itemCodeSeparator = Constant.CHARACTER_UNDESCORE.toString(); 
	@Transient protected Collection<ITEM> collection;

	public AbstractCollection(String code, String name, String abbreviation,String description) {
		super(code, name, abbreviation, description);
	}

	public Collection<ITEM> getCollection(){
		if(collection==null)
			collection = new ArrayList<>();
		return collection;
	}
	
	@SuppressWarnings("unchecked")
	public ITEM addItem(String code, String name){
		Class<ITEM> clazz = (Class<ITEM>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		try {
			ITEM item = clazz.newInstance();
			item.setCode( (StringUtils.isBlank(itemCodeSeparator) ? Constant.EMPTY_STRING : (this.code+itemCodeSeparator))+code);
			item.setName(name);
			((AbstractCollectionItem<AbstractCollection<ITEM>>)item).setCollection(this);
			getCollection().add(item);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
