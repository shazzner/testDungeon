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
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;

/**
 *
 * @author shazzner
 */
public class Tile {

    private Vector3f location;
    private int tileType;
    private static int WALLSIZE = 1;

    public Tile(Vector3f location, int tileType) {
        this.location = location;
        this.tileType = tileType;
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

    public Spatial makeWall() {
        Node wall = new Node();

        Material mat = new Material(App.getInstance().getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");

        mat.setTexture("DiffuseMap", App.getInstance().getAssetManager().loadTexture("Textures/348-diffuse.tga"));
        mat.setTexture("NormalMap", App.getInstance().getAssetManager().loadTexture("Textures/348-normal.tga"));

        Quad qf = new Quad(WALLSIZE, WALLSIZE, false);

        //Box b = new Box(0.5f, 0.5f, 0.5f);
        Geometry geomfl = new Geometry("Quadfloor", qf);


        geomfl.setMaterial(mat);
        geomfl.setLocalRotation(new Quaternion().fromAngleAxis((FastMath.PI * 3 / 2), new Vector3f(1, 0, 0)));
        geomfl.setLocalTranslation(0, 0, WALLSIZE);
        wall.attachChild(geomfl);

        switch (tileType) {

            case 1: {
                wall.attachChild(wallOne(mat));
            }
            break;
            case 2: {
                wall.attachChild(wallOne(mat));
                wall.attachChild(wallTwo(mat));
            }
            break;
            case 3: {
                wall.attachChild(wallOne(mat));
                wall.attachChild(wallTwo(mat));
                wall.attachChild(wallFour(mat));
            }
            break;
            case 4: {
                wall.attachChild(wallTwo(mat));
            }
            break;
            case 5: {
                wall.attachChild(wallTwo(mat));
                wall.attachChild(wallThree(mat));
            }
            break;
            case 6: {
                wall.attachChild(wallOne(mat));
                wall.attachChild(wallTwo(mat));
                wall.attachChild(wallThree(mat));
            }
            break;
            case 7: {
                wall.attachChild(wallThree(mat));
            }
            break;
            case 8: {
                wall.attachChild(wallThree(mat));
                wall.attachChild(wallFour(mat));
            }
            break;
            case 9: {
                wall.attachChild(wallTwo(mat));
                wall.attachChild(wallThree(mat));
                wall.attachChild(wallFour(mat));
            }
            break;
            case 10: {
                wall.attachChild(wallFour(mat));
            }
            break;
            case 11: {
                wall.attachChild(wallOne(mat));
                wall.attachChild(wallFour(mat));
            }
            break;
            case 12: {
                wall.attachChild(wallOne(mat));
                wall.attachChild(wallThree(mat));
                wall.attachChild(wallFour(mat));
            }
            break;
        }

        wall.setLocalTranslation(this.getLocation());
        return wall;
    }

    private Spatial wallOne(Material mat) {
        Quad q = new Quad(WALLSIZE, WALLSIZE, false);
        Geometry geom = new Geometry("Quad", q);
        geom.setMaterial(mat);
        return geom;
    }

    private Spatial wallTwo(Material mat) {
        Quad q2 = new Quad(WALLSIZE, WALLSIZE, false);
        Geometry geom2 = new Geometry("Quad2", q2);
        geom2.setMaterial(mat);
        geom2.setLocalRotation(new Quaternion().fromAngleAxis((FastMath.PI / 2), new Vector3f(0, 1, 0)));
        geom2.setLocalTranslation(0, 0, WALLSIZE);
        return geom2;
    }

    private Spatial wallThree(Material mat) {
        Quad q2 = new Quad(WALLSIZE, WALLSIZE, false);
        Geometry geom2 = new Geometry("Quad2", q2);
        geom2.setMaterial(mat);
        geom2.setLocalRotation(new Quaternion().fromAngleAxis((FastMath.PI), new Vector3f(0, 1, 0)));
        geom2.setLocalTranslation(WALLSIZE, 0, WALLSIZE);
        return geom2;
    }

    private Spatial wallFour(Material mat) {
        Quad q3 = new Quad(WALLSIZE, WALLSIZE, false);
        Geometry geom3 = new Geometry("Quad3", q3);
        geom3.setMaterial(mat);
        geom3.setLocalRotation(new Quaternion().fromAngleAxis((FastMath.PI * 3 / 2), new Vector3f(0, 1, 0)));
        geom3.setLocalTranslation(WALLSIZE, 0, 0);
        return geom3;
    }
}
