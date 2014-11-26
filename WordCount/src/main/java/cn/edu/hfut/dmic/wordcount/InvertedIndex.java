/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.hfut.dmic.wordcount;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

/**
 *
 * @author hu
 */
public class InvertedIndex {

    public InvertedIndex() {
    }

    public InvertedIndex(File file) throws IOException{
        read(file);
    }
    
    
    public HashMap<String, Integer> countMap = new HashMap<String, Integer>();
    public HashMap<String, HashSet<String>> occurMap = new HashMap<String, HashSet<String>>();
    public HashSet<String> articleSet=new HashSet<String>();
   
    public void addArticle(Article article) {
        articleSet.add(article.articleID);
        for (String word : article.getSortedWords()) {
            addWord(word, article.articleID);
        }
    }

    private synchronized void addWord(String word, String articleID) {
        HashSet<String> occur = occurMap.get(word);
        if (occur == null) {
            occur = new HashSet<String>();
            occurMap.put(word, occur);
        }
        if (occur.contains(articleID)) {
            return;
        }
        occur.add(articleID);
        Integer count = countMap.get(word);
        if (count == null) {
            count = 0;
        }
        count++;
        countMap.put(word, count);
    }

    public void save(File file) throws FileNotFoundException, IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
        dos.writeInt(occurMap.size());
       
        for (Entry<String, HashSet<String>> entry : occurMap.entrySet()) {
            String word=entry.getKey();
            dos.writeUTF(word);
            HashSet<String> occur = entry.getValue();
            dos.writeInt(occur.size());
            for(String articleID:occur){
                dos.writeUTF(articleID);
            }
        }
        dos.close();
    }
    
    public void read(File file) throws FileNotFoundException, IOException{
        countMap = new HashMap<String, Integer>();
        occurMap = new HashMap<String, HashSet<String>>();
        articleSet=new HashSet<String>();

        
        DataInputStream dis=new DataInputStream(new FileInputStream(file));
        
        int size=dis.readInt();
       
        for(int i=0;i<size;i++){
            String word=dis.readUTF();
            int count=dis.readInt();
            countMap.put(word, count);
            HashSet<String> occur=new HashSet<String>();
            for(int j=0;j<count;j++){
                String articleID=dis.readUTF();
                occur.add(articleID);
                if(!articleSet.contains(articleID)){
                    articleSet.add(articleID);
                }
            }
            occurMap.put(word, occur);
            
        }
        dis.close();
    }

    public static void main(String[] args) {
        InvertedIndex iindex = new InvertedIndex();
        iindex.addWord("test", "1");
        iindex.addWord("test2", "1");
        iindex.addWord("test", "2");
        iindex.addWord("test", "1");
        iindex.addWord("test3", "1");

        System.out.println(iindex.occurMap);
    }
}
