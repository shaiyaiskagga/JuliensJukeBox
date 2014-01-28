package juliensjukebox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;

import java.util.Random;


public class Math extends JFrame implements KeyListener, Runnable {
    
    JLabel clock, task = new JLabel();
    JTextField input = new JTextField(14);
    JButton start = new JButton("Start");
    
    final Thread thread = new Thread(this);
    
    Random r = new Random();
    int secondsLeft = 72, correct = 0, tries = 0, ersteZahl, zweiteZahl;
    char calculation;
    
    public Math() {
        
        // General
        setSize(200,200);
        setTitle("Math");
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));
        
        // Center
        clock.setAlignmentX(Component.CENTER_ALIGNMENT);
        task.setAlignmentX(Component.CENTER_ALIGNMENT);
        input.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Set font
        task.setFont(new Font("Arial",Font.BOLD,24));
        
        // Push it to the center (vertically)
        Box box = Box.createVerticalBox();
        add(box.createVerticalStrut(50));
        
        // Change Size&Position of textfield
        input.setMaximumSize(new Dimension(getWidth(),20));
        input.setHorizontalAlignment(JTextField.CENTER);
        input.addKeyListener(this);
        
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
        
        // Put an exercise on screen
        changeText();
        
        // One could say.. it is..
        showTime();
    }
    
    // Set the clock to the time remaining
    public void showTime() {
        while(true) {
            
            // Set the clock to current seconds Left
            clock.setText(formatTime(secondsLeft));
            
            // Pause
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) { }
        
            // When time is up, no possibility of entering an input
            if(secondsLeft != 0) {
                secondsLeft--;
            }else {
                input.setEditable(false);
                this.add(new JLabel("HALLLO")); //TODO: results
                break;
            }
        }
    }
    
    // Print new exercise to the textfield
    public void changeText() {
        
        // Determine calculation type
        switch(calculation) {
            // Addition
            case 'a': 
                do {
                    ersteZahl = r.nextInt(1000);
                    zweiteZahl = r.nextInt(1000);
                }while(ersteZahl+zweiteZahl > 10000);
                break;
            // Subtraction
            case 's':
                break;
            
            // Multiplication
            case 'm':
                break;
            
            // Division
            case 'd':
                break;
            
            // Not sure
            default:
                System.err.println("What are you doing?!");
                break;
        }
        
        task.setText(ersteZahl + " + " + zweiteZahl);
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
