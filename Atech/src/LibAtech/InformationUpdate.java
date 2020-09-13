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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibAtech;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database Information Update Class Handler.
 * Extended LibAtech.Database
 * @author l
 * @version 0.1.1
 */
public class InformationUpdate extends Database {
    /**
     * Default Constructor
     */
    
    public InformationUpdate(){}
    
    public ResultSet getItemResult() throws SQLException{
        return dbStatement.executeQuery("SELECT * FROM Item");
    }

}
