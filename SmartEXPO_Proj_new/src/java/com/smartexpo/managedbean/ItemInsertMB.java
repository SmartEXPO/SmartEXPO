/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.models.Audio;
import com.smartexpo.models.Author;
import com.smartexpo.models.Description;
import com.smartexpo.models.Item;
import com.smartexpo.models.ItemAudio;
import com.smartexpo.models.ItemAuthor;
import com.smartexpo.models.ItemVideo;
import com.smartexpo.models.Video;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author tornado718
 */
@ManagedBean
@SessionScoped
public class ItemInsertMB implements Serializable {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi;
    // ItemInsertMB Fields
    private static String ImageUploadComponentID = "img_upload";
    private String itemName;
    private String itemArea;
    private String desTitle;
    private String desContent;
    private List<Author> authors;
    private List<Audio> audios;
    private List<Video> videos;
    private Video selectedVideo;
    private Video video;
    private Audio selectedAudio;
    private Audio audio;
    private Author selectedAuthor;
    private Author author;
    private String authorName;
    private Date authorBirth;
    private Date authorDeath;
    private String authorIntro;
    private String imageurl;
    private String savedLocation;
    private String audioTitle;
    private String audioURL;
    private String audioDes;
    private String videoTitle;
    private String videoURL;
    private String videoDes;
    private static String Destination;
    private static String SubPath = "/web/upload/";
    private UploadedFile uploadedFile;

    /**
     * Creates a new instance of ItemInsertMB
     */
    public ItemInsertMB() {
        authors = new ArrayList<Author>();
        audios = new ArrayList<Audio>();
        videos = new ArrayList<Video>();
    }

