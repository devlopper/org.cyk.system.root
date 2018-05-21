package org.cyk.system.root.model.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExecutedMethodInputOutput extends org.cyk.utility.common.model.identifiable.Embeddable.BaseClass.JavaPersistenceEmbeddable implements Serializable {
	private static final long serialVersionUID = 1L;

	private Byte count;
	
	@Column(length=1024 * 32) private String text;
	
	private Boolean compressed;
	
	@Override
	public String getUiString() {
		return text;
	}
	
	/**/
	
	public static final String FIELD_COUNT = "count";
	public static final String FIELD_TEXT = "text";
	public static final String FIELD_COMPRESSED = "compressed";

}
