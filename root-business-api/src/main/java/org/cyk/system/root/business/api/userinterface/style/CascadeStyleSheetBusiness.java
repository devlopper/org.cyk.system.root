package org.cyk.system.root.business.api.userinterface.style;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;

public interface CascadeStyleSheetBusiness {

	void addClasses(CascadeStyleSheet cascadeStyleSheet, String...classes);

	void removeClasses(CascadeStyleSheet cascadeStyleSheet, String...classes);

	void addInlines(CascadeStyleSheet cascadeStyleSheet, String...inlines);

	void removeInlines(CascadeStyleSheet cascadeStyleSheet, String...inlines);

	void setUniqueClass(CascadeStyleSheet cascadeStyleSheet, String uniqueClass);

	String generateClass(String prefix, String label);

	String generateClass(Class<? extends AbstractIdentifiable> identifiableClass, Object identifier);

	String generateClass(AbstractIdentifiable identifiable);

	String generateUniqueClass(AbstractIdentifiable identifiable);

	String generateUniqueClass(String prefix, String label);

	String normalise(String value);

}
