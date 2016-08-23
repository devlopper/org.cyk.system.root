package org.cyk.system.root.model.language;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractCollectionItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class LanguageCollectionItem extends AbstractCollectionItem<LanguageCollection> implements Serializable {

	private static final long serialVersionUID = -165832578043422718L;
	
	@ManyToOne @NotNull private Language language;
	
	public LanguageCollectionItem(LanguageCollection collection, String code, String name) {
		super(collection, code, name);
	}

	public static final String FIELD_LANGUAGE = "language";
	/*
	@Override
	public String toString() {
		return language==null ? super.toString() : language.getName();
	}*/
}