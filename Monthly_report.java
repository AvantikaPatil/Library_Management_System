/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Slibrary;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author parth
 */
public class Monthly_report extends javax.swing.JFrame {

    /**
     * Creates new form Monthly_report
     */
    public Monthly_report() {
        initComponents();
        Connect();
        book();
//        Author();
//        Publisher();
//        Member();
    }


//    public class BookItem{
//        public int id;
//        String name;
//        
//        public BookItem(int id, String name){
//            this.id = id;
//            this.name = name;
//        }
//        
//        public String strname(){
//            return name;
//            
//        }
//    }
//    
//    public class AuthorItem{
//        public int id;
//        String name;
//        
//        
//        public AuthorItem(int id, String name){
//            this.id = id;
//            this.name = name;
//        }
//        
//        public String strname(){
//            return name;
//        }
//    }
//
//    
//    public class PublisherItem{
//        public int id;
//        String name;
//        
//        
//        public PublisherItem(int id, String name){
//            this.id = id;
//            this.name = name;
//        }
//        
//        public String strname(){
//            return name;
//        }
//    }
//     
//
//    public class MemberItem{
//        public int id;
//        String name;
//        
//        
//        public MemberItem(int id, String name){
//            this.id = id;
//            this.name = name;
//        }
//        
//        public String strname(){
//            return name;
//        }
//    }

    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    
    public void Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/slibrary", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Monthly_report.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }


