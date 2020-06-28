package com.nsu.photo_anthropology.converters;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HtmlParsing {

    public static String getImagePath(String address){
        String returnedImageAddress = "";
        try {
            Document document = Jsoup.connect(address).get();
            Elements contentElem = document.select("meta");
            for (Element element:contentElem){
                if(element.attr("property").equals("og:image")){
                    returnedImageAddress += element.attr("content");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnedImageAddress;
    }
}
