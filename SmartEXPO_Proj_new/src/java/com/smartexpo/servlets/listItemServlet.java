/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.servlets;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.models.Item;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author ben
 */
@WebServlet(name = "listItemServlet", urlPatterns = {"/listItemServlet"})
public class listItemServlet extends HttpServlet {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    private EntityManager em;
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
        //response.setContentType("text/plain;charset=UTF-8");
        System.out.println("!!!!!!!!!!NULLPOINTER  " + utx.toString() + "!!!!!!!!!!");
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

        GetInfo getInfo = new GetInfo(em, utx);

        System.out.println("*********" + getInfo.getAllItems().get(0).getItemName());

        System.out.println("########### Area: " + area);
        System.out.println("########### FROM: " + 15 * (Integer.parseInt(phase) - 1) + 1);

        List<Item> items = getInfo.getItemsFrom(
                getInfo.getItemByArea("Area " + area),
                15 * (Integer.parseInt(phase) - 1),
                15 * (Integer.parseInt(phase)));

        List<String> jsons = parseJson(items);

        String status;
        if (items.size() == 15) {
            status = "0";
        } else {
            status = "1";
        }
        return wrapper(jsons, status);
    }

    private List<String> parseJson(List<Item> items) {
        List<String> jsons = new ArrayList<String>();

        for (Item item : items) {
            String link = "item.xhtml?id=" + item.getItemId(); //TODO 具体网址怎么跳转
            String des = item.getDescription().getContent() + "this is long long long longlonglong long long longlonglong";
            if (des.length() > 20) {
                des = des.substring(0, 27) + "...";
            }

            String json = "{\"title\":\"" + item.getItemName()
                    + "\",\"img\":\"" + "http://www.inwebson.com/demo/blocksit-js/demo2/images/img27.jpg"
                    + "\",\"description\":\"" + des
                    + "\",\"author\":\"by " + "Famous Wellknown"
                    + "\",\"link\":\"" + link
                    + "\"}";
            jsons.add(json);
        }
        return jsons;
    }

    private String wrapper(List<String> jsons, String status) {
        String tmp = "{\"status\":\"" + status + "\",\"count\":" + jsons.size() + ",\"list\":[";
        boolean flag = false;
        for (String json : jsons) {
            tmp = tmp + json + ",";
            flag = true;
        }
        if (flag) {
            tmp = tmp.substring(0, tmp.length() - 1);
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
