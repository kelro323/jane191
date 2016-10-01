package org.apache.lucene.analysis.ko;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.ko.morph.AnalysisOutput;
import org.apache.lucene.analysis.ko.morph.AnalysisOutputComparator;
import org.apache.lucene.analysis.ko.morph.CompoundEntry;
import org.apache.lucene.analysis.ko.morph.CompoundNounAnalyzer;
import org.apache.lucene.analysis.ko.morph.LangToken;
import org.apache.lucene.analysis.ko.morph.LanguageSpliter;
import org.apache.lucene.analysis.ko.morph.MorphAnalyzer;
import org.apache.lucene.analysis.ko.morph.PatternConstants;
import org.apache.lucene.analysis.ko.morph.WordEntry;
import org.apache.lucene.analysis.ko.utils.DictionaryUtil;
import org.apache.lucene.analysis.ko.utils.EomiUtil;
import org.apache.lucene.analysis.ko.utils.FileUtil;
import org.apache.lucene.analysis.ko.utils.IrregularUtil;
import org.apache.lucene.analysis.ko.utils.KoreanEnv;
import org.apache.lucene.analysis.ko.utils.MorphUtil;
import org.apache.lucene.analysis.ko.utils.NounUtil;
import org.apache.lucene.analysis.ko.utils.SyllableUtil;
import org.apache.lucene.analysis.ko.utils.Utilities;
import org.apache.lucene.analysis.ko.utils.VerbUtil;

import junit.framework.TestCase;

public class MorphAnalyzerTest extends TestCase {

	public void testAnalyzer() throws Exception {
		MorphAnalyzer analyzer = new MorphAnalyzer();
		String text = "눈도 오고 해서 일찍 귀가했다.";
		
		//"해서" 처리 방안 고민
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
				o.getScore()+", "+o.getPos()+" RP:"+o.getUsedPos()+"/"+o.getUsedPosType());
					
