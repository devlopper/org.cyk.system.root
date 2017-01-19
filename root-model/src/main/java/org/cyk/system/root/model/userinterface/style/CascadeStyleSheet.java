package org.cyk.system.root.model.userinterface.style;

import static org.cyk.system.root.model.RootConstant.Configuration.CascadeStyleSheet.CLAZZ_SEPARATOR;
import static org.cyk.system.root.model.RootConstant.Configuration.CascadeStyleSheet.INLINE_SEPARATOR;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;

import lombok.Getter;
import lombok.Setter;

/**
 * Cascade style sheet
 * @author Komenan.Christian
 *
 */
@Getter @Setter @Embeddable
public class CascadeStyleSheet extends AbstractModelElement implements Serializable {

	private static final long serialVersionUID = 738142431416512052L;
	
	private String clazz,inline,uniqueClass;
	
	public void addClasses(String...classes){
		setClazz(CommonUtils.getInstance().concatenate(getClazz(), classes, CLAZZ_SEPARATOR,Boolean.TRUE));
	}
	
	public void addClass(String clazz){
		addClasses(clazz);
	}
	
	public void removeClasses(String...classes){
		String value = CommonUtils.getInstance().concatenate(getClazz(), classes, CLAZZ_SEPARATOR,Boolean.TRUE);
		setClazz(StringUtils.replace(getClazz(), value, Constant.EMPTY_STRING));
	}
	
	public void addInlines(String...inlines){
		setInline(CommonUtils.getInstance().concatenate(getInline(), inlines, INLINE_SEPARATOR,Boolean.TRUE));
	}
	
	public void addInline(String inline){
		addInlines(inline);
	}
	
	public void removeInlines(String...inlines){
		String value = CommonUtils.getInstance().concatenate(getClazz(), inlines, INLINE_SEPARATOR,Boolean.TRUE);
		setClazz(StringUtils.replace(getClazz(), value, Constant.EMPTY_STRING));
	}
	
	public void setUniqueClass(String uniqueClass){
		removeClasses(uniqueClass);
		this.uniqueClass = uniqueClass;
		addClasses(getUniqueClass());
	}
	
	@Override
	public String getUiString() {
		return "Class="+clazz+" , style="+inline;
	}
	
}