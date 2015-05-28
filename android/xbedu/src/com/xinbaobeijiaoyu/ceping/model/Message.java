package com.xinbaobeijiaoyu.ceping.model;

import java.util.ArrayList;


public class Message{
	public ArrayList<MessageInfo> ResultJson;
	public boolean IsSuccess;
	public String Message;
	
	public static class MessageInfo{
		public int ID;
		public int User_ID;
		public int Poster_ID;
		public boolean IsRead;
		public String Message_Title;
		public String Message_Content;
		public String MessDate;
		public String Subhead;
	}
}
