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
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

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
    private static final Logger LOG = Logger.getLogger(ItemViewManagedBean.class.getName());
    private List<Item> items;
    private Item selectedItem;
    private String authorName;
    private Date authorBirthDate;
    private Date authorDeathDate;
    private String authorIntro;
    private String audioTitle;
    private List<Audio> audios;
    private List<Video> videos;

    /**
     * Creates a new instance of ItemViewManagedBean
     */
    public ItemViewManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        gi = new GetInfo(emf, utx);
    }

    public String getAuthorName() {
        if (selectedItem == null) {
            LOG.log(Level.WARNING, "selectedItem null");
            return "";
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (authors != null && authors.size() != 0) {
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
            LOG.log(Level.WARNING, "selectedItem null");
            return null;
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (authors.size() != 0) {
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
            LOG.log(Level.WARNING, "selectedItem null");
            return null;
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (authors.size() != 0) {
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
            LOG.log(Level.WARNING, "selectedItem null");
            return "";
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (authors.size() != 0) {
            this.authorIntro = authors.get(0).getIntroduction();
            return authorIntro;
        } else {
            return "";
        }
    }

    public void setAuthorIntro(String AuthorIntro) {
        this.authorIntro = AuthorIntro;
    }

    public String getAudioTitle() {
        if (selectedItem == null) {
            LOG.log(Level.WARNING, "selectedItem null");
            return "";
        }
        List<Audio> audios = gi.getAudioByItemID(selectedItem.getItemId());
        if (audios.size() != 0) {
            this.audioTitle = audios.get(0).getTitle();
            return audioTitle;
        }

        return "";
    }

    public void setAudioTitle(String AudioTitle) {
        this.audioTitle = AudioTitle;
    }

    public String getVideoTitle() {
        if (selectedItem == null) {
            LOG.log(Level.WARNING, "selectedItem null");
            return "";
        }
        List<Video> videos = gi.getVideoByItemID(selectedItem.getItemId());
        if (videos.size() != 0) {
            this.VideoTitle = videos.get(0).getTitle();
            return VideoTitle;
        }
        return "";
    }

    public void setVideoTitle(String VideoTitle) {
        this.VideoTitle = VideoTitle;
    }
    private String VideoTitle;

    /**
     * @return the items
     */
    public List<Item> getItems() {
        if (items == null || items.size() == 0) {
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

    public int getCount() {
        return getItems().size();
    }

    public void storeEditedData() {
        try {
            ItemJpaController ijc = new ItemJpaController(utx, emf);
            ijc.edit(selectedItem);
            Author author = gi.getAuthorsByItemID(selectedItem.getItemId()).get(0);
            Video video = gi.getVideoByItemID(selectedItem.getItemId()).get(0);
            Audio audio = gi.getAudioByItemID(selectedItem.getItemId()).get(0);

            author.setBirthday(authorBirthDate);
            author.setDeathDate(authorDeathDate);
            author.setName(authorName);
            author.setIntroduction(authorIntro);

            video.setTitle(audioTitle);


            audio.setTitle(audioTitle);


            AudioJpaController ajc = new AudioJpaController(utx, emf);
            ajc.edit(audio);
            AuthorJpaController authorjc = new AuthorJpaController(utx, emf);
            authorjc.edit(author);
            VideoJpaController vjc = new VideoJpaController(utx, emf);
            vjc.edit(video);


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

    // edit接口，selectedItem为目标item
    public void editItem() {
    }

    // view接口，selectedItem为目标item
    public void viewDetail() {
    }

    public void destroyItem() throws NonexistentEntityException {
        try {
            getItems().remove(getSelectedItem());
            ItemJpaController ijc = new ItemJpaController(utx, emf);

            ItemAuthorJpaController iauthorjc = new ItemAuthorJpaController(utx, emf);
            List<ItemAuthor> itemAuthors = gi.getItemAuthorsByItemID(selectedItem.getItemId());
            for (int i = 0; i < itemAuthors.size(); i++) {

                iauthorjc.destroy(itemAuthors.get(i).getItemAuthorId());

            }

            ItemVideoJpaController ivjc = new ItemVideoJpaController(utx, emf);
            List<ItemVideo> itemVideos = gi.getItemVideosByItemID(selectedItem.getItemId());
            for (int i = 0; i < itemVideos.size(); i++) {

                ivjc.destroy(itemVideos.get(i).getItemVideoId());

            }

            ItemAudioJpaController iaudiojc = new ItemAudioJpaController(utx, emf);
            List<ItemAudio> itemAudios = gi.getItemAudiosByItemID(selectedItem.getItemId());
            for (int i = 0; i < itemAudios.size(); i++) {

                iaudiojc.destroy(itemAudios.get(i).getItemAudioId());

            }

            ItemCommentJpaController icjc = new ItemCommentJpaController(utx, emf);
            List<ItemComment> itemComments = gi.getItemCommentsByItemID(selectedItem.getItemId());
            for (int i = 0; i < itemComments.size(); i++) {

                icjc.destroy(itemComments.get(i).getItemCommentId());

            }

            DescriptionJpaController djc = new DescriptionJpaController(utx, emf);

            djc.destroy(selectedItem.getDescription().getDescriptionId());

            ItemDisplayColumnJpaController idcjc = new ItemDisplayColumnJpaController(utx, emf);
            List<DisplayColumn> displayColumns = gi.getDisplayColumnsByItemID(selectedItem.getItemId());
            for (int i = 0; i < displayColumns.size(); i++) {
                idcjc.destroy(displayColumns.get(i).getDisplayColumnId());
            }

            ijc.destroy(selectedItem.getItemId());

        } catch (RollbackFailureException ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (items.size() == 0) {
            items = null;
        }
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
}
