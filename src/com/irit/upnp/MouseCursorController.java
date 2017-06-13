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

    @UpnpAction(name = "SetXYPercent")
    public void setXYPercent(@UpnpInputArgument(name = "Commande") String c) throws IOException, SAXException, ParserConfigurationException {
        commande = c;
        System.out.println("Commande reçue : " + commande);
        LecteurXml l = new LecteurXml(commande);
        HashMap<String,Float> args = new HashMap<>();
        args.put("X",l.getX());
        args.put("Y",l.getY());

        getPropertyChangeSupport().firePropertyChange("commande","",args);
    }

    @UpnpAction(name = "SubXY")
    public void subXY(@UpnpInputArgument(name = "Commande") String c) throws IOException, SAXException, ParserConfigurationException {
        commande = c;
        System.out.println("Commande reçue : " + commande);

        LecteurXml l = new LecteurXml(commande);

        HashMap<String,Float> args = new HashMap<>();
        args.put("X",l.getX());
        args.put("Y",l.getY());

        getPropertyChangeSupport().firePropertyChange("commandeSub", "", args);
    }
}
