/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ControllerCharts;
import Controller.Conexion;
import Model.ReportClients;
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
public class DialogClients extends javax.swing.JDialog {

    Conexion con = new Conexion();
    Connection cn;
    Statement st;
    ResultSet rs;
    DefaultTableModel modelo;
    private final ControllerCharts controllerGraficas;

    /**
     * Creates new form DialogClients
     */
    public DialogClients(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        controllerGraficas = new ControllerCharts();
        show_owners();
        RefreshReport();
    }

    void show_owners() {
        String sql = "SELECT * FROM `tb_pet_owners` WHERE 1";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            //Los datos que devuelve la consulta se muestran en la tabla
            Object[] owner = new Object[6];
            modelo = (DefaultTableModel) tbl_clientes.getModel();
            while (rs.next()) {
                owner[0] = rs.getInt("id");
                owner[1] = rs.getString("owner");
                owner[2] = rs.getString("document_type");
                owner[3] = rs.getInt("document");
                owner[4] = rs.getInt("contact");
                owner[5] = rs.getString("gender");
                modelo.addRow(owner);
                System.out.println(rs.getInt("id"));
            }
            tbl_clientes.setModel(modelo);
        } catch (SQLException e) {

        }
    }

    void add_owner() {
        String gender = "";
        String name = txt_nombreCliente.getText();
        String document_type = cb_tipoDocumento.getSelectedItem().toString();
        int document = Integer.parseInt(txt_numeroDocumento.getText());
        int contact = Integer.parseInt(txt_telefonoCliente.getText());

        if (rb_f.isSelected()) {
            gender = "Femenino";
        } else {
            gender = "Masculino";
        }

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta ingresar el nombre del dueño de la mascota");
        } else {
            String query = "INSERT INTO `tb_pet_owners`(`owner`, `document_type`, `document`, `contact`, `gender`) VALUES('" + name + "', '" + document_type + "'," + document + "," + contact + ", '" + gender + "')";

            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "El usuario ha sido creado");
                clear_rows_table();
                show_owners();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "No se pudo crear el usuario");
            }
        }
    }

    void edit_owner() {
        //Hacemos nuevamente lectura de los valores contenidos en los JTextField
        //Para identificar si el usuario modifico algún valor
        int id = Integer.parseInt(txt_idCliente.getText());
        String name = txt_nombreCliente.getText();
        String document_type = cb_tipoDocumento.getSelectedItem().toString();
        int document = Integer.parseInt(txt_numeroDocumento.getText());
        int contact = Integer.parseInt(txt_telefonoCliente.getText());
        String gender;
        if (rb_f.isSelected()) {
            gender = "Femenino";
        } else {
            gender = "Masculino";
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta ingresar el nombre del dueño");
        } else {
            String query = "UPDATE tb_pet_owners SET owner = '" + name + "', document_type= '" + document_type + "', document = " + document + ", contact=" + contact + ",gender= '" + gender + "' WHERE id = " + id;
            //UPDATE tb_persons SET dni =dni, nombre= 'name' WHERE id = id
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "El dueño ha sido modificado con éxito");
                clear_rows_table();
                show_owners();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "No se pudo modificar la información del dueño");
            }
        }
    }

    void delete_owner() {
        int fila = tbl_clientes.getSelectedRow();
        int id = Integer.parseInt(txt_idCliente.getText());
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "No has seleccionado un dueño de mascota");
        } else {

            System.out.println("ID: " + id);
            String query = "DELETE FROM tb_pet_owners WHERE id = " + id;
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "El dueño ha sido eliminado con exito");
                clear_rows_table();
                show_owners();
            } catch (HeadlessException | SQLException e) {
            }
        }
    }

    void clear_rows_table() {
        for (int i = 0; i < tbl_clientes.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
        txt_nombreCliente.setText("");
        txt_numeroDocumento.setText("");
        txt_telefonoCliente.setText("");
        txt_idCliente.setText("");
    }

     private void RefreshReport() {
        LinkedList<ReportClients> report = controllerGraficas.ListClients();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (ReportClients record: report) {
            dataset.setValue(record.getGender(), record.getAmount_gender());
        }
        JFreeChart chart = ChartFactory.createPieChart("Dueños por género", dataset, true, true, true);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_idCliente = new javax.swing.JTextField();
        txt_nombreCliente = new javax.swing.JTextField();
        cb_tipoDocumento = new javax.swing.JComboBox<>();
        txt_numeroDocumento = new javax.swing.JTextField();
        txt_telefonoCliente = new javax.swing.JTextField();
        rb_f = new javax.swing.JRadioButton();
        rb_m = new javax.swing.JRadioButton();
        btn_agregarCliente = new javax.swing.JToggleButton();
        btn_editarCliente = new javax.swing.JToggleButton();
        btn_eliminarCliente = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_clientes = new javax.swing.JTable();
        panelGrafica = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("FORMULARIO DE  REGISTRO DE DUEÑOS");

        jLabel2.setText("ID:");

        jLabel3.setText("Nombre:");

        jLabel4.setText("Tipo de documento:");

        jLabel5.setText("Número de documento:");

        jLabel6.setText("Teléfono:");

        jLabel7.setText("Género:");

        txt_idCliente.setEditable(false);

        cb_tipoDocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "C.C.", "Pasaporte", "C.E.", "T.I." }));

        buttonGroup1.add(rb_f);
        rb_f.setText("Femenino");

        buttonGroup1.add(rb_m);
        rb_m.setText("Masculino");

        btn_agregarCliente.setText("Agregar Cliente");
        btn_agregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarClienteActionPerformed(evt);
            }
        });

        btn_editarCliente.setText("Editar Cliente");
        btn_editarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarClienteActionPerformed(evt);
            }
        });

        btn_eliminarCliente.setText("Eliminar Cliente");
        btn_eliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(jLabel2))
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btn_agregarCliente)
                                .addGap(73, 73, 73)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rb_f)
                                        .addGap(29, 29, 29)
                                        .addComponent(rb_m))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_idCliente)
                                        .addComponent(txt_nombreCliente)
                                        .addComponent(cb_tipoDocumento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_numeroDocumento)
                                        .addComponent(txt_telefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(btn_editarCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                                .addComponent(btn_eliminarCliente)))))
                .addGap(65, 65, 65))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_idCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cb_tipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txt_numeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txt_telefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_f)
                        .addComponent(rb_m)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_editarCliente)
                    .addComponent(btn_agregarCliente)
                    .addComponent(btn_eliminarCliente))
                .addGap(0, 26, Short.MAX_VALUE))
        );

        tbl_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Tipo Doc.", "Número Doc.", "Teléfono", "Género"
            }
        ));
        tbl_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_clientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_clientes);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelGraficaLayout = new javax.swing.GroupLayout(panelGrafica);
        panelGrafica.setLayout(panelGraficaLayout);
        panelGraficaLayout.setHorizontalGroup(
            panelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 358, Short.MAX_VALUE)
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_agregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarClienteActionPerformed
        // TODO add your handling code here:
        add_owner();
        clear_rows_table();
        show_owners();
        RefreshReport();
    }//GEN-LAST:event_btn_agregarClienteActionPerformed

    private void btn_editarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarClienteActionPerformed
        // TODO add your handling code here:
        edit_owner();
        clear_rows_table();
        show_owners();
        RefreshReport();
    }//GEN-LAST:event_btn_editarClienteActionPerformed

    private void btn_eliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarClienteActionPerformed
        // TODO add your handling code here:
        delete_owner();
        clear_rows_table();
        show_owners();
        RefreshReport();
    }//GEN-LAST:event_btn_eliminarClienteActionPerformed

    private void tbl_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_clientesMouseClicked
        // TODO add your handling code here:
        int row = tbl_clientes.getSelectedRow();
        System.out.println(row);
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un dueño de mascota");
        } else {
            int idOwner = Integer.parseInt((String) tbl_clientes.getValueAt(row, 0).toString());
            String owner = (String) tbl_clientes.getValueAt(row, 1);
            String document_type = (String) tbl_clientes.getValueAt(row, 2);
            int document = Integer.parseInt((String) tbl_clientes.getValueAt(row, 3).toString());
            int contact = Integer.parseInt((String) tbl_clientes.getValueAt(row, 4).toString());
            String gender = (String) tbl_clientes.getValueAt(row, 5);
            System.out.println(idOwner + " - " + owner + " - " + document_type + " - " + document + " - " + contact + " - " + gender);
            txt_idCliente.setText("" + idOwner);
            txt_nombreCliente.setText(owner);
            cb_tipoDocumento.setSelectedItem(document_type);
            txt_numeroDocumento.setText("" + document);
            txt_telefonoCliente.setText("" + contact);
            if (gender.equals("Femenino")) {
                rb_f.setSelected(true);
            }
            if (gender.equals("Masculino")) {
                rb_m.setSelected(true);
            }
        }

    }//GEN-LAST:event_tbl_clientesMouseClicked

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
            java.util.logging.Logger.getLogger(DialogClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogClients dialog = new DialogClients(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cb_tipoDocumento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelGrafica;
    private javax.swing.JRadioButton rb_f;
    private javax.swing.JRadioButton rb_m;
    private javax.swing.JTable tbl_clientes;
    private javax.swing.JTextField txt_idCliente;
    private javax.swing.JTextField txt_nombreCliente;
    private javax.swing.JTextField txt_numeroDocumento;
    private javax.swing.JTextField txt_telefonoCliente;
    // End of variables declaration//GEN-END:variables
}
