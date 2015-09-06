package org.cyk.system.root.business.api.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.Rank;
import org.cyk.system.root.model.mathematics.Sort;

@Getter @Setter
public class SortDecorator<T> implements Sortable,Serializable{

	private static final long serialVersionUID = 2904415863383752856L;
	
	private T entity;
	private SortReader<T> sortReader;
	
	public SortDecorator(T entity,SortReader<T> sortReader) {
		super();
		this.entity = entity;	
		this.sortReader = sortReader;
	}

	@Override
	public Rank getRank() {
		return sortReader.read(entity).getRank();
	}

	@Override
	public BigDecimal getValue() {
		return sortReader.read(entity).getAverage().getValue();
	}
	
	/**/
	
	public static interface SortReader<T>{
		
		Sort read(T entity);

	}
}