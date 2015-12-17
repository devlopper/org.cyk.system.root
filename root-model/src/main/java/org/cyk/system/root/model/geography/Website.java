package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor
public class Website extends Contact implements Serializable {

	private static final long serialVersionUID = 923076998880521464L;

	@NotNull @Column(name="url",nullable=false) private String urlAsString;//TODO use UniformResourceLocator instead of String
	//@Transient private URL url;
	
	public Website(String urlAsString) {
		this.urlAsString = urlAsString;
	}
	/*
	public URL getUrl(){
		if(url==null)
			try {
				url = new URL(urlAsString);
			} catch (MalformedURLException e) {
				// Must Never Happen
				e.printStackTrace();
			}
		return url;
	}*/

	@Override
	public String toString() {
		return urlAsString;
	}
	
}
