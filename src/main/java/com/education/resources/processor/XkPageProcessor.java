//package com.education.resources.processor;
//
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.Site;
//import us.codecraft.webmagic.Spider;
//import us.codecraft.webmagic.pipeline.JsonFilePipeline;
//import us.codecraft.webmagic.processor.PageProcessor;
//
////爬取学科网数据
//public class XkPageProcessor implements PageProcessor {
//
//    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
////            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
////            .addHeader("Accept-Encoding","gzip, deflate, br")
////            .addHeader("Accept-Language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
////            .addHeader("Connection","keep-alive")
////            //.addHeader("Content-Length","23")
////            .addHeader("Content-Type","text/html; charset=utf-8")
////            .addHeader("Cookie","acw_tc=2760826b15853561063012194e3035647b275cac12d9d0 260c97237eb2cb04; UM_distinctid=1711e966fbf249-0e42ca98f04e7f-f313f6d-1fa400-1711e966fc0453; ASP.NET_SessionId=us1ybsvjtah40oy0zyjw2ihq; CNZZDATA1271266012=1565882433-1585351029-https%253A%252F%252Fwww.xj5u.com%252F%7C1586162970")
////            .addHeader("Host","www.xj5u.com")
////            .addHeader("Referer","https://www.xj5u.com/Index.html")
////            .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
//    @Override
//    public void process(Page page) {
////      page.addTargetRequests(page.getHtml().links().regex("(https://sx\\.zxxk\\.com/h/books-type3/\\w+/\\w+)").all());
////        page.putField("author", page.getUrl().regex("https://zxxk\\.com/(\\w+)/.*").toString());
//        page.putField("title", page.getHtml().xpath("//div[@class='tongbu']/table/tbody/tr/td/a").all());
//
////        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
//    }
//
//    @Override
//    public Site getSite() {
//        return site;
//    }
//
//    public static void main(String[] args) {
//        Spider.create(new XkPageProcessor()).addUrl("https://www.xj5u.com/Courseware.aspx/Index.html") .addPipeline(new JsonFilePipeline("D:\\webmagic\\")).thread(5).run();
//    }
//}
