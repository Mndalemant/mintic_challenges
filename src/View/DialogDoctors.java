/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ControllerDoctor;
import Controller.Conexion;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nicolas D'aleman
 */
public class DialogDoctors extends javax.swing.JDialog {

    Conexion con = new Conexion();
    Connection cn;
    Statement st;
    ResultSet rs;
    DefaultTableModel modelo;
    private final ControllerDoctor controller;

    /**
     * Creates new form DialogDoctors
     */
    public DialogDoctors(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        controller = new ControllerDoctor(this);
        show_doctors();
        controller.ConsultarHospital(cb_hospitalDoctor);
    }

    void show_doctors() {
        String sql = "SELECT * FROM `tb_doctor` WHERE 1";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            //Los datos que devuelve la consulta se muestran en la tabla
            Object[] owner = new Object[6];
            modelo = (DefaultTableModel) tbl_doctors.getModel();
            while (rs.next()) {
                owner[0] = rs.getInt("id");
                owner[1] = rs.getString("doctor");
                owner[2] = rs.getString("document_type");
                owner[3] = rs.getInt("document");
                owner[4] = rs.getInt("id_hospital");
                modelo.addRow(owner);
                System.out.println(rs.getInt("id"));
            }
            tbl_doctors.setModel(modelo);
        } catch (SQLException e) {

        }
    }

    void add_doctor() {
        String name = txt_nombreDoctor.getText();
        String document_type = cb_tipoDocumentoDoctor.getSelectedItem().toString();
        int document = Integer.parseInt(txt_numeroDocumentoDoctor.getText());
        int hospital = cb_hospitalDoctor.getSelectedIndex()+1;

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta ingresar el nombre del doctor");
        } else {
            String query = "INSERT INTO `tb_doctor`(`doctor`, `document_type`, `document`, `id_hospital`) VALUES('" + name + "', '" + document_type + "'," + document + "," + hospital + ")";

            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "El doctor ha sido creado");
                clear_rows_table();
                show_doctors();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "No se pudo crear el doctor" + e);
            }
        }
    }

    void edit_doctor() {
        //Hacemos nuevamente lectura de los valores contenidos en los JTextField
        //Para identificar si el usuario modifico algún valor
        int id = Integer.parseInt(txt_idDoctor.getText());
        String name = txt_nombreDoctor.getText();
        String document_type = cb_tipoDocumentoDoctor.getSelectedItem().toString();
        int document = Integer.parseInt(txt_numeroDocumentoDoctor.getText());
        int hospital = cb_hospitalDoctor.getSelectedIndex()+1;
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta ingresar el nombre del doctor");
        } else {
            String query = "UPDATE tb_doctor SET doctor = '" + name + "', document_type= '" + document_type + "', id_hospital = " + hospital + " WHERE id = " + id;
            //UPDATE tb_persons SET dni =dni, nombre= 'name' WHERE id = id
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "El doctor ha sido modificado con éxito");
                clear_rows_table();
                show_doctors();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "No se pudo modificar la información del doctor");
            }
        }
    }

    void delete_doctor() {
        int fila = tbl_doctors.getSelectedRow();
        int id = Integer.parseInt(txt_idDoctor.getText());
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "No has seleccionado un doctor");
        } else {

            System.out.println("ID: " + id);
            String query = "DELETE FROM tb_doctor WHERE id = " + id;
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "El doctor ha sido eliminado con exito");
                clear_rows_table();
                show_doctors();
            } catch (HeadlessException | SQLException e) {
            }
        }
    }

    void clear_rows_table() {
        for (int i = 0; i < tbl_doctors.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
        txt_idDoctor.setText("");
        txt_nombreDoctor.setText("");
        txt_numeroDocumentoDoctor.setText("");
        cb_hospitalDoctor.setSelectedIndex(0);
        cb_tipoDocumentoDoctor.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_numeroDocumentoDoctor = new javax.swing.JTextField();
        cb_tipoDocumentoDoctor = new javax.swing.JComboBox<>();
        txt_nombreDoctor = new javax.swing.JTextField();
        txt_idDoctor = new javax.swing.JTextField();
        btn_agregarCliente = new javax.swing.JToggleButton();
        btn_editarCliente = new javax.swing.JToggleButton();
        btn_eliminarCliente = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        cb_hospitalDoctor = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_doctors = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("REGISTRO DE DOCTORES");

        jLabel2.setText("ID:");

        jLabel3.setText("Nombre:");

        jLabel4.setText("Tipo de documento:");

        jLabel5.setText("Número de documento:");

        cb_tipoDocumentoDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "C.C.", "Pasaporte", "C.E.", "T.I." }));

        txt_idDoctor.setEditable(false);

        btn_agregarCliente.setText("Agregar Doctor");
        btn_agregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarClienteActionPerformed(evt);
            }
        });

        btn_editarCliente.setText("Editar Doctor");
        btn_editarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarClienteActionPerformed(evt);
            }
        });

        btn_eliminarCliente.setText("Eliminar Doctor");
        btn_eliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarClienteActionPerformed(evt);
            }
        });

        jLabel8.setText("Hospital:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(155, 155, 155)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb_hospitalDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_idDoctor)
                                .addComponent(txt_nombreDoctor)
                                .addComponent(cb_tipoDocumentoDoctor, 0, 201, Short.MAX_VALUE)
                                .addComponent(txt_numeroDocumentoDoctor)))))
                .addGap(201, 201, 201))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(305, 305, 305)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(btn_agregarCliente)
                        .addGap(97, 97, 97)
                        .addComponent(btn_editarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(btn_eliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_idDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nombreDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cb_tipoDocumentoDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txt_numeroDocumentoDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(cb_hospitalDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_agregarCliente)
                    .addComponent(btn_editarCliente)
                    .addComponent(btn_eliminarCliente))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        tbl_doctors.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Tipo Doc.", "Número Doc.", "Hospital"
            }
        ));
        tbl_doctors.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_doctorsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_doctors);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btn_agregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarClienteActionPerformed
        // TODO add your handling code here:
        add_doctor();
        clear_rows_table();
        show_doctors();
    }//GEN-LAST:event_btn_agregarClienteActionPerformed

    private void btn_editarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarClienteActionPerformed
        // TODO add your handling code here:
        edit_doctor();
        clear_rows_table();
        show_doctors();
    }//GEN-LAST:event_btn_editarClienteActionPerformed

    private void btn_eliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarClienteActionPerformed
        // TODO add your handling code here:
        delete_doctor();
        clear_rows_table();
        show_doctors();
    }//GEN-LAST:event_btn_eliminarClienteActionPerformed

    private void tbl_doctorsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_doctorsMouseClicked
        // TODO add your handling code here:
        int row = tbl_doctors.getSelectedRow();
        System.out.println(row);
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un dueño de mascota");
        } else {
            int idDoctor = Integer.parseInt((String) tbl_doctors.getValueAt(row, 0).toString());
            String name = (String) tbl_doctors.getValueAt(row, 1);
            String type_document = (String) tbl_doctors.getValueAt(row, 2);
            int document = Integer.parseInt((String) tbl_doctors.getValueAt(row, 3).toString());
            int id_hospital = Integer.parseInt((String) tbl_doctors.getValueAt(row, 4).toString());

            System.out.println(idDoctor + " - " + name + " - " + type_document + " - " + document + " - " + id_hospital);
            txt_idDoctor.setText("" + idDoctor);
            txt_nombreDoctor.setText(name);
            cb_tipoDocumentoDoctor.setSelectedItem(type_document);
            txt_numeroDocumentoDoctor.setText("" + document);
            cb_hospitalDoctor.setSelectedIndex(id_hospital - 1);
        }

    }//GEN-LAST:event_tbl_doctorsMouseClicked

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
            java.util.logging.Logger.getLogger(DialogDoctors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogDoctors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogDoctors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogDoctors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogDoctors dialog = new DialogDoctors(new javax.swing.JFrame(), true);
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
    private javax.swing.JToggleButton btn_agregarCliente;
    private javax.swing.JToggleButton btn_editarCliente;
    private javax.swing.JToggleButton btn_eliminarCliente;
    private javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JComboBox<Object> cb_hospitalDoctor;
    private javax.swing.JComboBox<String> cb_tipoDocumentoDoctor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_doctors;
    private javax.swing.JTextField txt_idDoctor;
    private javax.swing.JTextField txt_nombreDoctor;
    private javax.swing.JTextField txt_numeroDocumentoDoctor;
    // End of variables declaration//GEN-END:variables
}
