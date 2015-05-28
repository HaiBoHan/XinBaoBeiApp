package com.xinbaobeijiaoyu.ceping.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Login implements Serializable{
	public ArrayList<UserInfo> ResultJson;
	public boolean IsSuccess;
	public String Message;
	
	//改成王总模式
	public static class UserInfo implements Serializable{
		public int ID;
		public String Account;
		public String Name;
		public String Passwd;
		public String Sex;
		public String Age;//年龄
		public String Birthday;//生日
		public String Region;//地区
		public String Address;
		public String SelfSign;//签名
		public String Pic;
		public String Branch_ID;
		public String Branch_Name;
		public String Unit_id;

		public String Tel;
		public String Judge_user_account;
//		public String ModifiedBy;
//		public String CreatedBy;
//		public Date CreatedOn;
//		public Date ModifiedOn;
		public String SysVersion;
		public String HintMessage;
		
		public String BabyName;
	}
}