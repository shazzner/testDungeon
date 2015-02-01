/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import static com.jme3.app.SimpleApplication.INPUT_MAPPING_EXIT;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;

/**
 *
 * @author shazzner
 */
public class GameCharacterControl extends BetterCharacterControl
        implements ActionListener, AnalogListener {

    private boolean forward, backward, leftRotate, rightRotate, leftStrafe, rightStrafe;
    private float moveSpeed = 1000f;
    private float rotationSpeed = 100f;
    private Node head = new Node("Head");
    private Camera cam;

    @SuppressWarnings("LeakingThisInConstructor")
    public GameCharacterControl(float radius, float height, float mass) {
        super(radius, height, mass);
        head.setLocalTranslation(0, 0.75f, 0);

        App.getInstance().getInputManager().addListener(this, INPUT_MAPPING_EXIT);
        App.getInstance().getInputManager().addMapping("MoveForward", new KeyTrigger(KeyInput.KEY_W));
        App.getInstance().getInputManager().addListener(this, "MoveForward");
        App.getInstance().getInputManager().addMapping("MoveBackward", new KeyTrigger(KeyInput.KEY_S));
        App.getInstance().getInputManager().addListener(this, "MoveBackward");
        App.getInstance().getInputManager().addMapping("StrafeLeft", new KeyTrigger(KeyInput.KEY_A));
        App.getInstance().getInputManager().addListener(this, "StrafeLeft");
        App.getInstance().getInputManager().addMapping("StrafeRight", new KeyTrigger(KeyInput.KEY_D));
        App.getInstance().getInputManager().addListener(this, "StrafeRight");
        App.getInstance().getInputManager().addMapping("RotateLeft", new KeyTrigger(KeyInput.KEY_Q));
        App.getInstance().getInputManager().addListener(this, "RotateLeft");
        App.getInstance().getInputManager().addMapping("RotateRight", new KeyTrigger(KeyInput.KEY_E));
        App.getInstance().getInputManager().addListener(this, "RotateRight");
    }

    public void onAction(String action, boolean isPressed, float tpf) {
        if (isPressed) {
            if (action.equals("StrafeLeft")) {
                //leftStrafe = isPressed;
                moveCamera(tpf, true);
            } else if (action.equals("StrafeRight")) {
                rightStrafe = isPressed;
                moveCamera(-tpf, true);
            } else if (action.equals("MoveForward")) {
                forward = isPressed;
                moveCamera(tpf, false);
            } else if (action.equals("MoveBackward")) {
                backward = isPressed;
                moveCamera(-tpf, false);
            } else if (action.equals("RotateLeft")) {
                leftRotate = isPressed;
                rotateCamera(tpf, cam.getUp());
            } else if (action.equals("RotateRight")) {
                rightRotate = isPressed;
                rotateCamera(-tpf, cam.getUp());
            }
            //App.getInstance().getHelloText().setText("Cam " + cam.getRotation());
            App.getInstance().getHelloText().setText("Cam: " + cam.getLocation() + " Rotation: " + cam.getRotation() + " CurrentTileID: " + App.getInstance().getParty().getLevelID());

        }
    }

    public void onAnalog(String name, float value, float tpf) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(float tpf) {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
        Vector3f modelForwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_Z);
        Vector3f modelLeftDir = spatial.getWorldRotation().mult(Vector3f.UNIT_X);
        walkDirection.set(0, 0, 0);
    }

    public void setCamera(Camera cam) {
//        CameraNode camNode = new CameraNode("CamNode", cam);
//        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
//        head.attachChild(camNode);
        this.cam = cam;
    }

    protected void moveCamera(float value, boolean sideways) {
        
        Vector3f vel = new Vector3f();
        Vector3f pos = cam.getLocation().clone();
        
        Party party = App.getInstance().getParty();

        
        
        if (sideways) {
            cam.getLeft(vel);
            if (value > 0) {
                vel.multLocal(App.WALLSIZE);
            } else {
                vel.multLocal(-App.WALLSIZE);
            }
        } else {
            cam.getDirection(vel);
            if (value > 0) {
                vel.multLocal(App.WALLSIZE);
            } else {
                vel.multLocal(-App.WALLSIZE);
            }
        }
        //vel.multLocal(value * moveSpeed);

        pos.addLocal(vel);

        cam.setLocation(pos);
        
        
    }

    protected void rotateCamera(float value, Vector3f axis) {


        Matrix3f mat = new Matrix3f();
        if (value > 0) {
            mat.fromAngleNormalAxis(FastMath.PI / 2, axis);
        } else {
            mat.fromAngleNormalAxis(-FastMath.PI / 2, axis);
        }
        

        Vector3f up = cam.getUp();
        Vector3f left = cam.getLeft();
        Vector3f dir = cam.getDirection();

        mat.mult(up, up);
        mat.mult(left, left);
        mat.mult(dir, dir);

        Quaternion q = new Quaternion();
        q.fromAxes(left, up, dir);
        q.normalizeLocal();

        cam.setAxes(q);
    }

    protected void interpolateCamera(Vector3f point) {
    }
}
