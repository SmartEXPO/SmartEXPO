<%-- 
    Document   : displayManagers
    Created on : May 14, 2013, 8:56:55 PM
    Author     : tornado718
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Managers Display Page</title>
    </head>
    <body>
        <h1>Managers</h1>
        <table border="1">
                <thead>
                    <tr>
                        <th>Attrs</th>
                        <th>Value</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Name</td>
                        <td><%=request.getParameter("item_name")%></td>
                    </tr>
                    <tr>
                        <td>Description title</td>
                        <td><%=request.getParameter("des_title")%></td>
                    </tr>
                    <tr>
                        <td>Description content</td>
                        <td><%=request.getParameter("des_content")%></td>
                    </tr>
                    <tr>
                        <td>Author Name</td>
                        <td><%=request.getParameter("author_name")%></td>
                    </tr>
                    <tr>
                        <td>Author Introduction</td>
                        <td><%=request.getParameter("author_introduction")%></td>
                    </tr>
                    <tr>
                        <td>Video title</td>
                        <td><%=request.getParameter("video_title")%></td>
                    </tr>
                    <tr>
                        <td>Video url</td>
                        <td><%=request.getParameter("video_url")%></td>
                    </tr>
                    <tr>
                        <td>Audio title</td>
                        <td><%=request.getParameter("audio_title")%></td>
                    </tr>
                    <tr>
                        <td>Audio description</td>
                        <td><%=request.getParameter("audio_description")%></td>
                    </tr>
                    <tr>
                        <td>Audio url</td>
                        <td><%=request.getParameter("audio_url")%></td>
                    </tr>
                </tbody>
            </table>
    </body>
</html>
