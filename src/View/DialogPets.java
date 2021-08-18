/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ControllerCharts;
import Controller.ControllerOwners;
import Controller.Conexion;
import Controller.ControllerPets;
import Model.ReportPets;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Nicolas D'aleman
 */
public class DialogPets extends javax.swing.JDialog {

    Conexion con = new Conexion();
    Connection cn;
    Statement st;
    ResultSet rs;
    DefaultTableModel modelo;
    private final ControllerOwners controller;
    private final ControllerCharts controllerGraficas;
    private final ControllerPets controllerPets;

    /**
     * Creates new form DialogPets
     */
    public DialogPets(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        controller = new ControllerOwners(this);
        controllerGraficas = new ControllerCharts ();
        controllerPets = new ControllerPets(this);
        show_pets();
        RefreshReport();
        controllerPets.ConsultarDueños(cb_ownersPets);

    }

    void show_pets() {
        String sql = "SELECT * FROM tb_pet";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            Object[] pet = new Object[4];
            modelo = (DefaultTableModel) tbl_pets.getModel();
            while (rs.next()) {
                pet[0] = rs.getInt("id");
                pet[1] = rs.getString("name");
                pet[2] = rs.getString("pet_type");
                pet[3] = rs.getInt("id_owner_pet");
                modelo.addRow(pet);
                System.out.println(rs.getInt("id"));
            }
            tbl_pets.setModel(modelo);
        } catch (SQLException e) {

        }
    }

    void add_pet() {
        String name = txt_nombrePet.getText();
        int owner = cb_ownersPets.getSelectedIndex();
        String pet_type = cb_breedPet.getSelectedItem().toString();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta ingresar el nombre del departamento");
        } else {
            System.out.println(name + " - " + pet_type + " - " + 1);
            String query = "INSERT INTO `tb_pet`(name, `pet_type`,id_owner_pet) VALUES('" + name + "', '" + pet_type + "'," + owner + ")";

            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "La mascota ha sido creada con éxito");
                clear_rows_table();
                show_pets();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "No se pudo crear la mascota");
            }
        }
    }

    void edit_pet() {
        int id = Integer.parseInt(txt_idPet.getText());
        String name = txt_nombrePet.getText();
        int owner = cb_ownersPets.getSelectedIndex();
        String pet_type = cb_breedPet.getSelectedItem().toString();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta ingresar el nombre de la mascota");
        } else {
            String query = "UPDATE tb_pet SET name = '" + name + "', pet_type= '" + pet_type + "', id_owner_pet =" + owner + " WHERE id = " + id;
            //UPDATE tb_persons SET dni =dni, nombre= 'name' WHERE id = id
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "La información de la mascota ha sido modificada con éxito");
                clear_rows_table();
                show_pets();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "No se pudo modificar la información de la mascota");
            }
        }
    }

    void delete_pet() {
        int fila = tbl_pets.getSelectedRow();
        int id = Integer.parseInt(txt_idPet.getText());
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "No has seleccionado una mascotica");
        } else {

            System.out.println("ID: " + id);
            String query = "DELETE FROM tb_pet WHERE id = " + id;
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "La mascota ha sido eliminado con exito");
                clear_rows_table();
                show_pets();
            } catch (HeadlessException | SQLException e) {
            }
        }
    }

    void clear_rows_table() {
        for (int i = 0; i < tbl_pets.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
        txt_idPet.setText("");
        txt_nombrePet.setText("");
        cb_breedPet.setSelectedIndex(0);
        cb_ownersPets.setSelectedIndex(0);
    }

    private void RefreshReport() {
        LinkedList<ReportPets> report = controllerGraficas.ListPetType();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (ReportPets record: report) {
            dataset.setValue(record.getPet_type(), record.getAmount_pet_type());
        }
        JFreeChart chart = ChartFactory.createPieChart("Tipos de mascotas", dataset, true, true, true);
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panelGrafica.setLayout(new java.awt.BorderLayout());
        panelGrafica.add(panel, BorderLayout.CENTER);
        panelGrafica.validate();
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_nombrePet = new javax.swing.JTextField();
        cb_breedPet = new javax.swing.JComboBox<>();
        txt_idPet = new javax.swing.JTextField();
        btn_añadirMascota = new javax.swing.JToggleButton();
        btn_editarMascota = new javax.swing.JToggleButton();
        btn_eliminarMascota = new javax.swing.JToggleButton();
        cb_ownersPets = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_pets = new javax.swing.JTable();
        panelGrafica = new javax.swing.JPanel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("REGISTRO MASCOTAS");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Tipo:");

        jLabel4.setText("Dueño:");

        jLabel5.setText("Id:");

        cb_breedPet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gato", "Perro" }));

        txt_idPet.setEditable(false);

        btn_añadirMascota.setText("Añadir Mascota");
        btn_añadirMascota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_añadirMascotaActionPerformed(evt);
            }
        });

        btn_editarMascota.setText("Editar Información");
        btn_editarMascota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarMascotaActionPerformed(evt);
            }
        });

        btn_eliminarMascota.setText("Eliminar Mascota");
        btn_eliminarMascota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarMascotaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(324, 324, 324)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(65, 65, 65)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_nombrePet)
                    .addComponent(cb_breedPet, 0, 201, Short.MAX_VALUE)
                    .addComponent(txt_idPet)
                    .addComponent(cb_ownersPets, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(184, 184, 184))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(btn_añadirMascota)
                .addGap(91, 91, 91)
                .addComponent(btn_editarMascota)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(btn_eliminarMascota)
                .addGap(66, 66, 66))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_nombrePet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_breedPet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(cb_ownersPets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_idPet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_añadirMascota)
                    .addComponent(btn_editarMascota)
                    .addComponent(btn_eliminarMascota))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        tbl_pets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Tipo de mascota", "Dueño"
            }
        ));
        tbl_pets.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_petsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_pets);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelGraficaLayout = new javax.swing.GroupLayout(panelGrafica);
        panelGrafica.setLayout(panelGraficaLayout);
        panelGraficaLayout.setHorizontalGroup(
            panelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        panelGraficaLayout.setVerticalGroup(
            panelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_añadirMascotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_añadirMascotaActionPerformed
        // TODO add your handling code here:
        add_pet();
        clear_rows_table();
        show_pets();
        RefreshReport();
    }//GEN-LAST:event_btn_añadirMascotaActionPerformed

    private void btn_editarMascotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarMascotaActionPerformed
        // TODO add your handling code here:
        edit_pet();
        clear_rows_table();
        show_pets();
        RefreshReport();
    }//GEN-LAST:event_btn_editarMascotaActionPerformed

    private void btn_eliminarMascotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarMascotaActionPerformed
        // TODO add your handling code here:
        delete_pet();
        clear_rows_table();
        show_pets();
        RefreshReport();
    }//GEN-LAST:event_btn_eliminarMascotaActionPerformed

    private void tbl_petsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_petsMouseClicked
        // TODO add your handling code here:
        int row = tbl_pets.getSelectedRow();
        System.out.println(row);
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un dueño de mascota");
        } else {
            int idpet = Integer.parseInt((String) tbl_pets.getValueAt(row, 0).toString());
            String name = (String) tbl_pets.getValueAt(row, 1);
            String pet_type = (String) tbl_pets.getValueAt(row, 2);
            int id_owner = Integer.parseInt((String) tbl_pets.getValueAt(row, 3).toString());

            System.out.println(idpet + " - " + name + " - " + pet_type + " - " + id_owner);
            txt_idPet.setText("" + idpet);
            txt_nombrePet.setText(name);
            cb_ownersPets.setSelectedIndex(id_owner - 1);
            cb_breedPet.setSelectedItem(pet_type);

        }
    }//GEN-LAST:event_tbl_petsMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogPets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogPets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogPets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogPets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogPets dialog = new DialogPets(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btn_añadirMascota;
    private javax.swing.JToggleButton btn_editarMascota;
    private javax.swing.JToggleButton btn_eliminarMascota;
    private javax.swing.JComboBox<String> cb_breedPet;
    public javax.swing.JComboBox<Object> cb_ownersPets;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel panelGrafica;
    private javax.swing.JTable tbl_pets;
    private javax.swing.JTextField txt_idPet;
    private javax.swing.JTextField txt_nombrePet;
    // End of variables declaration//GEN-END:variables
}
