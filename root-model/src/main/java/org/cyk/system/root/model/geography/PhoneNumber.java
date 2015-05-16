package org.cyk.system.root.model.geography;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Getter @Setter @Entity @NoArgsConstructor
public class PhoneNumber extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	@ManyToOne @NotNull @JoinColumn(nullable=false)
	private PhoneNumberType type;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	@ManyToOne @NotNull @JoinColumn(nullable=false)
	private Locality country;
	
	@Input(label=@Text(value="phone.number-short")) @InputText
	@NotNull @Column(nullable=false)
	private String number;
	
	@Override
	public String toString() {
		if(number==null || number.isEmpty())
			return null;
		return /*(country==null?"":("+"+country.getCode()+" "))+*/number/*+(type==null?"":" ("+type.getName()+")")*/;//TODO move to Business
	}
}
