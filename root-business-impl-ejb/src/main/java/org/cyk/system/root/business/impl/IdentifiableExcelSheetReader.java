package org.cyk.system.root.business.impl;

import java.io.File;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.file.ExcelSheetReader;

public class IdentifiableExcelSheetReader<T extends AbstractIdentifiable> extends ExcelSheetReader.Adapter.Default {

	private static final long serialVersionUID = 325668260905847551L;

	public IdentifiableExcelSheetReader(File file,Class<T> identifiableClass) {
		super(file);
		setSheetName(identifiableClass);
    	setFromRowIndex(1);
    	setFromColumnIndex(0);
		//setPrimaryKeys(new HashSet<>(Utils.getCodes(inject(PersistenceInterfaceLocator.class).injectTyped(identifiableClass).readAll())));
	}
		
}
