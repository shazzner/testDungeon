package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
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
    List<LevelTile> tiles = new LinkedList<LevelTile>();

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }

        return instance;
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(10f);
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
             System.out.println("layerOrTileSet " + layerOrTileSet.getClass());
            Object obj  = map.getLayerOrTileset().get(1);
            System.out.println("obj " + obj.getClass());
            Layer layer  = (Layer)map.getLayerOrTileset().get(1);

            Data data = (Data)layer.getData().get(0);
            int x = 1;
            int z = 0;
            
            int width =  new Integer(layer.getWidth());
            int height = new Integer(layer.getHeight());
            System.out.println("width " + width);
            
            for (Tile tile:data.getTile()) {
                if (x > width) {
                    z++;
                    x = 1;
                }
                LevelTile levelTile = new LevelTile(new Vector3f(x,0,z), new  Integer(tile.getGid()));
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
