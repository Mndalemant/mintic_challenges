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
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Nicolas D'aleman
 */
public class ControllerPets implements ActionListener {

    Conexion con = new Conexion();
    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Owners owner;

    private final DialogPets view;

    public final void events() {
        view.cb_ownersPets.addActionListener(this);
    }
    //Controller DialogDoctor

    public ControllerPets(DialogPets view) {
        this.view = view;
        Owners item = (Owners) view.cb_ownersPets.getSelectedItem();
        events();
    }

    public ArrayList<Owners> getOwners(int id_owner) {

        ArrayList list = new ArrayList();
        try {
            String sql = "SELECT id, owner FROM `tb_pet_owners` WHERE 1";
            cn = con.getConnection();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (cn != null) {
                System.out.println("Conexión éxitosa tb_pet_owners DB");
                while (rs.next()) {
                    owner = new Owners();
                    owner.setId_owner(rs.getInt("id"));
                    owner.setOwner(rs.getString("owner"));
                    list.add(owner);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, ".::Error en la conexión::.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public void ConsultarDueños(JComboBox Owners) {
        String sql = "SELECT owner from tb_pet_owners";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Owners.addItem(rs.getString("owner"));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el nombre del dueño para el ComboBox de JDialogPets" + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

    }

}
