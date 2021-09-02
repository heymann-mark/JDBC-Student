package jdbcProject;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



import java.sql.Connection;
public class Student {
	private static Connection conn;
	private static int sid;
	public String sname;
	public static final String oracleServer = "localhost";
	public static final String oracleServerSid = "studentdb";
	private static void select(int sid,int cid){
		String sql = "insert into enrolled (student_id,course_id) values (?,?)";
		try{
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sid);
			pstmt.setInt(2,cid);
			pstmt.executeUpdate();
			pstmt.close();
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private static Course findCourse(int cid){
		try{
			PreparedStatement pstmt = conn.prepareStatement("select * from courses where course_id=?");
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				Course c = new Course();
				c.course_id = cid;
				c.course_name = rs.getString("cname");
				c.credits = rs.getInt("credits");
				return c;
			}
			rs.close();
			pstmt.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	private static boolean checkEnrolled(int sid,int cid){
		boolean ret = false;
		try{
			PreparedStatement pstmt = conn.prepareStatement("select * from enrolled where student_id=? and course_id=?");
			pstmt.setInt(1, sid);
			pstmt.setInt(2, cid);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				ret = true;
			}
			rs.close();
			pstmt.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}
	private static List<Course> searchCourse(String keyword){
		List<Course> list = new ArrayList<Course>();
		try{
			
			PreparedStatement pstmt = conn.prepareStatement("select * from courses where course_name like ?");
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Course c = new Course();
				c.course_id = rs.getInt("course_id");
				c.course_name = rs.getString("course_name");
				c.credits = rs.getInt("credits");
				list.add(c);
			}
			rs.close();
			pstmt.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}	
	
	
	
	private static void printL(){
		try{
			PreparedStatement pstmt = conn.prepareStatement("select * from courses");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				System.out.println(rs.getInt("course_id") + "," + rs.getString("cname") + "," + rs.getInt("credits"));
			}
			rs.close();
			pstmt.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void printM(){
		try{
			PreparedStatement pstmt = conn.prepareStatement("select * from enrolled a inner join courses b on a.course_id=b.cid where a.student_id=?");
			pstmt.setInt(1, sid);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				System.out.println(rs.getInt("course_id") + ":" + rs.getString("cname"));
			}
			rs.close();
			pstmt.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private static void unselect(int sid,int cid){
		String sql = "delete from enrolled where student_id=? and course_id=?";
		try{
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sid);
			pstmt.setInt(2,cid);
			pstmt.executeUpdate();
			pstmt.close();
			
			
			/*pstmt = conn.prepareStatement("update courses set credits=credits-1 where cid=?");
			pstmt.setInt(1, cid);
			pstmt.executeUpdate();
			pstmt.close();*/
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private static void addStudent(int sid,String sname){
		String sql = "insert into students (student_id,student_name) values (?,?)";
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sid);
			pstmt.setString(2,sname);
			pstmt.executeQuery();
			pstmt.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private static Student findStudent(int sid){
		try{
			PreparedStatement pstmt = conn.prepareStatement("select * from student where student_id=?");
			pstmt.setInt(1, sid);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				Student stu = new Student();
				stu.sid = rs.getInt("student_id");
				stu.sname = rs.getString("student_name");
				//stu.name = rs.getString("sname");
				return stu;
			}
			rs.close();
			pstmt.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	private static void printMenu(){
	       
		System.out.println("================== System Menu ==============");
		System.out.println("L - List: lists all records in the course table");
		System.out.println("E - Enroll: enrolls the active student in a course; user is prompted for course ID; check for conflicts, i.e., ");
		System.out.println("student cannot enroll twice in same course");
		System.out.println("W - Withdraw: deletes an entry in the Enrolled table corresponding to active student; student is  prompted for course ID to be withdrawn from");
		System.out.println("S - Search: search course based on substring of course name which is given by user; list all matching  courses");
		System.out.println("M - My Classes: lists all classes enrolled in by the active student.");
		System.out.println("X - Exit: exit application");
		System.out.println("================== System Menu ==============");
		System.out.println("Please input the command:");
		
	}
	public static Connection getConnection() {
		Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+oracleServer+"/"+ oracleServerSid,"root", "");
            System.out.println("Database is connected !!");
            //conn.close();
        }
        catch(Exception e) {
            System.out.print("Do not connect to DB - Error:"+e);
        }
		return conn;
	}
    public static void main(String args[]) {
    	conn = getConnection();
		if(conn == null){
			System.out.print("Cannot connect to the database!!");
			return;
		}
    	
		Scanner input = new Scanner(System.in);
		System.out.println
		("Please input your Student ID: or Enter -1 for New Student");
		try{
			
			sid = input.nextInt();
		}catch(Exception ex){
            
			System.out.println("Student ID must be a number!");
            sid=input.nextInt();
			System.exit(1);
		}
		if(sid == -1){
			System.out.print("Please input a New Student ID:");
			try{
                Scanner sid= new Scanner(System.in);
                //sid = input.nextInt();
			}catch(Exception ex){
				
				System.out.println("Student ID must be a number!");
               
				//System.exit(-1);
			}
			 sid=input.nextInt();
			Student stu = findStudent(sid);
			if(stu != null){
				System.out.println("Student ID already exist!. Enter Different Student ID");
                sid=input.nextInt();
				//System.exit(-1);
			}
			
			System.out.print("Please input your name:");
			String name = input.next();
			
			addStudent(sid,name);
		}else{
			
			Student stu = findStudent(sid);
			if(stu == null){
				
				System.out.println("Student(sid=" + sid + ") Not exist! ");
                sid=input.nextInt();
                //System.exit(-1);
			}
		}
		
		
		boolean exit = false;
		
		while(!exit){
			
			
			System.out.println("\n");
			printMenu();
			String cmd = input.next();
			if(cmd.equalsIgnoreCase("x")){
				exit = true;
			}else if(cmd.equalsIgnoreCase("l")){
				printL();
			}else if(cmd.equalsIgnoreCase("e")){
				System.out.print("Please input the Course ID:");
				int cid = input.nextInt();
				
				Course c = findCourse(cid);
				if(c == null){
					System.out.println("Course does not exists!");
					continue;
				}
				
				if(checkEnrolled(sid,cid)){
					System.out.println("Course aleady selected!");
					continue;
				}
				
				select(sid,cid);
				System.out.println("Enrolled in Course");
			}else if(cmd.equalsIgnoreCase("w")){
				System.out.print("Please input the Course ID:");
				int cid = input.nextInt();
				
				Course c = findCourse(cid);
				if(c == null){
					System.out.println("Course not exists!");
					continue;
				}
				
				if(!checkEnrolled(sid,cid)){
					
					System.out.println("You have not selected this course yet!");
					continue;
				}
				
				unselect(sid,cid);
				System.out.println("Withdrawed from Course");
			}else if(cmd.equalsIgnoreCase("s")){
				
				System.out.print("Please input the keyword:");
				String keyword = input.next();
				
				List<Course> list = searchCourse(keyword);
				for(Course c : list){
					System.out.println("cid:" + c.course_id + ",cname:" + c.course_name + ",credits:" + c.credits);
				}
				
			}else if(cmd.equalsIgnoreCase("m")){
				
				printM();
			}
			System.out.println("\n");
			
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}