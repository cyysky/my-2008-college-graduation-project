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
 */
package LibAtech;

import java.sql.*;

/**
 * Atech Database libary have useful database manage method implemented.
 * <p>This Database connection class handler the creation of the database in mysql
 * and table structure need by the Atech Software.</p>
 * <p>This Database also handler connection from Atech software to the mysql</p>
 * <p>Dependence version of MySQL is 5.0</p> 
 * @author l
 * @version 0.1.1
 */
public class Database {

    /**
     *Store Connection Create by constructor,connectMysql() method.
     */
    public Connection databaseConnection;
    public Statement dbStatement;
    public Statement dbUpdate;
    /**
     * Default Constructor
     */
    public Database() {
    }

    /**
     * Override Constructor with precreate connection.
     * @param url Url of the mysql database
     * @param user User of the mysql database   
     * @param password User's password of the mysql database
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public Database(String url, String user, String password) throws Exception {
        setConnectMysql(url, user, password);
    }

    /**
     * Atech Database connection manager Connection and create scrollable statement
     * @param url Url of the mysql database
     * @param user User of the mysql database   
     * @param password User's password of the mysql database
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void setConnectMysql(String url, String user, String password)
            throws Exception {
       
        Class.forName("com.mysql.jdbc.Driver");
        //System.out.println("Mysql Jdbc Driver Loaded.");

        //Establish the connection 
        this.databaseConnection = DriverManager.getConnection("jdbc:mysql://" + url, user, password);
        //System.out.println("Database Connected to " + url + " with user :" + user);
        this.dbStatement = databaseConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        this.dbUpdate = databaseConnection.createStatement();
        setUseDatabase();
    }
    public void setUseDatabase() throws SQLException {
        Statement stat = this.databaseConnection.createStatement();
        if(this.isDatabaseStructureExist()){
            stat.execute("USE Atech;");
        }
        else{
            this.setDatabaseStructure();
        }
    }
    /**
     * Create a new Atech database structure with complete table.
     * @throws java.sql.SQLException
     */
    public void setDatabaseStructure() throws SQLException {
        Statement stat = this.databaseConnection.createStatement();
        if (this.isDatabaseStructureExist()) {
            throw new SQLException("The Database Atech Exist In MySQL.\n" +
                    "Run dropDatabaseStructure() before use thid method.");
        }
        
        //Creating Database
        stat.execute("CREATE DATABASE Atech;");
        
        //Use Database
        stat.execute("USE Atech;");

        //Create the Supplier Table
        //                                                             Supplier
        stat.execute("CREATE TABLE Supplier (" +
                "SupplierNo INTEGER NOT NULL UNIQUE," +
                "SupplierName VARCHAR(100)," +
                "ContactNo VARCHAR(100)," +
                "Address VARCHAR(100)," +
                "PRIMARY KEY (SupplierNo)" +
                ")TYPE=INNODB;");

        //Create the Item Table
        //                                                               Item
        stat.execute("CREATE TABLE Item (" +
                "ItemNo INTEGER NOT NULL UNIQUE," +
                "Name VARCHAR(100)," +
                "Price DOUBLE," +
                "Quantity INTEGER," +
                "SupplierNo INTEGER NOT NULL," +
                "PRIMARY KEY(ItemNo)," +
                "FOREIGN KEY(SupplierNo) REFERENCES Supplier(SupplierNo) ON DELETE RESTRICT ON UPDATE CASCADE" +
                ")TYPE = INNODB;");

        //Create the SalesRecord Table
        //                                                           SalesRecord
        stat.execute("CREATE TABLE SalesRecord (" +
                "SalesNo INTEGER NOT NULL UNIQUE," +
                "Total DOUBLE,"+
                "Date    DATETIME," +
                "PRIMARY KEY(SalesNo)" +
                ")TYPE = INNODB;");

        //Create the SalesItem Table
        //                                                            SalesItem
        stat.execute("CREATE TABLE SalesItem (" +
                "SalesNo INTEGER NOT NULL," +
                "ItemNo INTEGER NOT NULL," +
                "Quantity INTEGER," +
                "PRIMARY KEY (ItemNo,SalesNo)," +
                "FOREIGN KEY (ItemNo) REFERENCES Item(ItemNo) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (SalesNo) REFERENCES SalesRecord(SalesNo) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")TYPE = INNODB;");

        //Create the Order Table
        //                                                           Order_Invoice
        stat.execute("CREATE TABLE Order_Invoice (" +
                "OrderNo INTEGER NOT NULL UNIQUE," +
                "SupplierNo INTEGER NOT NULL," +
                "Total DOUBLE," +
                "Delivered DATETIME," +
                "Paid DATETIME,"+
                "PRIMARY KEY (OrderNo)," +
                "FOREIGN KEY (SupplierNo) REFERENCES Supplier(SupplierNo) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")TYPE = INNODB;");

        //Create The Order Item
        //                                                             OrderItem
        stat.execute("CREATE TABLE OrderItem (" +
                "OrderNo INTEGER NOT NULL ," +
                "ItemNo INTEGER NOT NULL ," +
                "Quantity INTEGER," +
                "Cost DOUBLE,"+
                "PRIMARY KEY (OrderNo,ItemNo)," +
                "FOREIGN KEY (OrderNo) REFERENCES Order_Invoice(OrderNo) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (ItemNo) REFERENCES Item(ItemNo) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")TYPE = INNODB;");

    }

