/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.hfut.dmic.wordcount.example;

import cn.edu.hfut.dmic.wordcount.KeywordsExtractor;
import cn.edu.hfut.dmic.wordcount.Score;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author hu
 */
public class Extract {

    public static void main(String[] args) throws IOException, Exception {
        String content = "开源软件（OSS）提供给业务的利益已经很明确了，实施恰当地开源使用政策，对于维持内部系统的安全性和完整性是至关重要的。授予OSS使用者的权利和义务记录在开源许可证上面，而这个是根据原开发者指定的。开源的一些细节说明经常不能让人充分理解，许多年来，存在着许多开源反对者，争论许可证需求的关键。在业内有如此多虚假或不准确的信息，关于许可证引起的FUD（恐惧、不确定性、怀疑），企业如何能确保他们投资于开源的决定是正确的呢？";
        KeywordsExtractor extractor = new KeywordsExtractor(new File("keyword.ii"));
        
        ArrayList<Score> keywords=extractor.getKeyWords(content, 3);
        System.out.println("-------------------------------------");
        System.out.println("原文:\n"+content);
        System.out.println("-------------------------------------");
        System.out.println("关键词:");
        for(Score score:keywords){
            System.out.println(score.word+"   tfidf="+score.getTfidf());
        }
    }
}
