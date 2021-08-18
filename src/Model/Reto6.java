/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Conexion;
import View.FrameHospital;

/**
 *
 * @author Nicolas D'aleman
 */
public class Reto6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Conexion inst_connect = new Conexion();
       	inst_connect.getConnection();
        
        FrameHospital inst_frame = new FrameHospital();
        inst_connect.getConnection();
        inst_frame.setVisible(true);
    }
    
}
