package com.nextwordpredictor.main;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

class nextwordpredictormain  {
	public static void displaySuggestions(ResultSet rs) throws SQLException, ClassNotFoundException
	{   
		int count=0;
		 while(rs.next())
	    	  {
			    count++;
	    		String suggestedWords[]=rs.getString("word").split(" ");	    		
	    		System.out.print(suggestedWords[suggestedWords.length-1]);  
	    		System.out.print(",");
	    	   }  
		if(count==0)
		{
			ngramSuggestor ns=new ngramSuggestor();	  
			rs=ns.suggestUnigrams();
			displaySuggestions(rs);
		}
	}
	
  public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
  ngramSuggestor ns=new ngramSuggestor();	
  Unigram ug=new Unigram();
	Bigram bg=new Bigram();
	Trigram tg=new Trigram();
  int exit=0;
  ResultSet suggestions;
  while(exit!=1)
  {
	  Scanner myObj = new Scanner(System.in);
	  System.out.print("Enter message:");
	  suggestions=ns.suggestUnigrams();
	  System.out.print("[");
	  displaySuggestions(suggestions);
	  System.out.print("---]");
	  String word = myObj.nextLine();
	  if(word.equalsIgnoreCase("quit"))
	  {
		  exit=1;
		  break;
	  }
      String message="";
      while(!word.contains("."))
      {
    	  message=message+" "+word;
    	  message=message.trim();
    	  if(message.split(" ").length==1)
    	  {
    		  suggestions=ns.suggestBigrams(message);
    	  }
	      if(message.split(" ").length>=2)
	      {
		      suggestions=ns.suggestTrigrams(message);
		  }  
	      System.out.print(message+"[");
	      displaySuggestions(suggestions); 
	      System.out.print("---]");
         word = myObj.nextLine();
         if(word.equalsIgnoreCase("exit"))
         {
        	 exit=1;
         }
  		}
      message=message+" "+word;	
      ug.insertunigramfromtext(message);
      bg.insertbigramfromtext(message);
      tg.inserttrigramfromtext(message);
      ns.usingPrintWriter(message);
      System.out.println(message);
}
  }}