package com.smartexpo.models;

import com.smartexpo.models.ItemAuthor;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-17T21:36:49")
@StaticMetamodel(Author.class)
public class Author_ { 

    public static volatile SingularAttribute<Author, Date> birthday;
    public static volatile SingularAttribute<Author, ItemAuthor> itemAuthor;
    public static volatile SingularAttribute<Author, String> name;
    public static volatile SingularAttribute<Author, Date> deathDate;
    public static volatile SingularAttribute<Author, String> introduction;
    public static volatile SingularAttribute<Author, Integer> authorId;

}