    public void book(){
        try {
            pst = (PreparedStatement) con.prepareStatement("Select * from book");
            rs = pst.executeQuery();
            jCombo_book.removeAllItems();
//            String nm;
                       
            while(rs.next()){
//                nm=.trim();
               jCombo_book.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Monthly_report.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
//    public void Author(){
//        try {
//            pst = (PreparedStatement) con.prepareStatement("Select * from author");
//            rs = pst.executeQuery();
//            jCombo_choose.removeAllItems();
//            
//            while(rs.next()){
//                jCombo_choose.addItem(rs.getString(2));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Monthly_report.class.getName()).log(Level.SEVERE, null, ex);
//        }    
//    }
//    
//       
//    public void Publisher(){
//        try {
//            pst = (PreparedStatement) con.prepareStatement("Select * from publisher");
//            rs = pst.executeQuery();
//            jCombo_choose.removeAllItems();
//            
//            while(rs.next()){
//                jCombo_choose.addItem(rs.getString(2));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Monthly_report.class.getName()).log(Level.SEVERE, null, ex);
//        }       
//    }
//
//    
//    public void Member(){
//        try {
//            pst = (PreparedStatement) con.prepareStatement("Select * from member");
//            rs = pst.executeQuery();
//            jCombo_choose.removeAllItems();
//            
//            while(rs.next()){
//                jCombo_choose.addItem(rs.getString(2));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Monthly_report.class.getName()).log(Level.SEVERE, null, ex);
//        }       
//    }

    
    // fine()
    public void fine() {
        try {
            // Get the selected month from the combo box
            String selectedMonth = (String) jCombo_Month.getSelectedItem();  
            
            // Create PreparedStatement
            pst = (PreparedStatement) con.prepareStatement("SELECT SUM(fine) AS total_fine FROM returnbook WHERE MONTHNAME(returndate) = ? ");
            
            // Set the parameter value (month) based on the selected month name
            pst.setString(1, selectedMonth);

            // Execute the query
            rs = pst.executeQuery();

            // Display the result in the text area
            if (rs.next()) {
                int totalFine = rs.getInt("total_fine");
                t_fine.setText("" + totalFine);
            } else {
                t_fine.setText("0");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    // b_name()
    public void b_name() {
        try {
            // Create PreparedStatement
            pst = (PreparedStatement) con.prepareStatement("SELECT COUNT(bname) AS book_avail from book");
            
            // Execute the query
            rs = pst.executeQuery();

            // Display the result in the text area
            if (rs.next()) {
                int Book_avail = rs.getInt("book_avail");
                b_avail.setText("" + Book_avail);
            } else {
                b_avail.setText("0");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    // I_book()
    public void I_book() {
        try {
            // Get the selected month from the combo box
            String selectedMonth = (String) jCombo_Month.getSelectedItem();  

            // Create PreparedStatement
            pst = (PreparedStatement) con.prepareStatement("SELECT COUNT(memberid) AS issued_book_count from issued_book WHERE MONTHNAME(issuedate) = ?");
            
            // Set the parameter value (month) based on the selected month name
            pst.setString(1, selectedMonth);

            // Execute the query
            rs = pst.executeQuery();

            // Display the result in the text area
            if (rs.next()) {
                int issued_book_count= rs.getInt("issued_book_count");
                I_book.setText("" + issued_book_count);
            } else {
                I_book.setText("0");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void Report_Load(){
        int c;
        try {
            String selectedMonth = (String) jCombo_Month.getSelectedItem(); 
//            String choose = (String) jCombo_choose.getSelectedItem();
            String SelectedBook = (String) jCombo_book.getSelectedItem();
              
            pst = (PreparedStatement) con.prepareStatement("SELECT ib.memberid, m.name, b.bname, a.name, p.name FROM issued_book ib INNER JOIN book b ON ib.bookid = b.id INNER JOIN member m ON ib.memberid = m.id INNER JOIN author a ON b.author = a.id INNER JOIN publisher p ON b.publisher = p.id WHERE MONTHNAME(ib.issuedate) = ? AND b.bname = ?" );
            pst.setString(1, selectedMonth);
            pst.setString(2, SelectedBook);
            rs = pst.executeQuery();
            
            com.mysql.jdbc.ResultSetMetaData rsd = (com.mysql.jdbc.ResultSetMetaData) rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d = (DefaultTableModel)Treport.getModel();
            d.setRowCount(0);
            
            while(rs.next()){
                Vector v2 = new Vector();
                
                for(int i=1; i<=c; i++){
                    v2.add(rs.getString("ib.memberid"));
                    v2.add(rs.getString("m.name"));
                    v2.add(rs.getString("b.bname"));
                    v2.add(rs.getString("a.name"));
                    v2.add(rs.getString("p.name"));
                }
                d.addRow(v2);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
  
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCombo_Month = new javax.swing.JComboBox<>();
        jCombo_book = new javax.swing.JComboBox<>();
        Search = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Treport = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        b_avail = new javax.swing.JTextField();
        I_book = new javax.swing.JTextField();
        t_fine = new javax.swing.JTextField();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Library Management System");

        jPanel1.setBackground(new java.awt.Color(102, 0, 51));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Report");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Month");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Book");

        jCombo_Month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "Octomber", "November", "December" }));

        jCombo_book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCombo_bookActionPerformed(evt);
            }
        });

        Search.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        Search.setText("Search");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        Treport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Member_id", "Member_name", "Book", "Author", "Publisher"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Treport);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Available Book ");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Issued Book ");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Total Fine");

        cancel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jCombo_Month, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jCombo_book, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(b_avail, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(I_book, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_fine, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(67, 67, 67))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(434, 434, 434)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jCombo_Month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jCombo_book, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b_avail, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(I_book, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(t_fine, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75)
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        // TODO add your handling code here:      
        fine();
        b_name();
        I_book();     
        Report_Load();
    }//GEN-LAST:event_SearchActionPerformed

    private void jCombo_bookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCombo_bookActionPerformed
        // TODO add your handling code here:
        
//        if(jCombo_book.getSelectedItem().equals("Book")){
////            BookItem bitem = (BookItem) jCombo_choose.getSelectedItem();
//              book();
//        }
//        else if(jCombo_book.getSelectedItem().equals("Author")){
////           AuthorItem aitem = (AuthorItem) jCombo_choose.getSelectedItem(); 
//             Author();
//        }
//        else if(jCombo_book.getSelectedItem().equals("Publisher")){
////            PublisherItem pitem = (PublisherItem) jCombo_choose.getSelectedItem();
//                Publisher();
//        }
//        else if(jCombo_book.getSelectedItem().equals("Member")){
////            MemberItem mitem = (MemberItem) jCombo_choose.getSelectedItem();
//            Member();
//        }
        
    }//GEN-LAST:event_jCombo_bookActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_cancelActionPerformed

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
            java.util.logging.Logger.getLogger(Monthly_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Monthly_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Monthly_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Monthly_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Monthly_report().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField I_book;
    private javax.swing.JButton Search;
    private javax.swing.JTable Treport;
    private javax.swing.JTextField b_avail;
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox<String> jCombo_Month;
    private javax.swing.JComboBox<String> jCombo_book;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField t_fine;
    // End of variables declaration//GEN-END:variables
}
