/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;

/**
 *
 * @author shazzner
 */
public class LevelTile {

    private Vector3f location;
    private int tileType, tileID;
    private boolean northWall, westWall, southWall, eastWall;

    public LevelTile(Vector3f location, int tileType, boolean northWall, boolean westWall, boolean southWall, boolean eastWall, int tileID) {
        this.location = location;
        this.tileType = tileType;
        this.northWall = northWall;
        this.westWall = westWall;
        this.southWall = southWall;
        this.eastWall = eastWall;
        this.tileID = tileID;
    }

    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }

    public int getTileType() {
        return tileType;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public Spatial makeWall() {
        Node wall = new Node();

        Material mat = new Material(App.getInstance().getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");

        mat.setTexture("DiffuseMap", App.getInstance().getAssetManager().loadTexture("Textures/348-diffuse.tga"));
        mat.setTexture("NormalMap", App.getInstance().getAssetManager().loadTexture("Textures/348-normal.tga"));

        /* Floor */
        Quad qf = new Quad(App.WALLSIZE, App.WALLSIZE, false);
        Geometry geomfl = new Geometry("Quadfloor", qf);
        geomfl.setMaterial(mat);
        geomfl.setLocalRotation(new Quaternion().fromAngleAxis((FastMath.PI * 3 / 2), new Vector3f(1, 0, 0)));
        geomfl.setLocalTranslation(0, 0, App.WALLSIZE);
        wall.attachChild(geomfl);
        if (northWall) {
            wall.attachChild(wallOne(mat));
        }
        if (westWall) {
            wall.attachChild(wallTwo(mat));
        }
        if (southWall) {
            wall.attachChild(wallThree(mat));
        }
        if (eastWall) {
            wall.attachChild(wallFour(mat));
        }
        /* Ceiling */
        Quad qc = new Quad(App.WALLSIZE, App.WALLSIZE, false);
        Geometry geomcl = new Geometry("Quadceiling", qc);
        geomcl.setMaterial(mat);
        geomcl.setLocalRotation(new Quaternion().fromAngleAxis((FastMath.PI / 2), new Vector3f(1, 0, 0)));
        geomcl.setLocalTranslation(0, App.WALLSIZE, 0);
        wall.attachChild(geomcl);

        wall.setLocalTranslation(this.getLocation());
        return wall;
    }

    private Spatial wallOne(Material mat) {
        Quad q = new Quad(App.WALLSIZE, App.WALLSIZE, false);
        Geometry geom = new Geometry("Quad", q);
        geom.setMaterial(mat);
        return geom;
    }

    private Spatial wallTwo(Material mat) {
        Quad q2 = new Quad(App.WALLSIZE, App.WALLSIZE, false);
        Geometry geom2 = new Geometry("Quad2", q2);
        geom2.setMaterial(mat);
        geom2.setLocalRotation(new Quaternion().fromAngleAxis((FastMath.PI / 2), new Vector3f(0, 1, 0)));
        geom2.setLocalTranslation(0, 0, App.WALLSIZE);
        return geom2;
    }

    private Spatial wallThree(Material mat) {
        Quad q2 = new Quad(App.WALLSIZE, App.WALLSIZE, false);
        Geometry geom2 = new Geometry("Quad2", q2);
        geom2.setMaterial(mat);
        geom2.setLocalRotation(new Quaternion().fromAngleAxis((FastMath.PI), new Vector3f(0, 1, 0)));
        geom2.setLocalTranslation(App.WALLSIZE, 0, App.WALLSIZE);
        return geom2;
    }

    private Spatial wallFour(Material mat) {
        Quad q3 = new Quad(App.WALLSIZE, App.WALLSIZE, false);
        Geometry geom3 = new Geometry("Quad3", q3);
        geom3.setMaterial(mat);
        geom3.setLocalRotation(new Quaternion().fromAngleAxis((FastMath.PI * 3 / 2), new Vector3f(0, 1, 0)));
        geom3.setLocalTranslation(App.WALLSIZE, 0, 0);
        return geom3;
    }
    
    public Vector3f getTileCenter() {
        Vector3f tileCenter = App.getInstance().getTiles().get(this.tileID).getLocation();
        tileCenter = tileCenter.add(App.WALLSIZE / 2 , App.WALLSIZE / 2, App.WALLSIZE / 2);
        return tileCenter;
        
    }
}
