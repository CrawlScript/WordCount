/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.hfut.dmic.wordcount;

import cn.edu.hfut.dmic.htmlbot.contentextractor.ContentExtractor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Document;



/**
 *
 * @author hu
 */
public class KeywordsExtractor {
    
    protected InvertedIndex iindex;
    
    public KeywordsExtractor(){
        iindex=new InvertedIndex();
    }
    public KeywordsExtractor(File iindexFile) throws IOException{
        iindex=new InvertedIndex(iindexFile);
    }
    
    public void addTrainData(String content,String articleID) throws Exception{
        MMSegArticle article=new MMSegArticle(content, articleID);
        iindex.addArticle(article);
    }
    
    
    
    public static void main(String[] args) throws Exception{
       
//        File f=new File("/home/hu/data/keywords");
//        KeywordsExtractor ke=new KeywordsExtractor();
//        ke.addTrainDataFromDir(f);
//        ke.save(new File("/home/hu/data/keyword.ii"));
               
        
        File f=new File("/home/hu/data/keyword.ii");
        KeywordsExtractor ke=new KeywordsExtractor(f);
        String content=ContentExtractor.getContentByURL("http://news.xinhuanet.com/tw/2014-11/26/c_1113413480.htm");
        System.out.println(content);
        Article at=new MMSegArticle(content,null);
        at.computeTFIDF(ke.iindex);
    
        System.out.println(at.sortedTfidf);
       
    }
    
    public ArrayList<Score> getKeyWords(String content,int topN) throws Exception{
        ArrayList<Score> result=new ArrayList<Score>();
        Article article=new MMSegArticle(content,null);
        article.computeTFIDF(iindex);
        int min=Math.min(topN, article.sortedTfidf.size());
        for(int i=0;i<min;i++){
            String word=article.sortedTfidf.get(i);
            double tfidf=article.tfidfMap.get(word);
            Score score=new Score(word, tfidf);
            result.add(score);
        }
        return result;
    }
    

    public void addTrainDataFromDir(File dir) throws IOException, Exception{
        File[] files=dir.listFiles();
        for(int i=0;i<files.length;i++){
            File f=files[i];
            String fName=f.getName();
            String content=WordCountUtil.readAllFromFile(f);
            Article article=new MMSegArticle(content, fName);
            iindex.addArticle(article);
            //System.out.println(article.toCSV());
            int process=(int) ((i+1)*100.0/files.length);
            System.out.println("Train:"+process+"%");
        }
    }
    
    public void read(File f) throws IOException{
        iindex.read(f);
    }
    
    public void save(File f) throws IOException{
        iindex.save(f);
    }
    
    
    
    
    
    
}
