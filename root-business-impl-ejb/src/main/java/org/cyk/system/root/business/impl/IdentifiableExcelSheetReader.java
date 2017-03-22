package org.cyk.system.root.business.impl;

import java.io.File;
import java.util.HashSet;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.system.root.persistence.impl.Utils;
import org.cyk.utility.common.file.ExcelSheetReader;

public class IdentifiableExcelSheetReader<T extends AbstractIdentifiable> extends ExcelSheetReader.Adapter.Default {

	private static final long serialVersionUID = 325668260905847551L;

	public IdentifiableExcelSheetReader(File file,Class<T> identifiableClass) {
		super(file);
		setPrimaryKeys(new HashSet<>(Utils.getCodes(inject(PersistenceInterfaceLocator.class).injectTyped(identifiableClass).readAll())));
	}
		
}
