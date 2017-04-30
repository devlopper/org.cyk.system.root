package org.cyk.system.root.persistence.impl.file;

import java.io.Serializable;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.persistence.api.file.FileDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class FileDaoImpl extends AbstractTypedDao<File> implements FileDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

}
 