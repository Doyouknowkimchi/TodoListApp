package com.todo.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.todo.menu.Menu;

public class TodoItem {
    private String title;
    private String desc;
    private Date current_date;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private String dateToStr;
	private String category;
	private String due_date;
    public int num;

    /*
    public TodoItem(Sㅡtring title, String desc){ // 지워야 된다
        this.title=title;
        this.desc=desc;
        this.current_date=new Date();
    	this.dateToStr = dateFormat.format(current_date);
    }
    */

	public TodoItem(int num , String category, String title, String desc, String due_date){
    	this.num = num;		
    	this.category=category;
        this.title=title;
        this.desc=desc;
        this.due_date=due_date;
        this.current_date=new Date();
    	this.dateToStr = dateFormat.format(current_date);
    }
    
    public TodoItem(String category, String title, String desc, String due_date, String dateToStr){
    	this.category=category;
        this.title=title;
        this.desc=desc;
        this.due_date=due_date;
    	this.dateToStr = dateToStr;
    }
    
    public TodoItem(int num, String category, String title, String desc, String due_date, String dateToStr){
    	this.num = num;
    	this.category=category;
        this.title=title;
        this.desc=desc;
        this.due_date=due_date;
    	this.dateToStr = dateToStr;
    }
    
    
    
    
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
    

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

    public Date getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(Date current_date) {
        this.current_date = current_date;
    }
    
    public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
    
	public String getDateToStr() {
		return dateToStr;
	}
	
	public void setDue_dateToStr(Date current_date) { //dueDate
    	this.dateToStr = dateFormat.format(current_date);
	}
	
	public void setDateToStr(Date current_date) {
    	this.dateToStr = dateFormat.format(current_date);
	}
    
    public String toSaveString() {		//txtfile 저장에 사용
    	return num+"##"+category + "##" + title + "##" + desc + "##" + due_date +"##"+ dateToStr + "\n";
    }
    
}
