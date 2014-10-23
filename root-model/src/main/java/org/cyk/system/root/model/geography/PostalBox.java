package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Getter @Setter @Entity
@AllArgsConstructor @NoArgsConstructor
public class PostalBox extends Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 923076998880521464L;
	
	@Input(label=@Text(value="postal.box")) @NotNull
	private String value;


	@Override
	public String toString() {
		return value;
	}
}
