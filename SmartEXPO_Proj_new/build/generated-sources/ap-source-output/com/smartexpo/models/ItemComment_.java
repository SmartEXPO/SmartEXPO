package com.smartexpo.models;

import com.smartexpo.models.Comment;
import com.smartexpo.models.Item;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-17T20:44:24")
@StaticMetamodel(ItemComment.class)
public class ItemComment_ { 

    public static volatile SingularAttribute<ItemComment, Integer> itemCommentId;
    public static volatile SingularAttribute<ItemComment, Item> itemId;
    public static volatile SingularAttribute<ItemComment, Comment> commentId;

}