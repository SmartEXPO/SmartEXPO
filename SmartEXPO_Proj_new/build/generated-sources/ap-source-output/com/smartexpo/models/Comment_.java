package com.smartexpo.models;

import com.smartexpo.models.ItemComment;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-18T20:25:50")
@StaticMetamodel(Comment.class)
public class Comment_ { 

    public static volatile SingularAttribute<Comment, String> content;
    public static volatile SingularAttribute<Comment, String> username;
    public static volatile SingularAttribute<Comment, Date> time;
    public static volatile SingularAttribute<Comment, Integer> commentId;
    public static volatile SingularAttribute<Comment, ItemComment> itemComment;

}