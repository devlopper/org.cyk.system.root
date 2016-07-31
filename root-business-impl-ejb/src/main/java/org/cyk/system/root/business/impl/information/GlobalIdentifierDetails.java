package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.security.RudDetails;
import org.cyk.system.root.business.impl.time.PeriodDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GlobalIdentifierDetails<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractOutputDetails<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 1708181273704661027L;

	@Input @InputText private String creationDate,createdBy;
	
	@IncludeInputs(layout=Layout.VERTICAL) private PeriodDetails period;
	
	@IncludeInputs(layout=Layout.VERTICAL) private RudDetails rud;
	
	public GlobalIdentifierDetails(IDENTIFIABLE identifiable) {
		super(identifiable);
		if(identifiable.getGlobalIdentifier()==null)
			return;
		this.code = identifiable.getGlobalIdentifier().getCode();
		this.name = identifiable.getGlobalIdentifier().getName();
		this.image = identifiable.getGlobalIdentifier().getImage();
		
		this.creationDate = formatDateTime(identifiable.getGlobalIdentifier().getCreationDate());
		this.createdBy = formatUsingBusiness(identifiable.getGlobalIdentifier().getCreatedBy());
		
		this.period = new PeriodDetails(identifiable.getGlobalIdentifier().getExistencePeriod());
		
		this.rud = new RudDetails(identifiable.getGlobalIdentifier().getRud());
	}

}
