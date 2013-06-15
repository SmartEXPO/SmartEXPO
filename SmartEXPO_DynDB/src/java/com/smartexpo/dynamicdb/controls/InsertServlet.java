/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dynamicdb.controls;

import com.smartexpo.dynamicdb.concretemodels.AttrGroupModel;
import com.smartexpo.dynamicdb.concretemodels.AudioModel;
import com.smartexpo.dynamicdb.concretemodels.AuthorModel;
import com.smartexpo.dynamicdb.concretemodels.CommentModel;
import com.smartexpo.dynamicdb.concretemodels.DescriptionModel;
import com.smartexpo.dynamicdb.concretemodels.ItemModel;
import com.smartexpo.dynamicdb.concretemodels.VideoModel;
import com.smartexpo.dynamicdb.interfaces.Persister;
import com.smartexpo.dynamicdb.models.Attr;
import com.smartexpo.dynamicdb.models.AttrGroup;
import com.smartexpo.dynamicdb.models.DBItem;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.bytecode.stackmap.TypeData;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tornado718
 */
@WebServlet(name = "InsertServlet", urlPatterns = {"/InsertServlet"})
public class InsertServlet extends HttpServlet implements Persister {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    private EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private javax.transaction.UserTransaction utx;
    private static final Logger LOG = Logger.getLogger(InsertServlet.class.getName());
    private GetInfoFeatured gif;

    public String test;
    private int test2;
    private Integer test3;
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

        gif = new GetInfoFeatured(emf, utx, this);
       
        ItemModel itemModel=new ItemModel();
        itemModel.setArea("Area 5");
        itemModel.setHTML("asdfhoashdfasopfhsahdofhasohfphaspfhapsdhfhasdiofhsaoipdhfopashdofiha");
        itemModel.setImageURL("http:///");
        itemModel.setItemName(request.getParameter("item_name"));
        
        DescriptionModel desModel=new DescriptionModel();
        desModel.setDescriptionContent(request.getParameter("des_content"));
        desModel.setDescriptionTitle(request.getParameter("des_title"));
        
        
        AuthorModel author=new AuthorModel();
        author.setAuthorName("author name");
        author.setBirthDate(new Date());
        author.setDeathDate(new Date());
        author.setIntroduction("author introduction");
        
        VideoModel videoModel=new VideoModel();
        videoModel.setVideoDescription(request.getParameter("video_description"));
        videoModel.setVideoTitle(request.getParameter("video_title"));
        videoModel.setVideoURL(request.getParameter("video_url"));
        
        AudioModel audioModel=new AudioModel();
        audioModel.setAudioDescription(request.getParameter("audio_description"));
        audioModel.setAudioTitle(request.getParameter("audio_title"));
        audioModel.setAudioURL(request.getParameter("audio_url"));
        
        
        CommentModel commentModel=new CommentModel();
        commentModel.setContent(test);
        commentModel.setUsername(test);
        commentModel.setTime(new Date());
        
        itemModel.addDescription(desModel);
        itemModel.addAuthor(author);
        itemModel.addAuthor(author);
        itemModel.addAudio(audioModel);
        itemModel.addVideo(videoModel);
        itemModel.addComment(commentModel);
        itemModel.setItem(gif);
        
        author.setAuthorName("another name");
        
        
        
//        if(attr.getAttrText()==null){
//            LOG.log(Level.WARNING,"!!!attrText null!!!");
//        }
//        if(attr.getAttrText().getAttrTextValue()==null){
//            LOG.log(Level.WARNING,"!!!attrText null!!!"); 
//        }
        
        
        
        //gif.addAgAgAttach(itemModel.getSelf(), author.getSelf());
        
        
//        author.create(gif);
        itemModel.create(gif);
        author.edit(gif);
        LOG.log(Level.WARNING,"authorName:"+gif.getStringAttr(author.getSelf(), "authorName"));
        
        LOG.log(Level.WARNING, "author birthDate:"+gif.getDateAttr(author.getSelf(), "birthDate"));
        
        List<AttrGroup> authors=gif.getAttrGroupsByAttrGroupName(gif.getItemByItemId(1).getItemAttrGroupId(),"authors");
        for(int i=0;i<authors.size();i++){
            LOG.log(Level.WARNING,gif.getStringAttr(authors.get(i),"authorName"));
        }
        
        //itemModel.edit(gif);
        //author.delete(gif);
        
        //gif.addAgAgAttach(itemModel.getSelf(), author.getSelf());
        //LOG.log(Level.WARNING, "author id:"+ author.getAttrGroupId());
        
        
        
        
        
/*
 * 
        
        
        DBItem item = new DBItem();
        item.setItemName(request.getParameter("item_name"));
        gif.addItem(item);

        DBItem item2 = new DBItem();
        item2.setItemName("new Item");
        gif.addItem(item2);


        Attr imageurl = gif.generateStringAttr("image_url11", "http::///////");
        gif.addAttr(item, imageurl);

        String getstring = gif.getStringAttr(item, "image_url11");
        LOG.log(Level.WARNING, "value:" + getstring);


        AttrGroup author = new AttrGroup();
        author.setAttrGroupName("author");
        Attr authorName = gif.generateStringAttr("authorName", request.getParameter("author_name"));
        Attr authorBirth = gif.generateDateAttr("authorBirthDay", new Date());

        gif.addAttrGroup(item, author);
        LOG.log(Level.WARNING, "authorid before:" + author.getAttrGroupId());
        gif.addAttrGroup(item2, author);
        LOG.log(Level.WARNING, "authorid after:" + author.getAttrGroupId());
        gif.addAttr(author, authorName);
        gif.addAttr(author, authorBirth);
        List<AttrGroup> getAuthor = gif.getAGByName(item2, "author");
        gif.editAttrString(authorName.getAttrString(), "hahaa");
        //for(int i=0;i<getAuthor.size();i++){
        //    gif.detachAttrGroup(item2.getItemAttrGroupId(), author);
        //}

        //LOG.log(Level.WARNING, "getAuthor.name:"+ gif.getStringAttr(getAuthor.get(0), "authorName"));

*/

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

    @Override
    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}
