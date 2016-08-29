package org.apache.lucene.analsys.ko.morph;

import java.util.List;

import org.apache.lucene.analysis.ko.morph.AnalysisOutput;
import org.apache.lucene.analysis.ko.morph.MorphAnalyzer;

import junit.framework.TestCase;

public class MorphAnalyzerTest extends TestCase {

	public void testMorphAnalyzer() throws Exception {
		
		String[] inputs = new String[]{"보일러)가"};
		
		MorphAnalyzer analyzer = new MorphAnalyzer();
		
		for(String input : inputs) {
			analyzer.setDivisibleOne(true);
			
			List<AnalysisOutput> oList = analyzer.analyze(input);
			for(AnalysisOutput o : oList) {
				System.out.println(o.toString() + "/"+o.getScore()+":"+o.getCNounList().size());
			}
			
//			analyzer.setDivisibleOne(false);
//			oList = analyzer.analyze(input);
//			for(AnalysisOutput o : oList) {
//				System.out.println(o.toString() + "/"+o.getScore()+":"+o.getCNounList().size());
//			}
		}
	}
	
}
