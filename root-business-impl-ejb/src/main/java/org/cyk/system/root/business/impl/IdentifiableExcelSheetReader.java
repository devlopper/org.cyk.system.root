package org.cyk.system.root.business.impl;

import java.util.HashSet;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.Utils;
import org.cyk.utility.common.file.ExcelSheetReader;

public class IdentifiableExcelSheetReader<T extends AbstractIdentifiable> extends ExcelSheetReader.Adapter.Default {

	private static final long serialVersionUID = 325668260905847551L;

	public IdentifiableExcelSheetReader(Class<T> identifiableClass) {
		setPrimaryKeys(new HashSet<>(Utils.getCodes(inject(BusinessInterfaceLocator.class).injectTyped(identifiableClass).findAll())));
	}
		
}
