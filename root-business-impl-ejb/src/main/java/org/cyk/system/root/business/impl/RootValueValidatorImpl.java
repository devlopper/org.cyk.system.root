package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.cyk.system.root.business.api.RootValueValidator;
import org.cyk.utility.common.FileExtensionGroup;

public class RootValueValidatorImpl implements RootValueValidator,Serializable {

	private static final long serialVersionUID = -4852641723010830142L;

	public static Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+$");//FIXME name can have accent and so on
	
	public static Set<String> EXTENSIONS = new HashSet<>();
    static{
    	for(FileExtensionGroup fileExtensionGroup : FileExtensionGroup.values())
    		EXTENSIONS.addAll(fileExtensionGroup.getExtensions());
    }
    
    public static Long SIZE_MINIMUM = 1l;
    public static Long SIZE_MAXIMUM = 1024l * 1024 * 10; //10M
    
    public static Long PASSWORD_MINIMUM_LENGHT = 6l;
    public static Long PASSWORD_MAXIMUM_LENGHT = null; //Unlimited
    
    /**/
	
	@Override
	public Boolean isValidPersonName(String name) {
		return Boolean.TRUE;//TODO enable after pattern fixed //NAME_PATTERN.matcher(name).find();		
	}

	@Override
	public Boolean isValidFileExtension(String extension,Set<String> extensions) {
		return (extensions==null?EXTENSIONS:extensions).contains(extension);
	}
	
	@Override
	public Boolean isValidFileExtension(String extension) {
		return isValidFileExtension(extension, EXTENSIONS);
	}

	@Override
	public Boolean isValidFileSize(Long size) {
		return isValidFileSize(size, SIZE_MINIMUM, SIZE_MAXIMUM);
	}
	
	@Override
	public Boolean isValidFileSize(Long size, Long minimumSize, Long maximumSize) {
		return minimumSize <= size && size <= maximumSize;
	}

	@Override
	public Boolean isValidUserAccountPassword(String password) {
		return Boolean.TRUE;
	}

}
