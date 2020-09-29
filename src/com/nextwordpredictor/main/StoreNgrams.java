package com.nextwordpredictor.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class StoreNgrams {
	public static void insertUnigramsIntoDB( Map<String,Integer> unigrams) throws ClassNotFoundException
	{
		try{  
			final String SQL_INSERT = "INSERT INTO unigram (word, count) VALUES (?,?)";
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/ngrams","root","secret"); 
		    PreparedStatement statement = con.prepareStatement(SQL_INSERT);
		    for(Map.Entry<String, Integer>  entry:unigrams.entrySet() )
		    {
		    	statement.setString(1, entry.getKey());
		    	statement.setFloat(2, entry.getValue());
		    	statement.addBatch();
		    }
		    statement.executeBatch();
		}
		catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
            return; // Exit if there was an error
        }
			   
			
		
	}
	public void insertBigramsIntoDB(Map<String,Integer> bigrams,Map<String,Integer> unigrams) throws ClassNotFoundException
	{
		try{  
			final String SQL_INSERT = "INSERT INTO bigram (word, count, probability) VALUES (?,?,?)";
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/ngrams","root","secret"); 
		    PreparedStatement statement = con.prepareStatement(SQL_INSERT);
		    for(Map.Entry<String, Integer>  entry:bigrams.entrySet() )
		    {
		    	statement.setString(1, entry.getKey());
		    	statement.setFloat(2, entry.getValue());
		    	String[] previosWord=entry.getKey().split(" ");
		    	//System.out.println(previosWord[0]);
		    	int prevCount=unigrams.get(previosWord[0].toLowerCase());
		    	Float prob=((float) entry.getValue()/(float) prevCount);
		    	statement.setFloat(3,prob);
		    	statement.addBatch();
		    }
		    statement.executeBatch();
		}
		catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
            return; // Exit if there was an error
        }	
	}
	public void insertTrigramsIntoDB(Map<String,Integer> trigrams,Map<String,Integer> bigrams) throws ClassNotFoundException
	{
		try{  
			final String SQL_INSERT = "INSERT INTO trigram (word, count, probability) VALUES (?,?,?)";
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/ngrams","root","secret"); 
		    PreparedStatement statement = con.prepareStatement(SQL_INSERT);
		    for(Map.Entry<String, Integer>  entry:trigrams.entrySet() )
		    {
		    	statement.setString(1, entry.getKey());
		    	statement.setFloat(2, entry.getValue());
		    	String[] previosWord=entry.getKey().split(" ");
		    	String previous=previosWord[0]+" "+previosWord[1];
		    	int prevCount=bigrams.get(previous.toLowerCase());
		    	Float prob= ((float)entry.getValue()/(float)prevCount);
		    	statement.setFloat(3,prob);
		    	statement.addBatch();
		    }
		    statement.executeBatch();
		}
		catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
            return; // Exit if there was an error
        }
	}
}
