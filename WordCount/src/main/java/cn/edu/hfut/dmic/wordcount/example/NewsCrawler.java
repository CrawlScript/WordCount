/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.hfut.dmic.wordcount.example;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.FileUtils;
import cn.edu.hfut.dmic.webcollector.util.LogUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author hu
 */
public class NewsCrawler extends BreadthCrawler{
    
    protected String downloadDir=null;
    protected String newsUrlRegex=null;
    protected String contentCSSSelector=null;
    protected File dir=null;
    
    public NewsCrawler(String downloadDir){
        this.downloadDir=downloadDir;
        dir=new File(downloadDir);
        if(dir.exists()){
            FileUtils.deleteDir(dir);
        }
        dir.mkdirs();
    }

    AtomicInteger id=new AtomicInteger(1);
    
    @Override
    public void visit(Page page) {
        if(!Pattern.matches(newsUrlRegex, page.getUrl())){
            return;
        }
        Elements contentDivs=page.getDoc().select(contentCSSSelector);
        if(contentDivs.isEmpty())
            return;
        String content=contentDivs.first().text().trim();
        if(content.isEmpty())
            return;
        
        try {
            File file=new File(dir,id.getAndIncrement()+"");
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(content.getBytes("utf-8"));
            fos.close();
          
        } catch (Exception ex) {
            LogUtils.getLogger().info("Exception", ex);
        }
        
    }
    
    
    
    
    public static void main(String[] args) throws Exception{
        NewsCrawler crawler=new NewsCrawler("newsdata");
        crawler.addSeed("http://www.xinhuanet.com/");
        crawler.addRegex("http://.*xinhuanet.com.*");
        
        crawler.setNewsUrlRegex("http://news.xinhuanet.com/.+/201.*.htm");
        
        crawler.addRegex("-.*#.*");
        crawler.addRegex("-.*jpg.*");
        crawler.addRegex("-.*gif.*");
        crawler.addRegex("-.*png.*");
        crawler.addRegex("-.*\\?.*");
        
        crawler.setContentCSSSelector("div[id=content]");
        
        crawler.setThreads(30);
        crawler.setIsContentStored(false);
        crawler.start(10);
                
    }
    
    
    
    public String getDownloadDir() {
        return downloadDir;
    }

    public void setDownloadDir(String downloadDir) {
        this.downloadDir = downloadDir;
    }

    public String getNewsUrlRegex() {
        return newsUrlRegex;
    }

    public void setNewsUrlRegex(String newsUrlRegex) {
        this.newsUrlRegex = newsUrlRegex;
    }

    public String getContentCSSSelector() {
        return contentCSSSelector;
    }

    public void setContentCSSSelector(String contentCSSSelector) {
        this.contentCSSSelector = contentCSSSelector;
    }

    public File getDir() {
        return dir;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }
}
