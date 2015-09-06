package org.cyk.system.root.business.api;

import java.util.Set;


public interface RootValueValidator {

	/* Person */
	
	Boolean isValidPersonName(String name);
	
	/* File */
	
	Boolean isValidFileExtension(String extension,Set<String> extensions);
	
	Boolean isValidFileExtension(String extension);
	
	Boolean isValidFileSize(Long size);
	
	Boolean isValidFileSize(Long size,Long minimumSize,Long maximumSize);
	
	/* UserAccount */
	
	Boolean isValidUserAccountPassword(String password);
}
