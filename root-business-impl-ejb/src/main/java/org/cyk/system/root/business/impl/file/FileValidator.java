package org.cyk.system.root.business.impl.file;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.RootValueValidatorImpl;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.validation.Client;

@Getter @Setter
public class FileValidator extends AbstractValidator<File> implements Serializable {

    private static final long serialVersionUID = -3799482462496328200L;
    
    
    protected Set<String> extensions = new HashSet<>(RootValueValidatorImpl.EXTENSIONS);
    protected Long minimumSize=RootValueValidatorImpl.SIZE_MINIMUM,maximumSize=RootValueValidatorImpl.SIZE_MAXIMUM;
    
    /**/
    
    @AssertTrue(message="{validation.file.extension}",groups=Client.class)
    public boolean isValidExtension(){
        return valueValidator.isValidFileExtension(object.getExtension(),extensions);
    }
    
    @AssertTrue(message="{validation.file.size}",groups=Client.class)
    public boolean isValidSize(){
        return valueValidator.isValidFileSize((long) object.getBytes().length,minimumSize,maximumSize);
    }
    
    /**/
    
    
}