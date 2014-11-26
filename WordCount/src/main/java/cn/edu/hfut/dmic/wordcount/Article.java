/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.hfut.dmic.wordcount;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;


/**
 *
 * @author hu
 */
public abstract class Article {
    
    protected LinkedList<String> sortedWords=null;
    protected HashMap<String, Integer> countMap=null;
    protected HashMap<String, Double> tfidfMap=null;
    protected LinkedList<String> sortedTfidf=null;
    protected String content;
    protected String articleID;
    
   
    
    private double computeTFIDF(String word,int total,InvertedIndex iindex){
        Integer count=countMap.get(word);
        if(count==null){
            return 0;
        }
        double tf=(count+1.0)/(total+1.0);
        double idf=iindex.articleSet.size()+1.0;
        Integer df=iindex.countMap.get(word);
        if(df==null)
            df=0;
        idf=idf/(df+1.0);
        idf=Math.log(idf);
        return tf*idf;
        
    }
    public void computeTFIDF(InvertedIndex iindex){
        int total=getTotal();
        tfidfMap=new HashMap<String, Double>();
        for(Entry<String,Integer> entry:countMap.entrySet()){
            String word=entry.getKey();
            double tfidf=computeTFIDF(word, total, iindex);
            tfidfMap.put(word, tfidf);
        }
        sortTFIDF();
        
    }
    
    public int getTotal(){
        int result=0;
        for(Entry<String,Integer> entry:countMap.entrySet()){
            result+=entry.getValue();
        }
        return result;
    }

    
    
    
   
    
    public Article(){
        
    }

    public LinkedList<String> getSortedWords() {
        
        return sortedWords;
    }

    public void setSortedWords(LinkedList<String> sortedWords) {
        this.sortedWords = sortedWords;
    }

    public HashMap<String, Integer> getCountMap() {
        return countMap;
    }

    public void setCountMap(HashMap<String, Integer> countMap) {
        this.countMap = countMap;
    }
    
    

    public Article(String content, String articleID) throws Exception {
        this.content = content;
        this.articleID = articleID;
        initSegmenter();
        segment();
    }
    
    public abstract void initSegmenter();
    
    
    
    public void segment() throws Exception{

     
        countMap = new HashMap<String, Integer>();
        String word=null;
        while ((word=nextWord())!= null) {
            
            Integer count = countMap.get(word);
            if (count == null) {
                count = 0;
            }
            count++;
            countMap.put(word, count);
        }
     
        sort();
        
    }
    
    
    
    
    public void sort(){
            sortedWords=WordCountUtil.sortMapToList(countMap);   
    }
    
    public void sortTFIDF(){
            sortedTfidf=WordCountUtil.sortDoubleMapToList(tfidfMap);
    }
    
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        for(String word:sortedWords){
            int count=countMap.get(word);
            sb.append(word+"="+count+"\n");
        }
        return sb.toString();
    }
    
    public String toCSV(){
        StringBuilder sb=new StringBuilder();
        for(String word:sortedWords){
            int count=countMap.get(word);
            sb.append(word+","+count+"\n");
        }
        return sb.toString();
    }
    
    public abstract String nextWord() throws Exception;
}
