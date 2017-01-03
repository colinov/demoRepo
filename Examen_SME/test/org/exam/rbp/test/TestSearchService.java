package org.exam.rbp.test;

import org.exam.rbp.SearchConstants;
import org.exam.rbp.SearchFactory;
import org.exam.rbp.SearchService;
import org.junit.Assert;
import org.junit.Test;

public class TestSearchService {

	@Test
	public void testHashtag(){
		System.out.println("Init testHashtag");
		SearchService search=SearchFactory.getSearcher(SearchConstants.HASHTAG);
		boolean result=search.search("in/url.txt");
		Assert.assertTrue(result);
		System.out.println("End testHashtag");
	}
	
	@Test
	public void testTwiteraccount(){
		System.out.println("Init testTwiteraccount");
		SearchService search=SearchFactory.getSearcher(SearchConstants.TWITERACCOUNT);
		boolean result=search.search("in/url.txt");
		Assert.assertTrue(result);
		System.out.println("End testTwiteraccount");
	}
	
	@Test
	public void testProperName(){
		System.out.println("Init testProperName");
		SearchService search=SearchFactory.getSearcher(SearchConstants.PROPERNAME);
		boolean result=search.search("in/url.txt");
		Assert.assertTrue(result);
		System.out.println("End testProperName");
	}
	
}