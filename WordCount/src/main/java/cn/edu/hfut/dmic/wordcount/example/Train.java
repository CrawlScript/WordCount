/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.hfut.dmic.wordcount.example;

import cn.edu.hfut.dmic.wordcount.KeywordsExtractor;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author hu
 */
public class Train {
    
    public static void main(String[] args) throws IOException, Exception{
        File f=new File("newsdata");
        KeywordsExtractor ke=new KeywordsExtractor();
        ke.addTrainDataFromDir(f);
        ke.save(new File("keyword.ii"));
    }
    
}
