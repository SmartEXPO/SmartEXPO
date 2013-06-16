<%-- 
    Document   : itemInsertPage
    Created on : May 13, 2013, 7:06:05 PM
    Author     : tornado718
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Item Info Input Assistant</title>
    </head>
    <body>
        <h1>Item Info Input Assistant</h1>
        <form action="InsertServlet" method="POST">
            <table border="1">
                <thead>
                    <tr>
                        <th>Attrs</th>
                        <th>Input</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Name</td>
                        <td><input type="text" name="item_name" value="item_name" /></td>
                    </tr>
                    <tr>
                        <td>Description title</td>
                        <td><input type="text" name="des_title" value="des_title" /></td>
                    </tr>
                    <tr>
                        <td>Description content</td>
                        <td><input type="text" name="des_content" value="des_content" /></td>
                    </tr>
                    <tr>
                        <td>Author Name</td>
                        <td><input type="text" name="author_name" value="author_name" /></td>
                    </tr>
                    <tr>
                        <td>Author Introduction</td>
                        <td><input type="text" name="author_introduction" value="author_introduction" /></td>
                    </tr>
                    <tr>
                        <td>Video title</td>
                        <td><input type="text" name="video_title" value="video_title" /></td>
                    </tr>
                    <tr>
                        <td>Video url</td>
                        <td><input type="text" name="video_url" value="video_url" /></td>
                    </tr>
                    <tr>
                        <td>Audio title</td>
                        <td><input type="text" name="audio_title" value="audio_title" /></td>
                    </tr>
                    <tr>
                        <td>Audio description</td>
                        <td><input type="text" name="audio_description" value="audio_description" /></td>
                    </tr>
                    <tr>
                        <td>Audio url</td>
                        <td><input type="text" name="audio_url" value="audio_url" /></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="Submit" /></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </body>
</html>
