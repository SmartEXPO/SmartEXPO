/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dynamicdb.concretemodels;

/**
 *
 * @author tornado718
 */
public class DescriptionModel extends AttrGroupModel{
    protected String descriptionTitle;
    protected String descriptionContent;

    public String getDescriptionTitle() {
        return descriptionTitle;
    }

    public void setDescriptionTitle(String descriptionTitle) {
        this.descriptionTitle = descriptionTitle;
    }

    public String getDescriptionContent() {
        return descriptionContent;
    }

    public void setDescriptionContent(String descriptionContent) {
        this.descriptionContent = descriptionContent;
    }
    
    
    
}
