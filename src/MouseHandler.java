import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler extends MouseAdapter {

    private WorldView view;

    public MouseHandler(WorldView view) {
        this.view = view;
    }

    /**
     * Create a bowling ball at the current mouse position.
     * @param e event object containing the mouse position
     */
    public void mousePressed(MouseEvent e) {

    }
}
