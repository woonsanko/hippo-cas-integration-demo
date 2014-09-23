package org.example.casintegdemo.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@Node(jcrType="casintegdemo:textdocument")
public class TextDocument extends BaseDocument{
    
    public String getTitle() {
        return getProperty("casintegdemo:title");
    }

    public String getSummary() {
        return getProperty("casintegdemo:summary");
    }
    
    public HippoHtml getHtml(){
        return getHippoHtml("casintegdemo:body");    
    }

}
