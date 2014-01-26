package juliensjukebox;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.awt.Color;

public class MouseHandler implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        e.getComponent().setBounds(e.getComponent().getX()+4, e.getComponent().getY()+2, 
                e.getComponent().getWidth()-8, e.getComponent().getHeight()-4);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        e.getComponent().setBounds(e.getComponent().getX()-4, e.getComponent().getY()-2, 
                e.getComponent().getWidth()+8, e.getComponent().getHeight()+4);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setBackground(new Color(48,48,48));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setBackground(new Color(0,0,0));
    }
    
}
