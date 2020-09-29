package com.nextwordpredictor.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Unigram {

	public  Map<String,Integer> insertUnigram() throws SQLException, IOException, ClassNotFoundException {
         Map<String,Integer> unigramMap=new HashMap<String, Integer>();
		File file = new File("C:\\Users\\jyoth\\Desktop\\Spring-2020\\Artificial Intelligence\\Projects\\Trainingdatasetcleann.txt"); 
		BufferedReader br = new BufferedReader(new FileReader(file));
		 String eachline;
		 StoreNgrams sc=new StoreNgrams();
		 while ((eachline = br.readLine()) != null)
		 { 
			 String tosplit=eachline.trim();
			 String[] insplitwords= tosplit.split(" ");
			 for(int i=0;i<insplitwords.length-1;i++)
			 {
				 String word=insplitwords[i];
				
				 Iterator it = unigramMap.entrySet().iterator();
			     int count=0;
			     int mapvalue=1;
			     for(Map.Entry<String, Integer>  entry:unigramMap.entrySet() )
					{
					 	String mapkey= entry.getKey();
					 if(mapkey.equalsIgnoreCase(word))
					 {
						 mapvalue=entry.getValue();
						 mapvalue++;
						 unigramMap.remove(entry.getKey());
						 break;
					 }
			        }
			     unigramMap.put(word.toLowerCase(), mapvalue);
			     
			 }
		 }
		 
		 unigramMap.forEach((k,v) -> System.out.println(k + ":" + v));
		 sc.insertUnigramsIntoDB(unigramMap);
		 return unigramMap;
}
	public void insertunigramfromtext(String message)
	{
		
		String[] split=message.split(" ");
		for(int i=0;i<split.length;i++)
		{
			StringBuilder sentence = new StringBuilder();
			String word1=split[i].toLowerCase().trim();
			for(int j=0;j<word1.length();j++)
			 {
				 char letter=word1.charAt(j);
				 if(Character.isAlphabetic(letter))
				 {
					 sentence.append(letter);
				 }
			 }
			String word=sentence.toString();
			ResultSet rs = null;
			float wordcount;
			try {
				final String SQL_SELECT = "Select word,count from unigram where word =?";
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/ngrams","root","secret");
				PreparedStatement statement = con.prepareStatement(SQL_SELECT);
				
				statement.setString(1,word);
				rs=statement.executeQuery(); 
				if(rs.next())
				{
				do{
				wordcount=rs.getFloat("count");
			    final String SQL_UPDATE = "update unigram set count=? where word=?";
			    PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE);
			    preparedStatement.setString(2, word);
			    preparedStatement.setFloat(1,wordcount+1);
			    preparedStatement.executeUpdate();
				}while(rs.next()); }
				else if(rs.next()==false){
					
					final String SQL_INSERT = "insert into unigram (word,count) values (?,?) ";
					PreparedStatement ps = con.prepareStatement(SQL_INSERT);
					ps.setString(1,word);
					ps.setFloat(2,1);
					ps.executeUpdate();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

}