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
import java.awt.ItemSelectable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@WebServlet(name = "InsertItemServlet", urlPatterns = {"/InsertItemServlet"})
public class InsertItemServlet extends HttpServlet {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @Resource
    private UserTransaction utx;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            Logger logger = Logger.getLogger(InsertItemServlet.class.getName());

            logger.log(Level.WARNING, "in insertItemServlet");
            
            
            
            
            
            
            Item item = new Item();
            item.setItemName(request.getParameter("item_name"));
            Description des = new Description();
            des.setContent(request.getParameter("des_content"));
            des.setTitle(request.getParameter("des_title"));
            des.setItemId(item);


            Author author = new Author();
            author.setIntroduction(request.getParameter("author_introduction"));
            author.setName(request.getParameter("author_name"));


            ItemAuthor itemAuthor = new ItemAuthor();
            itemAuthor.setAuthorId(author);
            itemAuthor.setItemId(item);

            Author author1 = new Author();
            author1.setIntroduction(request.getParameter("author_introduction"));
            author1.setName(request.getParameter("author_name")+"something");


            ItemAuthor itemAuthor1 = new ItemAuthor();
            itemAuthor1.setAuthorId(author1);
            itemAuthor1.setItemId(item);
            
            Video video = new Video();
            video.setTitle(request.getParameter("video_title"));
            video.setUrl(request.getParameter("video_url"));
            video.setDescription(request.getParameter("video_description"));
            
            ItemVideo itemVideo=new ItemVideo();
            itemVideo.setItemId(item);
            itemVideo.setVideoId(video);

            Audio audio = new Audio();
            audio.setDescription(request.getParameter("audio_description"));
            audio.setTitle(request.getParameter("audio_title"));
            audio.setUrl(request.getParameter("audio_url"));

            ItemAudio itemAudio=new ItemAudio();
            itemAudio.setAudioId(audio);
            itemAudio.setItemId(item);

            Audio audio1 = new Audio();
            audio1.setDescription(request.getParameter("audio_description"));
            audio1.setTitle(request.getParameter("audio_title"));
            audio1.setUrl(request.getParameter("audio_url"));

            ItemAudio itemAudio1=new ItemAudio();
            itemAudio1.setAudioId(audio1);
            itemAudio1.setItemId(item);


            
            Manager manager=new Manager();
            manager.setUsername("asdfasd");
            manager.setPassword("owefnwnv");
            
            Permission permission=new Permission();
            permission.setPermissionName("asfe11");
            
            Permission permission2=new Permission();
            permission2.setPermissionName("asfe22");
            
            ManagerPermission mp=new ManagerPermission();
            mp.setManagerId(manager);
            mp.setPermissionId(permission);
            
            ManagerPermission mp2=new ManagerPermission();
            mp2.setManagerId(manager);
            mp2.setPermissionId(permission2);
            
            
            Comment comment=new Comment();
            comment.setContent("heiehei");
            comment.setUsername("max");
            comment.setTime(new Date());
            
            Comment comment2=new Comment();
            comment2.setContent("asdfadf");
            comment2.setUsername("asdf");
            comment2.setTime(new Date());
            
            
            ItemComment ic=new ItemComment();
            ic.setItemId(item);
            ic.setCommentId(comment);
            
            ItemComment ic2=new ItemComment();
            ic2.setItemId(item);
            ic2.setCommentId(comment2);
            
            utx.begin();
            
            em.persist(permission);
            em.persist(manager);
            em.persist(mp);
            em.persist(mp2);
            em.persist(permission2);
            
            em.persist(comment);
            em.persist(ic);
           // em.persist(item);
            
            em.persist(comment2);
            em.persist(ic2);
            
            em.persist(item);
            em.persist(des);
            em.persist(itemAuthor);
            em.persist(itemAuthor1);
            em.persist(author1);
            em.persist(author);
            em.persist(audio);
            em.persist(itemAudio);
            em.persist(audio1);
            em.persist(itemAudio1);
            em.persist(video);
            em.persist(itemVideo);
            
            utx.commit();
            
            GetInfo gi=new GetInfo(em, utx);
            logger.log(Level.WARNING,item.getItemName());
            List<Author> authors=gi.getAuthorsByItemID(item.getItemId());
            for(int i=0;i<authors.size();i++){
                logger.log(Level.WARNING,authors.get(i).getName());
            }
            
            
            List<Audio> audios=gi.getAudioByItemID(item.getItemId());
            for(int i=0;i<audios.size();i++){
                logger.log(Level.WARNING,audios.get(i).getTitle());
            }

            List<Video> videos=gi.getVideoByItemID(item.getItemId());
            for(int i=0;i<videos.size();i++){
                logger.log(Level.WARNING,videos.get(i).getTitle());
            }
            
            List<Description> descriptions=gi.getDescriptionByItemID(item.getItemId());
            for(int i=0;i<descriptions.size();i++){
                logger.log(Level.WARNING,descriptions.get(i).getTitle());
            }
            
            List<Permission> permissions = gi.getPermissionByID(4);
            for (int i = 0; i < permissions.size(); i++) {
                logger.log(Level.WARNING,permissions.get(i).getPermissionName());
            }
            
            List<Comment> comments = gi.getCommentByItemID(item.getItemId());
            for (int i=0;i<comments.size();i++){
                Comment commenti;
                commenti = comments.get(i);
                logger.log(Level.WARNING,commenti.getUsername()+" "+commenti.getContent());
            }
            
            
            request.setAttribute("item_name", gi.getItemByID(item.getItemId()).getItemName());
            request.setAttribute("des_content", gi.getDescriptionByItemID(item.getItemId()).get(0).getContent());
            request.setAttribute("des_title", gi.getDescriptionByItemID(item.getItemId()).get(0).getTitle());
            request.setAttribute("author_introduction", gi.getAuthorsByItemID(item.getItemId()).get(0).getIntroduction());
            request.setAttribute("author_name", gi.getAuthorsByItemID(item.getItemId()).get(0).getName());
            request.setAttribute("video_title", gi.getVideoByItemID(item.getItemId()).get(0).getTitle());
            request.setAttribute("video_url", gi.getVideoByItemID(item.getItemId()).get(0).getUrl());
            request.setAttribute("video_description", gi.getVideoByItemID(item.getItemId()).get(0).getDescription());
            request.setAttribute("audio_description", gi.getAudioByItemID(item.getItemId()).get(0).getDescription());
            request.setAttribute("audio_title", gi.getAudioByItemID(item.getItemId()).get(0).getTitle());
            request.setAttribute("audio_url", gi.getAudioByItemID(item.getItemId()).get(0).getUrl());
            
            
            request.getRequestDispatcher("itemPages/itemInserted.jsp").forward(request, response);
        } catch (NotSupportedException ex) {
            Logger.getLogger(InsertItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(InsertItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(InsertItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(InsertItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(InsertItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(InsertItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(InsertItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
