/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.models.Audio;
import com.smartexpo.models.Author;
import com.smartexpo.models.Description;
import com.smartexpo.models.Item;
import com.smartexpo.models.ItemAudio;
import com.smartexpo.models.ItemAuthor;
import com.smartexpo.models.ItemVideo;
import com.smartexpo.models.Video;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
@ManagedBean
@SessionScoped
public class ItemInsertMB implements Serializable{

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @Resource
    private UserTransaction utx;
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDesTitle() {
        return desTitle;
    }

    public void setDesTitle(String desTitle) {
        this.desTitle = desTitle;
    }

    public String getDesContent() {
        return desContent;
    }

    public void setDesContent(String desContent) {
        this.desContent = desContent;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getAuthorBirth() {
        return authorBirth;
    }

    public void setAuthorBirth(Date authorBirth) {
        this.authorBirth = authorBirth;
    }

    public Date getAuthorDeath() {
        return authorDeath;
    }

    public void setAuthorDeath(Date authorDeath) {
        this.authorDeath = authorDeath;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public void setAuthorIntro(String authorIntro) {
        this.authorIntro = authorIntro;
    }

    public List<Audio> getAudios() {
        return audios;
    }

    public void setAudios(List<Audio> audios) {
        this.audios = audios;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    private String desTitle;
    private String desContent;
    private List<Author> authors;
    private String authorName;
    private Date authorBirth;
    private Date authorDeath;
    private String authorIntro;
    private List<Audio> audios;
    private List<Video> videos;
    private Video video;
    private Audio audio;
    private Author author;
    private String imageurl;
    private String audioTitle;

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public String getAudioDes() {
        return audioDes;
    }

    public void setAudioDes(String audioDes) {
        this.audioDes = audioDes;
    }
    private String audioURL;
    private String audioDes;
    private String videoTitle;

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoDes() {
        return videoDes;
    }

    public void setVideoDes(String videoDes) {
        this.videoDes = videoDes;
    }
    private String videoURL;
    private String videoDes;

    /**
     * Creates a new instance of ItemInsertMB
     */
    public ItemInsertMB() {
    }

    public void persist() {
        try {
            Item item = new Item();
            item.setItemName(itemName);
            item.setImageurl(imageurl);

            Description description = new Description();
            description.setTitle(desTitle);
            description.setContent(desContent);
            description.setItemId(item);


            Author author = new Author();
            author.setName(authorName);
            author.setBirthday(authorBirth);
            author.setDeathDate(authorDeath);
            author.setIntroduction(authorIntro);

            ItemAuthor itemAuthor = new ItemAuthor();
            itemAuthor.setItemId(item);
            itemAuthor.setAuthorId(author);

            Video video = new Video();
            video.setTitle(videoTitle);
            video.setUrl(videoURL);
            video.setDescription(videoDes);

            ItemVideo itemVideo = new ItemVideo();
            itemVideo.setItemId(item);
            itemVideo.setVideoId(video);

            Audio audio = new Audio();
            audio.setTitle(audioTitle);
            audio.setDescription(audioDes);
            audio.setUrl(audioURL);

            ItemAudio itemAudio = new ItemAudio();
            itemAudio.setAudioId(audio);
            itemAudio.setItemId(item);


            utx.begin();


            em.persist(item);
            em.persist(audio);
            em.persist(author);
            em.persist(video);
            em.persist(itemAudio);
            em.persist(itemAuthor);
            em.persist(itemVideo);
            em.persist(description);


            utx.commit();
        } catch (NotSupportedException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
