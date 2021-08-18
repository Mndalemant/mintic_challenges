/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ReportClients;
import Model.ReportPets;
import View.FrameHospital;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author Nicolas D'aleman
 */
public class ControllerCharts {

    public LinkedList<ReportPets> ListPetType() {
        Conexion con = new Conexion();
        Connection cn;
        Statement st;
        ResultSet rs;
        LinkedList<ReportPets> report = new LinkedList<>();
        try {
            String sql = "SELECT pet_type,count(pet_type) FROM `tb_pet` GROUP BY pet_type";
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if (cn != null) {
                System.out.println("Conexión éxitosa Base de datos tb_pet");
                while (rs.next()) {
                    ReportPets record = new ReportPets();
                    String pet_type = rs.getString(1);
                    int amount = rs.getInt(2);
                    record.setPet_type(pet_type);
                    record.setAmount_pet_type(amount);
                    report.add(record);
                }
            } else {
                JOptionPane.showMessageDialog(null, ".::Error al realizar la consulta::.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, ".::Error en la conexión::.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return report;
    }
    
        public LinkedList<ReportClients> ListClients() {
        Conexion con = new Conexion();
        Connection cn;
        Statement st;
        ResultSet rs;
        LinkedList<ReportClients> report = new LinkedList<>();
        try {
            String sql = "SELECT gender,count(gender) FROM `tb_pet_owners` GROUP BY gender";
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if (cn != null) {
                System.out.println("Conexión éxitosa Base de datos tb_pet_owners");
                while (rs.next()) {
                    ReportClients record = new ReportClients();
                    String gender = rs.getString(1);
                    int amount = rs.getInt(2);
                    record.setGender(gender);
                    record.setAmount_gender(amount);
                    report.add(record);
                }
            } else {
                JOptionPane.showMessageDialog(null, ".::Error al realizar la consulta::.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, ".::Error en la conexión::.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return report;
    }

}
