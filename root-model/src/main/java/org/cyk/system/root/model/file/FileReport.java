package org.cyk.system.root.model.file;

import java.io.InputStream;
import java.io.Serializable;

import org.cyk.utility.common.generator.AbstractGeneratable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileReport extends AbstractGeneratable<FileReport> implements Serializable {

	private static final long serialVersionUID = 4295607879248462659L;

	private InputStream image;
	private Boolean generate=Boolean.FALSE;
	private String identifier,creationDate,createdBy,code,name,otherDetails,birthLocation,deathLocation,owner,weight;
	private String generator,sender,contentWriter;
	
	@Override
	public void setSource(Object source) {
		super.setSource(source);
		if(source==null){
			
		}else{
			this.code = ((File)source).getCode();
			this.generator = ((File)source).getGenerator();
			this.sender = ((File)source).getSender();
			this.contentWriter = ((File)source).getContentWriter();
		}
		
	}
	
	@Override
	public void generate() {
		this.code = provider.randomWord(10, 25);
		this.generator = provider.randomWord(10, 25);
		this.sender = provider.randomWord(10, 25);
		this.contentWriter = provider.randomWord(10, 25);
	}
}
