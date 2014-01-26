package juliensjukebox;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.Font;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;


public class Math extends JFrame implements KeyListener, Runnable {
    
    JLabel clock = new JLabel();
    JLabel task = new JLabel();
    JTextField input = new JTextField(14);
    JButton start = new JButton("Start");
    
    final Thread thread = new Thread(this);
    
    Random r = new Random();
    int secondsLeft = 72, correct = 0, tries = 0, ersteZahl, zweiteZahl;
    
    public Math() {
        
        // General
        setSize(200,200);
        setTitle("Math");
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));
        
        Box box = Box.createVerticalBox();
        
        // Zentrieren
        clock.setAlignmentX(Component.CENTER_ALIGNMENT);
        task.setAlignmentX(Component.CENTER_ALIGNMENT);
        input.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(box.createVerticalStrut(50));
        input.setMaximumSize(new Dimension(getWidth(),20));
        input.setHorizontalAlignment(JTextField.CENTER);
        
        // WindowListener (to go back to main menu)
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new JuliensJukeBox();
                e.getWindow().dispose();
            }
        });
        
        // Start Button
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setVisible(false);
                add(task);
                add(clock);
                add(input);
                thread.start();
            }
        });
        
        input.addKeyListener(this);
        add(start);
        
        setVisible(true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            evaluieren(Integer.parseInt(input.getText()));
            changeText();
        }
    }
    
    @Override
    public void run() {
        changeText();
        
        // Timer
        while(true) {
            clock.setText(formatTime(secondsLeft));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Math.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            // Wenn Timer abgelaufen, dann keine Möglichkeit mehr Input
            if(secondsLeft != 0) {
                secondsLeft--;
            }else {
                input.setEditable(false);
                this.add(new JLabel("HALLLO"));
                break;
            }
        }
    }
    
    // Neue Aufgabe auf dem TextField ausgeben 
    public void changeText() {
        do {
            ersteZahl = r.nextInt(1000);
            zweiteZahl = r.nextInt(1000);
        }while(ersteZahl+zweiteZahl > 10000);
        
        task.setText(ersteZahl + " + " + zweiteZahl);
        task.setFont(new Font("Arial",Font.BOLD,24));
    }
    
    // Umwandlung von Sekunden in Minuten und Sekunden
    private String formatTime(int seconds) {
        String m,s;
        int minutes;
        
        if(seconds / 60.0 > 1) {
            minutes = (int)(seconds/60.0);
            seconds -= minutes*60;
        }else{
            minutes = 0;
        }
        
        // Zeit in 00:00 Angeben
        if(minutes < 10)
            m = "0" + String.valueOf(minutes);
        else
            m = String.valueOf(minutes);
        
        if(seconds < 10)
            s = "0" + String.valueOf(seconds);
        else
            s = String.valueOf(seconds);
        
        return m + ":" + s;
    
    }

    // Prüfen ob Eingabe richtig ist
    public void evaluieren(int input) {
        if((ersteZahl + zweiteZahl) == input) {
            correct++;
            System.out.println("Correct!");
        }
        tries++;
        this.input.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}
}

/*
 * TODO: Sehen was richtig war und was falsch
 * 
 */
