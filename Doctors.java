package hospital.management.sys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {

	private Connection con;
//    private Scanner sc;

    public Doctors(Connection con) {
    	this.con =con;
//    	this.sc =sc;
    	
    }
       
    
    public void viewDoctors() {
    	
    	String query= "select * from doctors";
    	try {
			
    		PreparedStatement ps = con.prepareStatement(query);
    		ResultSet rs= ps.executeQuery();
    		
    		System.out.println("Doctors");
    		System.out.println("+-------------+-----------------------+------------------+");
    		System.out.println("|   Doc. ID   |       Doctor's Name   |  Specialization  |");
    		System.out.println("+-------------+-----------------------+------------------+");
    		while(rs.next()) {
    			int Id= rs.getInt("id");
    			String Name= rs.getString("name");
    			String Specialization= rs.getString("specialization");
    			System.out.printf("|  %-9s  |  %-19s  |  %-14s  |\n",Id,Name,Specialization);
    			System.out.println("+-------------+-----------------------+------------------+");
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public boolean getDoctorById(int id) {
    	String query ="select * from doctors where id= ?";
    	
    	try {
		
        	PreparedStatement ps= con.prepareStatement(query);
        	ps.setInt(1, id);
        	
        	ResultSet rs= ps.executeQuery();
        	if(rs.next()) {
        		 return true;
        		
        	}
        	else {
        		return false;
        	}
        	
    	} 
    	catch (SQLException e) {

    		e.printStackTrace();
    	}
    	return false;
    }
}