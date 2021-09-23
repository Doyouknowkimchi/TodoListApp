package com.todo.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private Date current_date;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private String dateToStr;
    


    public TodoItem(String title, String desc){
        this.title=title;
        this.desc=desc;
        this.current_date=new Date();
    	this.dateToStr = dateFormat.format(current_date);
    }
    
    public TodoItem(String title, String desc, String dateToStr){
        this.title=title;
        this.desc=desc;
    	this.dateToStr = dateToStr;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(Date current_date) {
        this.current_date = current_date;
    }
    
	public String getDateToStr() {
		return dateToStr;
	}

	public void setDateToStr(Date current_date) {
    	this.dateToStr = dateFormat.format(current_date);
	}
    
    public String toSaveString() {		
    	return title + "##" + desc + "##" + dateToStr + "\n";
    }
}
