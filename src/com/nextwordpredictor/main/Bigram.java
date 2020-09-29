package com.nextwordpredictor.main;
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Bigram extends StoreNgrams {
	public static Map<String,Integer> insertBigrams(Map<String,Integer> unigrams) throws IOException, ClassNotFoundException
	{
		File file = new File("C:\\Users\\jyoth\\Desktop\\Spring-2020\\Artificial Intelligence\\Projects\\Trainingdatasetcleann.txt"); 
		Map<String,Integer> suggestedwordmap=new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		 String eachline;
		 StoreNgrams sc=new StoreNgrams();
		 while ((eachline = br.readLine()) != null)
		 { 
			 String tosplit=eachline.trim();
			 String[] insplitwords= tosplit.split(" ");
			 for(int i=0;i<insplitwords.length-2;i++)
			 {
		      String bigram=insplitwords[i]+" "+insplitwords[i+1];
		      int known=0;
		      for(Map.Entry<String, Integer>  entry:suggestedwordmap.entrySet() )
				{
		    	  if(entry.getKey().equalsIgnoreCase(bigram))
		    	  {   known=1;
		    		  int count=entry.getValue();
		    		  entry.setValue(count+1);
				}		    	    
		    	  }
		      if(known==0)
		      {
		    	 
		      suggestedwordmap.put(bigram.toLowerCase(),1);
	}}

		 }
		 sc.insertBigramsIntoDB(suggestedwordmap, unigrams);
		return suggestedwordmap;
	}
	public void insertbigramfromtext(String message)
	{
		
		String[] split=message.split(" ");
		for(int i=0;i<split.length-1;i++)
		{
			
			String word1=split[i].toLowerCase().trim();
			String word2=split[i+1].toLowerCase().trim();
			String cleanword1= cleandata(word1);
			String cleanword2= cleandata(word2);
			String word=cleanword1+" "+cleanword2;
			ResultSet rs = null;
			ResultSet rs1 = null;
			float wordcount;
			try {
				final String SQL_SELECT = "Select word,count from bigram where word =?";
				final String SQL_SELECT_unigram = "Select word, count from unigram where word =?";
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/ngrams","root","secret");
				PreparedStatement statement1 = con.prepareStatement(SQL_SELECT_unigram);
				statement1.setString(1,cleanword1);
				rs1=statement1.executeQuery();
				while(rs1.next())
				{
				float count1=rs1.getFloat("count");
				PreparedStatement statement = con.prepareStatement(SQL_SELECT);
				statement.setString(1,word);
				rs=statement.executeQuery(); 
				if(rs.next())
				{
				do{
				wordcount=rs.getFloat("count");
			    final String SQL_UPDATE = "update bigram set count=?, probability=? where word=?";
			    PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE);
			    preparedStatement.setString(3, word);
			    preparedStatement.setFloat(1,wordcount+1);
			    float toenter= (wordcount+1) /count1;
			  
			    preparedStatement.setFloat(2,toenter);
			    
			    preparedStatement.executeUpdate();
				}while(rs.next()); }
				else if(rs.next()==false){
					
					final String SQL_INSERT = "insert into bigram (word,count,probability) values (?,?,?) ";
					PreparedStatement ps = con.prepareStatement(SQL_INSERT);
					ps.setString(1,word);
					ps.setFloat(2,1);
					float toenter=1/count1;
					ps.setFloat(3,toenter);
					ps.executeUpdate();
				}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	public String cleandata(String word)
	{
		StringBuilder sentence = new StringBuilder();
		for(int j=0;j<word.length();j++)
		 {
			 char letter=word.charAt(j);
			 if(Character.isAlphabetic(letter))
			 {
				 sentence.append(letter);
			 }
		 }
		String reword=sentence.toString();
		return reword;
	}
	
	
	}
