package zoe;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.analysis.ko.morph.AnalysisOutput;
import org.apache.lucene.analysis.ko.morph.MorphAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public class Test {
	
	public static void main(String args[]) {
		
		BufferedReader br = null ;
		
		try {
			br = new BufferedReader(new FileReader(args[0])) ;
			String line = null ;
			while( (line=br.readLine()) != null ) {
				if( line.trim().equals("") )
					continue ;
				analyze(line) ;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if( br != null ) try { br.close(); } catch(Exception ee) {}
		}
	}
	
	public static void analyze(String szText) {
		//String szText = "하늘에 계신 우리 아버지 아버지의 이름이 거룩히 빛나시" ;
		TokenStream ts = null ;
		String output = "" ;
		try {
			KoreanAnalyzer analyzer = new KoreanAnalyzer() ;
			MorphAnalyzer morph = new MorphAnalyzer() ;
			ts = analyzer.tokenStream(null,  new StringReader(szText)) ;
			CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class) ;
			
			
			
			ts.reset() ;
			while(ts.incrementToken()) {
				String token = termAtt.toString();
				//System.out.println(token) ;
				
				//System.out.println("<<<<<<<") ;
				List<AnalysisOutput> results = morph.analyze(token) ;
				for(AnalysisOutput o : results){
					//System.out.println("[" + o.toString() +"]") ;
					
					output += "[" + o.getPatn() + ":" + o.toString() +"]" ;
				}
				//System.out.println(">>>>>>>>") ;
				
			}
			System.out.println(szText) ;
			System.out.println(output) ;
			ts.end() ;			
		}
		catch(Exception e) {
			System.out.println(szText + ">> Exception : " + e.getMessage()) ;
		}
		finally {
			if( ts != null ) try { ts.close() ; } catch(Exception ee){}
		}
	}
	
	public static void analyze2(String szText) {
		//String szText = "하늘에 계신 우리 아버지 아버지의 이름이 거룩히 빛나시" ;
		TokenStream ts = null ;
		String output = "" ;
		try {
			KoreanAnalyzer analyzer = new KoreanAnalyzer() ;
			MorphAnalyzer morph = new MorphAnalyzer() ;
			ts = analyzer.tokenStream(null,  new StringReader(szText)) ;
			CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class) ;
			
			
			String tokens[] = szText.split("\\s") ;

			for(int i=0 ; i<tokens.length ; i++) {
				String token = tokens[i] ;
				//System.out.println(token) ;
				
				//System.out.println("<<<<<<<") ;
				List<AnalysisOutput> results = morph.analyze(token) ;
				for(AnalysisOutput o : results){
					//System.out.println("[" + o.toString() +"]") ;
					
					output += "[" + o.toString() +"]" ;
				}
				//System.out.println(">>>>>>>>") ;
				
			}
			System.out.println(szText) ;
			System.out.println(output) ;
			ts.end() ;			
		}
		catch(Exception e) {
			System.out.println(szText + ">> Exception : " + e.getMessage()) ;
		}
		finally {
			if( ts != null ) try { ts.close() ; } catch(Exception ee){}
		}
	}

}
