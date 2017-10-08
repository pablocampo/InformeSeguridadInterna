/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package informeseguridadinterna;

import javax.swing.border.*;
import java.util.*;
import javax.naming.directory.*;
import javax.naming.*;
import javax.swing.*;
import javax.swing.text.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.lang.Throwable.*;

/**
 * Write a description of class Interfaz here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIInterface
{
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS'Z'");
    private InformeSeguridadInterna buscador;
    
    private static String MY_ATTRS[] = { "cn", "decisionPointCn", "emailConfirmation", "decisionUserMember", 
                                        "flowID", "modifierUser", "createTimestamp", "lastModifiedFlow", 
                                        "modifyTimestamp", "causeOfRejection", "actualDecisionPointFlow", 
                                    "canceled", "closed", "inProcessFlow", "closedFlow" };

    private JFrame frame;  
    
    private JOptionPane bla;
    /**
     * Constructor for objects of class Interfaz
     */
    public GUIInterface(InformeSeguridadInterna buscador)
    {
        // initialise instance variables
        this.buscador = buscador;
        this.frame = new JFrame("GENERADOR INFORMES MENSUALES");
    }

    /**
     * This method receives a JComponent as an argument and adds it a frame in order to view it.
     * 
     */
    private void cambiarPanel(JComponent c, int width, int height)
    {
        // put your code here
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Container contentPane = frame.getContentPane();
        contentPane.removeAll();
        frame.setSize(width,height);        
        frame.setLocationRelativeTo(null);
        contentPane.add(c);
        
        frame.setVisible(true);
        frame.revalidate();
        
    }
    
    /**
     * This method shows the login screen
     */
    public void loginMenu(){

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();  //Cargamos los GridBagConstraints

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/WBSVision.png"));  //Variable que contiene una imagen.
        Image image = imageIcon.getImage(); 
        Image newimg = image.getScaledInstance(200, 65,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
     
        JLabel picLabel = new JLabel(imageIcon);    //JLabel que contien la imagen.
        gbc.gridy = 0;  //casilla vertical
        gbc.weightx =0.1;
        gbc.weighty =0.1;
        gbc.gridwidth = 1;  //casilla vertical
        gbc.gridheight = 1;  //casilla vertical
        gbc.insets = new Insets(0,0,0,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        gbc.anchor = gbc.NORTHWEST;
        gbc.ipadx =0;
        gbc.ipady =0;
        panel.add(picLabel,gbc);    //añadimos el JLabel de la imagen al JPanel
        picLabel.setLayout(new GridBagLayout());    //configuramos el JLabel de la imagen para que emplee GridBagLayout

        gbc.gridy = 1;  //casilla vertical
        
        gbc.ipadx =0;
        gbc.gridheight = 1;  //casilla vertical
        gbc.weighty =0;
        gbc.gridwidth = 1;  //casilla vertical
        gbc.anchor = gbc.WEST;

        JLabel userLabel = new JLabel("Usuario");
        gbc.gridx = 0;  //casilla horizontal
        gbc.insets = new Insets(20,80,50,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        panel.add(userLabel,gbc);    //añadimos el JLabel de la imagen al JPanel
        final JTextField userVal = new JTextField(30);
        gbc.ipadx =100;
        gbc.insets = new Insets(20,100,50,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        panel.add(userVal,gbc);    //añadimos el JLabel de la imagen al JPanel
        
        JLabel passwdLabel = new JLabel("Contraseña");
        //gbc.gridx = 0;  //casilla horizontal
        gbc.insets = new Insets(60,0,50,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        panel.add(passwdLabel,gbc);    //añadimos el JLabel de la imagen al JPanel
        final JPasswordField passwdVal = new JPasswordField();
        gbc.ipadx =100;
        gbc.insets = new Insets(60,80,50,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        //gbc.gridwidth = 10;  //casilla vertical
        panel.add(passwdVal,gbc);    //añadimos el JLabel de la imagen al JPanel

        JButton botonBuscar = new JButton("Validar");
        gbc.weightx =0;
        gbc.weighty =0;
        gbc.gridx = 0;  //casilla horizontal
        gbc.gridy = 2;  //casilla vertical
        gbc.anchor = gbc.WEST;
        gbc.insets = new Insets(0,50,40,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        
        panel.add(botonBuscar,gbc);
        
        botonBuscar.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent e){
                }
            }
        );
        cambiarPanel(panel,600, 300);
    }

    /**
     * This method shows the main menu
     */
    public void mostrarMenu(){

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();  //Cargamos los GridBagConstraints

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/WBSVision.png"));  //Variable que contiene una imagen.
        Image image = imageIcon.getImage(); 
        Image newimg = image.getScaledInstance(200, 65,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
     
        JLabel picLabel = new JLabel(imageIcon);    //JLabel que contien la imagen.
        gbc.gridy = 0;  //casilla vertical
        gbc.weightx =0.1;
        gbc.weighty =0.1;
        gbc.gridwidth = 1;  //casilla vertical
        gbc.gridheight = 1;  //casilla vertical
        gbc.insets = new Insets(0,0,0,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        gbc.anchor = gbc.NORTHWEST;
        gbc.ipadx =0;
        gbc.ipady =0;
        panel.add(picLabel,gbc);    //añadimos el JLabel de la imagen al JPanel
        picLabel.setLayout(new GridBagLayout());    //configuramos el JLabel de la imagen para que emplee GridBagLayout

        gbc.gridy = 1;  //casilla vertical
        
        gbc.ipadx =0;
        gbc.gridheight = 1;  //casilla vertical
        gbc.weighty =0;
        gbc.gridwidth = 1;  //casilla vertical
        gbc.anchor = gbc.WEST;

        JLabel yearLabel = new JLabel("Año");
        gbc.gridx = 0;  //casilla horizontal
        gbc.insets = new Insets(20,50,50,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        panel.add(yearLabel,gbc);    //añadimos el JLabel de la imagen al JPanel
        final JTextField yearVal = new JTextField(30);
        gbc.ipadx =100;
        gbc.insets = new Insets(20,100,50,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        panel.add(yearVal,gbc);    //añadimos el JLabel de la imagen al JPanel
        
        JLabel monthLabel = new JLabel("Mes");
        gbc.gridx = 1;  //casilla horizontal
        gbc.insets = new Insets(20,0,50,200);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        panel.add(monthLabel,gbc);    //añadimos el JLabel de la imagen al JPanel
        final JTextField monthVal = new JTextField(30);
        gbc.ipadx =100;
        gbc.insets = new Insets(20,50,50,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        //gbc.gridwidth = 10;  //casilla vertical
        panel.add(monthVal,gbc);    //añadimos el JLabel de la imagen al JPanel

        JButton botonBuscar = new JButton("Generar Informe");
        gbc.weightx =0;
        gbc.weighty =0;
        gbc.gridx = 0;  //casilla horizontal
        gbc.gridy = 2;  //casilla vertical
        gbc.anchor = gbc.WEST;
        gbc.insets = new Insets(0,50,40,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        
        panel.add(botonBuscar,gbc);
        
        botonBuscar.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent e){
                    try{
                        //When button "Buscar" is clicked upon, this method is launched
                       if (!inputOK(yearVal.getText(), monthVal.getText())){
                           inputNoValido();
                        }
                       else{

                        int year = Integer.parseInt(yearVal.getText());
                        int month = Integer.parseInt(monthVal.getText());

                        ArrayList<Transaction> transList = buscador.launchSearch(year, month);
                        WriteExcelFile.writeXLSXFile(yearVal.getText().trim(), monthVal.getText().trim(),transList);
                       }
                   }
                    catch(Exception ex){}
                }
            }
        );
        cambiarPanel(panel,600, 300);
    }

    public void mostrarMenu_OLD(){

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();  //Cargamos los GridBagConstraints

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/WBSVision.png"));  //Variable que contiene una imagen.
        Image image = imageIcon.getImage(); 
        Image newimg = image.getScaledInstance(200, 65,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
     
        JLabel picLabel = new JLabel(imageIcon);    //JLabel que contien la imagen.
        gbc.gridy = 0;  //casilla vertical
        gbc.weightx =0.1;
        gbc.weighty =0.1;
        gbc.gridwidth = 1;  //casilla vertical
        gbc.gridheight = 1;  //casilla vertical
        gbc.insets = new Insets(0,0,0,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        gbc.anchor = gbc.NORTHWEST;
        gbc.ipadx =0;
        gbc.ipady =0;
        panel.add(picLabel,gbc);    //añadimos el JLabel de la imagen al JPanel
        picLabel.setLayout(new GridBagLayout());    //configuramos el JLabel de la imagen para que emplee GridBagLayout

        gbc.gridy = 1;  //casilla vertical
        
        gbc.ipadx =0;
        gbc.gridheight = 1;  //casilla vertical
        gbc.weighty =0;
         //gbc.weightx =1;
        gbc.gridwidth = 1;  //casilla vertical
        gbc.anchor = gbc.WEST;


        JLabel yearLabel = new JLabel("Año");
        gbc.gridx = 0;  //casilla horizontal
        gbc.insets = new Insets(20,50,50,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        panel.add(yearLabel,gbc);    //añadimos el JLabel de la imagen al JPanel
        final JTextField yearVal = new JTextField(30);
        gbc.ipadx =100;
        gbc.insets = new Insets(20,100,50,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        //gbc.gridwidth = 10;  //casilla vertical
        panel.add(yearVal,gbc);    //añadimos el JLabel de la imagen al JPanel
        
        
        JLabel monthLabel = new JLabel("Mes");
        gbc.gridx = 1;  //casilla horizontal
        gbc.insets = new Insets(20,0,50,200);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        panel.add(monthLabel,gbc);    //añadimos el JLabel de la imagen al JPanel
        final JTextField monthVal = new JTextField(30);
        gbc.ipadx =100;
        gbc.insets = new Insets(20,50,50,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        //gbc.gridwidth = 10;  //casilla vertical
        panel.add(monthVal,gbc);    //añadimos el JLabel de la imagen al JPanel

        JButton botonBuscar = new JButton("Generar Informe");
        gbc.weightx =0;
        gbc.weighty =0;
        gbc.gridx = 0;  //casilla horizontal
        gbc.gridy = 2;  //casilla vertical
        gbc.anchor = gbc.WEST;
        gbc.insets = new Insets(0,50,40,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        //gbc.insets = new Insets(0,70,40,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        //panel.add(yearLabel,gbc);
        //gbc.insets = new Insets(0,165,40,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        //panel.add(yearVal,gbc);
        //gbc.insets = new Insets(110,100,0,0);// posició del JLabel en la casilla (de arriba, de izq, de abajo, de derecha)
        
        //gbc.gridy = 1;  //casilla vertical
        panel.add(botonBuscar,gbc);
        
        botonBuscar.addActionListener( new ActionListener()
            {
                public void actionPerformed(ActionEvent e){
                    try{
                        //When button "Buscar" is clicked upon, this method is launched
                       if (!inputOK(yearVal.getText(), monthVal.getText())){
                           inputNoValido();
                        }
                       else{

                        int year = Integer.parseInt(yearVal.getText());
                        int month = Integer.parseInt(monthVal.getText());

                        ArrayList<Transaction> transList = buscador.launchSearch(year, month);
                        WriteExcelFile.writeXLSXFile(yearVal.getText().trim(), monthVal.getText().trim(),transList);
                       }
                   }
                    catch(Exception ex){}
                }
            }
        );
        cambiarPanel(panel,600, 300);
    }
   
    private boolean inputOK(String year, String month){
        if (year.isEmpty() || month.isEmpty() ||
            year.length() != 4 ||
            month.length() != 2 ||
            Integer.parseInt(year) < 2010 || 
            Integer.parseInt(year) > 2030 || 
            Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12){ return false;}
        return true;
    }
    
    public void imprimirMensajeAdHoc(String mensaje){
        System.out.println(mensaje);
        JOptionPane.showMessageDialog(null, mensaje);
    }
    public void inputNoValido(){
        JOptionPane.showMessageDialog(null, "Datos de entrada inválidos.");
    }
    

}

    
    
