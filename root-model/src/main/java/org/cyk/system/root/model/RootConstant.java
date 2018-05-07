package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.FieldHelper;

public interface RootConstant {

	public static class Code implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static String SEPARATOR = Constant.EMPTY_STRING;

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
		
		public static String generate(AbstractCollectionItem<?> item){
			return generate((AbstractCollection<?>)item.getCollection(),item.getCode());
		}
		
		public static String getRelativeCode(AbstractCollection<?> collection,String code){
			return StringUtils.isBlank(collection.getItemCodeSeparator()) ? code : StringUtils.split(code,collection.getItemCodeSeparator())[1];
		}
		
		public static String getRelativeCode(AbstractCollectionItem<?> item){
			return getRelativeCode((AbstractCollection<?>) item.getCollection(), item.getCode());
		}
		
		public static String generateFieldNotNull(Class<?> aClass,String...fieldNames){
			return generate(aClass,FieldHelper.getInstance().buildPath(fieldNames),NotNull.class);
		}
		
		/**/
		
		public static interface Entity {
			String PERSON = "PERSON";
			String STORE = "STORE";
			String PRODUCT = "PRODUCT";
		}
		
		public static interface Property {
			String CODE = "CODE";
			String NAME = "NAME";
		}
		
		public static interface Tangibility {
			
			String TANGIBLE = "TANGIBLE";
			String INTANGIBLE = "INTANGIBLE";
		}
		
		public static interface Software {
			
			String INSTALLED = "INSTALLED";
			String GOOGLEMAIL = "GOOGLEMAIL";
			String YAHOOMAIL = "YAHOOMAIL";
		}
		
		public static interface UserAccount {
			
			String APPLICATION = "APPLICATION";
			
		}
		
		public static interface Sex {
			
			String MALE = "MALE";
			String FEMALE = "FEMALE";
			
		}
		
		public static interface NotificationTemplate {
			
			String ALARM_EMAIL = "ALARMMAIL";
			String ALARM_SMS = "ALARMSMS";
			String ALARM_USER_INTERFACE = "ALARMUSERINTERFACE";
			
		}
		
		public static interface LocationType {
			
			String HOME = "HOME";
			String OFFICE = "OFFICE";
		}
		
		public static interface PhoneNumberType {
			
			String LAND = "LAND";
			String MOBILE = "MOBILE";
			
			String __DEFAULT__ = MOBILE;
		}
		
		public static interface LocalityType {
			
			String COUNTRY = "COUNTRY";
			String CITY = "CITY";
			String CONTINENT = "CONTINENT";
		}
		
		public static interface Locality {
			
			String AFRICA = "AFRICA";
			String AMERICA = "AMERICA";
			String ASIA = "ASIA";
			String AUSTRALIA = "AUSTRALIA";
			String EUROPE = "EUROPA";
		}
		
		public static interface Country {
			
			String COTE_DIVOIRE = "CI";
			
			String __DEFAULT__ = COTE_DIVOIRE;
		}
		
		public static interface MaritalStatus {
			
			String BACHELOR = "BACHELOR";
			String MARRIED = "MARRIED";
			
		}
		
		public static interface JobFunction {
			
			
		}
		
		public static interface JobTitle {
			
			
		}
		
		public static interface PersonTitle {
			
			String MISTER = "MISTER";
			String MADAM = "MADAM";
			String MISS = "MISS";
			String DOCTOR = "DOCTOR";
		}
		
		public static interface FileRepresentationType {
			
			String IDENTITY_IMAGE = "IDENTITY_IMAGE";
			String IDENTITY_DOCUMENT = "IDENTITY_DOCUMENT";
			String REPORT_BACKGROUND_IMAGE = "REPORT_BACKGROUND_IMAGE";
			
		}
		
		public static interface PersonRelationshipTypeGroup {
			
			String FAMILY = "FAMILY";
			String SOCIETY = "SOCIETY";
			
		}
		
		public static interface PersonRelationshipType {
			
