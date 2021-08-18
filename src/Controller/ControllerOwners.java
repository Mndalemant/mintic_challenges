/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Owners;
import View.DialogPets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Model.Owners;

/**
 *
 * @author Nicolas D'aleman
 */
public class ControllerOwners implements ActionListener {

    Conexion con = new Conexion();
    Connection cn;
    Statement st;
    ResultSet rs;
    Owners datosOwner;

    private final DialogPets view;

    public final void events() {
        view.cb_ownersPets.addActionListener(this);
    }

    //Controller DialogPets
    public ControllerOwners(DialogPets view) {
        this.view = view;
        Owners item = (Owners) view.cb_ownersPets.getSelectedItem();
        events();
    }

    public ArrayList<Owners> getOwners(int idOwner) {
        ArrayList list = new ArrayList();
        try {
            String sql = "SELECT id, owner FROM `tb_pet_owners` WHERE 1";
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if (cn != null) {
                System.out.println("Conexión éxitosa ControllerOwners");
                while (rs.next()) {
                    datosOwner = new Owners();
                    datosOwner.setId_owner(rs.getInt("id"));
                    datosOwner.setOwner(rs.getString("owner"));
                    list.add(datosOwner);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, ".::Error en la conexión::.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

    }

}
