/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.jpgcontrollers.AudioJpaController;
import com.smartexpo.jpgcontrollers.AuthorJpaController;
import com.smartexpo.jpgcontrollers.DescriptionJpaController;
import com.smartexpo.jpgcontrollers.ItemAudioJpaController;
import com.smartexpo.jpgcontrollers.ItemAuthorJpaController;
import com.smartexpo.jpgcontrollers.ItemCommentJpaController;
import com.smartexpo.jpgcontrollers.ItemDisplayColumnJpaController;
import com.smartexpo.jpgcontrollers.ItemJpaController;
import com.smartexpo.jpgcontrollers.ItemVideoJpaController;
import com.smartexpo.jpgcontrollers.VideoJpaController;
import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Audio;
import com.smartexpo.models.Author;
import com.smartexpo.models.DisplayColumn;
import com.smartexpo.models.Item;
import com.smartexpo.models.ItemAudio;
import com.smartexpo.models.ItemAuthor;
import com.smartexpo.models.ItemComment;
import com.smartexpo.models.ItemVideo;
import com.smartexpo.models.Video;
import com.smartexpo.util.FileManager;
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
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class ItemViewManagedBean implements Serializable {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi;
    private List<Item> items;
    private Item selectedItem;
    private String authorName;
    private Date authorBirthDate;
    private Date authorDeathDate;
    private String authorIntro;
    private Audio selectedAudio;
    private String audioTitle;
    private String audioURL;
    private String audioDes;
    private Video selectedVideo;
    private String videoTitle;
    private String videoURL;
    private String videoDes;
    private List<Audio> audios;
    private List<Video> videos;
    private UploadedFile uploadedFile;

    /**
     * Creates a new instance of ItemViewManagedBean
     */
    public ItemViewManagedBean() {
        audios = new ArrayList<Audio>();
        videos = new ArrayList<Video>();
    }

    @PostConstruct
    public void postConstruct() {
        gi = new GetInfo(emf, utx);
    }

    public String getAuthorName() {
        if (selectedItem == null) {
            return "";
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (authors != null && !authors.isEmpty()) {
            this.authorName = authors.get(0).getName();
            return authorName;
        } else {
            return "";
        }
    }

    public void setAuthorName(String AuthorName) {
        this.authorName = AuthorName;
    }

    public Date getAuthorBirthDate() {
        if (selectedItem == null) {
            return null;
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (!authors.isEmpty()) {
            this.authorBirthDate = authors.get(0).getBirthday();
            return authorBirthDate;
        } else {
            return null;
        }
    }

    public void setAuthorBirthDate(Date AuthorBirthDate) {
        this.authorBirthDate = AuthorBirthDate;
    }

    public Date getAuthorDeathDate() {
        if (selectedItem == null) {
            return null;
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (!authors.isEmpty()) {
            this.authorDeathDate = authors.get(0).getDeathDate();
            return authorDeathDate;
        } else {
            return null;
        }
    }

    public void setAuthorDeathDate(Date AuthorDeathDate) {
        this.authorDeathDate = AuthorDeathDate;
    }

    public String getAuthorIntro() {
        if (selectedItem == null) {
            return "";
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (!authors.isEmpty()) {
            this.authorIntro = authors.get(0).getIntroduction();
            return authorIntro;
        } else {
            return "";
        }
    }

    public void setAuthorIntro(String AuthorIntro) {
        this.authorIntro = AuthorIntro;
    }

    public Audio getSelectedAudio() {
        return selectedAudio;
    }

    public void setSelectedAudio(Audio selectedAudio) {
        this.selectedAudio = selectedAudio;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String AudioTitle) {
        this.audioTitle = AudioTitle;
    }

    public String getAudioDes() {
        return audioDes;
    }

    public void setAudioDes(String audioDes) {
        this.audioDes = audioDes;
    }

    public Video getSelectedVideo() {
        return selectedVideo;
    }

    public void setSelectedVideo(Video selectedVideo) {
        this.selectedVideo = selectedVideo;
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

    /**
     * @return the items
     */
    public List<Item> getItems() {
        if (items == null || items.isEmpty()) {
            ItemJpaController ijc = new ItemJpaController(utx, emf);
            items = ijc.findItemEntities();
        }

        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * @return the selectedItem
     */
    public Item getSelectedItem() {
        return selectedItem;
    }

    /**
     * @param selectedItem the selectedItem to set
     */
    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * @return the audios
     */
    public List<Audio> getAudios() {
        return audios;
    }

    /**
     * @param audios the audios to set
     */
    public void setAudios(List<Audio> audios) {
        this.audios = audios;
    }

    /**
     * @return the videos
     */
    public List<Video> getVideos() {
        return videos;
    }

    /**
     * @param videos the videos to set
     */
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public int getCount() {
        return getItems().size();
    }

    public void addAudio() {
        Audio audio = new Audio();
        audio.setTitle(audioTitle);
        audio.setDescription(audioDes);

        if (uploadedFile != null) {
            String[] audioLocPair =
                    FileManager.getInstance().processStore(uploadedFile, "audios/");
            audioURL = audioLocPair[0];
        }

        audio.setUrl(audioURL);
        audios.add(audio);
        audioTitle = audioURL = audioDes = null;

        for (int i = 0; i < audios.size(); ++i) {
            Audio tmp = audios.get(i);
        }
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
                FileManager.getInstance().deleteFile(des + "/web" + url);

                audios.remove(i);
            }
        }
    }

    public int getAudioSize() {
        return audios.size();
    }

    public void addVideo() {
        Video video = new Video();
        video.setTitle(videoTitle);
        video.setDescription(videoDes);

        if (uploadedFile != null) {
            String[] videoLocPair =
                    FileManager.getInstance().processStore(uploadedFile, "videos/");
            videoURL = videoLocPair[0];
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
                FileManager.getInstance().deleteFile(des + "/web" + url);

                videos.remove(i);
            }
        }
    }

    public int getVideoSize() {
        return videos.size();
    }

    // view接口，selectedItem为目标item
    public void beginViewDetail() {
        
    }

    // 根据selectedItem找到对应Audio放在audios这个List中
    public void beginViewAudio() {
        audios = gi.getAudioByItemID(selectedItem.getItemId());
    }

    // 根据selectedItem找到对应Video放在videos这个List中
    public void beginViewVideo() {
        videos = gi.getVideoByItemID(selectedItem.getItemId());
    }

    // edit接口，selectedItem为目标item
    public void beginEditItem() {
        
    }

    public String beginEditAudio() {
        audios = gi.getAudioByItemID(selectedItem.getItemId());
        return "av_editor.xhtml";
    }

    public void beginEditVideo() {
        videos = gi.getVideoByItemID(selectedItem.getItemId());
    }

    // @stormmax TODO 存储除了Audio和Video部分的信息
    public void storeEditedData() {
        try {
            ItemJpaController ijc = new ItemJpaController(utx, emf);
            ijc.edit(selectedItem);

            if (gi.getAuthorsByItemID(selectedItem.getItemId()) != null
                    && !gi.getAudioByItemID(selectedItem.getItemId()).isEmpty()) {
                Author author = gi.getAuthorsByItemID(selectedItem.getItemId()).get(0);

                author.setBirthday(authorBirthDate);
                author.setDeathDate(authorDeathDate);
                author.setName(authorName);
                author.setIntroduction(authorIntro);

                AuthorJpaController authorjc = new AuthorJpaController(utx, emf);
                authorjc.edit(author);
            }

        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void destroyItem() throws NonexistentEntityException {
        try {
            getItems().remove(getSelectedItem());
            ItemJpaController ijc = new ItemJpaController(utx, emf);

            ItemAuthorJpaController iauthorjc = new ItemAuthorJpaController(utx, emf);
            List<ItemAuthor> itemAuthors = gi.getItemAuthorsByItemID(selectedItem.getItemId());
            for (int i = 0; i < itemAuthors.size(); ++i) {

                iauthorjc.destroy(itemAuthors.get(i).getItemAuthorId());

            }

            ItemVideoJpaController ivjc = new ItemVideoJpaController(utx, emf);
            List<ItemVideo> itemVideos = gi.getItemVideosByItemID(selectedItem.getItemId());
            for (int i = 0; i < itemVideos.size(); ++i) {

                ivjc.destroy(itemVideos.get(i).getItemVideoId());

            }

            ItemAudioJpaController iaudiojc = new ItemAudioJpaController(utx, emf);
            List<ItemAudio> itemAudios = gi.getItemAudiosByItemID(selectedItem.getItemId());
            for (int i = 0; i < itemAudios.size(); ++i) {

                iaudiojc.destroy(itemAudios.get(i).getItemAudioId());

            }

            ItemCommentJpaController icjc = new ItemCommentJpaController(utx, emf);
            List<ItemComment> itemComments = gi.getItemCommentsByItemID(selectedItem.getItemId());
            for (int i = 0; i < itemComments.size(); ++i) {

                icjc.destroy(itemComments.get(i).getItemCommentId());

            }

            DescriptionJpaController djc = new DescriptionJpaController(utx, emf);

            djc.destroy(selectedItem.getDescription().getDescriptionId());

            ItemDisplayColumnJpaController idcjc = new ItemDisplayColumnJpaController(utx, emf);
            List<DisplayColumn> displayColumns = gi.getDisplayColumnsByItemID(selectedItem.getItemId());
            for (int i = 0; i < displayColumns.size(); ++i) {
                idcjc.destroy(displayColumns.get(i).getDisplayColumnId());
            }

            ijc.destroy(selectedItem.getItemId());

        } catch (RollbackFailureException ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (items.isEmpty()) {
            items = null;
        }
    }

    // @stormmax TODO 存储修改过后的Audio和Video部分，分别在audios和videos两个list中
    public void avEditFinish() {
        AudioJpaController ajc=new AudioJpaController(utx, emf);
        for(int i=0;i<audios.size();i++){
            try {
                ajc.edit(audios.get(i));
            } catch (IllegalOrphanException ex) {
                Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        VideoJpaController vjc=new VideoJpaController(utx, emf);
        for(int i=0;i<videos.size();i++){
            try {
                vjc.edit(videos.get(i));
            } catch (IllegalOrphanException ex) {
                Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        RequestContext.getCurrentInstance()
                .execute(("alert('Modify successfully!');location.reload(true)"));
        
    }

    public void back() {
        RequestContext.getCurrentInstance()
                .execute(("location.reload(true)"));
    }

    public void upload(FileUploadEvent event) {
        uploadedFile = event.getFile();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Upload successfully!"));
    }
}
