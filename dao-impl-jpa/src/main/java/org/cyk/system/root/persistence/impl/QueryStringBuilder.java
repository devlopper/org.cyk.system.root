package org.cyk.system.root.persistence.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.computation.LogicalOperator;

public class QueryStringBuilder implements Serializable {

	/* JPQL */
	public static final String KW_JPQL_SELECT = "SELECT";
	public static final String KW_JPQL_FROM = "FROM";
	public static final String KW_JPQL_ORDER_BY = "ORDER BY";
	public static final String KW_JPQL_WHERE = "WHERE";
	public static final String KW_JPQL_COUNT = "COUNT";
	public static final String KW_JPQL_AND = "AND";
	public static final String KW_JPQL_OR = "OR";
	 
	/* Named Queries */ 
	public static final String KW_NQ_READ = "read";
	public static final String KW_NQ_COUNT = "count";
	
	public static final String VAR = "r";
	private static final String SELECT_FORMAT = "SELECT %1$s FROM %2$s %1$s";
	private static final String FUNCTION_FORMAT = "SELECT %1$s(%2$s.%3$s) FROM %4$s %2$s";
	private static final String WHERE_FORMAT = "%1$s.%2$s %3$s :%4$s";
	
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
	
	public QueryStringBuilder where(LogicalOperator aLogicalOperator,String anAttributeName,String aVarName,ArithmeticOperator anArithmeticOperator) {
		if(Boolean.TRUE.equals(where) && aLogicalOperator==null)
			throw new IllegalArgumentException("Use one of this operator in where clause of the query : "+StringUtils.join(LogicalOperator.values()," ")+
					" *** "+__value__);
		__value__.append(" "+(aLogicalOperator==null?KW_JPQL_WHERE:aLogicalOperator)+" "+String.format(WHERE_FORMAT,VAR,anAttributeName,anArithmeticOperator.getSymbol(),aVarName));
		where = true;
		return this;
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
	
	public QueryStringBuilder operator(String operator) {
		__value__.append(" "+operator+" ");
		return this;
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
		return and(anAttributeName, anAttributeName, anArithmeticOperator);
	}
	
	public QueryStringBuilder or(String anAttributeName){
		return and(anAttributeName, ArithmeticOperator.EQ);
	}
	
	public QueryStringBuilder orderBy(String...fieldNames) {
		__value__.append(KW_JPQL_ORDER_BY+" "+StringUtils.join(fieldNames," "));
		return this;
	}
	
	public QueryStringBuilder parenthesis(Boolean open) {
		__value__.append(Boolean.TRUE.equals(open)?"(":")");
		return this;
	}
	
	public String getValue(){
		return __value__.toString();
	}
	
}
