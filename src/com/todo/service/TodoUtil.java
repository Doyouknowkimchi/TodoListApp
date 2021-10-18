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
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("[Create item Section]\n"
				+ "enter the title > ");		
		title = sc.nextLine();
		
		if(list.isDuplicate(title)) {
			System.out.println("duplicate!");
			return;
		}
		
		System.out.printf("enter the category > ");		
		category = sc.nextLine().trim();
		
		System.out.printf("enter the description > ");
		desc = sc.nextLine().trim();
		
		System.out.printf("enter the due_date(yyyy/mm/dd) > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc, category, due_date); 
		if(list.addItem(t)>0)
			System.out.println("added.");
	}

	public static void deleteItem(TodoList l) { // 삭제하면 for문 돌려서 리스트 순서 싹 다시 줘야함, 이때는 i++로..

		System.out.printf("[Delete Item Section]\n"
				+ "enter the number of Item to remove > ");
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		StringTokenizer st = new StringTokenizer(str);
		System.out.printf(str + "\n");
		
		while(st.hasMoreTokens()){
			int index = Integer.parseInt(st.nextToken());
			System.out.printf(index + "\n");
			if (l.deleteItem(index)>0) {
				System.out.println(index + " is deleted");
			}
		}
		
	}


	public static void updateItem(TodoList l) { // @@@@@ 항목 순서로 바꿔야한다. @@@@@, 번호로 검색이라 이름 겹치는 문제 해결 되겠다.
		
		String new_title, new_desc = null, new_category, new_due_date = null;
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("[Edit Item Section]\n"
				+ "enter the number of the item you want to update > ");
		int index = sc.nextInt();
		sc.nextLine();
		
		System.out.printf("enter the new title > ");
		new_title = sc.nextLine().trim();
		
		System.out.printf("enter the new category > ");
		new_category = sc.nextLine().trim();

	//	if (l.isDuplicate(new_title)) {
	//		System.out.println("title can't be duplicate");
	//		return;
	//	}
		
		System.out.printf("enter the new description > ");
		String new_description = sc.nextLine().trim();
		
		System.out.printf("enter the new dueDate > ");
		String new_dueDate = sc.nextLine().trim();//수정 사항들 추가
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_dueDate);
		t.setId(index);
		if(l.editItem(t)>0);{
			l.editItem(t);
			System.out.println("editing complete");
			}
	}

	public static void listAll(TodoList l) {
		System.out.println("[" + "complete list, total : " + l.getCount() + "]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString()); // 시간추가
			//System.out.println(item.toSaveString());
		}
	}
	
	public static void listAll_txt(TodoList l) {
		for (TodoItem item : l.getList()) {
			//System.out.println("[" + item.getTitle() + "]" + " " + item.getDesc()); // 시간추가
			System.out.println(item.toSaveString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			//System.out.println("[" + item.getTitle() + "]" + " " + item.getDesc()); // 시간추가
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, int comp) {
		System.out.printf("[완료 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList(1)) {
			//System.out.println("[" + item.getTitle() + "]" + " " + item.getDesc()); // 시간추가
			System.out.println(item.toString());
		}		
	}
	
	public static void comp(TodoList l,String str) { //tokent 다시 숫자로 만든다.
		
		//list에서 item 가져와서...?
		//comp while에서 계속 작동, 가져오는 것도 하나. edit comp는 해당하는 item 넘버 수정하자. edititem으로 setcomp 하면 되겠는데.
		StringTokenizer st = new StringTokenizer(str);
		str = st.nextToken();
		
		//System.out.println(st);
		while(st.hasMoreTokens()){
			int id = Integer.parseInt(st.nextToken()); //숫자 다 tokenize 해줘야한다
			//System.out.println(id);
			for (TodoItem item : l.getList()) {
				if(item.getId() == id) {
					item.setComp(1); //하고 나서 반영을 해줘야한다
					if(l.checkComp(item)>0);{
						l.checkComp(item);
						System.out.println("editing complete");
						}	
				}
			}
		}
	}
	
	public static void find(TodoList l, String tok) {
		
		int count = 0;
		
		for (TodoItem item : l.getList(tok)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 "+count+"개의 항목을 찾았습니다.");
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
	        	 
	        	 int num = Integer.parseInt(tok.nextToken());  
	        	 String category = tok.nextToken();   
	        	 String title = tok.nextToken();
	        	 String desc = tok.nextToken();
	        	 String due_date = tok.nextToken();
	        	 String dateToStr = tok.nextToken();
	        	 
	        	 TodoItem item = new TodoItem(num, category, title, desc, due_date, dateToStr);
	        	 
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
