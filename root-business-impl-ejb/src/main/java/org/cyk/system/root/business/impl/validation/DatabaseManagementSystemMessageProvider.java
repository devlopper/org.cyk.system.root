package org.cyk.system.root.business.impl.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.utility.common.cdi.AbstractBean;
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
			//public static final String CANNOT_DELETE_OR_UPDATE_PARENT_ROW = "Cannot delete or update a parent row: a foreign key constraint fails (`";
			
			//public static final MessageTemplate  CANNOT_DELETE_OR_UPDATE_PARENT_ROW = new MessageTemplate("Cannot delete or update a parent row: a foreign key constraint fails (`","cannotdelete");
					
			public static final MessageTemplate  CANNOT_DELETE_OR_UPDATE_PARENT_ROW = new MessageTemplate("Cannot delete or update a parent row: a foreign key constraint fails (`"
					,"Cannot delete or update a parent row: "
					+ "a foreign key constraint fails \\(`(.*)`\\.`(.*)`, CONSTRAINT `(.*)` FOREIGN KEY \\(`(.*)`\\) REFERENCES `(.*)` (.*)\\)","cannotdelete"){
						private static final long serialVersionUID = 1L;
						
				protected String[] getTokens(Matcher matcher) {
					return new String[]{inject(LanguageBusiness.class).findText(
							inject(ApplicationBusiness.class).findBusinessEntityInfosByTableName(matcher.group(2)).getUserInterface().getLabelId())};		
				}
			};
			public static final MessageTemplate  DUPLICATE_ENTRY_FOR_KEY = new MessageTemplate("Duplicate entry '","Duplicate entry '(.*)' for key '(.*)'","attribute.duplicated"){
				private static final long serialVersionUID = 1L;
				protected String[] getTokens(Matcher matcher) {
					return new String[]{ inject(LanguageBusiness.class).findText(matcher.group(2).toLowerCase()),matcher.group(1).toLowerCase()};		
				}
			};
			
			public static final Collection<MessageTemplate>  MESSAGE_TEMPLATES = new ArrayList<>();
			static{
				MESSAGE_TEMPLATES.add(CANNOT_DELETE_OR_UPDATE_PARENT_ROW);
				MESSAGE_TEMPLATES.add(DUPLICATE_ENTRY_FOR_KEY);
			}
			
			@Override
			public List<String> getTokens(Throwable throwable) {
				List<String> tokens = new ArrayList<>();
				String message = throwable.toString();
				for(MessageTemplate messageTemplate : MESSAGE_TEMPLATES)
					if(messageTemplate.matchs(message, tokens))
						break;
				
				return tokens;
			}
		}
	}
	
	@Getter @Setter
	public static abstract class MessageTemplate extends AbstractBean implements Serializable {
		private static final long serialVersionUID = -6535860823999605992L;
		
		private String startMarker;
		private Pattern pattern;
		private String messageIdentifier;
	
		public MessageTemplate(String startMarker,String pattern,String messageIdentifier) {
			this.startMarker = startMarker;
			this.pattern = Pattern.compile(pattern);
			this.messageIdentifier = "exception.record."+messageIdentifier;
		}
		
		public Boolean isStartMarkerPresent(String string){
			return StringUtils.contains(string, startMarker);
		}
		
		public Matcher getMatcher(String string){
			Matcher matcher = pattern.matcher(string);
			matcher.find();
			return matcher;
		}
		
		public Boolean matchs(String string,List<String> tokens){
			Boolean matchs = null;
			if(matchs = isStartMarkerPresent(string)){
				tokens.add(getMessageIdentifier());
				Matcher matcher = getMatcher(string);
				for(String token : getTokens(matcher))
					tokens.add(token);
			}
			return matchs;
		}
		
		protected abstract String[] getTokens(Matcher matcher);
	}
}
