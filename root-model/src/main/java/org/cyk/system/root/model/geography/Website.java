package org.cyk.system.root.model.geography;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter @Entity
@AllArgsConstructor @NoArgsConstructor
public class Website extends Contact implements Serializable {

	private static final long serialVersionUID = 923076998880521464L;

	@Input @InputText
	@NotNull
	private URL url;
	
	public Website(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	@Override
	public String toString() {
		return url==null?"":url.toString();
	}
	
}
