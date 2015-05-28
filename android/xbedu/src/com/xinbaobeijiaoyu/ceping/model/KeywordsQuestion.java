package com.xinbaobeijiaoyu.ceping.model;

import java.util.ArrayList;

import com.xinbaobeijiaoyu.ceping.model.Subjective.SubjectiveInfo;

public class KeywordsQuestion  {
	public boolean IsSuccess;
	public String Message;
	public ArrayList<KeywordsInfo> ResultJson;

	public static class KeywordsInfo {
		public String ID;
		public String KeyWords;
		public ArrayList<KeywordsInfoSecond> Childs;
	}
	
	public static class KeywordsInfoSecond {
		public String ID;
		public String KeyWords;
		public ArrayList<KeywordsInfoTh> Childs;
	}
	
	public static class KeywordsInfoTh {
		public String ID;
		public String KeyWords;
	}
}
