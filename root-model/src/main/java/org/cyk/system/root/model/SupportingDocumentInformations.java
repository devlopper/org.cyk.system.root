package org.cyk.system.root.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.Setter;

/**
 * The supporting document informations justifying the existence of the object
 * @author Komenan.Christian
 *
 */
@Embeddable @Getter @Setter
public class SupportingDocumentInformations extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = -2272776792804730789L;

	/**
	 * The provider from whom this exist
	 */
	@Column(name=COLUMN_PROVIDER) private String provider;
	
	/**
	 * The identifier from which this exist
	 */
	@Column(name=COLUMN_IDENTIFIER) private String identifier;
	
	@Override
	public String getUiString() {
		return Constant.EMPTY_STRING+provider+Constant.CHARACTER_HYPHEN+identifier;
	}
	
	public static final String FIELD_PROVIDER = "provider";
	public static final String FIELD_IDENTIFIER = "identifier";
	
	public static final String COLUMN_PREFIX = "supportingdocument_";
	public static final String COLUMN_PROVIDER = COLUMN_PREFIX+FIELD_PROVIDER;
	public static final String COLUMN_IDENTIFIER = COLUMN_PREFIX+FIELD_IDENTIFIER;

}
