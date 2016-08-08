package org.cyk.system.root.model.spreadsheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.time.AbstractIdentifiablePeriod;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Storage of data in tabular form
 * @author Christian Yao Komenan
 *
 * @param <TEMPLATE>
 * @param <ROW>
 * @param <COLUMN>
 * @param <CELL>
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @MappedSuperclass @ModelBean(crudStrategy=CrudStrategy.BUSINESS)
public abstract class AbstractSpreadSheet<TEMPLATE,ROW,COLUMN,CELL> extends AbstractIdentifiablePeriod implements Serializable {

	private static final long serialVersionUID = -625974035216780560L;
	
	@ManyToOne protected TEMPLATE template;
	@Temporal(TemporalType.TIMESTAMP) @Column(nullable=false) @NotNull protected Date creationDate;
	
	@Transient protected Collection<ROW> rows = new ArrayList<>();
	@Transient protected Collection<COLUMN> columns = new ArrayList<>();
	@Transient protected Collection<CELL> cells = new ArrayList<>();		
	
	public static final String FIELD_TEMPLATE = "template";
}
