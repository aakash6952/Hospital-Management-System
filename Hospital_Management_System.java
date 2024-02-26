package hospital.management.sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hospital_Management_System {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
	 
		Scanner sc= new Scanner(System.in);
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","12345");
		
        Patients pat =new Patients(con, sc);  //Called the constructors from other classes of 
        Doctors doc = new Doctors(con); // same packages , so that they can be used
        
        System.out.print("'HOSPITAL MANAGEMENT SYSTEM'");

        while(true)
        {
        System.out.println("Enter your choice:- ");
        System.out.println("1. Add Patient");
        System.out.println("2. View Patient");
        System.out.println("3. View Doctors");
        System.out.println("4. Book Appointment");
        System.out.println("5. Exit");
        
        int choice = sc.nextInt();
        
        switch(choice) {
        	
        case 1:
        	//Add patient
        	pat.addPatient();
        	System.out.println();
        	break;
        case 2:
        	//View Patient
        	pat.viewPatients();
        	System.out.println();
        	break;
        case 3:
        	//View Doctors
        	doc.viewDoctors();
        	System.out.println();
        	break;
        case 4:
        	//Book Appointment
        	bookAppointment(pat, doc,con,sc);
        	System.out.println();
        	break;
        case 5:
        	System.out.println("Thank You! for using Hospital Management System !!!");;
        	return;
        default:
        	System.out.print("Enter the correct choice !!");
        	break;
        
       }
        }
        
	} catch (SQLException e) {
		e.printStackTrace();
	}

	}
	
	public static void bookAppointment(Patients pat, Doctors doc,  Connection con, Scanner sc) {
		
		System.out.print("Enter Patient ID:-");
		int patient_id= sc.nextInt();
		System.out.print("Enter Doctor ID:-");
		int doctor_id = sc.nextInt();
		System.out.print("Enter the appointment date (YYYY-MM-DD):-");
		String appointment_date= sc.next();	
		
		if(pat.getPatientById(patient_id) && doc.getDoctorById(doctor_id)) {
			
			if(doctorAvailablity(appointment_date, doctor_id, con)) {
				String query= "insert into appointments(patient_id, doctor_id, appointment_date) values(?,?,?)";
				
				try {
					PreparedStatement ps = con.prepareStatement(query); //entering query in the table
					
					ps.setInt(1, patient_id);
					ps.setInt(2, doctor_id);
					ps.setString(3, appointment_date);
					
					int result = ps.executeUpdate();
					
					if(result>0) {
						
						System.out.print("Appointment Booked !!");
				}
					else {
						System.out.print("Failed to book appointment. !!");
					}
									
				} catch (SQLException e) {
					e.printStackTrace();
				}
					
			}else{
                System.out.println("Doctor not available on this date!!");
            }
        }else{
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
			
		}
	
	
	public static boolean doctorAvailablity(String appointment_date, int doctor_id, Connection con) {
	
		String query = "select count(*) from appointments where appointment_date=? and doctor_id=? ";
		
		try {
			PreparedStatement ps= con.prepareStatement(query);
			
			ps.setString(1, appointment_date);
			ps.setInt(2, doctor_id);
			
			ResultSet rs= ps.executeQuery();
			
			if(rs.next()) {
				int count = rs.getInt(1);
				if(count==0) {
					return true;
				}
				else {
					return false;
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	return false;
	}

}


