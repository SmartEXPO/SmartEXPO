package com.smartexpo.models;

import com.smartexpo.models.Item;
import com.smartexpo.models.Video;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-19T16:54:36")
@StaticMetamodel(ItemVideo.class)
public class ItemVideo_ { 

    public static volatile SingularAttribute<ItemVideo, Video> videoId;
    public static volatile SingularAttribute<ItemVideo, Integer> itemVideoId;
    public static volatile SingularAttribute<ItemVideo, Item> itemId;

}