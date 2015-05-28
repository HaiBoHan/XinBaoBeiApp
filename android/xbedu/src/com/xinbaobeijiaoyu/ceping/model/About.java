package com.xinbaobeijiaoyu.ceping.model;

import java.util.ArrayList;

public class About{
	public ArrayList<AboutUsInfo> ResultJson;
	public boolean IsSuccess;
	public String Message;
	
	public static class AboutUsInfo{
		public int ID;
		public String AboutUs_Content;
	}
}
