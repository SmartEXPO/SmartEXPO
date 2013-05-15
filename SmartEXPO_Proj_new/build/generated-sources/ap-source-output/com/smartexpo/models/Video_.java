package com.smartexpo.models;

import com.smartexpo.models.ItemVideo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-15T13:39:58")
@StaticMetamodel(Video.class)
public class Video_ { 

    public static volatile SingularAttribute<Video, String> title;
    public static volatile SingularAttribute<Video, ItemVideo> itemVideo;
    public static volatile SingularAttribute<Video, String> description;
    public static volatile SingularAttribute<Video, Integer> videoId;
    public static volatile SingularAttribute<Video, String> url;

}