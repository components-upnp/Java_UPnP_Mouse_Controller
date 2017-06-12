package com.irit.upnp;

import com.irit.xml.LecteurXml;
import org.fourthline.cling.binding.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by mkostiuk on 12/06/2017.
 */
@UpnpService(
        serviceId = @UpnpServiceId("MouseCursorService"),
        serviceType = @UpnpServiceType(value = "MouseCursorService")
)
public class MouseCursorController {

    private final PropertyChangeSupport propertyChangeSupport;

    public MouseCursorController() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(name = "Commande", defaultValue = "")
    public String commande = "";

    @UpnpAction(name = "SetCommande")
    public void setCommande(@UpnpInputArgument(name = "NewCommandeValue") String c) throws IOException, SAXException, ParserConfigurationException {
        commande = c;
        LecteurXml l = new LecteurXml(commande);
        HashMap<String,Integer> args = new HashMap<>();
        args.put("X",l.getX());
        args.put("Y",l.getY());

        getPropertyChangeSupport().firePropertyChange("commande","",args);
    }
}
