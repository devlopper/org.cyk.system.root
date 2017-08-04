package org.cyk.system.root.business.impl.userinterface.style;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.userinterface.style.CascadeStyleSheetBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

public class CascadeStyleSheetBusinessImpl extends AbstractBean implements CascadeStyleSheetBusiness {

	private static final long serialVersionUID = 1L;

	@Override
	public void addClasses(CascadeStyleSheet cascadeStyleSheet,String...classes){
		cascadeStyleSheet.addClasses(classes);
	}
	
	@Override
	public void removeClasses(CascadeStyleSheet cascadeStyleSheet,String...classes){
		cascadeStyleSheet.removeClasses(classes);
	}
	
	@Override
	public void addInlines(CascadeStyleSheet cascadeStyleSheet,String...inlines){
		cascadeStyleSheet.addInlines(inlines);
	}
	
	@Override
	public void removeInlines(CascadeStyleSheet cascadeStyleSheet,String...inlines){
		cascadeStyleSheet.removeInlines(inlines);
	}
	
	@Override
	public void setUniqueClass(CascadeStyleSheet cascadeStyleSheet,String uniqueClass){
		cascadeStyleSheet.setUniqueClass(uniqueClass);
	}
	
	/**/
	@Override
	public String generateClass(String prefix,String label){
		return StringUtils.join(new String[]{StringUtils.lowerCase(StringUtils.defaultIfBlank(prefix, null)),normalise(label)},Constant.CHARACTER_UNDESCORE.toString());
	}
	
	@Override
	public String generateClass(Class<? extends AbstractIdentifiable> identifiableClass,Object identifier){
		return generateClass(generateClass(RootConstant.Configuration.CascadeStyleSheet.IDENTIFIABLE_CLASS_PREFIX, identifiableClass.getSimpleName())
				, identifier == null ? Constant.EMPTY_STRING : identifier.toString());
	}
	
	@Override
	public String generateClass(AbstractIdentifiable identifiable){
		return generateClass(identifiable.getClass(), StringUtils.isBlank(identifiable.getCode()) ? identifiable.getIdentifier() : identifiable.getCode());
	}
	
	@Override
	public String generateUniqueClass(AbstractIdentifiable identifiable){
		return StringUtils.join(new String[]{generateClass(identifiable),String.valueOf(System.currentTimeMillis()),RandomStringUtils.randomAlphabetic(2)},Constant.CHARACTER_UNDESCORE.toString());
	}
	
	@Override
	public String generateUniqueClass(String prefix,String label){
		return StringUtils.join(new String[]{generateClass(prefix, label),String.valueOf(System.currentTimeMillis()),RandomStringUtils.randomAlphabetic(2)},Constant.CHARACTER_UNDESCORE.toString());
	}
	
	/**/
	
	@Override
	public String normalise(String value){
		value = StringUtils.replaceChars(value, BAD_CHARACTERS, SUB_CHARACTERS);
		value = StringUtils.replaceChars(value, SEP_CHARACTERS, StringUtils.repeat(Constant.CHARACTER_UNDESCORE.toString(), SEP_CHARACTERS.length()));
		String charactersToDeleted = Constant.CHARACTER_SPACE.toString();
		value = StringUtils.lowerCase(StringUtils.replaceChars(value, charactersToDeleted, null));
		return value;
	}
	
	private static final String BAD_CHARACTERS = "àéèôùç";
	private static final String SUB_CHARACTERS = "aeeouc";
	private static final String SEP_CHARACTERS = "-`' &#()[]{}|.";
	
}
