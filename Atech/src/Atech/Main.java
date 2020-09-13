/**    Atech
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
 * Main.java
 *
 * Created on March 28, 2008, 3:37 PM
 */
package Atech;

//import java.awt.FlowLayout;
import LibAtech.CentredBackgroundBorder;
import LibAtech.InformationUpdate;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Date;
import java.util.TimeZone;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Main GUI of Atech Electronic Trading System.
 * @author  l
 * @version 0.1.2
 */
public class Main extends javax.swing.JFrame {

    /** Creates new form Main */
    public Main() {
        initComponents();
        initMenu();
        initInternalFrame();
        initDatabase();
        refreshTime();
        refreshDatabase();
    }

    private void initMenu() {
        jdp.add(jifMenu, new Integer(3));
        jifMenu.setBounds(10, 15, 1, 1);
        jifMenu.pack();
        jifMenu.setFrameIcon(new ImageIcon(getClass().getResource("/LibAtech/AtechIcon.png")));

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle sr = ge.getMaximumWindowBounds();
        this.setSize((int) sr.getWidth(), (int) sr.getHeight());

        try {
            URL url = this.getClass().getResource("/LibAtech/AtechLogo.png");
            BufferedImage image = ImageIO.read(url);
            jdp.setBorder(new CentredBackgroundBorder(image));
        } catch (Exception e) {
            exceptionHandler(e);
        }
    }

    private void initInternalFrame() {
        Integer layer = new Integer(4);
        int x,y,i;
        x=150;y=15;i=27;
        
        jdp.add(jifSales, layer);
        jifSales.setBounds(x, y, 1, 1);
        jifSales.pack();
        jifSales.setFrameIcon(new ImageIcon(getClass().getResource("/LibAtech/AtechIcon.png")));
        
        jdp.add(jifStock, layer);
        jifStock.setBounds(x+=i, y+=i, 1, 1);
        jifStock.pack();
        jifStock.setFrameIcon(new ImageIcon(getClass().getResource("/LibAtech/AtechIcon.png")));

        jdp.add(jifSupplier, layer);
        jifSupplier.setBounds(x+=i, y+=i, 1, 1);
        jifSupplier.pack();
        jifSupplier.setFrameIcon(new ImageIcon(getClass().getResource("/LibAtech/AtechIcon.png")));

        jdp.add(jifPurchaseOrder, layer);
        jifPurchaseOrder.setBounds(x+=i, y+=i, 1, 1);
        jifPurchaseOrder.pack();
        jifPurchaseOrder.setFrameIcon(new ImageIcon(getClass().getResource("/LibAtech/AtechIcon.png")));

        jdp.add(jifOrder, layer);
        jifOrder.setBounds(x+=i, y+=i, 1, 1);
        jifOrder.pack();
        jifOrder.setFrameIcon(new ImageIcon(getClass().getResource("/LibAtech/AtechIcon.png")));

        jdp.add(jifReport, layer);
        jifReport.setBounds(x+=i,y+=i, 1, 1);
        jifReport.pack();
        jifReport.setFrameIcon(new ImageIcon(getClass().getResource("/LibAtech/AtechIcon.png")));
        
        jdp.add(jifAbout, layer);
        jifAbout.pack();
        jifAbout.setBounds((this.getWidth() - jifAbout.getWidth()) / 2, (this.getHeight() - jifAbout.getHeight() - 150 ) / 2, 1, 1);
        jifAbout.pack();
        jifAbout.setFrameIcon(new ImageIcon(getClass().getResource("/LibAtech/AtechIcon.png")));
    }

    private void refreshTime() {
        Timer timer = new Timer(0, new updateTime());
        timer.start();
        timer.setDelay(1000);
    }

    private class updateTime implements ActionListener {
        TimeZone t = TimeZone.getTimeZone("GMT+8");
        java.text.DateFormat sdf = new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss a");
        
        public void actionPerformed(ActionEvent e) {
            sdf.setCalendar(new java.util.GregorianCalendar(t));
            jlblTime.setText(sdf.format(new Date()));
        }
    }

    private void initDatabase() {
        jdp.add(jifDatabase, new Integer(6));
        jifDatabase.pack();
        jifDatabase.setBounds((this.getWidth() - jifDatabase.getWidth()) / 2, (this.getHeight() - jifDatabase.getHeight() - 150 ) / 2, 1, 1);
        jifDatabase.pack();
        jifDatabase.setFrameIcon(new ImageIcon(getClass().getResource("/LibAtech/AtechIcon.png")));
    }

    private void refreshDatabase() {
        databaseTimer = new Timer(9000, new checkDatabase());
        databaseTimer.setInitialDelay(0);
        databaseTimer.start();
    }
    
