package hospital.management.sys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
    @SuppressWarnings("unused")
	private Connection con;
    private Scanner sc;

    public Patients(Connection con, Scanner sc) {
    	this.con =con;
    	this.sc =sc;
    	
    }
    public void addPatient() {
    	System.out.print("Enter patient name:-");
    	String name= sc.next();
    	System.out.print("Enter patient age:-");
    	int age= sc.nextInt();
    	System.out.print("Enter patient gender:-");
    	String gender= sc.next();
    	
    	
    	try {
    		String query = "insert into patients(name, age, gender) values(?,?,?)";
    		PreparedStatement ps= con.prepareStatement(query);
    		
    		ps.setString(1,name);
    		ps.setInt(2, age);
    		ps.setString(3, gender);
    		
    		int affectedrows= ps.executeUpdate();
    		
    		if(affectedrows>0) {
    			System.out.println("Patient added succesfully !!");
    		}
    		else {
    			System.out.println("Failed, Try again ");
    			
    		}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    public void viewPatients() {
    	
    	String query= "select * from patients";
    	try {
			
    		PreparedStatement ps = con.prepareStatement(query);
    		ResultSet rs= ps.executeQuery();
    		
    		System.out.println("Patients");
    		System.out.println("+------------+------------------+-------+--------+");
    		System.out.println("|   Pat. ID  |    Name          |  Age  | Gender |");
    		System.out.println("+------------+------------------+-------+--------+");
    		while(rs.next()) {
    			int Id= rs.getInt("id");
    			String Name= rs.getString("name");
    			int Age= rs.getInt("age");
    			String Gender= rs.getString("gender");
    			System.out.printf("|%-12s|%-18s|%-7s|%-8s|\n",Id,Name,Age,Gender);
    			System.out.println("+-------------+------------------+-------+--------+");
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public boolean getPatientById(int id) {
    	String query ="select * from patients where id= ?";
    	
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
