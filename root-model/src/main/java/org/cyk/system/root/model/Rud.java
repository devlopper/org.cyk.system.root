package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable @Getter @Setter
public class Rud extends org.cyk.utility.common.model.identifiable.Embeddable.BaseClass.JavaPersistenceEmbeddable implements Serializable {
	private static final long serialVersionUID = -2272776792804730789L;

	private @Column(name=COLUMN_READABLE) Boolean readable;
	private @Column(name=COLUMN_UPDATABLE) Boolean updatable;
	private @Column(name=COLUMN_DELETABLE) Boolean deletable;

	public static final String FIELD_READABLE = "readable";
	public static final String FIELD_UPDATABLE = "updatable";
	public static final String FIELD_DELETABLE = "deletable";
	
	public static final String COLUMN_PREFIX = "rud_";
	public static final String COLUMN_READABLE = COLUMN_PREFIX+FIELD_READABLE;
	public static final String COLUMN_UPDATABLE = COLUMN_PREFIX+FIELD_UPDATABLE;
	public static final String COLUMN_DELETABLE = COLUMN_PREFIX+FIELD_DELETABLE;
}
