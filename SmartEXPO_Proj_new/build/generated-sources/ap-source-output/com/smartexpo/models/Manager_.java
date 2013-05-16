package com.smartexpo.models;

import com.smartexpo.models.ManagerPermission;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-16T15:38:19")
@StaticMetamodel(Manager.class)
public class Manager_ { 

    public static volatile SingularAttribute<Manager, String> username;
    public static volatile SingularAttribute<Manager, ManagerPermission> managerPermission;
    public static volatile SingularAttribute<Manager, Integer> managerId;
    public static volatile SingularAttribute<Manager, String> password;

}