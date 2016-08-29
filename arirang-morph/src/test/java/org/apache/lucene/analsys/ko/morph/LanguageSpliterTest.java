package org.apache.lucene.analsys.ko.morph;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.ko.morph.LangToken;
import org.apache.lucene.analysis.ko.morph.LanguageSpliter;
import org.junit.Test;

public class LanguageSpliterTest {

	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSplit() throws Exception {
		String str = "漢字ab가나달1234";
		
		LanguageSpliter spliter = new LanguageSpliter();
		
		List<LangToken> tokenList = spliter.split(str);
		
		for(LangToken t : tokenList) {
			System.out.println(t.getTerm()+":"+t.getType()+":"+t.getOffset());
		}
		
	}
}
