package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.BeanAdapter;

public interface DatabaseManagementSystemMessageProvider {

	List<String> getTokens(Throwable throwable);
	
	/**/
	
	public static class Adapter extends BeanAdapter implements DatabaseManagementSystemMessageProvider,Serializable {

		public static DatabaseManagementSystemMessageProvider DEFAULT = new MySql();
		
		private static final long serialVersionUID = -7138990113181697236L;

		@Override
		public List<String> getTokens(Throwable throwable) {
			return null;
		}
		
		/**/
		
		public static class MySql extends Adapter implements Serializable {

			private static final long serialVersionUID = -7138990113181697236L;
			
			public static final String MY_SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION = "MySQLIntegrityConstraintViolationException";
			public static final String  CANNOT_DELETE_OR_UPDATE_PARENT_ROW = "Cannot delete or update a parent row: a foreign key constraint fails (`";
			
			@Override
			public List<String> getTokens(Throwable throwable) {
				List<String> tokens = new ArrayList<>();
				String message = throwable.toString();
				if(StringUtils.contains(message, CANNOT_DELETE_OR_UPDATE_PARENT_ROW)){
					tokens.add("exception.record.cannotdelete");
					message = StringUtils.substringBetween(message, CANNOT_DELETE_OR_UPDATE_PARENT_ROW,Constant.CHARACTER_COMA.toString());
					String tableName = StringUtils.substringBetween(message,".`","`");
					String label = RootBusinessLayer.getInstance().getApplicationBusiness().findBusinessEntityInfosByTableName(tableName).getUserInterface().getLabelId();
					tokens.add(RootBusinessLayer.getInstance().getLanguageBusiness().findText(label));
				}
				return tokens;
			}
			
		}
		
	}
}
