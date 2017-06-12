package com.irit.upnp;

import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;

import java.beans.PropertyChangeSupport;

/**
 * Created by mkostiuk on 12/06/2017.
 */

@UpnpService(
        serviceType = @UpnpServiceType(value = "MouseButtonsService", version = 1),
        serviceId = @UpnpServiceId("MouseButtonsService")
)
public class MouseButtonsController {

    private final PropertyChangeSupport propertyChangeSupport;

    public MouseButtonsController() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }
}
