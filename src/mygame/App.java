package mygame;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.font.BitmapText;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import java.io.File;
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

    
    // TODO: move this out into a Level class
    List<LevelTile> tiles = new LinkedList<LevelTile>();
    int width, height;
    //Camera cam = new Camera();
    private Nifty nifty;
    private Party party = new Party(0);

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public BitmapText getHelloText() {
        return helloText;
    }

    public void setHelloText(BitmapText helloText) {
        this.helloText = helloText;
    }
    BitmapText helloText;
    
    public List<LevelTile> getTiles() {
        return tiles;
    }

    public void setTiles(List<LevelTile> tiles) {
        this.tiles = tiles;
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App(new DebugKeysAppState());
        }

        return instance;
    }

    public App(AppState... initialStates) {
        //super();

        if (initialStates != null) {
            for (AppState a : initialStates) {
                if (a != null) {
                    stateManager.attach(a);
                }
            }
        }
    }

    @Override
    public void simpleInitApp() {

        initHud();

        flyCam.setEnabled(false);
        
        //flyCam.setMoveSpeed(10f);
        //Node playerNode = new Node("Player");
        
        



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
            Object layerOrTileSet = map.getLayerOrTileset().get(0);
            Object obj = map.getLayerOrTileset().get(1);
            Layer layer = (Layer) map.getLayerOrTileset().get(1);

            initBlock();

            Data data = (Data) layer.getData().get(0);
            int x = 1;
            int z = 0;

            width = new Integer(layer.getWidth());
            height = new Integer(layer.getHeight());
            int iD = 0;

            for (Tile tile : data.getTile()) {
                boolean northWall = false, westWall = false, southWall = false, eastWall = false;

                switch (new Integer(tile.getGid())) {


                    case 1: {
                        northWall = true;
                    }
                    break;
                    case 2: {
                        northWall = true;
                        westWall = true;
                        
                    }
                    break;
                    case 3: {
                        northWall = true;
                        westWall = true;
                        eastWall = true;
                        
                    }
                    break;
                    case 4: {
                        westWall = true;
                    }
                    break;
                    case 5: {
                        westWall = true;
                        southWall = true;
                    }
                    break;
                    case 6: {
                        northWall = true;
                        westWall = true;
                        southWall = true;
                    }
                    break;
                    case 7: {
                        southWall = true;
                    }
                    break;
                    case 8: {
                        southWall = true;
                        eastWall = true;
                    }
                    break;
                    case 9: {
                        westWall = true;
                        southWall = true;
                        eastWall = true;
                    }
                    break;
                    case 10: {
                        eastWall = true;
                    }
                    break;
                    case 11: {
                        northWall = true;
                        eastWall = true;
                    }
                    break;
                    case 12: {
                        northWall = true;
                        southWall = true;
                        eastWall = true;
                    }
                    break;

                }
                if (x > width) {
                    z++;
                    x = 1;
                }
                LevelTile levelTile = new LevelTile(new Vector3f(x * WALLSIZE, 0, z * WALLSIZE), new Integer(tile.getGid()), northWall, westWall, southWall, eastWall, iD);
                tiles.add(levelTile);
                rootNode.attachChild(levelTile.makeWall());
                x++;
                iD++;
            }
        } catch (JAXBException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //party.setLevelID(0);

        GameCharacterControl charControl = new GameCharacterControl(0.25f, 0.25f, 8f);
        charControl.setCamera(cam);
        cam.setLocation(this.getLevelTile(party.getLevelID()).getTileCenter());

    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void initBlock() {
        /* A colored lit cube. Needs light source! */
        Box boxMesh = new Box(1f, 1f, 1f);
        Geometry boxGeo = new Geometry("Colored Box", boxMesh);
        Material boxMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        boxMat.setBoolean("UseMaterialColors", true);
        boxMat.setColor("Ambient", ColorRGBA.Blue);
        boxMat.setColor("Diffuse", ColorRGBA.Blue);
        boxGeo.setMaterial(boxMat);
        boxGeo.setLocalTranslation(new Vector3f(20f, 5f, 21f));
        rootNode.attachChild(boxGeo);
    }

    public void initHud() {
        /**
         * Write text on the screen (HUD)
         */
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Hello World");
        helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
        guiNode.attachChild(helloText);

    }
    
    public LevelTile getNorth(int curTile) {
        return tiles.get(curTile - width);
    }
    public LevelTile getSouth(int curTile) {
        return tiles.get(curTile + width);
    }
    public LevelTile getEast(int curTile) {
        return tiles.get(curTile + 1);
    }
    public LevelTile getWest(int curTile) {
        return tiles.get(curTile - 1);
    }
    
    public LevelTile getLevelTile(int tileID) {
        return tiles.get(tileID);
    }
}