    @PostConstruct
    public void postContrust() {
        String realPath = ((ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext()).getRealPath("/");

        for (int i = 0; i < 3; ++i) {
            realPath = realPath.substring(0, realPath.lastIndexOf("/"));
        }
        realPath = realPath + SubPath;
        Destination = realPath;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemArea() {
        return itemArea;
    }

    public void setItemArea(String itemArea) {
        this.itemArea = itemArea;
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

    public void persist() {
        try {
            utx.begin();

            Item item = new Item();
            item.setItemName(itemName);
            item.setImageurl(imageurl);
            item.setArea(itemArea);

            em.persist(item);

            Description description = new Description();
            description.setTitle(desTitle);
            description.setContent(desContent);
            description.setItemId(item);

            em.persist(description);

            for (int i = 0; i < authors.size(); ++i) {
                Author a = authors.get(i);
                ItemAuthor itemAuthor = new ItemAuthor();
                itemAuthor.setItemId(item);
                itemAuthor.setAuthorId(a);
                em.persist(a);
                em.persist(itemAuthor);
            }

            for (int i = 0; i < videos.size(); ++i) {
                Video v = videos.get(i);
                ItemVideo iv = new ItemVideo();
                iv.setItemId(item);
                iv.setVideoId(v);
                em.persist(v);
                em.persist(iv);
            }

            for (int i = 0; i < audios.size(); ++i) {
                Audio a = audios.get(i);
                ItemAudio ia = new ItemAudio();
                ia.setItemId(item);
                ia.setAudioId(a);
                em.persist(a);
                em.persist(ia);
            }

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

        clearAll();
        RequestContext.getCurrentInstance()
                .execute(("alert('Insert Successfully!');location.reload(true)"));
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public int getAuthorSize() {
        return authors.size();
    }

    public int getAudioSize() {
        return audios.size();
    }

    public int getVideoSize() {
        return videos.size();
    }

    public void addAuthor() {
        Author author = new Author();
        author.setName(authorName);
        author.setBirthday(authorBirth);
        author.setDeathDate(authorDeath);
        author.setIntroduction(authorIntro);
        authors.add(author);
        authorName = authorIntro = null;
        authorBirth = authorDeath = null;
    }

    public void removeAuthor() {
        for (int i = 0; i < authors.size(); ++i) {
            Author tmpAuthor = authors.get(i);
            if (tmpAuthor.getName().equals(selectedAuthor.getName())) {
                authors.remove(i);
            }
        }
    }

    // Audio多值添加处，结果暂存储于audios列表中
    public void addAudio() {
        Audio audio = new Audio();
        audio.setTitle(audioTitle);
        audio.setDescription(audioDes);

        if (uploadedFile != null) {
            audioURL = processStore(uploadedFile, "audios/");
        }

        audio.setUrl(audioURL);
        audios.add(audio);
        audioTitle = audioURL = audioDes = null;
    }

    public void removeAudio() {
        for (int i = 0; i < audios.size(); ++i) {
            Audio tmpAudio = audios.get(i);
            if (tmpAudio.getTitle().equals(selectedAudio.getTitle())) {
                String des = ((ServletContext) FacesContext.getCurrentInstance()
                        .getExternalContext().getContext()).getRealPath("/");
                for (int j = 0; j < 3; ++j) {
                    des = des.substring(0, des.lastIndexOf("/"));
                }
                String url = tmpAudio.getUrl();
                deleteFile(des + "/web" + url);

                audios.remove(i);
            }
        }
    }

    // Video多值添加处，结果暂存于videos列表中
    public void addVideo() {
        Video video = new Video();
        video.setTitle(videoTitle);
        video.setDescription(videoDes);

        if (uploadedFile != null) {
            videoURL = processStore(uploadedFile, "videos/");
        }

        video.setUrl(videoURL);
        videos.add(video);
        videoTitle = videoURL = videoDes = null;
    }

    public void removeVideo() {
        for (int i = 0; i < videos.size(); ++i) {
            Video tmpVideo = videos.get(i);
            if (tmpVideo.getTitle().equals(selectedVideo.getTitle())) {
                String des = ((ServletContext) FacesContext.getCurrentInstance()
                        .getExternalContext().getContext()).getRealPath("/");
                for (int j = 0; j < 3; ++j) {
                    des = des.substring(0, des.lastIndexOf("/"));
                }
                String url = tmpVideo.getUrl();
                deleteFile(des + "/web" + url);

                videos.remove(i);
            }
        }
    }

    /**
     * @return the selectedAudio
     */
    public Audio getSelectedAudio() {
        return selectedAudio;
    }

    /**
     * @param selectedAudio the selectedAudio to set
     */
    public void setSelectedAudio(Audio selectedAudio) {
        this.selectedAudio = selectedAudio;
    }

    /**
     * @return the selectedVideo
     */
    public Video getSelectedVideo() {
        return selectedVideo;
    }

    /**
     * @param selectedVideo the selectedVideo to set
     */
    public void setSelectedVideo(Video selectedVideo) {
        this.selectedVideo = selectedVideo;
    }

    /**
     * @return the selectedAuthor
     */
    public Author getSelectedAuthor() {
        return selectedAuthor;
    }

    /**
     * @param selectedAuthor the selectedAuthor to set
     */
    public void setSelectedAuthor(Author selectedAuthor) {
        this.selectedAuthor = selectedAuthor;
    }

    /**
     * @return the uploadedFile
     */
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    /**
     * @param uploadedFile the uploadedFile to set
     */
    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void upload(FileUploadEvent event) {
        uploadedFile = event.getFile();
        String componentID = event.getComponent().getId();

        if (componentID.equals(ImageUploadComponentID)) {
            if (savedLocation != null) {
                deleteFile(savedLocation);
            }
            imageurl = processStore(uploadedFile, "images/");
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Upload successfully!"));
    }

    /*
     * The processStore Method
     * Store the file
     * 
     * @param uploadedFile the uploadedFile to store
     * @param subDir the subdirectory for file to store
     * 
     * @return the savedLocation of uploaded file
     */
    private String processStore(UploadedFile uploadedFile, String subDir) {
        String contentType = uploadedFile.getContentType();
        String ext = contentType.substring(contentType.lastIndexOf("/") + 1, contentType.length());
        savedLocation = Destination + subDir + uploadedFile.hashCode() + "." + ext;
        String URL = savedLocation.substring(savedLocation.indexOf("/upload/"), savedLocation.length());

        try {
            storeFile(savedLocation, uploadedFile.getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return URL;
    }

    /*
     * The storeFile Method
     * store file to Server
     * 
     * @param URL url of file
     * @param in inputstream of file
     */
    private void storeFile(String saveLocation, InputStream in) {
        OutputStream out;
        try {
            out = new FileOutputStream(new File(saveLocation));

            int read;
            byte[] bytes = new byte[4096];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ItemInsertMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean deleteFile(String imageSavedLocation) {
        File file = new File(imageSavedLocation);
        return file.delete();
    }

    private void clearAll() {
        itemName = null;
        itemArea = null;
        imageurl = null;
        savedLocation = null;
        desTitle = null;
        desContent = null;
        authors = new ArrayList<Author>();
        audios = new ArrayList<Audio>();
        videos = new ArrayList<Video>();
    }
}
