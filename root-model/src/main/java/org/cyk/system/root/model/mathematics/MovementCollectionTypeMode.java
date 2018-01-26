package org.cyk.system.root.model.mathematics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Entity
@Table(uniqueConstraints={
		@UniqueConstraint(columnNames={MovementCollectionTypeMode.FIELD_TYPE,MovementCollectionTypeMode.FIELD_MODE})
})
public class MovementCollectionTypeMode extends AbstractIdentifiable implements Serializable {
	private static final long serialVersionUID = -4946585596435850782L;

	@ManyToOne @JoinColumn(name=COLUMN_TYPE) @NotNull private MovementCollectionType type;
	@ManyToOne @JoinColumn(name=COLUMN_MODE) @NotNull private MovementMode mode;
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_MODE = "mode";
	
	public static final String COLUMN_TYPE = FIELD_TYPE;
	public static final String COLUMN_MODE = FIELD_MODE;
}