			String FAMILY_PARENT = generate(PersonRelationshipTypeGroup.FAMILY,"PARENT");
			String FAMILY_SPOUSE = generate(PersonRelationshipTypeGroup.FAMILY,"SPOUSE");
			String FAMILY_BROTHER = generate(PersonRelationshipTypeGroup.FAMILY,"BROTHER");
			
			String SOCIETY_TO_CONTACT_IN_EMERGENCY_CASE = generate(PersonRelationshipTypeGroup.SOCIETY,"TOCONTACTINEMERGENCYCASE");
			String SOCIETY_DOCTOR = generate(PersonRelationshipTypeGroup.SOCIETY,"DOCTOR");
			
		}
		
		public static interface PersonRelationshipTypeRoleName {
			
			String HUSBAND = "HUSBAND";
			String WIFE = "WIFE";
			String FATHER = "FATHER";
			String MOTHER = "MOTHER";
			String SON = "SON";
			String DAUGHTER = "DAUGHTER";
			String BROTHER = "BROTHER";
			String SISTER = "SISTER";
			
		}
		
		public static interface PersonRelationshipTypeRole {
			
			String FAMILY_PARENT_FATHER = generate(PersonRelationshipType.FAMILY_PARENT,PersonRelationshipTypeRoleName.FATHER);
			String FAMILY_PARENT_MOTHER = generate(PersonRelationshipType.FAMILY_PARENT,PersonRelationshipTypeRoleName.MOTHER);
			String FAMILY_SPOUSE_HUSBAND = generate(PersonRelationshipType.FAMILY_SPOUSE,PersonRelationshipTypeRoleName.HUSBAND);
			String FAMILY_SPOUSE_WIFE = generate(PersonRelationshipType.FAMILY_SPOUSE,PersonRelationshipTypeRoleName.WIFE);
			String FAMILY_PARENT_SON = generate(PersonRelationshipType.FAMILY_PARENT,PersonRelationshipTypeRoleName.SON);
			String FAMILY_PARENT_DAUGHTER = generate(PersonRelationshipType.FAMILY_PARENT,PersonRelationshipTypeRoleName.DAUGHTER);
			
		}
		
		/**/
		
		public static interface TimeDivisionType {
			
			String DAY = "DAY";
			String WEEK = "WEEK";
			String MONTH = "MONTH";
			String TRIMESTER = "TRIMESTER";
			String SEMESTER = "SEMESTER";
			String YEAR = "YEAR";
		}
		
		public static interface DurationType {
			
			String FULL = "FULL";
			String PARTIAL = "PARTIAL";
			
		}
		
		public static interface BusinessServiceCollection {
			
			String EVENT = "EVENT";
			String FILE = "FILE";
			String GEOGRAPHY = "GEOGRAPHY";
			String INFORMATION = "INFORMATION";
			String LANGUAGE = "LANGUAGE";
			String MATHEMATICS = "MATHEMATICS";
			String MESSAGE = "MESSAGE";
			String NETWORK = "NETWORK";
			String PARTY = "PARTY";
			String TREE = "TREE";
			String SECURITY = "SECURITY";
			String TIME = "TIME";
		}
		
		public static interface MetricCollectionType {
			
			String ATTENDANCE = "ATTENDANCE";
			String BEHAVIOUR = "BEHAVIOUR";
			String COMMUNICATION = "COMMUNICATION";
		}
		
		public static interface MetricCollection {
			
			String ATTENDANCE = "ATTENDANCE";
			String BEHAVIOUR = "BEHAVIOUR";
			String COMMUNICATION = "COMMUNICATION";
			String IDENTIFIABLE_COLLECTION_MOVEMENT = "IDENTIFIABLECOLLECTIONMOVEMENT";
		}
		
		public static interface MeasureType {
			
			String DISTANCE = "DISTANCE";
			String TIME = "TIME";
			
		}
		
		public static interface Measure {
			
