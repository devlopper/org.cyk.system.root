package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;

public interface RootConstant {

	public static class Code implements Serializable {
		
		public static String SEPARATOR = Constant.EMPTY_STRING;
		
		private static final long serialVersionUID = 1L;

		public static String generate(Object...objects){
			Collection<String> collection = new ArrayList<>();
			for(Object object : objects)
				if(object instanceof Class<?>)
					collection.add(((Class<?>)object).getSimpleName().toUpperCase());
				else
					collection.add(object.toString());
			return StringUtils.join(collection,SEPARATOR);
		}
		
		public static class MetricCollectionType implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String ATTENDANCE = "ATTENDANCE";
			public static String BEHAVIOUR = "BEHAVIOUR";
			public static String COMMUNICATION = "COMMUNICATION";
		}
		
		public static class MetricCollection implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String ATTENDANCE = "ATTENDANCE";
			public static String BEHAVIOUR = "BEHAVIOUR";
			public static String COMMUNICATION = "COMMUNICATION";
			
		}
		
		
	}

}
