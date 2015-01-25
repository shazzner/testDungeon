package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.LinkedList;
import java.util.List;

/**
 * test
 *
 * @author normenhansen
 */
public class App extends SimpleApplication {
    private static App instance;

    List<Tile> tiles = new LinkedList<Tile>();
    
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

//        tiles.add(new Tile(new Vector3f(0,0,0), 0));
//        tiles.add(new Tile(new Vector3f(1,0,0), 0));
//        tiles.add(new Tile(new Vector3f(2,0,0), 0));
        tiles.add(new Tile(new Vector3f(3,0,0), 6));
        //tiles.add(new Tile(new Vector3f(0,0,1), 0));
//        tiles.add(new Tile(new Vector3f(0,0,0), 0));
//        tiles.add(new Tile(new Vector3f(1,0,1), 1));
//        tiles.add(new Tile(new Vector3f(2,0,0), 0));
//        tiles.add(new Tile(new Vector3f(1,0,4), 1));
//        tiles.add(new Tile(new Vector3f(0,0,4), 1));
//        tiles.add(new Tile(new Vector3f(2,0,3), 0));

        for (Tile tile:tiles) {
            rootNode.attachChild(tile.makeWall());
            
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
