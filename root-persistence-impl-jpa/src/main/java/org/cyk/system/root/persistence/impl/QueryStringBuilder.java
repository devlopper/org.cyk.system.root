package org.cyk.system.root.persistence.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public class QueryStringBuilder implements Serializable {

	private static final long serialVersionUID = 1814682900660172098L;
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
	 
	/* Named Queries */  
	public static final String KW_NQ_READ = "read";
	public static final String KW_NQ_COUNT = "count";
	public static final String KW_NQ_SUM = "sum";
	
	public static final String ATTRIBUTE_IDENTIFIER = "identifier";
	public static final String VAR = "r";
	public static final String VAR_IDENTIFIERS = "identifiers";
	private static final String SELECT_FORMAT = "SELECT %1$s FROM %2$s %1$s";
	private static final String FUNCTION_FORMAT = "SELECT %1$s(%2$s.%3$s) FROM %4$s %2$s";
	private static final String WHERE_FORMAT = "%1$s.%2$s %3$s %4$s";
	
	private StringBuilder __value__;
	private String from;
	private Boolean where;

	public QueryStringBuilder init(){
		__value__=new StringBuilder();
		from=null;
		where=null;
		return this;
	}
	
	public QueryStringBuilder from(Class<? extends AbstractIdentifiable> clazz){
		return from(clazz.getSimpleName());
	}
	
	public QueryStringBuilder from(String from){
		this.from = from;
		return this;
	}
	
	public QueryStringBuilder select(){
		__value__ = new StringBuilder(String.format(SELECT_FORMAT, VAR,from));
		return this;
	}
	
	public QueryStringBuilder select(Function function,String fieldName){
		__value__ = new StringBuilder(String.format(FUNCTION_FORMAT, function,VAR,fieldName,from));
		return this;
	}
		
	public QueryStringBuilder select(Function function){
		if(function==null)
			return select();
		else
			return select(function, "identifier");
	}
	
	public QueryStringBuilder where(LogicalOperator aLogicalOperator,String anAttributeName,String aVarName,ArithmeticOperator anArithmeticOperator,Boolean varOutside) {
		if(Boolean.TRUE.equals(where) && aLogicalOperator==null)
			throw new IllegalArgumentException("Use one of this operator in where clause of the query : "+StringUtils.join(LogicalOperator.values()," ")+
					" *** "+__value__);
		__value__.append(" "+(aLogicalOperator==null?KW_JPQL_WHERE:aLogicalOperator)+" "+
					String.format(WHERE_FORMAT,VAR,anAttributeName,anArithmeticOperator.getSymbol(),(Boolean.TRUE.equals(varOutside)?":":"")+aVarName));
		where = true;
		return this;
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
	
	public QueryStringBuilder whereIdentifierIn(String anAttributeName) {
		return where(anAttributeName+"."+ATTRIBUTE_IDENTIFIER,VAR_IDENTIFIERS,ArithmeticOperator.IN);
	}
	
	public QueryStringBuilder operator(String operator) {
		__value__.append(" "+operator+" ");
		return this;
	}
	
	/**/
	
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
		return in(aLogicalOperator, anAttributeName+"."+ATTRIBUTE_IDENTIFIER, VAR_IDENTIFIERS);
	}
	
	public QueryStringBuilder in(String anAttributeName,String aVarName){
		where(null, anAttributeName, aVarName, ArithmeticOperator.IN);
		return this;
	}
	
	public QueryStringBuilder in(String anAttributeName){
		return in(null, anAttributeName+"."+ATTRIBUTE_IDENTIFIER, VAR_IDENTIFIERS);
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
		__value__.append(VAR+"."+fieldName+" "+((ascending==null || Boolean.TRUE.equals(ascending))?KW_JPQL_ASC:KW_JPQL_DESC));
		return this;
	}
	
	public QueryStringBuilder parenthesis(Boolean open) {
		__value__.append(Boolean.TRUE.equals(open)?"(":")");
		return this;
	}
	
	public String getValue(){
		return __value__.toString();
	}
	
	@Override
	public String toString() {
		return getValue();
	}
}
