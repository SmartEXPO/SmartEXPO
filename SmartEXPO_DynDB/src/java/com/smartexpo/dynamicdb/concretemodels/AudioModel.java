/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dynamicdb.concretemodels;

/**
 *
 * @author tornado718
 */
public class AudioModel extends AttrGroupModel{
    protected String AudioTitle;
    protected String AudioURL;
    protected String AudioDescription;
    
    

    public String getAudioTitle() {
        return AudioTitle;
    }

    public void setAudioTitle(String AudioTitle) {
        this.AudioTitle = AudioTitle;
    }

    public String getAudioURL() {
        return AudioURL;
    }

    public void setAudioURL(String AudioURL) {
        this.AudioURL = AudioURL;
    }

    public String getAudioDescription() {
        return AudioDescription;
    }

    public void setAudioDescription(String AudioDescription) {
        this.AudioDescription = AudioDescription;
    }
    
}