    private Timer databaseTimer;

    private void dissconnectedDBHandler() {
        try {
            jlblDatabase.setText("Database Disconnected.");
            jtaDatabaseStatus.append("Database Disconnected.\n");
            jifDatabase.setVisible(true);
            jifDatabase.setSelected(true);
            jtfPassword.grabFocus();
            
            databaseTimer.stop();
        } catch (Exception e) {
            exceptionHandler(e);
        }
    }

    private class checkDatabase implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if (atechDB == null) {
                    dissconnectedDBHandler();
                } else if (atechDB.isConnected()) {
                    jlblDatabase.setText(atechDB.getDatabaseStatus());
                } else {
                    dissconnectedDBHandler();
                }
            } catch (Exception ex) {
                exceptionHandler(ex);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jifMenu = new javax.swing.JInternalFrame();
        jbReport = new javax.swing.JButton();
        jbSales = new javax.swing.JButton();
        jbStock = new javax.swing.JButton();
        jbSupplier = new javax.swing.JButton();
        jbPurchase = new javax.swing.JButton();
        jbExit = new javax.swing.JButton();
        jbOrder = new javax.swing.JButton();
        jifDatabase = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaDatabaseStatus = new javax.swing.JTextArea();
        jpDatabase = new javax.swing.JPanel();
        jbtConnect = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtfUserName = new javax.swing.JTextField();
        jtfPassword = new javax.swing.JPasswordField();
        jtfUrl = new javax.swing.JTextField();
        jdp = new javax.swing.JDesktopPane();
        jlblDatabase = new javax.swing.JLabel();
        jlblTime = new javax.swing.JLabel();
        jmBar = new javax.swing.JMenuBar();
        jmFile = new javax.swing.JMenu();
        jmExit = new javax.swing.JMenuItem();
        jmView = new javax.swing.JMenu();
        jmMinimize = new javax.swing.JMenuItem();
        jmRestore = new javax.swing.JMenuItem();
        jmHelp = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jifMenu.setResizable(true);
        jifMenu.setTitle(" Menu");
        jifMenu.setEnabled(false);
        jifMenu.setFocusCycleRoot(false);
        jifMenu.setFocusable(false);
        jifMenu.setVerifyInputWhenFocusTarget(false);
        jifMenu.setVisible(true);

        jbReport.setMnemonic('r');
        jbReport.setText("Report");
        jbReport.setFocusPainted(false);
        jbReport.setFocusable(false);
        jbReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbReportActionPerformed(evt);
            }
        });

        jbSales.setMnemonic('s');
        jbSales.setText("Sales");
        jbSales.setToolTipText("Show Sales Page");
        jbSales.setFocusPainted(false);
        jbSales.setFocusable(false);
        jbSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalesActionPerformed(evt);
            }
        });

        jbStock.setMnemonic('t');
        jbStock.setText("Stock");
        jbStock.setToolTipText("Show Stock Editor");
        jbStock.setFocusPainted(false);
        jbStock.setFocusable(false);
        jbStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStockActionPerformed(evt);
            }
        });

        jbSupplier.setMnemonic('u');
        jbSupplier.setText("Supplier");
        jbSupplier.setToolTipText("Show Supplier Page");
        jbSupplier.setFocusPainted(false);
        jbSupplier.setFocusable(false);
        jbSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSupplierActionPerformed(evt);
            }
        });

        jbPurchase.setMnemonic('p');
        jbPurchase.setText("Purchase");
        jbPurchase.setToolTipText("Show Purchase Page");
        jbPurchase.setFocusPainted(false);
        jbPurchase.setFocusable(false);
        jbPurchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPurchaseActionPerformed(evt);
            }
        });

        jbExit.setMnemonic('x');
        jbExit.setText("Exit");
        jbExit.setFocusPainted(false);
        jbExit.setFocusable(false);
        jbExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExitActionPerformed(evt);
            }
        });

        jbOrder.setMnemonic('o');
        jbOrder.setText("Order & Invoice");
        jbOrder.setToolTipText("Show Ordered Page");
        jbOrder.setFocusPainted(false);
        jbOrder.setFocusable(false);
        jbOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jifMenuLayout = new javax.swing.GroupLayout(jifMenu.getContentPane());
        jifMenu.getContentPane().setLayout(jifMenuLayout);
        jifMenuLayout.setHorizontalGroup(
            jifMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifMenuLayout.createSequentialGroup()
                .addGroup(jifMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jifMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbStock, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                    .addGroup(jifMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbPurchase, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                    .addGroup(jifMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbSales, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                    .addGroup(jifMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jifMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbReport, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbExit, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jifMenuLayout.setVerticalGroup(
            jifMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbSales, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbStock, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbPurchase, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbReport, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbExit, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                .addContainerGap())
        );

        jifDatabase.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jifDatabase.setIconifiable(true);
        jifDatabase.setResizable(true);
        jifDatabase.setTitle(" Database Connection Manager");
        jifDatabase.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        jtaDatabaseStatus.setColumns(20);
        jtaDatabaseStatus.setRows(5);
        jScrollPane1.setViewportView(jtaDatabaseStatus);

        jpDatabase.setBorder(javax.swing.BorderFactory.createTitledBorder("Atech Database"));
        jpDatabase.setToolTipText("For Advance Database Connection Setting");

        jbtConnect.setMnemonic('C');
        jbtConnect.setText("Connect to Database");
        jbtConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtConnectActionPerformed(evt);
            }
        });

        jLabel7.setText("Database URL");

        jLabel8.setText("Username");

        jLabel9.setText("Password");

        jtfUserName.setText("root");
        jtfUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfUserNameActionPerformed(evt);
            }
        });

        jtfPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfPasswordActionPerformed(evt);
            }
        });

        jtfUrl.setText("localhost");
        jtfUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfUrlActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpDatabaseLayout = new javax.swing.GroupLayout(jpDatabase);
        jpDatabase.setLayout(jpDatabaseLayout);
        jpDatabaseLayout.setHorizontalGroup(
            jpDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatabaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpDatabaseLayout.createSequentialGroup()
                        .addGroup(jpDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                            .addComponent(jtfPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                            .addComponent(jtfUrl, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)))
                    .addGroup(jpDatabaseLayout.createSequentialGroup()
                        .addComponent(jbtConnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addContainerGap())
        );
        jpDatabaseLayout.setVerticalGroup(
            jpDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatabaseLayout.createSequentialGroup()
                .addGroup(jpDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfUrl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtfUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtConnect)
                .addContainerGap())
        );

        javax.swing.GroupLayout jifDatabaseLayout = new javax.swing.GroupLayout(jifDatabase.getContentPane());
        jifDatabase.getContentPane().setLayout(jifDatabaseLayout);
        jifDatabaseLayout.setHorizontalGroup(
            jifDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jifDatabaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jifDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpDatabase, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                .addContainerGap())
        );
        jifDatabaseLayout.setVerticalGroup(
            jifDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jifDatabaseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Atech System");
        setMinimumSize(new java.awt.Dimension(400, 400));

        jdp.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jdp.setDragMode(javax.swing.JDesktopPane.OUTLINE_DRAG_MODE);

        jlblDatabase.setText("Database");

        jlblTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlblTime.setText("Time");

        jmFile.setText("File");

        jmExit.setText("Exit");
        jmExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmExitActionPerformed(evt);
            }
        });
        jmFile.add(jmExit);

        jmBar.add(jmFile);

        jmView.setText("View");

        jmMinimize.setText("Minimize All Windows");
        jmMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmMinimizeActionPerformed(evt);
            }
        });
        jmView.add(jmMinimize);

        jmRestore.setText("Restore All Windows");
        jmRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmRestoreActionPerformed(evt);
            }
        });
        jmView.add(jmRestore);

        jmBar.add(jmView);

        jmHelp.setText("About");
        jmHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmHelpActionPerformed(evt);
            }
        });

        jMenuItem1.setText("About Atech");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jmHelp.add(jMenuItem1);

        jmBar.add(jmHelp);

        setJMenuBar(jmBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jlblDatabase, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jdp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jdp, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jbSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalesActionPerformed
    this.setInternalFrameInfront(jifSales);
}//GEN-LAST:event_jbSalesActionPerformed