    /**
     * Method Checking Whenever the Database name "Atech" Exist or not.
     * @return Boolean Type, True represent the Atech database exist.
     * @throws java.lang.Exception
     */
    public boolean isDatabaseStructureExist() throws SQLException {
        Statement stat = this.databaseConnection.createStatement();
        ResultSet rs = stat.executeQuery("SHOW DATABASES;");
        boolean exist = false;
        while (rs.next()) {
            String database = rs.getString("Database");
            //System.out.println(database);
            if (database.equalsIgnoreCase("Atech")) {
                exist = true;
            }
        }
        return exist;
    }

    /**
     * Drop Atech Database,simple but powerful method.
     * <p>Warning : This will delete whole database structure and data inside will
     * lose permently.</p>
     * @throws java.lang.Exception Statement,SQL Exception
     */
    public void setDropDatabaseStructure() throws Exception {
        Statement stat = this.databaseConnection.createStatement();
        stat.execute("DROP DATABASE Atech");
    }

    /**
     * Close Connection to MySQL Database created by connectMysql() method.
     * @throws java.lang.Exception SQL Exception
     */
    public void setDisconnectMysql() throws Exception {
        this.databaseConnection.close();
    }

    /**
     * Auto drop and create a new Atech Database structure.
     * <p>Auto detected exist Atech database and drop it and replace database structure
     * with empty data. Often use to advance debuging.</p>
     * <p.Warning : This method will delete all the data permernent.
     * @throws java.lang.Exception
     */
    public void setRecreateDatabaseStructure() throws Exception {
        if (this.isDatabaseStructureExist()) {
            this.setDropDatabaseStructure();
        }
        this.setDatabaseStructure();
    }
    /**
     * Get The Database lates status and respond in complete santance.
     * @return String Formated Completed Santance of database Status;
     * @throws java.lang.SQLException
     */
    public String getDatabaseStatus() throws SQLException{
        if(databaseConnection.isClosed()){
          return "Connection Lose!";
        }
        DatabaseMetaData md = databaseConnection.getMetaData();
        return md.getDatabaseProductName()+" "+md.getDatabaseProductVersion()+" connected at \"" + md.getURL() +"\"";
    }
    /**
     * Check whether the program is connected to the MySQL.
     * @return boolean true if is connected.
     * @throws java.sql.SQLException
     */
    public boolean isConnected() throws SQLException{
        if(databaseConnection==null){
            return false;
        }
        if(databaseConnection.isClosed()){
            return false;           
        }
        else return true;
    }
}
