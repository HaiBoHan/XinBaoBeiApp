package com.xinbaobeijiaoyu.ceping.model;

import java.util.ArrayList;

import com.xinbaobeijiaoyu.ceping.model.KeywordsQuestion.KeywordsInfo;

public class QuestionHistory {
	public boolean IsSuccess;
	public String Message;
	public ArrayList<HistoryInfo> ResultJson;

	public static class HistoryInfo {
		public String UserID;
		public String UserAccount;
		public String KeyWords;
		public String CreatedTime;
		public String Age;
		public String QuestionID;
		public String Solution;
	}
}
