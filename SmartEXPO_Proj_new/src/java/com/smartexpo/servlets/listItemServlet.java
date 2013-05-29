/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ben
 */
@WebServlet(name = "listItemServlet", urlPatterns = {"/listItemServlet"})
public class listItemServlet extends HttpServlet {

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
        //response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.print(query(request.getParameter("area"), request.getParameter("phase")));
            out.println();
            out.flush();
            
        } finally {            
            out.close();
        }
    }
    
    private String query(String area, String phase) {
        //TODO 查询数据库逻辑
        //TODO 判断一下是不是这一区的所有的都查询完毕了，也即都显示完了，是的话status返回"1"，不是返回"0"，失败返回其他
        /*查出数据之后先用parseJson转成Json格式，然后用wrapper包装后返回
         *  parseJson();
         *  wrapper(); 
         * 目前的设想是一次查询15个item出来
         */
        return null;
    }
    
    private String parseJson(String title, String img, String description, String id, String author){
        String link = "/?itemid=" + id; //TODO 具体网址怎么跳转
        String json = "{\"title\":\""+title+"\",\"img\":\""+img+"\",\"description\":\""+description+"\",\"author\":\""+author+"\",\"link\":\""+link+"\"}";
        return json;
    }
    
    private String wrapper(String[] jsons, String status) {
        String tmp = "{\"status\":\""+status+"\",\"count\":"+jsons.length+",\"list\":[";
        for (String json : jsons) {
            tmp += json;
        }
        tmp = tmp + "]}";
        return tmp;
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
