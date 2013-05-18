package com.smartexpo.models;

import com.smartexpo.models.ItemAudio;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-18T12:19:57")
@StaticMetamodel(Audio.class)
public class Audio_ { 

    public static volatile SingularAttribute<Audio, String> title;
    public static volatile SingularAttribute<Audio, String> description;
    public static volatile SingularAttribute<Audio, ItemAudio> itemAudio;
    public static volatile SingularAttribute<Audio, Integer> audioId;
    public static volatile SingularAttribute<Audio, String> url;

}