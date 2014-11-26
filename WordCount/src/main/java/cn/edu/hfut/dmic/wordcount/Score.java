/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.hfut.dmic.wordcount;

/**
 *
 * @author hu
 */
public class Score {
    public String word;
    public double tfidf;

    public Score(String word, double tfidf) {
        this.word = word;
        this.tfidf = tfidf;
    }

    @Override
    public String toString() {
        return word+"   tfidf="+tfidf;
    }
    
    
    
    

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public double getTfidf() {
        return tfidf;
    }

    public void setTfidf(double tfidf) {
        this.tfidf = tfidf;
    }
    
}
