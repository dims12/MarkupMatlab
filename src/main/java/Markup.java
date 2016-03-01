import org.piccolo2d.PCamera;
import org.piccolo2d.PNode;
import org.piccolo2d.event.PMouseWheelZoomEventHandler;
import org.piccolo2d.extras.PFrame;
import org.piccolo2d.nodes.PImage;
import org.piccolo2d.util.PAffineTransform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * Created on 01.03.2016.
 */
public class Markup extends PFrame {

   private String fileName;
   private PImage image = new PImage();
   private boolean internal = false;
   private double zoom;

   @Override
   public void initialize() {

      getCanvas().getLayer().addChild(image);

      PMouseWheelZoomEventHandler mouseWheelZoomEventHandler = new PMouseWheelZoomEventHandler();
      mouseWheelZoomEventHandler.zoomAboutMouse();
      mouseWheelZoomEventHandler.setScaleFactor(-0.1);
      getCanvas().addInputEventListener(mouseWheelZoomEventHandler);

      getCanvas().getCamera().addPropertyChangeListener(PCamera.PROPERTY_VIEW_TRANSFORM, new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
            PAffineTransform t = (PAffineTransform) evt.getNewValue();
            if( !internal ) {
               internal = true;
               setZoom( t.getScale() );
               internal = false;
            }
         }
      });

      setDefaultCloseOperation(DISPOSE_ON_CLOSE);

   }

   public Markup() {
   }

   public String getFileName() {
      return fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
      image.setImage(fileName);
      image.repaint();
      setTitle(new File(fileName).getAbsolutePath());
   }

   public double getZoom() {
      return zoom;
   }

   public void setZoom(double newValue) {
      double oldValue = this.zoom;
      if( oldValue != newValue ) {
         this.zoom = newValue;
         if( !internal ) {
            internal = true;
            getCanvas().getCamera().setViewScale(newValue);
            internal = false;
         }
         firePropertyChange("zoom", oldValue, newValue);
      }


   }
}
