package mygame;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.AppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import mygame.jaxb.Data;
import mygame.jaxb.Layer;
import mygame.jaxb.Map;
import mygame.jaxb.Tile;

/**
 * test
 *
 * @author normenhansen
 */
public class App extends SimpleApplication {

    private static App instance;
    static int WALLSIZE = 10;
    List<LevelTile> tiles = new LinkedList<LevelTile>();
    //Camera cam = new Camera();
    private Nifty nifty;

    
    public static App getInstance() {
        if (instance == null) {
            instance = new App(new DebugKeysAppState());
        }

        return instance;
    }

    public App( AppState... initialStates ) {
        //super();
        
        if (initialStates != null) {
            for (AppState a : initialStates) {
                if (a != null) {
                    stateManager.attach(a);
                    System.out.println("AppState: " + a);
                }
            }
        }
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        //flyCam.setMoveSpeed(10f);
        Node playerNode = new Node("Player");
        GameCharacterControl charControl = new GameCharacterControl(0.25f, 0.25f, 8f);
        charControl.setCamera(cam);
    cam.setLocation(new Vector3f(20f, 5f, 20f) );
        
        
        
         NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
         nifty = niftyDisplay.getNifty();
         
        nifty.fromXml("Interface/HelloJme.xml", "start");

        // attach the nifty display to the gui view port as a processor
        //guiViewPort.addProcessor(niftyDisplay);


        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);

        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        try {
            File file = new File("/home/shazzner/Dev/DungeonTest/assets/Interface/map_purple.tmx");
            JAXBContext jaxbContext = JAXBContext.newInstance(Map.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Map map = (Map) jaxbUnmarshaller.unmarshal(file);
            //System.out.println(map);
            Object layerOrTileSet  = map.getLayerOrTileset().get(0);
            Object obj  = map.getLayerOrTileset().get(1);
            Layer layer  = (Layer)map.getLayerOrTileset().get(1);

            Data data = (Data)layer.getData().get(0);
            int x = 1;
            int z = 0;
            
            int width =  new Integer(layer.getWidth());
            int height = new Integer(layer.getHeight());
            
            for (Tile tile:data.getTile()) {
                if (x > width) {
                    z++;
                    x = 1;
                }
                LevelTile levelTile = new LevelTile(new Vector3f(x * WALLSIZE,0,z * WALLSIZE), new  Integer(tile.getGid()));
                rootNode.attachChild(levelTile.makeWall());
                x++;
            }
        } catch (JAXBException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
