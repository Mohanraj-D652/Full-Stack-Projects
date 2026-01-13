import java.io.*;
import java.util.*;
import java.sql.*;

class CRUD_Operations{

public static void ADD_Data(){

try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection mysqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Entertainment", "root", "mysql");

Scanner s = new Scanner(System.in);
System.out.println("Enter a New Web Series ID: ");
int seriesID = s.nextInt();

System.out.println("Enter a New Web Series Name: ");
String seriesName = s.next();

System.out.println("Enter a New Web Series Genre: ");
String seriesgenre = s.next();

System.out.println("Enter a New Web Series Realesed Platform: ");
String seriesplatform = s.next();

System.out.println("Enter a New Web Series IMDB Ratings: ");
Float seriesIMDB = s.nextFloat();

String ADD_Query = "insert into WebSeries values('"+seriesID+"', '"+seriesName+"', '"+seriesgenre+"', '"+seriesplatform+"', '"+seriesIMDB+"')";

Statement state = mysqlconnection.createStatement();
int cre = state.executeUpdate(ADD_Query);




String select_query = "select * from WebSeries;";
ResultSet exe = state.executeQuery(select_query);

System.out.println("------ New Updated Data Base ------");

while(exe.next()){

System.out.println(exe.getInt(1)+ " " +exe.getString(2)+ " " +exe.getString(3)+ " " +exe.getString(4)+ " " +exe.getFloat(5));
}

mysqlconnection.close();


}
catch(ClassNotFoundException ex){
System.out.println(ex.getMessage());
}
catch(SQLException ex){
System.out.println(ex.getMessage());
}

}







public static void Delete_Data(){

try{
  Class.forName("com.mysql.cj.jdbc.Driver");
Connection mysqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Entertainment", "root", "mysql");

Scanner s=new Scanner(System.in);
System.out.println("Enter a showID to Delete the Data: ");
int showid = s.nextInt();

String Delete_query = "Delete from WebSeries where ShowID = '"+showid+"' ;";

Statement state = mysqlconnection.createStatement();
int res = state.executeUpdate(Delete_query);

String select_query = "select * from WebSeries;";
ResultSet select = state.executeQuery(select_query);

System.out.println("------ New Updated Data Base ------");
while(select.next()){
System.out.println(select.getInt(1)+ " " +select.getString(2)+ " " +select.getString(3)+ " " +select.getString(4)+ " " +select.getFloat(5));
}
mysqlconnection.close();

  


}

catch(ClassNotFoundException ex){
System.out.println(ex.getMessage());
}
catch(SQLException ex){
System.out.println(ex.getMessage());
}

}


public static void Update_Data(){

try{

Class.forName("com.mysql.cj.jdbc.Driver");
Connection mysqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Entertainment", "root", "mysql");

Scanner s = new Scanner(System.in);

System.out.println("Enter a Show ID to Modify: ");
int showid = s.nextInt();

System.out.println("Enter a New Series Name: ");
String seriesname = s.next();

System.out.println("Enter a New Series Genre: ");
String seriesgenre = s.next();

System.out.println("Enter a New Show platform: ");
String seriesplatform = s.next();

System.out.println("Enter a New Show IMDB Ratings: ");
Float showIMDB = s.nextFloat();



String update_data = "Update WebSeries set ShowName = '"+seriesname+"', ShowGenre = '"+seriesgenre+"', ShowPlatform = '"+seriesplatform+"', IMDBRating = '"+showIMDB+"' where ShowID = '"+showid+"' ;";

Statement state = mysqlconnection.createStatement();
int res = state.executeUpdate(update_data);

String select_query = "select * from WebSeries;";
ResultSet select = state.executeQuery(select_query);

System.out.println("------ New Updated Data Base ------");
while(select.next()){
System.out.println(select.getInt(1)+ " " +select.getString(2)+ " " +select.getString(3)+ " " +select.getString(4)+ " " +select.getFloat(5));
}
mysqlconnection.close();


}
catch(ClassNotFoundException ex){
System.out.println(ex.getMessage());
}
catch(SQLException ex){
System.out.println(ex.getMessage());
}

}



public static void Search_Data(){

try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection mysqlconnector = DriverManager.getConnection("jdbc:mysql://localhost:3306/Entertainment", "root", "mysql");

Scanner s=new Scanner(System.in);
System.out.println("Enter a Show ID to Search: ");
int showid = s.nextInt();

String search_query = "select * from WebSeries where showID = '"+showid+"' ;";

Statement state = mysqlconnector.createStatement();
ResultSet res = state.executeQuery(search_query);

System.out.println("------ Search Data ------");
while(res.next()){

System.out.println(res.getInt(1)+ " " +res.getString(2)+ " " +res.getString(3)+ " " +res.getString(4)+ " " +res.getFloat(5));
}

mysqlconnector.close();


}

catch(ClassNotFoundException ex){
System.out.println(ex.getMessage());
}
catch(SQLException ex){
System.out.println(ex.getMessage());
}

}

public static void Display_DataBase(){

try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection mysqlconnector = DriverManager.getConnection("jdbc:mysql://localhost:3306/Entertainment", "root", "mysql");

String Display = "select * from WebSeries;";

Statement state = mysqlconnector.createStatement();
ResultSet res = state.executeQuery(Display);

System.out.println("------ Data Base ------");
while(res.next()){

System.out.println(res.getInt(1)+ " " +res.getString(2)+ " " +res.getString(3)+ " " +res.getString(4)+ " " +res.getFloat(5));
}

mysqlconnector.close();

}

catch(ClassNotFoundException ex){
System.out.println(ex.getMessage());
}
catch(SQLException ex){
System.out.println(ex.getMessage());
}



}


}

class myproject{
public static void main(String []args){

Scanner s = new Scanner(System.in);

boolean temp = true;

while(temp){

System.out.println(" Enter 1 for ADD Data || Enter 2 for Delete Data || Enter 3 for Update Data || Enter 4 for Search Data || Enter 5 for See All the Datas ");
System.out.println("------- Enter the Choice -------");

int input = s.nextInt();
switch(input){

case 1:
CRUD_Operations.ADD_Data();
break;

case 2:
CRUD_Operations.Delete_Data();
break;

case 3:
CRUD_Operations.Update_Data();
break;

case 4:
CRUD_Operations.Search_Data();
break;

case 5:
CRUD_Operations.Display_DataBase();
break;

default:
temp = false;
break;


}

}

}
}
































