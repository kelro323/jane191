package org.apache.lucene.analysis.ko.morph;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Collections;
import java.util.List;

public class WordEntry {

  public static final int IDX_NOUN = 0;
  public static final int IDX_VERB = 1;
  public static final int IDX_BUSA = 2;
  public static final int IDX_DOV = 3;
  public static final int IDX_BEV = 4;
  public static final int IDX_NE = 5;
  public static final int IDX_ADJ = 6; // 형용사
  public static final int IDX_NPR = 7;  // 명사의 분류 (M:Measure)
  public static final int IDX_CNOUNX = 8; 
  public static final int IDX_REGURA = 9;
  /*
   * 명사 분류 : a.동물 / b.바이오 / c.성격 / d.대명사 / e.교육 / f.금융 / g.기타(일반) / h.인명(고유) / i.IT / j.자연과학 / k.건설
   *         l.위치 / m.제조 / n.수사 / o.의존 / p.지명 / q. / r.고유(기타) / s.사람(일반명사) / t.시간 / u.유통 / v.서비스 
   *         w.법률/ x. / y.음악 / z.디자인&미디어
   * 동사 분류 : i.자동사 / t.타동사 / j.형용사 / v.자동사+타동사 / d.자동사+형용사 / k.타동사+형용사 / b.보조동사(보조형용사) -b는 더 세분히 나눌 지도 모름
   * 부사 분류 : d.지시관형사 / n.수관형사 / p.일반관형사 / c.접속부사 / a.일반부사
   */
  
  /**
   * 단어
   */
  private String word;
  
  /**
   * 단어특성
   */
  private char[] features;
  
  private List<CompoundEntry> compounds = Collections.EMPTY_LIST;
  
  public WordEntry() {
    
  }
  
  public WordEntry(String word) {
    this.word = word;
  }
  
  public WordEntry(String word, char[] cs) {
    this.word = word;
    this.features = cs;
  }
  
  public WordEntry(String word, List<CompoundEntry> c) {
    this.word = word;
    this.compounds = c;
  }
  
  public void setWord(String w) {
    this.word = w;
  }
  
  public String getWord() {
    return this.word;
  }
  
  public void setFeatures(char[] cs) {
    this.features = cs;
  }
  
  public char getFeature(int index) {
    if(features==null||features.length<=index) return '0';    
    return features[index];
  }
  
  public char[] getFeatures() {
    return this.features;
  }
  
  public void setCompounds(List<CompoundEntry> c) {
    this.compounds = c;
  }
  
  public List<CompoundEntry> getCompounds() {
    return this.compounds;
  }
}
