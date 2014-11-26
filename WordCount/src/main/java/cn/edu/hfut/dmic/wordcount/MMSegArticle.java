/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.hfut.dmic.wordcount;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.SimpleSeg;
import com.chenlb.mmseg4j.Word;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author hu
 */
public class MMSegArticle extends Article {

    public static MMSeg segmenter;
    Word w;

    
    public MMSegArticle(String content, String articleID) throws Exception {
        super(content, articleID);
  
    }

    @Override
    public void initSegmenter() {
        synchronized(segmenter){
            segmenter.reset(new StringReader(content));
        }
    }
    public static HashSet<String> stopWords = null;
    static{
        try {
            stopWords=new HashSet<String>();
            InputStream is = MMSegArticle.class.getResourceAsStream("/stopword.dic");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line=br.readLine())!=null){
                stopWords.add(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        Seg seg=null;
        seg=new SimpleSeg(Dictionary.getInstance());      
        segmenter = new MMSeg(new StringReader(""), seg);
    }

    

    @Override
    public String nextWord() throws Exception {
        while(true){
            w = segmenter.next();
            if(w==null)
                return null;
            String next=w.getString();
            if(stopWords.contains(next)){
                continue;
            }
            return next;
        }
        
    }

}
