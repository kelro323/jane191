package org.apache.lucene.analysis.ko.utils;

public class SynonymEntry {
	public static final int IDX_CHARACTER = 0;
	public static final int IDX_TECH = 1;
	public static final int IDX_KNOWLEDGE = 2;
	
	private String word;
	
	private String category;
	
	private int degree;
	
	public SynonymEntry() {
		
	}
	
	public SynonymEntry(String word, String category, int degree) {
		this.word = word;
		this.category = category;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	public int getDegree() {
		return degree;
	}
	
}
