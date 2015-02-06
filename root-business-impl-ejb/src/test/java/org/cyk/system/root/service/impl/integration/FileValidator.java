package org.cyk.system.root.service.impl.integration;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import lombok.Getter;

import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.validation.Client;

@Getter
public class FileValidator extends AbstractValidator<File> implements Serializable {

    private static final long serialVersionUID = -3799482462496328200L;
    
    private Set<String> extensions = new HashSet<>();
    
    @AssertTrue(message="{validation.file.extension}",groups=Client.class)
    public boolean isValidExtension(){
        return extensions.contains(object.getExtension());
    }
    
    @AssertTrue(message="{validation.file.size}",groups=Client.class)
    public boolean isValidSize(){
        return object.getBytes().length <= 1024 * 1024 * 99;
    }
    
}