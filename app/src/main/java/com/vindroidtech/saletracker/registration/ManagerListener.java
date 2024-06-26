package com.vindroidtech.saletracker.registration;

import com.vindroidtech.saletracker.managerRights.ManagerRightsModel;
import com.vindroidtech.saletracker.usertype.UserTypeModel;

public interface ManagerListener {
    void onMangerClicked(ManagerRightsModel managerRightsModel);
}