					System.out.println(o.getVsfx()+", "+o.getElist());
				}
			
			}
		}
	
		String input ="사랑한지";
		List<AnalysisOutput> candidates = new ArrayList<AnalysisOutput>();
		boolean josaFlag = true;
		boolean eomiFlag = true;
		String stem = "무거운";
		String eomi = "지";
		String[] morphs = EomiUtil.splitEomi(stem, eomi);
	
	    /*        
		int strlen = input.length();
		boolean isVerbOnly = false;
		analyzer.analysisWithEomi(input,"",candidates);
		    
		for(int i=strlen-1;i>0;i--) {
		      
			String stem = input.substring(0,i);
			String eomi = input.substring(i);

			char[] feature =  SyllableUtil.getFeature(eomi.charAt(0));    
			if(!isVerbOnly&&josaFlag&&feature[SyllableUtil.IDX_JOSA1]=='1') {
				analyzer.analysisWithJosa(stem,eomi,candidates);
			}
		      
			if(eomiFlag) {      
				analyzer.analysisWithEomi(stem,eomi,candidates);
			}      
		      
			if(josaFlag&&feature[SyllableUtil.IDX_JOSA2]=='0') josaFlag = false;
			if(eomiFlag&&feature[SyllableUtil.IDX_EOMI2]=='0') eomiFlag = false;
		      
		    if(!josaFlag&&!eomiFlag) break;
		}
		boolean changed = false;
	    boolean correct = false;
	    for(AnalysisOutput o:candidates) {
	    
	      if(o.getPatn()==PatternConstants.PTN_N) {
	    	  if(o.getScore()==AnalysisOutput.SCORE_CORRECT) {
	    		  correct=true;
	    		  break;
	    	  } else
	    		  continue;
	      }
	      
	      if(o.getScore()==AnalysisOutput.SCORE_CORRECT || isVerbOnly) {
	    	if(o.getPatn()<=PatternConstants.PTN_NJ && !isVerbOnly) analyzer.confirmCNoun(o, true);
	        break;
	      }
	      
	      if(o.getPatn()<PatternConstants.PTN_VM&&o.getStem().length()>2) {
	        if(!(correct&&o.getPatn()==PatternConstants.PTN_N) 
	        		&& !"내".equals(o.getVsfx())) 
	        	analyzer.confirmCNoun(o);
	        if(o.getScore()>=AnalysisOutput.SCORE_COMPOUNDS) changed=true;
	      }
	    
	    }
		for(AnalysisOutput c : candidates) {
			System.out.println(c+", "+c.getScore()+", "+c.getStem());
		}
		List<AnalysisOutput> results = new ArrayList<AnalysisOutput>();  
	    boolean hasCorrect = false;
	    boolean hasCorrectNoun = false;
	    boolean correctCnoun = false;
	    HashMap<String, AnalysisOutput> stems = new HashMap<String, AnalysisOutput>();
	    AnalysisOutput noun = null;
	    
	    double ratio = 0;
	    AnalysisOutput compound = null;
	    for(AnalysisOutput o:candidates) { 
	        
	        o.setSource(input);
	        
	        if(o.getScore()==AnalysisOutput.SCORE_FAIL) continue; // 분석에는 성공했으나, 제약조건에 실패
	        
	        if(o.getScore()==AnalysisOutput.SCORE_CORRECT && o.getPos()!=PatternConstants.POS_NOUN ) 
	        {
	          
	          hasCorrect = true;
	        }
	        else if(o.getPos()==PatternConstants.POS_NOUN
	            &&o.getScore()>=AnalysisOutput.SCORE_SIM_CORRECT) 
	        {
	          
	          if((hasCorrect||correctCnoun)&&o.getCNounList().size()>0) continue;
	          
	          if(o.getPos()==PatternConstants.POS_NOUN) 
	          {
	          	//복합어 중에서 체언+조사(들, 뿐)을 더 분할하기 위한 조건 문, 새로운 패턴인 NJSM을 추가 , (들(이건 사실 접사),뿐)은 의존명사인데 조사로도 쓰임
	          	//명사이나 조사로도 쓰이는 케이스를 더 찾아서 추가 시키면 될듯(한번 더 analyze하는 방법은 stack over flow error 유발하여 폐기)
	          	if(o.getScore()==AnalysisOutput.SCORE_SIM_CORRECT && 
	          			(o.getStem().endsWith("들")||o.getStem().endsWith("뿐"))) {
	          		int stlength = o.getStem().length();
	          		if(o.getPatn()==PatternConstants.PTN_NJ) {
	          			o.setJosa(o.getStem().substring(stlength-1)+o.getJosa());
	          			o.setStem(o.getStem().substring(0, stlength-1));
	          			o.setScore(AnalysisOutput.SCORE_CORRECT);
	          		} else if(o.getPatn()==PatternConstants.PTN_NSM) {
	          			o.setPatn(PatternConstants.PTN_NJSM);
	          			o.setJosa(o.getStem().substring(stlength-1));
	          			o.setStem(o.getStem().substring(0, stlength-1));
	          			o.setScore(AnalysisOutput.SCORE_CORRECT);
	          		}
	          	}
	            
	          }
	          else if(noun==null) 
	          {
	            noun = o;
	          }
	          else if(o.getPatn()==PatternConstants.PTN_N
	              ||(o.getPatn()>noun.getPatn())||
	              (o.getPatn()==noun.getPatn()&&
	                  o.getJosa()!=null&&noun.getJosa()!=null
	                  &&o.getJosa().length()>noun.getJosa().length())) 
	          {
	            results.remove(noun);
	            
	            noun = o;
	          }
	          hasCorrectNoun=true;
//	          if(o.getCNounList().size()>0) correctCnoun = true;
	        }
	        else if(o.getPos()==PatternConstants.POS_NOUN
	            &&o.getCNounList().size()>0&&!hasCorrect
	            &&!hasCorrectNoun) 
	        {
	          double curatio = NounUtil.countFoundNouns(o);
	          if(ratio<curatio&&(compound==null||(compound!=null&&compound.getJosa()==null))) {
	            ratio  = curatio;
	            compound = o;
	          }
	        }
	        else if(o.getPos()==PatternConstants.POS_NOUN
	            &&!hasCorrect
	            &&!hasCorrectNoun&&compound==null) 
	        {
	          
	        }
	        else if(o.getPatn()==PatternConstants.PTN_NSM) 
	        {
	          
	        } else {

	        }
	      }
	    System.out.println(candidates);
		
		*/
	}
	
}
