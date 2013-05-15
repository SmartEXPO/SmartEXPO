package com.smartexpo.models;

import com.smartexpo.models.Description;
import com.smartexpo.models.ItemAudio;
import com.smartexpo.models.ItemAuthor;
import com.smartexpo.models.ItemVideo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-15T20:46:23")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, String> itemName;
    public static volatile SingularAttribute<Item, ItemVideo> itemVideo;
    public static volatile SingularAttribute<Item, ItemAuthor> itemAuthor;
    public static volatile SingularAttribute<Item, Description> description;
    public static volatile SingularAttribute<Item, ItemAudio> itemAudio;
    public static volatile SingularAttribute<Item, Integer> itemId;

}