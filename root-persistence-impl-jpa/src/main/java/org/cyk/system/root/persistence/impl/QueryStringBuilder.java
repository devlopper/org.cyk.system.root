package org.cyk.system.root.persistence.impl;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public class QueryStringBuilder extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1814682900660172098L;
	
	//private static final String EXCEPTION_OPERATOR_MISSING_FORMAT = "Use one of this operator in where clause of the query : "
	//		+StringUtils.join(LogicalOperator.values(),",")+". Query = %s";
	private static final String SPACE = " ";
	private static final String SEMI_COLON = ":";
	private static final String PARENTHESIS_OPEN = "(";
	private static final String PARENTHESIS_CLOSE = ")";
	public static final String PERCENTAGE = "%";
	
	/* JPQL */
	public static final String KW_JPQL_SELECT = "SELECT";
	public static final String KW_JPQL_FROM = "FROM";
	public static final String KW_JPQL_ORDER_BY = "ORDER BY";
	public static final String KW_JPQL_WHERE = "WHERE";
	public static final String KW_JPQL_COUNT = "COUNT";
	public static final String KW_JPQL_SUM = "SUM";
	public static final String KW_JPQL_AND = "AND";
	public static final String KW_JPQL_OR = "OR";
	public static final String KW_JPQL_ASC = "ASC";
	public static final String KW_JPQL_DESC = "DESC";
	public static final String KW_JPQL_LIKE = "LIKE";
	public static final String KW_JPQL_EXISTS = "EXISTS";
	 
	/* Named Queries */  
	public static final String KW_NQ_READ = "read";
	public static final String KW_NQ_COUNT = "count";
	public static final String KW_NQ_SUM = "sum";
	public static final String KW_NQ_EXECUTE = "execute";
	public static final String KW_NQ_COMPUTE = "compute";
	
	public static final String VAR = "r";
	public static final String VAR_SUBQUERY = "sr";
	public static final String VAR_IDENTIFIERS = "identifiers";
	public static final String VAR_CLASS = "classes";
	public static final String VAR_BETWEEN_FROM = "fromvalue";
	public static final String VAR_BETWEEN_TO = "tovalue";
	private static final String TYPE_FORMAT = "TYPE(%s)";
	private static final String SELECT_FORMAT = "SELECT %1$s FROM %2$s %1$s";
	private static final String FUNCTION_FORMAT = "SELECT %1$s(%2$s.%3$s) FROM %4$s %2$s";
	private static final String PREDICATE_FORMAT = "%1$s %2$s %3$s";
	private static final String WHERE_VAR_FORMAT = "%1$s.%2$s %3$s %4$s";
	private static final String PREDICATE_BETWEEN_FORMAT = "%1$s.%2$s BETWEEN %3$s AND %4$s";
	
	private static final String SUBQUERY_EXISTS_FORMAT = KW_JPQL_EXISTS+"(%1$s)";
	
	public static final String CRITERIA_SELECT_FORMAT = "SELECT %1$s FROM %2$s %1$s ";
	public static final String CRITERIA_DATE_BETWEEN_WHERE_FORMAT = "WHERE %1$s BETWEEN :fromDate AND :toDate ";
	public static final String CRITERIA_DATE_ORDER_FORMAT = " ORDER BY %1$s %2$s ";
	
	@Getter @Setter private String rootEntityVariableName = VAR;
	private StringBuilder __value__;
	private String from;
	private Boolean where;
	private LogicalOperator currentLogicalOperator;
	private QueryStringBuilder masterQueryStringBuilder,subQueryStringBuilder;
	private Boolean globalParenthesisOpened = Boolean.FALSE;

	public QueryStringBuilder init(String rootEntityVariableName){
		__value__=new StringBuilder();
		this.rootEntityVariableName = StringUtils.isBlank(rootEntityVariableName)?VAR:rootEntityVariableName;
		from=null;
		where=null;
		currentLogicalOperator=null;
		return this;
	}
	
	public QueryStringBuilder init(){
		return init(null);
	}
	
	public QueryStringBuilder from(Class<? extends AbstractIdentifiable> clazz){
		return from(clazz.getSimpleName());
	}
	
	public QueryStringBuilder from(String from){
		this.from = from;
		return this;
	}
	
	public QueryStringBuilder selectString(String string){
		__value__ = new StringBuilder(String.format("SELECT %s FROM %s %s", string,from,rootEntityVariableName));
		return this;
	}
	
	public QueryStringBuilder select(){
		__value__ = new StringBuilder(String.format(SELECT_FORMAT, rootEntityVariableName,from));
		return this;
	}
	
	public QueryStringBuilder select(Function function,String fieldName){
		__value__ = new StringBuilder(String.format(FUNCTION_FORMAT, function,rootEntityVariableName,fieldName,from));
		return this;
	}
		
	public QueryStringBuilder select(Function function){
		if(function==null)
			return select();
		else
			return select(function, AbstractIdentifiable.FIELD_IDENTIFIER);
	}
	
	public QueryStringBuilder whereString(String value){
		appendSpace();
		if(!Boolean.TRUE.equals(where)){
			__append__(KW_JPQL_WHERE);
		}
		appendSpace();
		__append__(value);
		where = true;
		return this;
	}
	
	public QueryStringBuilder whereStringFormat(String format){
		whereString(String.format(format, rootEntityVariableName));
		return this;
	}
	
	public QueryStringBuilder where(LogicalOperator aLogicalOperator,String anAttributeName,String aVarName,ArithmeticOperator anArithmeticOperator,Boolean varOutside) {
		LogicalOperator logicalOperator = aLogicalOperator==null?currentLogicalOperator:aLogicalOperator;
		//if(Boolean.TRUE.equals(where) && logicalOperator==null)
		//	throw new IllegalArgumentException(String.format(EXCEPTION_OPERATOR_MISSING_FORMAT, __value__));
		
		String predicate;
		if(StringUtils.isBlank(anAttributeName))
			predicate = String.format(PREDICATE_FORMAT,rootEntityVariableName,anArithmeticOperator.getSymbol(),value(aVarName, varOutside));
		else
			predicate = String.format(WHERE_VAR_FORMAT,rootEntityVariableName,anAttributeName,anArithmeticOperator.getSymbol(),value(aVarName, varOutside));
		//System.out.println("QueryStringBuilder.where() "+anAttributeName+" , "+logicalOperator+" / "+currentLogicalOperator);
		if(logicalOperator==null){
			;
		}else if(currentLogicalOperator==null){
			appendSpace();
			__append__(logicalOperator.name());
		}
		currentLogicalOperator=null;
		return whereString(predicate);
	}
	
	public QueryStringBuilder where(String anAttributeName,String aVarName,Boolean varOutside) {
		return where(null, anAttributeName, aVarName, ArithmeticOperator.EQ, varOutside);
	}
	
	private String predicate(String field,ArithmeticOperator operator,String value,Boolean dynamicValue){
		return String.format(PREDICATE_FORMAT, field,operator.getSymbol(),(Boolean.TRUE.equals(dynamicValue)?":":"")+value);
	}
	private String predicate(String field,ArithmeticOperator operator,String value){
		return predicate(field, operator, value, Boolean.TRUE);
	}
	
	public QueryStringBuilder where(){
		return whereString(null);
	}
	
	public QueryStringBuilder where(LogicalOperator aLogicalOperator,String anAttributeName,String aVarName,ArithmeticOperator anArithmeticOperator) {
	    return where(aLogicalOperator, anAttributeName, aVarName, anArithmeticOperator, true);
	}
	
	public QueryStringBuilder where(String anAttributeName,String aVarName,ArithmeticOperator anArithmeticOperator) {
		return where(null, anAttributeName, aVarName, anArithmeticOperator);
	}
	
	public QueryStringBuilder where(String anAttributeName,String aVarName) {
		return where(null, anAttributeName, aVarName, ArithmeticOperator.EQ);
	}
	
	public QueryStringBuilder where(String anAttributeName,ArithmeticOperator anArithmeticOperator) {
		return where(anAttributeName,anAttributeName,anArithmeticOperator);
	}
	
	public QueryStringBuilder where(String anAttributeName) {
		return where(anAttributeName,anAttributeName,ArithmeticOperator.EQ);
	}
	
	public QueryStringBuilder whereIdentifier(String anAttributeName,Boolean in) {
		String fieldName = (StringUtils.isBlank(anAttributeName)?"":(anAttributeName+"."))+AbstractIdentifiable.FIELD_IDENTIFIER;
		return where(fieldName,VAR_IDENTIFIERS,Boolean.TRUE.equals(in)?ArithmeticOperator.IN:ArithmeticOperator.NOT_IN);
	}
	
	public QueryStringBuilder whereIdentifierIn(String anAttributeName) {
		return whereIdentifier(anAttributeName, Boolean.TRUE);
	}
	public QueryStringBuilder whereIdentifierIn() {
		return whereIdentifierIn(null);
	}
	
	public QueryStringBuilder whereIdentifierNotIn(String anAttributeName) {
		return whereIdentifier(anAttributeName, Boolean.FALSE);
	}
	public QueryStringBuilder whereIdentifierNotIn() {
		return whereIdentifierNotIn(null);
	}
	
	public QueryStringBuilder whereClass(String variableName,Boolean in) {
		String type = String.format(TYPE_FORMAT, StringUtils.isBlank(variableName)?rootEntityVariableName:variableName);
		return whereString(predicate(type,Boolean.TRUE.equals(in)?ArithmeticOperator.IN:ArithmeticOperator.NOT_IN,VAR_CLASS));
	}
	
	public QueryStringBuilder whereClassIn(String variableName) {
		return whereClass(variableName, Boolean.TRUE);
	}
	
	public QueryStringBuilder whereClassIn() {
		return whereClassIn(null);
	}
	
	public QueryStringBuilder whereClassNotIn(String variableName) {
		return whereClass(variableName, Boolean.FALSE);
	}
	
	public QueryStringBuilder whereClassNotIn() {
		return whereClassNotIn(null);
	}
		
	public QueryStringBuilder exists(String subQuery) {
		appendSpace();
		if(StringUtils.isBlank(subQuery)){
			append(KW_JPQL_EXISTS);
			parenthesis(Boolean.TRUE);
			globalParenthesisOpened = Boolean.TRUE;
			return this;
		}else{
			return whereString(String.format(SUBQUERY_EXISTS_FORMAT, subQuery));
		}
		
	}
	
	public QueryStringBuilder exists(QueryStringBuilder subQuery) {
		return exists(subQuery.getValue());
	}
	public QueryStringBuilder exists() {
		return exists("");
	}
	
	
	public QueryStringBuilder parenthesis(Boolean open){
		__append__(Boolean.TRUE.equals(open)?PARENTHESIS_OPEN:PARENTHESIS_CLOSE);
		return this;
	}
	
	public QueryStringBuilder append(String string){
		__append__(string);
		return this;
	}
	
	private QueryStringBuilder operator(String operator) {
		appendSpace();
		__append__(operator);
		return this;
	}
	
	public QueryStringBuilder operator(LogicalOperator operator) {
		//currentLogicalOperator = operator;
		return operator(operator.name());
	}
	
	public QueryStringBuilder and() {
		return operator(LogicalOperator.AND);
	}
	
	public QueryStringBuilder or() {
		return operator(LogicalOperator.OR);
	}
	
	/**/
	
	public QueryStringBuilder and(String anAttributeName,String aVarName,Boolean varOutside) {
		return where(LogicalOperator.AND, anAttributeName, aVarName, ArithmeticOperator.EQ, varOutside);
	}
	
	public QueryStringBuilder and(String anAttributeName,String aVarName,ArithmeticOperator anArithmeticOperator){
		where(LogicalOperator.AND, anAttributeName, aVarName, anArithmeticOperator);
		return this;
	}
	
	public QueryStringBuilder and(String anAttributeName,ArithmeticOperator anArithmeticOperator){
		return and(anAttributeName, anAttributeName, anArithmeticOperator);
	}
	
	public QueryStringBuilder and(String anAttributeName){
		return and(anAttributeName, ArithmeticOperator.EQ);
	}
	
	public QueryStringBuilder or(String anAttributeName,String aVarName,ArithmeticOperator anArithmeticOperator){
		where(LogicalOperator.OR, anAttributeName, aVarName, anArithmeticOperator);
		return this;
	} 
	
	public QueryStringBuilder or(String anAttributeName,ArithmeticOperator anArithmeticOperator){
		return or(anAttributeName, anAttributeName, anArithmeticOperator);
	}
	
	public QueryStringBuilder or(String anAttributeName){
		return or(anAttributeName, ArithmeticOperator.EQ);
	}
	
	/**/
	
	public QueryStringBuilder in(LogicalOperator aLogicalOperator,String anAttributeName,String aVarName){
		where(aLogicalOperator, anAttributeName, aVarName, ArithmeticOperator.IN);
		return this;
	}
	
	public QueryStringBuilder in(LogicalOperator aLogicalOperator,String anAttributeName){
		return in(aLogicalOperator, anAttributeName+"."+AbstractIdentifiable.FIELD_IDENTIFIER, VAR_IDENTIFIERS);
	}
	
	public QueryStringBuilder in(String anAttributeName,String aVarName){
		where(null, anAttributeName, aVarName, ArithmeticOperator.IN);
		return this;
	}
	
	public QueryStringBuilder in(String anAttributeName){
		return in(null, (StringUtils.isNotBlank(anAttributeName)?anAttributeName+".":"")+AbstractIdentifiable.FIELD_IDENTIFIER, VAR_IDENTIFIERS);
	}
	
	public QueryStringBuilder between(String fieldPath,String fromDate,String toDate){
		//"r.tangibleProductStockMovement.date BETWEEN :fromDate AND :toDate AND ABS(r.tangibleProductStockMovement.quantity) >= :minimumQuantity"
		whereString(String.format(PREDICATE_BETWEEN_FORMAT, rootEntityVariableName,fieldPath,fromDate,toDate));
		return this;
	}
	
	public QueryStringBuilder between(String fieldPath){
		return between(fieldPath, value(VAR_BETWEEN_FROM, Boolean.TRUE), value(VAR_BETWEEN_TO, Boolean.TRUE));
	}
	
	public QueryStringBuilder orderBy(String...fieldNames) {
		__value__.append(" "+KW_JPQL_ORDER_BY);
		for(int i=0;i<fieldNames.length;i++)
			__value__.append((i>0?",":" ")+fieldNames[i]+" ASC");
		return this;
	}
	
	public QueryStringBuilder orderBy(String fieldName,Boolean ascending) {
		if(StringUtils.contains(__value__, KW_JPQL_ORDER_BY)){
			__value__.append(",");
		}else{
			__value__.append(" "+KW_JPQL_ORDER_BY+" ");
		}
		__value__.append(rootEntityVariableName+"."+fieldName+" "+((ascending==null || Boolean.TRUE.equals(ascending))?KW_JPQL_ASC:KW_JPQL_DESC));
		return this;
	}
	
	public QueryStringBuilder openSubQueryStringBuilder(Class<? extends AbstractIdentifiable> aClass,String masterFieldPath,String rootEntityVariableName,Function function){
		subQueryStringBuilder = new QueryStringBuilder();
		subQueryStringBuilder.masterQueryStringBuilder = this;
		subQueryStringBuilder.init(rootEntityVariableName).from(aClass).select(function);
		if(StringUtils.isBlank(masterFieldPath))//Automatic join
			subQueryStringBuilder.where(null, this.rootEntityVariableName, Boolean.FALSE);
		else
			subQueryStringBuilder.where(masterFieldPath, this.rootEntityVariableName, Boolean.FALSE);
			
		return subQueryStringBuilder;
	}
	public QueryStringBuilder openSubQueryStringBuilder(Class<? extends AbstractIdentifiable> aClass,String masterFieldPath,String rootEntityVariableName){
		return openSubQueryStringBuilder(aClass,masterFieldPath, rootEntityVariableName,null);
	}
	public QueryStringBuilder openSubQueryStringBuilder(Class<? extends AbstractIdentifiable> aClass,String masterFieldPath){
		return openSubQueryStringBuilder(aClass,masterFieldPath, VAR_SUBQUERY);
	}
	public QueryStringBuilder openSubQueryStringBuilder(Class<? extends AbstractIdentifiable> aClass){
		return openSubQueryStringBuilder(aClass,null, VAR_SUBQUERY);
	}
	
	public QueryStringBuilder closeSubQueryStringBuilder(){
		masterQueryStringBuilder.append(getValue());
		masterQueryStringBuilder.closeGlobalOpenedParenthesis();
		masterQueryStringBuilder.subQueryStringBuilder = null;
		return masterQueryStringBuilder;
	}
	
	private void closeGlobalOpenedParenthesis(){
		if(Boolean.TRUE.equals(globalParenthesisOpened))
			parenthesis(Boolean.FALSE);
	}
	
	public String getValue(){
		return __value__.toString();
	}
	
	private void appendSpace(){
		if(Boolean.FALSE.equals(StringUtils.endsWith(__value__, SPACE)))
			__value__.append(SPACE);
	}
	
	private void __append__(String value){
		if(value==null)
			;
		else
			__value__.append(value);
	}
	
	private String value(String name,Boolean outSide){
		if(Boolean.TRUE.equals(outSide))
			return SEMI_COLON+name;
		return name;
	}
	
	@Override
	public String toString() {
		return getValue();
	}
}
