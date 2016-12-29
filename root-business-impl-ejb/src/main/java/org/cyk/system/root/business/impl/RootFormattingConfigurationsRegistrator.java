package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.value.MeasureBusiness;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueSet;
import org.cyk.utility.common.Constant;

public class RootFormattingConfigurationsRegistrator extends AbstractFormattingConfigurationsRegistrator implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void register() {
		register(IntervalExtremity.class, new AbstractFormatter<IntervalExtremity>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(IntervalExtremity intervalExtremity, ContentType contentType) {
				String marker = Boolean.TRUE.equals(intervalExtremity.getExcluded()) ? (Boolean.TRUE.equals(intervalExtremity.getIsLow())?"]":"[")
						:(Boolean.TRUE.equals(intervalExtremity.getIsLow())?"[":"]")
						,number=intervalExtremity.getValue()==null ? IntervalExtremity.INFINITE : inject(NumberBusiness.class).format(intervalExtremity.getValue());
				return String.format(IntervalExtremity.FORMAT, Boolean.TRUE.equals(intervalExtremity.getIsLow())?marker:number
						,Boolean.TRUE.equals(intervalExtremity.getIsLow())?number:marker);
			}
		});
        register(Interval.class, new AbstractFormatter<Interval>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(Interval interval, ContentType contentType) {
				return String.format(Interval.FORMAT,inject(FormatterBusiness.class).format(interval.getLow()),inject(FormatterBusiness.class).format(interval.getHigh()));
			}
		});
        register(Value.class, new AbstractFormatter<Value>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(Value value, ContentType contentType) {
				if(value.get()==null && Boolean.TRUE.equals(value.getNullable()))
					return value.getNullString().getCode();
				switch(value.getType()){
				case BOOLEAN:
					return inject(LanguageBusiness.class).findResponseText(value.getBooleanValue().get());
				case NUMBER:
					if(value.getMeasure()==null)
						return inject(NumberBusiness.class).format(value.getNumberValue().get());
					return inject(NumberBusiness.class).format(inject(MeasureBusiness.class).computeQuotient(value.getMeasure(),value.getNumberValue().get()));
				case STRING:
					if(ValueSet.INTERVAL_RELATIVE_CODE.equals(value.getSet())){
						return value.getStringValue().get();
					}else
						return value.getStringValue().get();//TODO must depends on string value type
				}
				return null;
			}
		});
        register(Metric.class, new AbstractFormatter<Metric>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(Metric metric, ContentType contentType) {
				return metric.getName()+(metric.getMeasure()==null ? Constant.EMPTY_STRING 
						:(Constant.CHARACTER_LEFT_PARENTHESIS+inject(FormatterBusiness.class).format(metric.getMeasure(), contentType)+Constant.CHARACTER_RIGHT_PARENTHESIS));
			}
		});
        register(MetricValue.class, new AbstractFormatter<MetricValue>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(MetricValue metricValue, ContentType contentType) {
				return inject(FormatterBusiness.class).format(metricValue.getMetric(), contentType)+Constant.CHARACTER_LEFT_PARENTHESIS
						+inject(FormatterBusiness.class).format(metricValue.getValue(), contentType)+Constant.CHARACTER_RIGHT_PARENTHESIS;
			}
		});
        register(NestedSet.class, new AbstractFormatter<NestedSet>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(NestedSet nestedSet, ContentType contentType) {
				return nestedSet.getIdentifier().toString();
			}
		});
        register(NestedSetNode.class, new AbstractFormatter<NestedSetNode>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(NestedSetNode nestedSetNode, ContentType contentType) {
				return nestedSetNode.getLeftIndex()+Constant.CHARACTER_COMA.toString()+nestedSetNode.getRightIndex();
			}
		});
        register(GlobalIdentifier.class, new AbstractFormatter<GlobalIdentifier>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(GlobalIdentifier globalIdentifier, ContentType contentType) {
				return globalIdentifier.getIdentifier()+Constant.CHARACTER_SLASH+globalIdentifier.getCode();
			}
		});
        
        register(File.class, new AbstractFormatter<File>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(File file, ContentType contentType) {
				if(StringUtils.isBlank(file.getCode()))
					if(StringUtils.isBlank(file.getName()))
						return file.getUiString();
					else
						return file.getName();
				else
					if(StringUtils.isBlank(file.getName()))
						return file.getCode();
					else
						return file.getCode()+Constant.CHARACTER_SLASH+file.getName();
			}
		});
        
        register(PhoneNumber.class, new AbstractFormatter<PhoneNumber>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(PhoneNumber phoneNumber, ContentType contentType) {
				if(StringUtils.isBlank(phoneNumber.getNumber()))
					return null;
				StringBuilder stringBuilder = new StringBuilder();
				if(phoneNumber.getCountry()!=null)
					stringBuilder.append(Constant.CHARACTER_PLUS+phoneNumber.getCountry().getPhoneNumberCode().toString()+Constant.CHARACTER_SPACE);
				stringBuilder.append(phoneNumber.getNumber());
				return stringBuilder.toString();
			}
		});
	}

}
