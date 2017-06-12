package com.irit.upnp;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by mkostiuk on 12/06/2017.
 */
public class MouseControllerServer implements Runnable {

    private LocalService<MouseCursorController> mouseCursorService;
    private LocalService<MouseButtonsController> mouseButtonsService;
    private Dimension sizeScreen;
    private Robot robot;

    public void run() {
        try {

            sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
            robot = new Robot();

            final UpnpService upnpService = new UpnpServiceImpl();

            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(
                    createDevice()
            );

            mouseCursorService.getManager().getImplementation()
                    .getPropertyChangeSupport().addPropertyChangeListener(
                    evt -> {
                        if (evt.getPropertyName().equals("commande")) {
                            HashMap<String, Integer> args = (HashMap<String, Integer>) evt.getNewValue();
                            int x = args.get("X");
                            int y = args.get("Y");

                            System.out.println("X : " + x + " Y : " + y);

                            robot.mouseMove(sizeScreen.height*(x/100), sizeScreen.width*(y/100));
                        }
                    }
            );

        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private LocalDevice createDevice() throws ValidationException {
        DeviceIdentity identity =
                new DeviceIdentity(
                        UDN.uniqueSystemIdentifier("UPnP Mouse Controller")
                );

        DeviceType type =
                new UDADeviceType("JavaController", 1);

        DeviceDetails details =
                new DeviceDetails(
                        "UPnP Mouse Controller",					// Friendly Name
                        new ManufacturerDetails(
                                "UPS-IRIT",								// Manufacturer
                                ""),								// Manufacturer URL
                        new ModelDetails(
                                "MouseController",						// Model Name
                                "Composant permettant de contrôler la souris via UPnP",	// Model Description
                                "v1" 								// Model Number
                        )
                );
        mouseButtonsService =
                new AnnotationLocalServiceBinder().read(MouseButtonsController.class);
        mouseButtonsService.setManager(
                new DefaultServiceManager( mouseButtonsService, MouseButtonsController.class)
        );

       mouseCursorService =
               new AnnotationLocalServiceBinder().read(MouseCursorController.class);
       mouseCursorService.setManager(
               new DefaultServiceManager(mouseCursorService, MouseCursorController.class)
       );


        return new LocalDevice(
                identity, type, details,
                new LocalService[] { mouseButtonsService,mouseCursorService}
        );
    }
}
