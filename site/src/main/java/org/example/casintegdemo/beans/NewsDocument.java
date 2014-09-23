package org.example.casintegdemo.beans;

import java.util.Calendar;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean;

@Node(jcrType="casintegdemo:newsdocument")
public class NewsDocument extends BaseDocument{

    public String getTitle() {
        return getProperty("casintegdemo:title");
    }
    
    public String getSummary() {
        return getProperty("casintegdemo:summary");
    }
    
    public Calendar getDate() {
        return getProperty("casintegdemo:date");
    }

    public HippoHtml getHtml(){
        return getHippoHtml("casintegdemo:body");    
    }

    /**
     * Get the imageset of the newspage
     *
     * @return the imageset of the newspage
     */
    public HippoGalleryImageSetBean getImage() {
        return getLinkedBean("casintegdemo:image", HippoGalleryImageSetBean.class);
    }


}
