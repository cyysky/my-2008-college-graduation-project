/*    Atech
 *    Copyright (C) 2008  l & k
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Supplier.java
 *
 * Created on April 2, 2008, 2:56 PM
 */

package Atech;

import LibAtech.InformationUpdate;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  l
 */
public class Supplier extends javax.swing.JInternalFrame {
    
    InformationUpdate atechDB;
    Main atechMain;
    
    ResultSet rs;
    ResultSetMetaData rsm;
    
    Vector rowVector = new Vector();
    Vector columnHeaderVector = new Vector();
    Vector insertVector = new Vector();
    
    int selectedRow = 0;
    int selectedColumn = 0;
    
    DefaultTableModel dtm = new DefaultTableModel();
    TableModelListener h = new TableModelListener() {

        public void tableChanged(TableModelEvent ev) {
            try {
                int lastRow = jtSupplier.getRowCount() - 1;
                int selectedRow = jtSupplier.getSelectedRow();
                
                if (selectedRow != lastRow) {
                    
                    rs.absolute(selectedRow + 1);
                    for (int i = 1; i <= rsm.getColumnCount(); i++) {
                        rs.updateObject(i, jtSupplier.getValueAt(selectedRow, i - 1));
                    }
                    rs.updateRow();
                    refreshTable();
                } 
                //Primary key cannot be null.So check the primay key equal to null or !
                else if (jtSupplier.getSelectedRow() == lastRow) {
                    
                    int primaryKey1 = -1;
                    
                    
                    for (int col = 1; col <= rsm.getColumnCount(); col++) {
                       
                        if (rsm.getColumnName(col).equalsIgnoreCase("SupplierNo")) {
                            primaryKey1 = col;
                        }
                    }
                    if (primaryKey1 == -1 ) {
                        System.out.println("Problem");
                        return;                        
                    }
                    if(     jtSupplier.getValueAt(selectedRow, primaryKey1-1)!=null ){
                        
                        rs.moveToInsertRow();
                        
                        for (int i = 1; i <= rsm.getColumnCount(); i++) {
                            rs.updateObject(i, dtm.getValueAt(dtm.getRowCount() - 1, i - 1));
                        }
                        
                        rs.insertRow();
                        
                        insertVector.clear();
                        for (int i = 1; i <= rsm.getColumnCount(); i++) {
                            insertVector.addElement(null);
                        }
                        refreshTable();
                    }
                }
                           
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(atechMain, ex.getMessage(),"Error In Updating",JOptionPane.WARNING_MESSAGE);
                refreshTable();
            }
        }
    };
    
    ListSelectionModel rowSM, colSM;
    ListSelectionListener l = new ListSelectionListener() {

        public void valueChanged(ListSelectionEvent e) {
            try {
                //Ignore extra message
                if (e.getValueIsAdjusting()) {
                    return;
                }
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();

                if (lsm.isSelectionEmpty()) {
                    //System.out.println("No columns are selected.");
                } else {
                    selectedRow = jtSupplier.getSelectedRow();
                    selectedColumn = jtSupplier.getSelectedColumn();
                    jlblSearchColumn.setText("Search " + dtm.getColumnName(jtSupplier.getSelectedColumn()));
                    if(selectedRow == jtSupplier.getRowCount()-1){
            jbDelete.setEnabled(false);
        }else{
            jbDelete.setEnabled(true);
        }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }       
        
    };
    /** Creates new form Supplier */
    public Supplier(InformationUpdate atechDB, Main atechMain) {
        this.atechDB = atechDB;
        this.atechMain = atechMain;
        initComponents();

        rowSM = jtSupplier.getSelectionModel();
        rowSM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        colSM = jtSupplier.getColumnModel().getSelectionModel();
        colSM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jtSupplier.setModel(this.dtm);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane6 = new javax.swing.JScrollPane();
        jtSupplier = new javax.swing.JTable();
        jlblSearchColumn = new javax.swing.JLabel();
        jtfSearch = new javax.swing.JTextField();
        jbDelete = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Supplier Detail Table");
        setDoubleBuffered(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jtSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtSupplier.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtSupplier.setShowHorizontalLines(false);
        jScrollPane6.setViewportView(jtSupplier);

        jlblSearchColumn.setText("Search Column");

        jtfSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfSearchActionPerformed(evt);
            }
        });

        jbDelete.setText("Delete Row");
        jbDelete.setEnabled(false);
        jbDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jlblSearchColumn)
                        .addGap(28, 28, 28)
                        .addComponent(jtfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(jbDelete)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbDelete)
                    .addComponent(jlblSearchColumn)
                    .addComponent(jtfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfSearchActionPerformed
search();
    
}//GEN-LAST:event_jtfSearchActionPerformed

private void jbDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDeleteActionPerformed
delete();
}//GEN-LAST:event_jbDeleteActionPerformed

