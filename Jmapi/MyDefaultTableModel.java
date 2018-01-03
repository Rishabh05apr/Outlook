/**
 * @(#)MyDefaultTableModel.java
 *
 *
 * @author 
 * @version 1.00 2014/7/2
 */
import javax.swing.*;
import javax.swing.table.*;
class MyDefaultTableModel extends DefaultTableModel {

	    MyDefaultTableModel(Object[][] data, Object[] columnNames) {
	    	
	    	super(data, columnNames);
	    }
	    
	    public boolean isCellEditable(int row, int column) {
	    	
	        return false;
	    }
	}