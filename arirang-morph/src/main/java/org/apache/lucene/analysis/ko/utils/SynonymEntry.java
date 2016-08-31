package org.apache.lucene.analysis.ko.utils;

public class SynonymEntry {
	
	private String word;
	
	private String category;
	
	private String detail;
	
	private int degree;
	
	public SynonymEntry() {
	}
	
	public SynonymEntry(String word, String category, String detail) {
		this.word = word;
		this.category = category;
		this.detail = detail;
		this.degree = 10;
	}
	
	public SynonymEntry(String word, String category, String detail, int degree) {
		this.word = word;
		this.category = category;
		this.degree = degree;
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
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	public int getDegree() {
		return degree;
	}
	
}
