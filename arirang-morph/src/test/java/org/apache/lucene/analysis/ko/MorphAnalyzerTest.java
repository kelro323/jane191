package org.apache.lucene.analysis.ko;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.ko.morph.AnalysisOutput;
import org.apache.lucene.analysis.ko.morph.CompoundNounAnalyzer;
import org.apache.lucene.analysis.ko.morph.LangToken;
import org.apache.lucene.analysis.ko.morph.LanguageSpliter;
import org.apache.lucene.analysis.ko.morph.MorphAnalyzer;
import org.apache.lucene.analysis.ko.utils.VerbUtil;

import junit.framework.TestCase;

public class MorphAnalyzerTest extends TestCase {

	public void testAnalyzer() throws Exception {
		MorphAnalyzer analyzer = new MorphAnalyzer();
		String text = "경대하는 사람이라 생각하였다.";
		
		CompoundNounAnalyzer cnAnalyzer = new CompoundNounAnalyzer();
		StringTokenizer str = new StringTokenizer(text,".");
		List<AnalysisOutput> outputs = new ArrayList<AnalysisOutput>();
		while(str.hasMoreTokens()) {
			String info = str.nextToken();
			StringTokenizer str2 = new StringTokenizer(info);
			while(str2.hasMoreTokens()) {
				String info2 = str2.nextToken();
				outputs = analyzer.analyze(info2);
				for(AnalysisOutput o : outputs) {
					System.out.println(o+", "+o.getPatn()+", "+
				o.getScore()+", "+o.getPos()+" UP:"+o.getUsedPos()+"/"+o.getUsedPosType());
					//System.out.println(o.getVsfx()+", "+o.getPomi());	
					if(o.getScore() == AnalysisOutput.SCORE_ANALYSIS) {
						List<LangToken> lang = LanguageSpliter.split(o.getSource());
						for(LangToken to : lang) {
							System.out.println(to.getType());
						}
					}
				}
			}
		}
	}
}