private void jbPurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPurchaseActionPerformed
    this.setInternalFrameInfront(jifPurchaseOrder);
}//GEN-LAST:event_jbPurchaseActionPerformed

private void jbReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbReportActionPerformed
    this.setInternalFrameInfront(jifReport);
}//GEN-LAST:event_jbReportActionPerformed

private void jbSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSupplierActionPerformed
    this.setInternalFrameInfront(jifSupplier);
}//GEN-LAST:event_jbSupplierActionPerformed

private void jbStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbStockActionPerformed
    this.setInternalFrameInfront(jifStock);
}//GEN-LAST:event_jbStockActionPerformed

private void jmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmExitActionPerformed
    try{
        this.atechDB.setDisconnectMysql();
        System.exit(0);
    
    } catch (Exception e) {
       System.exit(0);
       exceptionHandler(e);
    }
    System.exit(0);
}//GEN-LAST:event_jmExitActionPerformed

private void jbExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExitActionPerformed
    try {
        if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "Do you really want to exit?","Exit Comfirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)) {
            this.atechDB.setDisconnectMysql();
            System.exit(0);
        }
    } catch (Exception e) {
        System.exit(0);
        exceptionHandler(e);
    }
}//GEN-LAST:event_jbExitActionPerformed

private void jmMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMinimizeActionPerformed
    try {
        jifDatabase.setIcon(true);
        jifSales.setIcon(true);
        jifStock.setIcon(true);
        jifSupplier.setIcon(true);
        jifPurchaseOrder.setIcon(true);
        jifReport.setIcon(true);
        jifOrder.setIcon(true);
       
    } catch (Exception e) {
        exceptionHandler(e);
    }
}//GEN-LAST:event_jmMinimizeActionPerformed

