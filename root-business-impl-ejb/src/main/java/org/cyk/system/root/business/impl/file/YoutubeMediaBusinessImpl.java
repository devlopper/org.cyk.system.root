package org.cyk.system.root.business.impl.file;

import java.io.Serializable;
import java.net.URI;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.YoutubeMediaBusiness;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;

public class YoutubeMediaBusinessImpl implements YoutubeMediaBusiness , Serializable {

	private static final long serialVersionUID = -3596293198479369047L;

	private static final Integer VIDEO_ID_LENGTH = 11;
	private static final String VIDEO_ID_PATTERN_STRING = ".{"+VIDEO_ID_LENGTH+"}";
	
	private static final String VIDEO_EMBEDDED_URI_FORMAT = "http://www.youtube.com/embed/%s";
	
	private static final HashSet<URI> DOMAIN_URIS = new LinkedHashSet<>();
	private static final HashSet<Pattern> VIDEO_URI_PATTERNS = new HashSet<>();
	private static final String YOUTUBE_THUMBNAIL_URI_FORMAT = "http://img.youtube.com/vi/%s/%s.jpg";
	
	static{
		DOMAIN_URIS.add(URI.create("http://www.youtube.com"));
		DOMAIN_URIS.add(URI.create("https://www.youtube.com"));
		
		addPattern("/watch\\?v=("+VIDEO_ID_PATTERN_STRING+")");//http://www.youtube.com/watch?v=VIDEO_ID
		addPattern("/v/("+VIDEO_ID_PATTERN_STRING+")");//http://www.youtube.com/v/VIDEO_ID
		addPattern("/embed/("+VIDEO_ID_PATTERN_STRING+")");//http://www.youtube.com/embed/VIDEO_ID
		
	}
	
	private static void addPattern(String string){
		//TODO must concact all domains
		VIDEO_URI_PATTERNS.add(Pattern.compile("http[s]?://www\\.youtube\\.com"+string,Pattern.CASE_INSENSITIVE));
	}
	
	@Override
	public String findVideoId(URI uri) {
		exceptionIfNotBelongingToDomain(uri);
		for(Pattern pattern : VIDEO_URI_PATTERNS){
			Matcher matcher = pattern.matcher(uri.toString());
			if(matcher.find()){
				if(StringUtils.length(matcher.group(1))==VIDEO_ID_LENGTH)
					return matcher.group(1);
			}
		}
		ExceptionUtils.getInstance().exception("findVideoId");
		return null;
	}
	
	@Override
	public URI findThumbnailUri(URI uri,ThumnailSize size) {
		//TODO use Youtube API to fetch data
		exceptionIfNotBelongingToDomain(uri);
		String sizeString=null,uriString=null;
	
		switch(size){
		case _1:sizeString = "default";break;
		case _2:sizeString = "hqdefault";break;
		case _3:sizeString = "mqdefault";break;
		case _4:sizeString = "sddefault";break;
		case _5:sizeString = "maxresdefault";break;
		}
		uriString = String.format(YOUTUBE_THUMBNAIL_URI_FORMAT, findVideoId(uri),sizeString);
		
		return URI.create(uriString);
	}

	@Override
	public URI findEmbeddedUri(URI uri) {
		return URI.create(String.format(VIDEO_EMBEDDED_URI_FORMAT, findVideoId(uri)));
	}
	
	private Boolean belongsToDomain(URI uri){
		for(URI domain : DOMAIN_URIS)
			if(!domain.relativize(uri).equals(uri))
				return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	private void exceptionIfNotBelongingToDomain(URI uri){
		ExceptionUtils.getInstance().exception(!belongsToDomain(uri), "exception.media.uri.domain.belonging",new Object[]{uri});
	}
	
	public static void main(String[] args) {
		//YoutubeMediaBusinessImpl youtube = new YoutubeMediaBusinessImpl();
		//String ps = "http[sS]://www\\.youtube\\.com/v/.{11}, http[sS]://www\\.youtube\\.com/embed/.{11}, http[sS]://www\\.youtube\\.com/watch\\?v=(.{11})";
		//String ps = "http[sS]://www\\.youtube\\.com/watch\\?v=(.{11})";
		String ps = "http[s]?://www\\.youtube\\.com/watch\\?v=(.{11})";
		Pattern p = Pattern.compile(ps);
		String url = "https://www.youtube.com/watch?v=3svPkthjWcM";
		Matcher matcher = p.matcher(url);
		
		//ystem.out.println(matcher.find());
		//ystem.out.println(matcher.group(1));
		
		//ystem.out.println(youtube.findVideoId(URI.create("http://www.youtube.com/watch?v=3svPkthjWcM")));
	}
	
	
}
