package org.cyk.system.root.business.impl.party.person;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignatureDetails extends AbstractOutputDetails<Person> implements Serializable {
	private static final long serialVersionUID = -1498269103849317057L;
	
	@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) private File specimen;
	
	public SignatureDetails(Person person) {
		super(person);
		if(person.getExtendedInformations()!=null){
			if(person.getExtendedInformations().getSignatureSpecimen()!=null)
				specimen = person.getExtendedInformations().getSignatureSpecimen();
		}
	}
	
	public static final String LABEL_IDENTIFIER = "signature";
	
	public static final String FIELD_SPECIMEN = "specimen";
}