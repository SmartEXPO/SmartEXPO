package com.smartexpo.models;

import com.smartexpo.models.Item;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-16T15:38:19")
@StaticMetamodel(Description.class)
public class Description_ { 

    public static volatile SingularAttribute<Description, String> content;
    public static volatile SingularAttribute<Description, String> title;
    public static volatile SingularAttribute<Description, Integer> descriptionId;
    public static volatile SingularAttribute<Description, Item> itemId;

}