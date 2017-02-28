package org.cyk.system.root.model.search;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class StringSearchCriteria extends AbstractFieldValueSearchCriteria<String> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;
	
	private static final String MATCH_ZERO_OR_MANY_CHARACTERS = Constant.CHARACTER_PERCENT.toString(); 
	//private static final String MATCH_ONE_AND_ONLY_ONE_CHARACTER = Constant.CHARACTER_UNDESCORE.toString(); 
	
	public enum LocationType{START,INSIDE,END,EXACT}
	
	private LocationType locationType = LocationType.EXACT;
	
	{
		nullValue = Constant.EMPTY_STRING;
	}
	
	public StringSearchCriteria(String value,LocationType locationType) {
		super(value);
		this.locationType = locationType;
	}
	
	public StringSearchCriteria(String value) {
		this(value,LocationType.INSIDE);
	}
	
	public StringSearchCriteria(StringSearchCriteria criteria) {
		super(criteria);
		this.locationType = criteria.locationType;
	}
	
	@Override
	public Boolean isNull() {
		return StringUtils.isEmpty(value);
	}
	
	@Override
	public String getPreparedValue() {
		return value==null?nullValue:value;
	}
	
	public String getLikeValue(){
		switch(locationType){
		case START:return MATCH_ZERO_OR_MANY_CHARACTERS+getPreparedValue();
		case INSIDE:return MATCH_ZERO_OR_MANY_CHARACTERS+getPreparedValue()+MATCH_ZERO_OR_MANY_CHARACTERS;
		case END:return getPreparedValue()+MATCH_ZERO_OR_MANY_CHARACTERS;
		case EXACT:return getPreparedValue();
		}
		return getPreparedValue();
	}
	
	public void excludeCode(Collection<? extends AbstractIdentifiable> excludedIdentifiables){
		if(excludedIdentifiables!=null)
			for(AbstractIdentifiable excluded : excludedIdentifiables)
				getExcluded().add(excluded.getCode());
	}

}
