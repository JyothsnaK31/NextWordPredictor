package com.nextwordpredictor.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class ngramModel
{
	public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException
	{
		Unigram ug=new Unigram();
		Bigram bg=new Bigram();
		Trigram tg=new Trigram();
		Map<String,Integer> unigrams=ug.insertUnigram();
		System.out.println("unigram done");
		Map<String,Integer> bigrams=bg.insertBigrams(unigrams);
		System.out.println("Bigram done");
		tg.insertTrigrams(bigrams);
		System.out.println("triigram done");
	}

}
