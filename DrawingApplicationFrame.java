/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2ddrawingapplication;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author acv
 */
public class DrawingApplicationFrame extends JFrame
{

    // Create the panels for the top of the application. One panel for each
    // line and one to contain both of those panels.

    //create the widgets for the firstLine Panel.

    //create the widgets for the secondLine Panel.

    
    // Variables for drawPanel.

    // add status label
  
    // Constructor for DrawingApplicationFrame
    
    //first row
    private final JPanel firstLinePanel;
    private final JButton UndoJButton;
    private final JButton ClearJButton;
    private final JCheckBox FilledCheckbox;
    private final JComboBox <String> Shape;
    private static final String[] shapes = {"Rectangle" ,"Oval", "Line"};
    
    
    //second row
    private final JPanel secondLinePanel;
    private final JCheckBox Gradient;
    private final JButton changeColorButton1;
    private final JButton changeColorButton2;
    private final JTextField textFieldWidth;
    private final JTextField textFieldLength;
    private final JCheckBox DashCheckbox;
   
    //variables to store color selected
    private Color firstColor = Color.BLACK;
    private Color secondColor = Color.BLACK;
    
    
    //panels
    private final JPanel topPanel;
    private final DrawPanel drawPanel;
    private final JLabel statusLabel;
    
    private ArrayList<MyShapes> shapesDrawn;

    
    public DrawingApplicationFrame()
    {
        // add widgets to panels
       
        
        UndoJButton = new JButton("Undo");
        UndoJButton.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        if (!shapesDrawn.isEmpty()) {
                            shapesDrawn.remove(shapesDrawn.size() - 1);
                            drawPanel.repaint();
                    }
                    }
                }
                
        ); 
        ClearJButton = new JButton("Clear");
        ClearJButton.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                         shapesDrawn = new ArrayList<>();
                         drawPanel.repaint();

                    }
                }
            
        );
        Shape = new JComboBox<String>(shapes);
        Shape.setMaximumRowCount(3);
        FilledCheckbox = new JCheckBox("Filled");
        firstLinePanel = new JPanel();
        
        firstLinePanel.add(UndoJButton);
        firstLinePanel.add(ClearJButton);
        firstLinePanel.add(Shape); //not sure how to get "Shaped: to the left of combo
        firstLinePanel.add(FilledCheckbox);
              
        Gradient = new JCheckBox("Use Gradient");
        changeColorButton1 = new JButton("1st Color...");
        changeColorButton1.addActionListener(
            new ActionListener()
            {
                
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    firstColor = JColorChooser.showDialog(DrawingApplicationFrame.this, "Choose a color", firstColor);
                   
                    if (firstColor == null)
                        firstColor = Color.BLACK;
                }
            }
        );
             
        changeColorButton2 = new JButton("2nd Color...");
        changeColorButton2.addActionListener(
            new ActionListener()
            {
                
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    secondColor = JColorChooser.showDialog(DrawingApplicationFrame.this, "Choose a color", secondColor);
                    
                    if (secondColor == null)
                        secondColor = Color.BLACK;
                }
            }
        );
    
        DashCheckbox = new JCheckBox("Dashed");
        secondLinePanel = new JPanel();
        textFieldWidth = new JTextField("10",2);
        textFieldLength = new JTextField("15",2);
            
        secondLinePanel.add(Gradient);
        secondLinePanel.add(changeColorButton1);
        secondLinePanel.add(changeColorButton2);
        secondLinePanel.add(new JLabel("Line Width:"));
        secondLinePanel.add(textFieldWidth);
        secondLinePanel.add(new JLabel("Line Length:"));
        secondLinePanel.add(textFieldLength);
        secondLinePanel.add(DashCheckbox);
               
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,1));
        topPanel.add(firstLinePanel, BorderLayout.NORTH);
        topPanel.add(secondLinePanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH); 
        
        drawPanel = new DrawPanel();
        drawPanel.setBackground(Color.WHITE);
        add(drawPanel, BorderLayout.CENTER);
        
        
        statusLabel = new JLabel();
        add(statusLabel, BorderLayout.SOUTH);
        
        // firstLine widgets

        // secondLine widgets

        // add top panel of two panels

        // add topPanel to North, drawPanel to Center, and statusLabel to South
        
        //add listeners and event handlers
    }

    // Create event handlers, if needed

    // Create a private inner class for the DrawPanel.
    private class DrawPanel extends JPanel
    {
        
           
        public DrawPanel()
        {
           super();
           shapesDrawn = new ArrayList<MyShapes>();
           addMouseListener(new MouseHandler());
           addMouseMotionListener(new MouseHandler());
          
        }
        
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            this.setBackground(Color.WHITE);
            Graphics2D g2d = (Graphics2D) g;
            for(MyShapes s : shapesDrawn)
            	s.draw(g2d); //loop through and draw each shape in the shapes arraylist
    
        }
        public ArrayList<MyShapes> getShapesDrawn() { return shapesDrawn; }
    
        private class MouseHandler extends MouseAdapter implements MouseMotionListener
        {
            
            @Override
            public void mousePressed(MouseEvent event)
            {
               
               float[] dash_array = {Float.parseFloat(textFieldLength.getText())};
               Point newStart;
               Paint newPaint;
               Stroke newStroke;
               boolean newFilled = FilledCheckbox.isSelected();
               
               newStart = new Point(event.getX(),event.getY());
               if(DashCheckbox.isSelected())
            		newStroke = new BasicStroke(Float.parseFloat(textFieldWidth.getText()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dash_array, 0);
               else
            		newStroke = new BasicStroke(Float.parseFloat(textFieldWidth.getText()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            	
            	// set fill 
               if(Gradient.isSelected())
            		newPaint = new GradientPaint(0, 0, firstColor, 50, 50, secondColor, true);
               else
            		newPaint = firstColor;
            	
            	// add appropriate MyShape to ArrayList shapesDrawn and repaint DrawPanel
               if(Shape.getSelectedIndex() == 1) // combo box index 1
                        shapesDrawn.add(new MyOval(newStart, newStart, newPaint, newStroke, newFilled));	
               else if(Shape.getSelectedIndex() == 0) //combo box index 0
            		shapesDrawn.add(new MyRectangle(newStart, newStart, newPaint, newStroke, newFilled));
               else // all others. one other choice left
            		shapesDrawn.add(new MyLine(newStart, newStart, newPaint, newStroke));
               repaint();
               statusLabel.setText(String.format("[%d,%d]", event.getX(), event.getY()));
            }

            
            @Override
            public void mouseReleased(MouseEvent event)
            {
                shapesDrawn.get(shapesDrawn.size() - 1).setEndPoint(new Point(event.getX(), event.getY()));
                repaint();
                statusLabel.setText(String.format("[%d,%d]", event.getX(), event.getY()));
         
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {
                shapesDrawn.get(shapesDrawn.size() - 1).setEndPoint(new Point(event.getX(), event.getY()));
                repaint();
                statusLabel.setText(String.format("[%d,%d]", event.getX(), event.getY()));
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
               statusLabel.setText(String.format("[%d,%d]", event.getX(), event.getY()));
            }
        }

    }
}