private void jmRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRestoreActionPerformed
    try {
        jifSales.setIcon(false);
        jifStock.setIcon(false);
        jifSupplier.setIcon(false);
        jifPurchaseOrder.setIcon(false);
        jifReport.setIcon(false);
        jifDatabase.setIcon(false);
        jifOrder.setIcon(false);
    } catch (Exception e) {
        exceptionHandler(e);
    }
}//GEN-LAST:event_jmRestoreActionPerformed

private void jbtConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConnectActionPerformed
// TODO add your handling code here:
    connect();
}//GEN-LAST:event_jbtConnectActionPerformed
private void connect(){
        try {
        atechDB.setConnectMysql(jtfUrl.getText(), jtfUserName.getText(), String.valueOf(jtfPassword.getPassword()));
        jtaDatabaseStatus.append(atechDB.getDatabaseStatus()+"\n");
        jifDatabase.setVisible(false);
        databaseTimer.start();
        jifMenu.setSelected(true);
    } catch (Exception e) {
        jtaDatabaseStatus.append(e.getMessage() + "\n");
        exceptionHandler(e);
    }
}
private void jbOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbOrderActionPerformed
    this.setInternalFrameInfront(jifOrder);
}//GEN-LAST:event_jbOrderActionPerformed

private void jtfPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfPasswordActionPerformed
connect();
}//GEN-LAST:event_jtfPasswordActionPerformed

private void jtfUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfUserNameActionPerformed
connect();
}//GEN-LAST:event_jtfUserNameActionPerformed

private void jtfUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfUrlActionPerformed
connect();
}//GEN-LAST:event_jtfUrlActionPerformed

private void jmHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmHelpActionPerformed
   
}//GEN-LAST:event_jmHelpActionPerformed

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
 jifAbout.setVisible(true);
}//GEN-LAST:event_jMenuItem1ActionPerformed
private  void setInternalFrameInfront(JInternalFrame jif) {
        try {
            jif.setSelected(true);

            if (!(jif.isVisible())) {
                jif.setVisible(true);
            }
        } catch (Exception e) {
            exceptionHandler(e);
        }
    }
    /**
     * Standardized the exception handler by calling this method.
     * @param e Exception Event
     */
    public void exceptionHandler(Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbExit;
    private javax.swing.JButton jbOrder;
    private javax.swing.JButton jbPurchase;
    private javax.swing.JButton jbReport;
    private javax.swing.JButton jbSales;
    private javax.swing.JButton jbStock;
    private javax.swing.JButton jbSupplier;
    private javax.swing.JButton jbtConnect;
    public javax.swing.JDesktopPane jdp;
    private javax.swing.JInternalFrame jifDatabase;
    private javax.swing.JInternalFrame jifMenu;
    public javax.swing.JLabel jlblDatabase;
    private javax.swing.JLabel jlblTime;
    private javax.swing.JMenuBar jmBar;
    private javax.swing.JMenuItem jmExit;
    private javax.swing.JMenu jmFile;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenuItem jmMinimize;
    private javax.swing.JMenuItem jmRestore;
    private javax.swing.JMenu jmView;
    private javax.swing.JPanel jpDatabase;
    private javax.swing.JTextArea jtaDatabaseStatus;
    private javax.swing.JPasswordField jtfPassword;
    private javax.swing.JTextField jtfUrl;
    private javax.swing.JTextField jtfUserName;
    // End of variables declaration//GEN-END:variables
    
    // Self Declaration
    protected InformationUpdate atechDB = new InformationUpdate();
    private JInternalFrame jifSales= new Sales(atechDB,jdp);
    private JInternalFrame jifPurchaseOrder = new PurchaseOrder(atechDB,jdp);
    private JInternalFrame jifStock = new Stock(atechDB,this);
    private JInternalFrame jifReport = new Report(atechDB,this);
    private JInternalFrame jifSupplier = new Supplier(atechDB,this);
    private JInternalFrame jifOrder = new OrderInvoice(atechDB,this);
    private JInternalFrame jifAbout = new About();
    //End og Self Declaration
}

