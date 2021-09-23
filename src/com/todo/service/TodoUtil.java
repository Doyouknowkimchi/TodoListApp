package com.todo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("[Create item Section]\n"
				+ "enter the title > ");
		
		title = sc.nextLine();
		//sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		
		System.out.printf("enter the description > ");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {

		System.out.printf("[Delete Item Section]\n"
				+ "enter the title of item to remove > ");
		
		Scanner sc = new Scanner(System.in);
		String title = sc.nextLine();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("[Edit Item Section]\n"
				+ "enter the title of the item you want to update\n"
				+ "\n");
		String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("title doesn't exist");
			return;
		}

		System.out.println("enter the new title of the item");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the new description ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("item updated");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "]" + " " + item.getDesc() +" - "+ item.getDateToStr()); // 시간추가
			//System.out.println(item.toSaveString());
		}
	}
	
	public static void listAll_txt(TodoList l) {
		for (TodoItem item : l.getList()) {
			//System.out.println("[" + item.getTitle() + "]" + " " + item.getDesc()); // 시간추가
			System.out.println(item.toSaveString());
		}
	}
	
	public static void loadList(TodoList l, String string) {
		try {
			 File file = new File(string);
			 FileReader filereader = new FileReader(file);
			 BufferedReader bufReader = new BufferedReader(filereader);
			 String line = "";
			 
	         while((line = bufReader.readLine()) != null){ // 나눠서 저장
	        	 line += "##";
	        	 StringTokenizer tok = new StringTokenizer(line,"##");
	                
	        	 String title = tok.nextToken();
	        	 String desc = tok.nextToken();
	        	 String dateToStr = tok.nextToken();
	        	 
	        	 TodoItem item = new TodoItem(title, desc, dateToStr);
	        	 
	        	 l.addItem(item);
	            }
	            //.readLine()은 끝에 개행문자를 읽지 않는다.            
	            bufReader.close();
	        }catch (FileNotFoundException e) {
	            // TODO: handle exception
	        }catch(IOException e){
	            System.out.println(e);
	        }
	    }
	
	public static void saveList(TodoList l, String string) {
		try {
			
			FileWriter fw = new FileWriter(string, false);
			
			for (TodoItem item : l.getList()) {
				fw.write(item.toSaveString());
				fw.flush();
			}
			fw.close();
		}catch(Exception e){
            e.printStackTrace();
        }
	}
}
