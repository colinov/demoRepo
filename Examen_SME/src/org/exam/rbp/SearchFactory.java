package org.exam.rbp;

public class SearchFactory {

	public static SearchService getSearcher(String mode){
		if(mode!=null&&SearchConstants.HASHTAG.equals(mode.trim())){
			return new HashTagSearchService();
		} else if(mode!=null&&SearchConstants.TWITERACCOUNT.equals(mode.trim())){
			return new TwitterAccountSearchService();
		} else if(mode!=null&&SearchConstants.PROPERNAME.equals(mode.trim())){
			return new ProperNameSearchService();
		} else{
			//TODO otros casos por implementar
			return null;
		}
	}
}