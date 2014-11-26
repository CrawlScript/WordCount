/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.hfut.dmic.wordcount;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author hu
 */
public class WordCountUtil {

    public static String readAllFromFile(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        byte[] buf = new byte[2048];
        int read;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((read = fis.read(buf)) != -1) {
            bos.write(buf, 0, read);
        }
        fis.close();
        return bos.toString();

    }

    public static LinkedList<String> sortMapToList(Map<String, Integer> wordMap) {
        LinkedList<String> result = new LinkedList<String>();
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            int count = entry.getValue();
            String word = entry.getKey();
            
            for (int i = 0; i < result.size() + 1; i++) {
                if (i == result.size()) {
                    result.add(i, word);
                    break;
                } else if (wordMap.get(result.get(i)) <= count) {
                    result.add(i, word);
                    break;
                }
            }

        }
        return result;

    }

    public static LinkedList<String> sortDoubleMapToList(Map<String, Double> wordMap) {
        LinkedList<String> result = new LinkedList<String>();
        for (Map.Entry<String, Double> entry : wordMap.entrySet()) {
            Double count = entry.getValue();
            String word = entry.getKey();
            for (int i = 0; i < result.size() + 1; i++) {
                if (i == result.size()) {
                    result.add(i, word);
                    break;
                }else if (wordMap.get(result.get(i)) <= count) {
                    result.add(i, word);
                    break;
                }
            }

        }
        return result;

    }
}
