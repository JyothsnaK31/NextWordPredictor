package com.nextwordpredictor.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Dataclean {
public void datacleanset() throws IOException
{
	File file = new File("C:\\Users\\Mallika\\en_US.twitter.txt"); 
	 BufferedReader br = new BufferedReader(new FileReader(file));
	 String eachline;
	 while ((eachline = br.readLine()) != null)
	 { 
		 String line ="";
		 String tosplit=eachline.trim();
		 if(!tosplit.equals(""))
		 {
		 String[] splitwords= tosplit.split(" ");
		 for(int i=0;i<splitwords.length;i++)
		 {
			 String word=splitwords[i];
			 
			 if(word.matches("^[a-zA-Z]*$"))
			 {
				 
				 line=line+" "+word; 
				 //System.out.println(line);
			 }else {
				 StringBuilder sentence = new StringBuilder();
			 for(int j=0;j<word.length();j++)
			 {
				 char letter=word.charAt(j);
				 if(Character.isAlphabetic(letter))
				 {
					 sentence.append(letter);
				 }
			 }
			 line=line+" "+sentence.toString();
			 }
		 }
		 usingPrintWriter(line.trim());
	 }
	 }
}
public static void usingPrintWriter(String msg) throws IOException 
{
    FileWriter fileWriter = new FileWriter("C:\\Users\\Mallika\\Trainingdatasetclean.txt", true); //Set true for append mode
    PrintWriter printWriter = new PrintWriter(fileWriter);
    printWriter.println(msg);  //New line
    printWriter.close();
}
public static void main(String args[]) throws IOException
{
	Dataclean d=new Dataclean();
	d.datacleanset();
}
}