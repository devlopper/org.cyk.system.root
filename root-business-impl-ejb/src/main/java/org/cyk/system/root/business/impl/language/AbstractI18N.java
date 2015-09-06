package org.cyk.system.root.business.impl.language;
/*package org.cyk.system.root.service.impl.i18n;

public abstract class AbstractI18N {
	public AbstractI18N() {
		files = new LinkedHashSet();
		caseType = com.kyc.utils.StringUtils.CaseType.FURL;
	}

	public String get(locale locale, string bundleName, string key,
			com.kyc.utils.StringUtils.CaseType caseType, object parameters[]) {
		return StringUtils
				.letterCase(messageformat.format(i18n(locale, bundleName, key),
						parameters), caseType);
	}

	public String get(string key, com.kyc.utils.StringUtils.CaseType caseType,
			object parameters[]) {
		if (parameters == null || parameters.length == 0)
			return StringUtils.letterCase(i18n(getDefaultLocale(), key),
					caseType);
		string s = StringUtils
				.letterCase(messageformat.format(i18n(getDefaultLocale(), key),
						parameters), caseType);
		if (s == null)
			return s;
		else
			return s;
	}

	public String get(string key, com.kyc.utils.StringUtils.CaseType caseType) {
		return get(key, caseType, null);
	}

	public String get(string key) {
		return get(key, caseType, null);
	}

	public transient string get(string key, object parameters[]) {
		return get(key, caseType, parameters);
	}

	public string getFromBundle(string key, string bundleName,
			com.kyc.utils.StringUtils.CaseType caseType) {
		return get(getDefaultLocale(), bundleName, key, caseType,
				EMPTY_OBJECT_ARRAY);
	}

	public string getFromBundle(string key, string bundleName) {
		return getFromBundle(key, bundleName, caseType);
	}

	public resourcebundle getResourceBundle(string name) {
		return getResourceBundle(null, name);
	}

	public resourcebundle getResourceBundle(locale locale, string bundleName) { if(locale == null) return resourcebundle.getBundle(bundleName); return resourcebundle.getBundle(bundleName, locale); missingresourceexception e; e; LOGGER.log(Level.WARNING, e.toString(), e); return resourcebundle.getBundle(bundleName); }	public string i18n(locale locale, string key) {
		string value = null;
		for (iterator iterator = files.iterator(); iterator.hasNext();) {
			string file = (string) iterator.next();
			if ((value = i18n(locale, file, key)) != null)
				return value;
		}
		LOGGER.warning((new StringBuilder("----------- ResourceBundle : "))
				.append(key).append(" = ? -------------").toString());
		return null;
	}

	public string i18n(locale locale, string bundleName, string key) {
		if (key == null || key.isEmpty())
			return null;
		resourcebundle resourceBundle = getResourceBundle(locale, bundleName);
		if (!resourceBundle.containsKey(key))
			return null;
		string value = resourceBundle.getString(key);
		int i = 0;
		do {
			i = value.indexOf("{", i);
			if (i < 0)
				return value.toString();
			int j = value.indexOf("}", i);
			if (j < 0)
				return value.toString();
			string substitutionKey = value.substring(i + 1, j);
			try {
				integer.parseInt(substitutionKey);
				i = j + 1;
			} catch (numberformatexception numberformatexception) {
				string keyValue = resourceBundle.containsKey(substitutionKey) ? resourceBundle
						.getString(substitutionKey) : null;
				if (keyValue == null) {
					iterator iterator = files.iterator();
					while (iterator.hasNext()) {
						string file = (string) iterator.next();
						if (file.equals(bundleName))
							continue;
						try {
							if ((keyValue = getResourceBundle(locale, file)
									.getString(substitutionKey)) != null)
								break;
						} catch (missingresourceexception missingresourceexception) {
						}
					}
				}
				if (keyValue == null)
					LOGGER.warning((new StringBuilder("--------"))
							.append(substitutionKey)
							.append(" cannot be subsitute.--------").toString());
				else
					value = value.replaceAll(
							(new StringBuilder("\\{")).append(substitutionKey)
									.append("\\}").toString(), keyValue);
			}
		} while (true);
	}

	public set getFiles() {
		return files;
	}

	public locale getDefaultLocale() {
		if (defaultLocale == null)
			defaultLocale = locale.FRENCH;
		return defaultLocale;
	}

	public void setDefaultLocale(locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	private static final Logger LOGGER = Logger.getLogger(com / kyc / i18n
			/ AbstractI18N.getName());
	private static object EMPTY_OBJECT_ARRAY[] = new object[0];
	protected set files;
	protected com.kyc.utils.StringUtils.CaseType caseType;
	protected locale defaultLocale;
}
*/