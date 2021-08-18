/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Doctors;
import View.DialogDoctors;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Nicolas D'aleman
 */
public class ControllerDoctor implements ActionListener {

    Conexion con = new Conexion();
    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Doctors doctors;

    private final DialogDoctors view;

    public final void events() {
        view.cb_hospitalDoctor.addActionListener(this);
    }
    //Controller DialogDoctor

    public ControllerDoctor(DialogDoctors view) {
        this.view = view;
        Doctors item = (Doctors) view.cb_hospitalDoctor.getSelectedItem();
        events();
    }

    public ArrayList<Doctors> getDoctors(int idDoctor) {

        ArrayList list = new ArrayList();
        try {
            String sql = "SELECT id, doctor FROM `tb_doctor` WHERE 1";
            cn = con.getConnection();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (cn != null) {
                System.out.println("Conexión éxitosa DataModelDB");
                while (rs.next()) {
                    doctors = new Doctors();
                    doctors.setId_doctor(rs.getInt("id"));
                    doctors.setDoctor(rs.getString("doctor"));
                    list.add(doctors);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, ".::Error en la conexión::.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
        public void ConsultarHospital(JComboBox Doctor){
        String sql = "SELECT name from tb_hospital";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Doctor.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el id del hospital para el ComboBox de JDialogDoctor" + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

    }

}
