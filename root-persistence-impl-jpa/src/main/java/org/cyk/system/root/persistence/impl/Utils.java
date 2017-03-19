package org.cyk.system.root.persistence.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;


public class Utils {
	 
	public static Collection<Long> ids(Collection<? extends AbstractIdentifiable> identifiables){
		Collection<Long> ids = new HashSet<>();
		for(AbstractIdentifiable identifiable : identifiables)
			ids.add(identifiable.getIdentifier());
		return ids;
	}
	
	public static Collection<String> getCodes(Collection<? extends AbstractIdentifiable> identifiables){
		Collection<String> codes = new HashSet<>();
		for(AbstractIdentifiable identifiable : identifiables)
			codes.add(identifiable.getCode());
		return codes;
	}

	public static Collection<String> getGlobalIdentfierValues(Collection<GlobalIdentifier> globalIdentifiers){
		Collection<String> ids = new HashSet<>();
		for(GlobalIdentifier globalIdentifier : globalIdentifiers)
			ids.add(globalIdentifier.getIdentifier());
		return ids;
	}
	
	public static Collection<File>  getFiles(Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers){
		Collection<File> files = new ArrayList<>();
		if(fileIdentifiableGlobalIdentifiers!=null)
			for(FileIdentifiableGlobalIdentifier fileIdentifiableGlobalIdentifier : fileIdentifiableGlobalIdentifiers)
				files.add(fileIdentifiableGlobalIdentifier.getFile());
		return files;
	}
}
