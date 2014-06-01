package org.cyk.system.root.model.geography;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.cyk.utility.common.annotation.UIField;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity
@AllArgsConstructor @NoArgsConstructor
public class Website extends Contact implements Serializable {

	private static final long serialVersionUID = 923076998880521464L;

	@UIField @NotNull @Column(nullable=false)
	private URL url;
	
	public Website(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	@Override
	public String toString() {
		return url==null?"":url.toString();
	}
	
}
