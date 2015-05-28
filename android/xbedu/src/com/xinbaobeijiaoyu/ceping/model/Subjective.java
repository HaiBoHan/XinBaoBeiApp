package com.xinbaobeijiaoyu.ceping.model;

import java.util.ArrayList;


public class Subjective  {
	public boolean IsSuccess;
	public String Message;
	public ArrayList<SubjectiveInfo> ResultJson;

	public static class SubjectiveInfo {
		public String ID;
		public String Question_Title;
		public String Question_Title_Part1;
		public String Question_Title_Part2;
		public String AboutAge;
		public String Answer;
		public String KeyWords;
		public String AboutAgeBegin;
		public String AboutAgeEnd;
	}
}
