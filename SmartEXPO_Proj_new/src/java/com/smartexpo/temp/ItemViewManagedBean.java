/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.jpgcontrollers.ItemAudioJpaController;
import com.smartexpo.jpgcontrollers.ItemAuthorJpaController;
import com.smartexpo.jpgcontrollers.ItemCommentJpaController;
import com.smartexpo.jpgcontrollers.ItemJpaController;
import com.smartexpo.jpgcontrollers.ItemVideoJpaController;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Audio;
import com.smartexpo.models.Item;
import com.smartexpo.models.ItemAudio;
import com.smartexpo.models.ItemAuthor;
import com.smartexpo.models.ItemComment;
import com.smartexpo.models.ItemVideo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final Logger LOG = Logger.getLogger(CommentViewManagedBean.class.getName());
    private List<Item> items;
    private Item selectedItem;

    /**
     * Creates a new instance of ItemViewManagedBean
     */
    public ItemViewManagedBean() {
    }

    /**
     * @return the items
     */
    public List<Item> getItems() {
        if (items == null) {
            gi = new GetInfo(emf, utx);
            items = new ItemJpaController(utx, emf).findItemEntities();

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

    public void destroyItem() {
        gi = new GetInfo(emf, utx);
        Item item = gi.getItemByID(selectedItem.getItemId());
        ItemJpaController ijc = new ItemJpaController(utx, emf);
        ItemAudioJpaController iaudjc = new ItemAudioJpaController(utx, emf);
        ItemVideoJpaController ivjc = new ItemVideoJpaController(utx, emf);
        ItemAuthorJpaController iautjc = new ItemAuthorJpaController(utx, emf);
        ItemCommentJpaController icjc = new ItemCommentJpaController(utx, emf);
        
        try {
            List<ItemAudio> itemAudios = gi.getItemAudiosByItemID(selectedItem.getItemId());
            for (int i = 0; i < itemAudios.size(); i++) {
                iaudjc.destroy(itemAudios.get(i).getItemAudioId());
            }
            
            
            List<ItemAuthor> itemAuthors= gi.getItemAuthorsByItemID(selectedItem.getItemId());
            for(int i=0;i<itemAuthors.size();i++){
                iautjc.destroy(itemAuthors.get(i).getItemAuthorId());
            }
            
            List<ItemVideo> itemVideos=gi.getItemVideosByItemID(selectedItem.getItemId());
            for (int i = 0; i < itemVideos.size(); i++) {
                ivjc.destroy(itemVideos.get(i).getItemVideoId());
            }
            
            List<ItemComment> itemComments = gi.getItemCommentsByItemID(selectedItem.getItemId());
            for(int i=0;i<itemComments.size();i++){
                icjc.destroy(itemComments.get(i).getItemCommentId());
            }
            
            ijc.destroy(selectedItem.getItemId());
            
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ItemViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }










        items.remove(selectedItem);
    }
}
