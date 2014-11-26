/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.hfut.dmic.wordcount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hu
 */
public class CombinedArticle extends Article{
    
    
    
    
    public CombinedArticle(ArrayList<Article> wcList){
        countMap = new HashMap<String, Integer>();
        for(int i=0;i<wcList.size();i++){
            Article wc=wcList.get(i);
            for(Map.Entry<String,Integer> entry:wc.countMap.entrySet()){
                String word=entry.getKey();
                Integer addCount=entry.getValue();
                Integer count=countMap.get(word);
                if(count==null)
                    count=0;
                count+=addCount;
                countMap.put(word, count);
            }
        }
        sort();
    }
    
    

    @Override
    public void initSegmenter() {
    }

    @Override
    public String nextWord() throws Exception {
        return null;
    }

    
   
}
