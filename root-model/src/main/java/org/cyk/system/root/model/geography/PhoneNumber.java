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
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Getter @Setter @Entity @NoArgsConstructor
public class PhoneNumber extends Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne @Input @NotNull @JoinColumn(nullable=false)
	private PhoneNumberType type;
	
	@ManyToOne @Input @NotNull @JoinColumn(nullable=false)
	private Locality country;
	
	@Input(label=@Text(value="phone.number-short")) @NotNull @Column(nullable=false)
	private String number;
	
	@Override
	public String toString() {
		if(number==null || number.isEmpty())
			return null;
		return (country==null?"":("+"+country.getCode()+" "))+number+(type==null?"":" ("+type.getName()+")");
	}
}
