package org.cyk.system.root.model.userinterface.spreadsheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @MappedSuperclass
public abstract class AbstractSpreadSheetTemplate<ROW,COLUMN> extends AbstractEnumeration implements Serializable {

	private static final long serialVersionUID = -625974035216780560L;
	
	@Transient
	protected Collection<ROW> rows = new ArrayList<>();
	
	@Transient
	protected Collection<COLUMN> columns = new ArrayList<>();

}
