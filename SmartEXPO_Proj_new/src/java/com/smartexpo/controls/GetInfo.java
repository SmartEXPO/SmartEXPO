/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.controls;

import com.smartexpo.models.Audio;
import com.smartexpo.models.Author;
import com.smartexpo.models.Comment;
import com.smartexpo.models.Description;
import com.smartexpo.models.Item;
import com.smartexpo.models.ItemAudio;
import com.smartexpo.models.ItemAuthor;
import com.smartexpo.models.ItemComment;
import com.smartexpo.models.ItemVideo;
import com.smartexpo.models.Manager;
import com.smartexpo.models.ManagerPermission;
import com.smartexpo.models.Permission;
import com.smartexpo.models.Video;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class GetInfo {

    private EntityManager em = null;
    private UserTransaction utx = null;
    private Item item;
    private Manager manager;

    public GetInfo(EntityManager _em, UserTransaction _utx) {
        this.em = _em;
        this.utx = _utx;
    }

    public Item getItemByID(int id) {
        List<Item> items = em.createNamedQuery("Item.findByItemId").setParameter("itemId", id).getResultList();
        if (!items.isEmpty()) {
            this.item = items.get(0);
            return item;
        }
        return null;
    }

    public List<Manager> getManagerByManagerID(int id) {
        List<Manager> managers = em.createNamedQuery("Manager.findByManagerId").setParameter("managerId", id).getResultList();
        if (!managers.isEmpty()) {
            this.manager = managers.get(0);
            return managers;
        }
        return null;
    }

    public List<Manager> getManagerByName(String name) {
        List<Manager> managers = em.createNamedQuery("Manager.findByUsername").setParameter("username", name).getResultList();
        if (!managers.isEmpty()) {
            this.manager = managers.get(0);
            return managers;
        }
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Author> getAuthorsByItemID(int id) {
        if (item == null) {
            getItemByID(id);
        }
        List<ItemAuthor> itemAuthors = em.createNamedQuery("ItemAuthor.findByItemId").setParameter("itemId", item).getResultList();//.setParameter("itemId", item.getItemId())
        List<Author> authors = new ArrayList<Author>();
        for (int i = 0; i < itemAuthors.size(); i++) {
            ItemAuthor ia = itemAuthors.get(i);
            authors.addAll(em.createNamedQuery("Author.findByAuthorId").setParameter("authorId", ia.getAuthorId().getAuthorId()).getResultList());
        }


        return authors;

    }

    public List<Permission> getPermissionByID(int id) {
        if (manager == null) {
            getManagerByManagerID(id);
            if (manager == null) {
                return null;
            }
        }
        List<ManagerPermission> managerPermissions = em.createNamedQuery("ManagerPermission.findByManagerId").setParameter("managerId", manager).getResultList();
        List<Permission> permissions = new ArrayList<Permission>();
        for (int i = 0; i < managerPermissions.size(); i++) {
            ManagerPermission mp = managerPermissions.get(i);
            permissions.addAll(em.createNamedQuery("Permission.findByPermissionId").setParameter("permissionId", mp.getPermissionId().getPermissionId()).getResultList());
        }

        return permissions;


    }

    public List<Audio> getAudioByItemID(int id) {
        if (item == null) {
            getItemByID(id);
        }
        List<ItemAudio> itemAudios = em.createNamedQuery("ItemAudio.findByItemId").setParameter("itemId", item).getResultList();//.setParameter("itemId", item.getItemId())
        List<Audio> audios = new ArrayList<Audio>();
        for (int i = 0; i < itemAudios.size(); i++) {
            ItemAudio ia = itemAudios.get(i);
            audios.addAll(em.createNamedQuery("Audio.findByAudioId").setParameter("audioId", ia.getAudioId().getAudioId()).getResultList());
        }


        return audios;
    }

    public List<Comment> getCommentByItemID(int id) {
        if (item == null) {
            getItemByID(id);

        }
        List<ItemComment> itemComments = em.createNamedQuery("ItemComment.findByItemId").setParameter("itemId", item).getResultList();
        List<Comment> comments = new ArrayList<Comment>();
        for (int i = 0; i < itemComments.size(); i++) {
            ItemComment ic = itemComments.get(i);
            comments.addAll(em.createNamedQuery("Comment.findByCommentId").setParameter("commentId", ic.getCommentId().getCommentId()).getResultList());
        }

        return comments;
    }

    public List<Video> getVideoByItemID(int id) {
        if (item == null) {
            getItemByID(id);
        }
        List<ItemVideo> itemVideos = em.createNamedQuery("ItemVideo.findByItemId").setParameter("itemId", item).getResultList();//.setParameter("itemId", item.getItemId())
        List<Video> videos = new ArrayList<Video>();
        for (int i = 0; i < itemVideos.size(); i++) {
            ItemVideo ia = itemVideos.get(i);
            videos.addAll(em.createNamedQuery("Video.findByVideoId").setParameter("videoId", ia.getVideoId().getVideoId()).getResultList());
        }


        return videos;
    }

    public List<Description> getDescriptionByItemID(int id) {
        if (item == null) {
            getItemByID(id);
        }

        List<Description> descriptions = em.createNamedQuery("Description.findByItemId").setParameter("itemId", item).getResultList();
        return descriptions;

    }
}
