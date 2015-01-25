/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
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
    private float moveSpeed;
    private Node head = new Node("Head");

    public GameCharacterControl(float radius, float height, float mass) {
        super(radius, height, mass);
        head.setLocalTranslation(0, 0.75f, 0);
    }

    
    
    public void onAction(String action, boolean isPressed, float tpf) {
        if (action.equals("StrafeLeft")) {
            leftStrafe = isPressed;
        } else if (action.equals("StrafeRight")) {
            rightStrafe = isPressed;
        } else if (action.equals("MoveForward")) {
            forward = isPressed;
        } else if (action.equals("MoveBackward")) {
            backward = isPressed;
        }
    }

    public void onAnalog(String name, float value, float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(float tpf) {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
        Vector3f modelForwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_Z);
        Vector3f modelLeftDir = spatial.getWorldRotation().mult(Vector3f.UNIT_X);
        walkDirection.set(0, 0, 0);
    }
    
    public void setCamera(Camera cam) {
        CameraNode camNode = new CameraNode("CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        head.attachChild(camNode);
    }
    
}