			String TIME_DAY = generate(MeasureType.TIME,"DAY");
			String TIME_WEEK = generate(MeasureType.TIME,"WEEK");
			String TIME_MONTH = generate(MeasureType.TIME,"MONTH");
			String TIME_TRIMESTER = generate(MeasureType.TIME,"TRIMESTER");
			String TIME_SEMESTER = generate(MeasureType.TIME,"SEMESTER");
			String TIME_YEAR = generate(MeasureType.TIME,"YEAR");
			
		}
		
		public static interface NullString {
			
			String NOT_ASSESSED = "NA";
			
			
		}
		
		public static interface SmtpProperties {
			
			String DEFAULT = "DEFAULT";
			
			
		}
		
		public static interface ScriptEvaluationEngine {
			
			String JAVASCRIPT = "javascript";
			
			
		}
		
		public static interface IdentifiableCollectionType {
			
			String CONTACT_COLLECTION = "CONTACTCOLLECTION";
			String PERSON = "PERSON";
			String MOVEMENT = "MOVEMENT";
			
		}
		
		public static interface IdentifiablePeriodCollectionType {
			
			String CASH_REGISTER_WORKING_DAY = "CASHREGISTERWORKINGDAY";
			
		}
		
		public static interface IdentifiablePeriodCollection {
			
			String CASH_REGISTER_WORKING_DAY = "CASHREGISTERWORKINGDAY";
		
		}
		
		public static interface Role {
			
			String ADMINISTRATOR = "ADMINISTRATOR";
			String MANAGER = "MANAGER";
			String SECURITY_MANAGER = "SECURITY_MANAGER";//TODO remove underscore
			String SETTING_MANAGER = "SETTING_MANAGER";
			String USER = "USER";
			
		}
		
		public static interface IntervalCollection {
			
		}
		
		public static interface Interval {
			
			String INTEGER_NUMBERS = "INTEGERNUMBERS";
			String POSITIVE_INTEGER_NUMBERS = "POSITIVEINTEGERNUMBERS";
			String POSITIVE_INTEGER_NUMBERS_WITHOUT_ZERO = "POSITIVEINTEGERNUMBERSWITHOUTZERO"; 
			String NEGATIVE_INTEGER_NUMBERS = "NEGATIVEINTEGERNUMBERS"; 

			
			String MOVEMENT_ACTION_INCREMENT = "MOVEMENTACTIONINCREMENT";
			String MOVEMENT_ACTION_DECREMENT = "MOVEMENTACTIONDECREMENT";
			String MOVEMENT_COLLECTION_VALUE = "MOVEMENTCOLLECTIONVALUE";
			String MOVEMENT_COLLECTION_STOCK_VALUE = "MOVEMENTCOLLECTIONSTOCKVALUE";
		}
		
		public static interface MovementGroupType {
			String INCREMENTATION_DECREMENTATION = "INCREMENTATIONDECREMENTATION";
			String INVENTORY = "INVENTORY";
			String PROCUREMENT = "PROCUREMENT";
			String TRANSFER = "TRANSFER";
			String TRANSFER_ACKNOWLEDGMENT = "TRANSFERACKNOWLEDGMENT";
			String TRANSFER_BACK = "TRANSFERBACK";
			String SALE = "SALE";
			String SALE_BACK = "SALEBACK";
		}
		
		public static interface MovementReason {
			
			String INVENTORY = "INVENTORY";
			String PROCUREMENT = "PROCUREMENT";
			String TRANSFER = "TRANSFER";
			String TRANSFER_ACKNOWLEDGMENT = "TRANSFERACKNOWLEDGMENT";
			String TRANSFER_BACK = "TRANSFERBACK";
			String TRANSFER_DELETE = "TRANSFERDELETE";
			String SALE = "SALE";
			String SALE_BACK = "SALEBACK";
			String DELETION = "DELETION";
		}
		
		public static interface MovementAction {
			
			String INCREMENT = "INCREMENT";
			String DECREMENT = "DECREMENT";
		}
		
		public static interface MovementMode {
			
