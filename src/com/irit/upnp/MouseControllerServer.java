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
import java.awt.event.InputEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

/**
 * Created by mkostiuk on 12/06/2017.
 */
public class MouseControllerServer implements Runnable {

    private LocalService<MouseCursorController> mouseCursorService;
    private LocalService<MouseButtonsController> mouseButtonsService;
    private Dimension sizeScreen;
    private Robot robot;
    private float xCursor,yCursor;

    public void run() {
        try {

            sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
            robot = new Robot();

            xCursor = sizeScreen.width / 2;
            yCursor = sizeScreen.height / 2;

            final UpnpService upnpService = new UpnpServiceImpl();

            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(
                    createDevice()
            );

            mouseCursorService.getManager().getImplementation()
                    .getPropertyChangeSupport().addPropertyChangeListener(
                    evt -> {
                        if (evt.getPropertyName().equals("commande")) {
                            HashMap<String, Float> args = (HashMap<String, Float>) evt.getNewValue();
                            float x = args.get("X");
                            float y = args.get("Y");

                            System.out.println("X : " + x + " Y : " + y);
                            System.out.println("height : " + sizeScreen.height + " width : " + sizeScreen.width);
                            float valX = sizeScreen.width*(x/100);
                            float valY = sizeScreen.height*(y/100);
                            System.out.println("X : " + valX + " Y : " + valY);

                            robot.mouseMove(Math.round(valX), Math.round(valY));
                        }

                        if (evt.getPropertyName().equals("commandeSub")) {
                            HashMap<String, Float> args = (HashMap<String, Float>) evt.getNewValue();
                            float x = -(args.get("X") * 10);
                            float y = args.get("Y") * 10;

                            if ((xCursor + x < sizeScreen.width) && (yCursor + y < sizeScreen.height)) {
                                xCursor += x;
                                yCursor += y;
                            }

                            robot.mouseMove(Math.round(xCursor), Math.round(yCursor));
                        }
                    }
            );

            mouseButtonsService.getManager().getImplementation().getPropertyChangeSupport()
                    .addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals("commandeButtons")) {
                                if (evt.getNewValue().equals("CENTRE")) {
                                    robot.mousePress(InputEvent.BUTTON1_MASK);
                                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                                }
                            }
                        }
                    });

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
