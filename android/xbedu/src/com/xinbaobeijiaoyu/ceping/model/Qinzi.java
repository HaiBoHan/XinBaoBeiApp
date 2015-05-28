package com.xinbaobeijiaoyu.ceping.model;

import java.util.ArrayList;


public class Qinzi  {
	public boolean IsSuccess;
	public String Message;
	public ArrayList<QinziInfo> ResultJson;

	public static class QinziInfo {
		public int ID;
		public String Stage;
		public String AboutAge;
		public String Content;
	}
}
