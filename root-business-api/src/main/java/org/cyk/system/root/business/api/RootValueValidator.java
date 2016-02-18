package org.cyk.system.root.business.api;

import java.util.Set;

import org.cyk.utility.common.FileExtension;


public interface RootValueValidator {

	/* Person */
	
	Boolean isValidPersonName(String name);
	
	/* File */
	
	Boolean isValidFileExtension(String extension,Set<FileExtension> extensions);
	
	Boolean isValidFileExtension(String extension);
	
	Boolean isValidFileSize(Long size);
	
	Boolean isValidFileSize(Long size,Long minimumSize,Long maximumSize);
	
	/* UserAccount */
	
	Boolean isValidUserAccountPassword(String password);
}
