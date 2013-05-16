package com.smartexpo.models;

import com.smartexpo.models.Author;
import com.smartexpo.models.Item;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-16T16:34:18")
@StaticMetamodel(ItemAuthor.class)
public class ItemAuthor_ { 

    public static volatile SingularAttribute<ItemAuthor, Integer> itemAuthorId;
    public static volatile SingularAttribute<ItemAuthor, Item> itemId;
    public static volatile SingularAttribute<ItemAuthor, Author> authorId;

}