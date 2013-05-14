/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.controls;

import com.smartexpo.models.Author;
import com.smartexpo.models.Item;
import com.smartexpo.models.ItemAuthor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class GetInfo {
    
    private EntityManager em=null;
    private UserTransaction utx=null;
    
    private Item item;
    
    public GetInfo(EntityManager _em,UserTransaction _utx){
        this.em=_em;
        this.utx=_utx;
    }
    
    
    public Item getItemByID(int id){
        List<Item> items= em.createNamedQuery("Item.findByItemId").setParameter("itemId", id).getResultList();
        if(!items.isEmpty()){
            this.item=items.get(0);
            return item;
        }
        return null;
    }
    /**
     *
     * @param id
     * @return
     */
    public List<Author> getAuthorsByItemID(int id){
        if(item==null){
            getItemByID(id);
        }
        List<ItemAuthor> itemAuthors= em.createNamedQuery("ItemAuthor.findByItemId").setParameter("itemId", item).getResultList();//.setParameter("itemId", item.getItemId())
        List<Author> authors=new ArrayList<Author>();
        for(int i=0;i<itemAuthors.size();i++){
            ItemAuthor ia=itemAuthors.get(i);
            authors.addAll(em.createNamedQuery("Author.findByAuthorId").setParameter("authorId", ia.getAuthorId().getAuthorId()).getResultList());
        }
        
        
        return authors;
        
    }
}
