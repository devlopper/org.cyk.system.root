package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;

public interface RootConstant {

	public static class Code implements Serializable {
		
		public static String SEPARATOR = Constant.EMPTY_STRING;
		
		private static final long serialVersionUID = 1L;

		public static String generateFromString(String string){
			return StringUtils.remove(string, Constant.CHARACTER_SPACE);
		}
		
		public static String generate(Object...objects){
			Collection<String> collection = new ArrayList<>();
			for(Object object : objects)
				if(object instanceof Class<?>)
					collection.add(((Class<?>)object).getSimpleName().toUpperCase());
				else
					collection.add(object.toString());
			return StringUtils.join(collection,SEPARATOR);
		}
		
		public static String generate(AbstractCollection<?> collection,String code){
			if(StringUtils.isBlank(collection.getItemCodeSeparator()))
				return code;
			else
				return collection.getCode()+collection.getItemCodeSeparator()+ code;
		}
		
		public static String getRelativeCode(AbstractCollection<?> collection,String code){
			return StringUtils.isBlank(collection.getItemCodeSeparator()) ? code : StringUtils.split(code,collection.getItemCodeSeparator())[1];
		}
		
		public static String getRelativeCode(AbstractCollectionItem<?> item){
			return getRelativeCode((AbstractCollection<?>) item.getCollection(), item.getCode());
		}
		
		/**/
		
		public static class Software implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String INSTALLED = "INSTALLED";
			public static String GOOGLEMAIL = "GOOGLEMAIL";
			public static String YAHOOMAIL = "YAHOOMAIL";
		}
		