			String CASH = "CASH";
			String CHEQUE = "CHEQUE";
			String BANK_TRANSFER = "BANKTRANSFER";
			String MOBILE_PAYMENT = "MOBILEPAYMENT";
			String GIFT_CARD = "GIFTCARD";
			
		}
		
		public static interface MovementCollectionType {
			
			String DEFAULT = "DEFAULT";
			
			String CASH_REGISTER = "CASHREGISTER";
			String STOCK_REGISTER = "STOCKREGISTER";
			String SALE_BALANCE = "SALEBALANCE";
		}
		
		public static interface MovementCollection {
			
			String CASH_REGISTER = "CASHREGISTER";
			
		}
		
		public static interface LanguageEntry {
			
			String YES = "yes";
			String NO = "no";
			String NOT_SPECIFIED = "not.specified";
			String NO_RESULT_FOUND = "noresultfound";
		}
		
		public static interface UniformResourceLocatorParameter {
			
			public static String ENCODED="encoded";
			
		}
		
		public static interface UserInterfaceMenuRenderType {
			
			String PLAIN="PLAIN";
			String SLIDE="SLIDE";
			String PANEL="PANEL";
			String TAB="TAB";
			String BREAD_CRUMB="BREAD_CRUMB";
			String BAR="BAR";

		}
		
		public static interface UserInterfaceMenuLocation {
			
			String NORTH="NORTH";
			String EAST="EAST";
			String SOUTH="SOUTH";
			String WEST="WEST";

		}
		
		public static interface UserInterfaceMenuType {
			
			String PRIMARY="PRIMARY";
			String SECONDARY="SECONDARY";
			String MAIN="MAIN";
			
		}
		
		public static interface BusinessRole {
			
			String MANAGER="MANAGER";
			String SIGNER="SIGNER";
			String IN_CHARGE="INCHARGE";
			String PROVIDER="PROVIDER";
			String COMPANY="COMPANY";
			String SENDER="SENDER";
			String RECEIVER="RECEIVER";
			/**
			 * a place where things are kept before they are sent out to shop
			 */
			String WAREHOUSE="WAREHOUSE";
			/**
			 * a place where you can buy things
			 */
			String STORE="STORE";
			
		}
		
		public static interface StoreType {
			
			String PRODUCT="PRODUCT";
			String PRODUCT_WAREHOUSE="PRODUCTWAREHOUSE";
			String PRODUCT_SHOP="PRODUCTSHOP";
		}
		
		public static interface Store {
			
			String PRODUCT="PRODUCT";
			String PRODUCT_WAREHOUSE="PRODUCTWAREHOUSE";
			String PRODUCT_SHOP="PRODUCTSHOP";
		}
		
		public static interface InformationState {
			
			/**
			 * a plan or an idea which is suggested to think about and decide upon
			 */
			String PROPOSAL = "PROPOSAL";
			
			/**
			 * thing that is known or proved to be true
			 */
			String FACT = "FACT";
		}
		
		public static interface TransferType {
			
			String TANGIBLE="TANGIBLE";
			String PRODUCT="PRODUCT";
			
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
		
		public static interface Script {
			String INSTANCE = "instance";
			String STRING_HELPER = "stringHelper";
			String FIELD_HELPER = "fieldHelper";
			String GENERIC_BUSINESS = "genericBusiness";
			String FORMATTER_BUSINESS = "formatterBusiness";
			String NUMBER_BUSINESS = "numberBusiness";
			String TIME_BUSINESS = "timeBusiness";
			String NUMBER_BUSINESS_FORMAT_ARGUMENTS = NUMBER_BUSINESS+"FormatArguments";
			String METRIC_BUSINESS = "metricBusiness";
			String METRIC_VALUE_BUSINESS = "metricValueBusiness";
			String VALUE_BUSINESS = "valueBusiness";
			String IS_DRAFT = "isDraft";
		}
		
		public static class ReportTemplate implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static Locale LOCALE = Locale.FRENCH;
			
		}
		
		public static class StoreType implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static String DEFAULT_CODE;
			
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
