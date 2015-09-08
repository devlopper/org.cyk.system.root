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
			@AttributeOverride(name=StringValueGeneratorPadding.FIELD_PATTERN,column=@Column(name=COLUMN_LEFT_PATTERN))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_LENGHT,column=@Column(name=COLUMN_LEFT_LENGHT))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_PREFIX,column=@Column(name=COLUMN_LEFT_PREFIX))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_SUFFIX,column=@Column(name=COLUMN_LEFT_SUFFIX))
	})
	private StringValueGeneratorPadding leftPadding = new StringValueGeneratorPadding();
	
	@Embedded @AttributeOverrides(value={
			@AttributeOverride(name=StringValueGeneratorPadding.FIELD_PATTERN,column=@Column(name=COLUMN_RIGHT_PATTERN))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_LENGHT,column=@Column(name=COLUMN_RIGHT_LENGHT))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_PREFIX,column=@Column(name=COLUMN_RIGHT_PREFIX))
			,@AttributeOverride(name=StringValueGeneratorPadding.FIELD_SUFFIX,column=@Column(name=COLUMN_RIGHT_SUFFIX))
	})
	private StringValueGeneratorPadding rightPadding = new StringValueGeneratorPadding();
	
	private Long lenght;
	
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
		s.append(" lenght="+lenght);
		return s.toString();
	}

	public static final String FIELD_LEFT_PADDING = "leftPadding";
	public static final String FIELD_RIGHT_PADDING = "rightPadding";
	public static final String FIELD_LENGHT = "lenght";
	
	public static final String COLUMN_LEFT_PATTERN = "left_"+StringValueGeneratorPadding.FIELD_PATTERN;
	public static final String COLUMN_LEFT_LENGHT = "left_"+StringValueGeneratorPadding.FIELD_LENGHT;
	public static final String COLUMN_LEFT_PREFIX = "left_"+StringValueGeneratorPadding.FIELD_PREFIX;
	public static final String COLUMN_LEFT_SUFFIX = "left_"+StringValueGeneratorPadding.FIELD_SUFFIX;
	
	public static final String COLUMN_RIGHT_PATTERN = "right_"+StringValueGeneratorPadding.FIELD_PATTERN;
	public static final String COLUMN_RIGHT_LENGHT = "right_"+StringValueGeneratorPadding.FIELD_LENGHT;
	public static final String COLUMN_RIGHT_PREFIX = "right_"+StringValueGeneratorPadding.FIELD_PREFIX;
	public static final String COLUMN_RIGHT_SUFFIX = "right_"+StringValueGeneratorPadding.FIELD_SUFFIX;
}