		public static class UserAccount implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String APPLICATION = "APPLICATION";
			
		}
		
		public static class Sex implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String MALE = "MALE";
			public static String FEMALE = "FEMALE";
			
		}
		
		public static class NotificationTemplate implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String ALARM_EMAIL = "ALARMMAIL";
			public static String ALARM_SMS = "ALARMSMS";
			public static String ALARM_USER_INTERFACE = "ALARMUSERINTERFACE";
			
		}
		
		public static class LocationType implements Serializable {
			private static final long serialVersionUID = 1L;
		
			public static String HOME = "HOME";
			public static String OFFICE = "OFFICE";
		}
		
		public static class PhoneNumberType implements Serializable {
			private static final long serialVersionUID = 1L;
		
			public static String LAND = "LAND";
			public static String MOBILE = "MOBILE";
		}
		
		public static class LocalityType implements Serializable {
			private static final long serialVersionUID = 1L;
		
			public static String COUNTRY = "COUNTRY";
			public static String CITY = "CITY";
			public static String CONTINENT = "CONTINENT";
		}
		
		public static class Locality implements Serializable {
			private static final long serialVersionUID = 1L;
		
			public static String AFRICA = "AFRICA";
			public static String AMERICA = "AMERICA";
			public static String ASIA = "ASIA";
			public static String AUSTRALIA = "AUSTRALIA";
			public static String EUROPE = "EUROPA";
		}
		
		public static class Country implements Serializable {
			private static final long serialVersionUID = 1L;
		
			public static String COTE_DIVOIRE = "CI";
		}
		
		public static class MaritalStatus implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String BACHELOR = "BACHELOR";
			public static String MARRIED = "MARRIED";
			
		}
		
		public static class JobFunction implements Serializable {
			private static final long serialVersionUID = 1L;
			
			
		}
		
		public static class JobTitle implements Serializable {
			private static final long serialVersionUID = 1L;
			
			
		}
		
		public static class PersonTitle implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String MISTER = "MISTER";
			public static String MADAM = "MADAM";
			public static String MISS = "MISS";
			public static String DOCTOR = "DOCTOR";
		}
		
		public static class FileRepresentationType implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String IDENTITY_IMAGE = "IDENTITY_IMAGE";
			public static String IDENTITY_DOCUMENT = "IDENTITY_DOCUMENT";
			public static String REPORT_BACKGROUND_IMAGE = "REPORT_BACKGROUND_IMAGE";
			
		}
		
		public static class PersonRelationshipTypeGroup implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String FAMILY = "FAMILY";
			public static String SOCIETY = "SOCIETY";
			
		}
		
		public static class PersonRelationshipType implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String FAMILY_PARENT = generate(PersonRelationshipTypeGroup.FAMILY,"PARENT");
			public static String FAMILY_SPOUSE = generate(PersonRelationshipTypeGroup.FAMILY,"SPOUSE");
			public static String FAMILY_BROTHER = generate(PersonRelationshipTypeGroup.FAMILY,"BROTHER");
			
			public static String SOCIETY_TO_CONTACT_IN_EMERGENCY_CASE = generate(PersonRelationshipTypeGroup.SOCIETY,"TOCONTACTINEMERGENCYCASE");
			public static String SOCIETY_DOCTOR = generate(PersonRelationshipTypeGroup.SOCIETY,"DOCTOR");
			
		}
		
		public static class PersonRelationshipTypeRoleName implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String HUSBAND = "HUSBAND";
			public static String WIFE = "WIFE";
			public static String FATHER = "FATHER";
			public static String MOTHER = "MOTHER";
			public static String SON = "SON";
			public static String DAUGHTER = "DAUGHTER";
			public static String BROTHER = "BROTHER";
			public static String SISTER = "SISTER";
			
		}
		
		public static class PersonRelationshipTypeRole implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String FAMILY_PARENT_FATHER = generate(PersonRelationshipType.FAMILY_PARENT,PersonRelationshipTypeRoleName.FATHER);
			public static String FAMILY_PARENT_MOTHER = generate(PersonRelationshipType.FAMILY_PARENT,PersonRelationshipTypeRoleName.MOTHER);
			public static String FAMILY_SPOUSE_HUSBAND = generate(PersonRelationshipType.FAMILY_SPOUSE,PersonRelationshipTypeRoleName.HUSBAND);
			public static String FAMILY_SPOUSE_WIFE = generate(PersonRelationshipType.FAMILY_SPOUSE,PersonRelationshipTypeRoleName.WIFE);
			public static String FAMILY_PARENT_SON = generate(PersonRelationshipType.FAMILY_PARENT,PersonRelationshipTypeRoleName.SON);
			public static String FAMILY_PARENT_DAUGHTER = generate(PersonRelationshipType.FAMILY_PARENT,PersonRelationshipTypeRoleName.DAUGHTER);
			
		}
		
		/**/
		
		public static class TimeDivisionType implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String DAY = "DAY";
			public static String WEEK = "WEEK";
			public static String MONTH = "MONTH";
			public static String TRIMESTER = "TRIMESTER";
			public static String SEMESTER = "SEMESTER";
			public static String YEAR = "YEAR";
		}
		
		public static class BusinessServiceCollection implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String EVENT = "EVENT";
			public static String FILE = "FILE";
			public static String GEOGRAPHY = "GEOGRAPHY";
			public static String INFORMATION = "INFORMATION";
			public static String LANGUAGE = "LANGUAGE";
			public static String MATHEMATICS = "MATHEMATICS";
			public static String MESSAGE = "MESSAGE";
			public static String NETWORK = "NETWORK";
			public static String PARTY = "PARTY";
			public static String TREE = "TREE";
			public static String SECURITY = "SECURITY";
			public static String TIME = "TIME";
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
		
		public static class MeasureType implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String DISTANCE = "DISTANCE";
			public static String TIME = "TIME";
			
		}
		
		public static class Measure implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String TIME_DAY = generate(MeasureType.TIME,"DAY");
			public static String TIME_WEEK = generate(MeasureType.TIME,"WEEK");
			public static String TIME_MONTH = generate(MeasureType.TIME,"MONTH");
			public static String TIME_TRIMESTER = generate(MeasureType.TIME,"TRIMESTER");
			public static String TIME_SEMESTER = generate(MeasureType.TIME,"SEMESTER");
			public static String TIME_YEAR = generate(MeasureType.TIME,"YEAR");
			
		}
		
		public static class NullString implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String NOT_ASSESSED = "NA";
			
			
		}
		
		public static class SmtpProperties implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String DEFAULT = "DEFAULT";
			
			
		}
		
		public static class ScriptEvaluationEngine implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String JAVASCRIPT = "javascript";
			
			
		}
		
		public static class IdentifiableCollectionType implements Serializable {
			private static final long serialVersionUID = 1L;
		
			public static String CONTACT_COLLECTION = "CONTACTCOLLECTION";
			public static String PERSON = "PERSON";
			
		}
		
		public static class Role implements Serializable {
			private static final long serialVersionUID = 1L;
		
			public static String ADMINISTRATOR = "ADMINISTRATOR";
			public static String MANAGER = "MANAGER";
			public static String SECURITY_MANAGER = "SECURITY_MANAGER";//TODO remove underscore
			public static String SETTING_MANAGER = "SETTING_MANAGER";
			public static String USER = "USER";
			
		}
		
		public static class IntervalCollection implements Serializable {
			private static final long serialVersionUID = 1L;
			
		}
		
		public static class Interval implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String MOVEMENT_ACTION_INCREMENT = "MOVEMENTACTIONINCREMENT";
			public static String MOVEMENT_ACTION_DECREMENT = "MOVEMENTACTIONDECREMENT";
			public static String MOVEMENT_COLLECTION_VALUE = "MOVEMENTCOLLECTIONVALUE";
	
		}
		
		public static class MovementAction implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String INCREMENT = "INCREMENT";
			public static String DECREMENT = "DECREMENT";
		}
		
		public static class LanguageEntry implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static final String YES = "yes";
			public static final String NO = "no";
			public static final String NOT_SPECIFIED = "not.specified";
			public static final String NO_RESULT_FOUND = "noresultfound";
		}
		
		public static class UniformResourceLocatorParameter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String ENCODED="encoded";
			
		}
		
		public static class UserInterfaceMenuRenderType implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static final String PLAIN="PLAIN";
			public static final String SLIDE="SLIDE";
			public static final String PANEL="PANEL";
			public static final String TAB="TAB";
			public static final String BREAD_CRUMB="BREAD_CRUMB";
			public static final String BAR="BAR";

		}
		
		public static class UserInterfaceMenuLocation implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static final String NORTH="NORTH";
			public static final String EAST="EAST";
			public static final String SOUTH="SOUTH";
			public static final String WEST="WEST";

		}
		
		public static class UserInterfaceMenuType implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static final String PRIMARY="PRIMARY";
			public static final String SECONDARY="SECONDARY";
			public static final String MAIN="MAIN";
			

		}
		
		/**/
		
	}
	
	public static class Configuration implements Serializable {

		private static final long serialVersionUID = 1L;
		
		public static class CascadeStyleSheet implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static final String CLAZZ_SEPARATOR = Constant.CHARACTER_SPACE.toString();
			public static final String INLINE_SEPARATOR = Constant.CHARACTER_SEMI_COLON.toString();
			public static final String IDENTIFIABLE_CLASS_PREFIX = "identifiable";
			
		}
		
		public static class ScriptVariable implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String LOCALE = "locale";
			public static String RETURNED = "RETURN";
			
		}
		
		public static class Script implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String GENERIC_BUSINESS = "genericBusiness";
			public static String FORMATTER_BUSINESS = "formatterBusiness";
			public static String NUMBER_BUSINESS = "numberBusiness";
			public static String TIME_BUSINESS = "timeBusiness";
			public static String NUMBER_BUSINESS_FORMAT_ARGUMENTS = NUMBER_BUSINESS+"FormatArguments";
			public static String METRIC_BUSINESS = "metricBusiness";
			public static String METRIC_VALUE_BUSINESS = "metricValueBusiness";
			public static String VALUE_BUSINESS = "valueBusiness";
			public static String IS_DRAFT = "isDraft";
		}
		
		public static class ReportTemplate implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static Locale LOCALE = Locale.FRENCH;
			
		}
		
		public static class SmtpProperties implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static final String SMTP = "smtp";
			public static final String PROPERTY_FORMAT = "mail."+SMTP+"%s.%s";
			
			public static String getProperty(String name,Boolean secured){
				return String.format(PROPERTY_FORMAT, Boolean.TRUE.equals(secured) ? "s" : Constant.EMPTY_STRING,name);
			}
			public static String getProperty(String name){
				return getProperty(name, Boolean.FALSE);
			}
			
			public static final String HOST = "host";
			public static final String FROM = "from";
			public static final String USER = "user";
			public static final String PASSWORD = "password";
			public static final String PORT = "port";
			public static final String AUTH = "auth";
			public static final String STARTTLS_ENABLE = "starttls.enable";
			public static final String SSL_ENABLE = "ssl.enable";
			
			public static final String PROPERTY_USERNAME = getProperty(USER);
			public static final String PROPERTY_PASSWORD = getProperty(PASSWORD);
			public static final String PROPERTY_FROM = getProperty(FROM);
			
			
		}
	}

}
