package com.irit.upnp;

import com.irit.xml.LecteurXml;
import org.fourthline.cling.binding.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

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

    @UpnpStateVariable(name = "ButtonCommande")
    private String buttonCommande = "";

    @UpnpAction(name = "SetButtonCommande")
    public void setButtonCommande(@UpnpInputArgument(name = "ButtonCommande") String c) throws IOException, SAXException, ParserConfigurationException {
        buttonCommande = c;
        LecteurXml l = new LecteurXml(buttonCommande);
        getPropertyChangeSupport().firePropertyChange("commandeButtons", "", l.getCommande());
    }
}
