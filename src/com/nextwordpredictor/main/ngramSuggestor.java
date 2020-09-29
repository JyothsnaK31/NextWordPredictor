package com.nextwordpredictor.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ngramSuggestor {
	public static ResultSet suggestUnigrams() throws ClassNotFoundException
	{
		ResultSet rs = null;
		try {
			Statement stmt = null;
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/ngrams","root","secret");
			stmt = con.createStatement();
		    rs = stmt.executeQuery("SELECT word,count FROM unigram order by count desc limit 3;");
		   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return rs;     
		 
	}
	public static ResultSet suggestBigrams(String message)
	{
		String[] words=message.split(" ");
		String bigram =words[words.length-1];
		bigram=bigram.trim();
		ResultSet rs = null;
		try {
			final String SQL_INSERT = "Select word,count,probability from bigram where word like ? order by probability desc limit 3";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/ngrams","root","secret");
			PreparedStatement statement = con.prepareStatement(SQL_INSERT);
			String like=bigram+" %";
			statement.setString(1,like);
		    rs=statement.executeQuery(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return rs;  

		
	}
	public static ResultSet suggestTrigrams(String message) {
		String[] words=message.split(" ");
		String trigram =words[words.length-2]+" "+words[words.length-1];
		trigram=trigram.trim();
		ResultSet rs = null;
		try {
			final String SQL_INSERT = "Select word,count,probability from trigram where word like ? order by probability desc limit 3";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/ngrams","root","secret");
			PreparedStatement statement = con.prepareStatement(SQL_INSERT);
			String like=trigram+" %";
			statement.setString(1,like);
		    rs=statement.executeQuery(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return rs;  

		
	}
	public static ResultSet suggestFourgrams(String message) {
		String[] words=message.split(" ");
		String trigram =words[words.length-3]+" "+words[words.length-2]+" "+words[words.length-1];
		trigram=trigram.trim();
		ResultSet rs = null;
		try {
			final String SQL_INSERT = "Select word,count,probability from trigram where word like ? order by probability desc limit 3";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/ngrams","root","secret");
			PreparedStatement statement = con.prepareStatement(SQL_INSERT);
			String like=trigram+" %";
			statement.setString(1,like);
		    rs=statement.executeQuery(); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return rs;  
	}
	
	
	public static void usingPrintWriter(String msg) throws IOException 
	{
	    FileWriter fileWriter = new FileWriter("C:\\\\Users\\\\jyoth\\\\Desktop\\\\Spring-2020\\\\Artificial Intelligence\\\\Projects\\\\Trainingdatasetcleann.txt", true); //Set true for append mode
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println(msg);  //New line
	    printWriter.close();
	}
	
	
}
