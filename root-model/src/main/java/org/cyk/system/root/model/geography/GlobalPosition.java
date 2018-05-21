package org.cyk.system.root.model.geography;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Global positioning system data
 * @author Christian Yao Komenan
 *
 */
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GlobalPosition extends org.cyk.utility.common.model.identifiable.Embeddable.BaseClass.JavaPersistenceEmbeddable implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name=COLUMN_LONGITUDE,precision=COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE) private BigDecimal longitude;
	@Column(name=COLUMN_LATITUDE,precision=COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE) private BigDecimal latitude;
	@Column(name=COLUMN_ALTITUDE,precision=COLUMN_VALUE_PRECISION,scale=FLOAT_SCALE) private BigDecimal altitude;

	public static final String FIELD_LONGITUDE = "longitude";
	public static final String FIELD_LATITUDE = "latitude";
	public static final String FIELD_ALTITUDE = "altitude";
	
	public static final String COLUMN_PREFIX = "globalposition_";
	public static final String COLUMN_LONGITUDE = COLUMN_PREFIX+FIELD_LONGITUDE;
	public static final String COLUMN_LATITUDE = COLUMN_PREFIX+FIELD_LATITUDE;
	public static final String COLUMN_ALTITUDE = COLUMN_PREFIX+FIELD_ALTITUDE;
	
	@Override
	public String getUiString() {
		return null;
	}
	
}