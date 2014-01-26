package juliensjukebox;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.BorderFactory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class JuliensJukeBox extends JFrame implements ActionListener {
    
    Playlist pl = new Playlist();
    JButton muteSound;
    boolean muted = false;
    
    JuliensJukeBox() {
        
        // setBackground
        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon(getClass().getResource("general/dancefloor.jpg"))));
        
        // Layout
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);
        
        // General
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Julien\'s Jukebox (c) Armadillo Productions");
        getContentPane().setBackground(new Color(214, 50, 0));
        
        // Set Look And Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JuliensJukeBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Icon
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("general/jukebox.png")));
        
        // Get Button directories
        JButton buttons[] = new JButton[5];
        
        buttons[0] = new JButton("Math");
        buttons[1] = new JButton("Memory");
        buttons[2] = new JButton("Accuracy");
        buttons[3] = new JButton("Vocabulary");
        buttons[4] = new JButton("Quit");
        
        // Set Button Properties
        for(int i = 0; i < buttons.length; i++) {
            
            // Position
            gbc.gridy = i+1;
            gbc.insets = new Insets(0,0,10,0);
            gbl.setConstraints(buttons[i], gbc);
            
            // Properties
            buttons[i].setMargin(new Insets(10,50,10,50));
            buttons[i].setPreferredSize(new Dimension(200, 50));
            buttons[i].setFont(new Font("Tahoma",Font.BOLD,16));
            buttons[i].setForeground(new Color(255,255,255));
            buttons[i].setBackground(new Color(0,0,0));
            buttons[i].setBorder(BorderFactory.createEmptyBorder());
            
            // EvenListeners
            buttons[i].addActionListener(this);
            buttons[i].setActionCommand(Integer.toString(i));
            buttons[i].addMouseListener(new MouseHandler());
            add(buttons[i]);
        }
        
        // Mute Button
        muteSound = new JButton(new ImageIcon("button\\musicOn.png"));
        try {
            Image img = ImageIO.read(getClass().getResource("button/musicOn.png"));
            muteSound.setIcon(new ImageIcon(img));
        } catch (IOException ex) { }
        
        // Mute Button Properties
        muteSound.setOpaque(false);
        muteSound.setContentAreaFilled(false);
        muteSound.setBorderPainted(false);
        muteSound.setActionCommand(Integer.toString(5));
        muteSound.addActionListener(this);
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(0,300,0,0);
        gbl.setConstraints(muteSound, gbc);
        add(muteSound);
        
        // Refresh
        setSize(399,549);
        setSize(400,525);
        setLocationRelativeTo(null);
        
        // Play Music
        pl.openClips();
        pl.startPlaying();
        
        setVisible(true);
    }
    
    // ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(Integer.parseInt(e.getActionCommand())) {
            case 0: 
                this.dispose();
                pl.stopPlaying();
                new Math();
                break;

            case 1:
                break;
            
            case 2:
                break;
            
            case 3:
                break;
                
            case 4:
                System.exit(0);
                break;
            
            // Mute Button
            case 5:
                try {
                    if(muted == false) {
                        Image img = ImageIO.read(getClass().getResource("button/musicOff.png"));
                        muteSound.setIcon(new ImageIcon(img));
                        pl.stopPlaying();
                        muted = true;
                    }else{
                        Image img = ImageIO.read(getClass().getResource("button/musicOn.png"));
                        muteSound.setIcon(new ImageIcon(img));
                        muted = false;
                        pl.openClips();
                        pl.startPlaying();
                    }
                } catch (IOException ex) { }
                
                
                break;
                
            default:
                break;
        }
    }
    
    // Main
    public static void main(String[] args) {
        new JuliensJukeBox();
    }

}
