package com.smartexpo.models;

import com.smartexpo.models.Manager;
import com.smartexpo.models.Permission;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-19T16:54:36")
@StaticMetamodel(ManagerPermission.class)
public class ManagerPermission_ { 

    public static volatile SingularAttribute<ManagerPermission, Manager> managerId;
    public static volatile SingularAttribute<ManagerPermission, Integer> managerPermissionId;
    public static volatile SingularAttribute<ManagerPermission, Permission> permissionId;

}