private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
    refreshTable();
}//GEN-LAST:event_formInternalFrameActivated

private void delete(){
    try {
        if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(atechMain, "Are you really want to delete selected item ?", "Delete Comfirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
            rs.absolute(jtSupplier.getSelectedRow() + 1);
            rs.deleteRow();
            refreshTable();
        }
    } catch (Exception ex) {
        refreshTable();
    }
}

private void search(){
        for(int i=0;i<dtm.getRowCount();i++){
        if(String.valueOf(jtSupplier.getValueAt(i,selectedColumn)).equalsIgnoreCase(jtfSearch.getText())){
            selectedRow = i;
            rowSM.setSelectionInterval(selectedRow, selectedRow);
            colSM.setSelectionInterval(selectedColumn, selectedColumn);
        }
    }
    try{
        String columnName = dtm.getColumnName(selectedColumn);
        ResultSet tempRS = atechDB.dbStatement.executeQuery("SELECT * FROM Supplier WHERE "+columnName+" LIKE '%"+jtfSearch.getText()+"%'");
        JInternalFrame jif = new DisplayQuery(tempRS,"Search " +columnName+" result");
        
        atechMain.jdp.add(jif, new Integer(4));
        jif.pack();
        int x =(atechMain.jdp.getWidth()-jif.getWidth())/2;
        int y = (atechMain.jdp.getHeight()-jif.getHeight())/2;
        jif.setBounds(x,y,1, 1);
        jif.pack();
        jif.show();
    }catch(SQLException ex){
        
    }
}

private void refreshTable() {
    
    dtm.removeTableModelListener(h);
    rowSM.removeListSelectionListener(l);
    colSM.removeListSelectionListener(l);
    
    jbDelete.setEnabled(false);
    
    try {
        rowVector.clear();
        columnHeaderVector.clear();
        
        rs = atechDB.dbStatement.executeQuery("SELECT * FROM Supplier;");
        rsm = rs.getMetaData();
        rs.moveToCurrentRow();
        
        while (rs.next()) {
            Vector singleRow = new Vector();
            for (int x = 0; x < rsm.getColumnCount(); x++) {
                singleRow.addElement(rs.getObject(x + 1));
            }
            rowVector.addElement(singleRow);
        }
        
        for (int c = 1; c <= rsm.getColumnCount(); c++) {
            columnHeaderVector.addElement(rsm.getColumnName(c));
        }
        
        this.dtm.setDataVector(rowVector, columnHeaderVector);
        
        if (jtSupplier.getRowCount()==0 && jtSupplier.getColumnCount()==0) {
            selectedRow =0;
            selectedColumn =0;
            insertVector.clear();
            for (int i = 1; i <= rsm.getColumnCount(); i++) {
                insertVector.addElement(null);
            }
        }
        dtm.addRow(insertVector);        
    } catch (Exception e) {
        e.printStackTrace();
    }
    rowSM.setSelectionInterval(selectedRow, selectedRow);
    colSM.setSelectionInterval(selectedColumn, selectedColumn);
    jtSupplier.repaint();
    
    jlblSearchColumn.setText("Search "+dtm.getColumnName(selectedColumn));
    jbDelete.setEnabled(true);
    
    dtm.addTableModelListener(h);
    rowSM.addListSelectionListener(l);
    colSM.addListSelectionListener(l);
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton jbDelete;
    private javax.swing.JLabel jlblSearchColumn;
    private javax.swing.JTable jtSupplier;
    private javax.swing.JTextField jtfSearch;
    // End of variables declaration//GEN-END:variables

}
