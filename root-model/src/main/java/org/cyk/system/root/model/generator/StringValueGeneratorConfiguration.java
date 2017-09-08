package org.cyk.system.root.model.generator;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractModelElement;

@Getter @Setter @Embeddable
public class StringValueGeneratorConfiguration extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 2700928054823690772L;

	@Embedded @AttributeOverrides(value={
			@AttributeOverride(name=StringValueGeneratorPadding.FIELD_PATTERN,column=@Column(name=COLUMN_LEFT_PADDING_PATTERN))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_LENGTH,column=@Column(name=COLUMN_LEFT_PADDING_LENGTH))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_PREFIX,column=@Column(name=COLUMN_LEFT_PADDING_PREFIX))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_SUFFIX,column=@Column(name=COLUMN_LEFT_PADDING_SUFFIX))
	})
	private StringValueGeneratorPadding leftPadding = new StringValueGeneratorPadding();
	
	@Embedded @AttributeOverrides(value={
			@AttributeOverride(name=StringValueGeneratorPadding.FIELD_PATTERN,column=@Column(name=COLUMN_RIGHT_PADDING_PATTERN))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_LENGTH,column=@Column(name=COLUMN_RIGHT_PADDING_LENGTH))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_PREFIX,column=@Column(name=COLUMN_RIGHT_PADDING_PREFIX))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_SUFFIX,column=@Column(name=COLUMN_RIGHT_PADDING_SUFFIX))
	})
	private StringValueGeneratorPadding rightPadding = new StringValueGeneratorPadding();
	
	private Long length;
	
	@Override
	public String getUiString() {
		return toString();
	}
	
	@Override
	public String getLogMessage() {
		StringBuilder s = new StringBuilder();
		if(leftPadding!=null)
			s.append("Left padding = "+leftPadding.getLogMessage());
		if(rightPadding!=null)
			s.append(" Right padding = "+rightPadding.getLogMessage());
		s.append(" lenght="+length);
		return s.toString();
	}

	public static final String FIELD_LEFT_PADDING = "leftPadding";
	public static final String FIELD_RIGHT_PADDING = "rightPadding";
	public static final String FIELD_LENGTH = "length";
	
	private static final String COLUMN_LEFT_PADDING_ = "left_";
	public static final String COLUMN_LEFT_PADDING_PATTERN = COLUMN_LEFT_PADDING_+StringValueGeneratorPadding.FIELD_PATTERN;
	public static final String COLUMN_LEFT_PADDING_LENGTH = COLUMN_LEFT_PADDING_+StringValueGeneratorPadding.FIELD_LENGTH;
	public static final String COLUMN_LEFT_PADDING_PREFIX = COLUMN_LEFT_PADDING_+StringValueGeneratorPadding.FIELD_PREFIX;
	public static final String COLUMN_LEFT_PADDING_SUFFIX = COLUMN_LEFT_PADDING_+StringValueGeneratorPadding.FIELD_SUFFIX;
	
	private static final String COLUMN_RIGHT_PADDING_ = "right_";
	public static final String COLUMN_RIGHT_PADDING_PATTERN = COLUMN_RIGHT_PADDING_+StringValueGeneratorPadding.FIELD_PATTERN;
	public static final String COLUMN_RIGHT_PADDING_LENGTH = COLUMN_RIGHT_PADDING_+StringValueGeneratorPadding.FIELD_LENGTH;
	public static final String COLUMN_RIGHT_PADDING_PREFIX = COLUMN_RIGHT_PADDING_+StringValueGeneratorPadding.FIELD_PREFIX;
	public static final String COLUMN_RIGHT_PADDING_SUFFIX = COLUMN_RIGHT_PADDING_+StringValueGeneratorPadding.FIELD_SUFFIX;
}
