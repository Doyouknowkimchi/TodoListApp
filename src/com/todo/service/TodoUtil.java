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
		
		String category, title, desc, due_date, dateToStr;
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("[Create item Section]\n"
				+ "enter the category > ");		
		category = sc.nextLine();
		
		System.out.printf("enter the title > ");		
		title = sc.nextLine();
		//sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		
		System.out.printf("enter the description > ");
		desc = sc.nextLine();
		
		System.out.printf("enter the due_date(yyyy/mm/dd) > ");
		due_date = sc.nextLine();
		
		TodoItem t = new TodoItem(list.listSize()+1, category, title, desc, due_date);
		
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) { // �����ϸ� for�� ������ ����Ʈ ���� �� �ٽ� �����, �̶��� i++��..

		System.out.printf("[Delete Item Section]\n"
				+ "enter the number of Item to remove > ");
		
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		sc.nextLine();
		
		System.out.printf("\n");
		for (TodoItem item : l.getList()) {
			if (number == item.getNum()) {
				System.out.println("[" + item.getCategory() + "]" +" "+ item.getTitle()+ " " + item.getDesc() +" - "+ item.getDue_date() + " - "+ item.getDateToStr());//�ش� ���� ���
				System.out.printf("delete the above items? (y/n) > ");
				
				String ans = sc.nextLine();
				
				if(ans.equalsIgnoreCase("y")) { // Y or N �ޱ�
					l.deleteItem(item);
					System.out.println("It's been deleted.");					
				}
				
				break;
			}
		}
		
		int i = 1;
		
		for (TodoItem item : l.getList()) {
			item.setNum(i);
			i++;
		}
	}


	public static void updateItem(TodoList l) { // @@@@@ �׸� ������ �ٲ���Ѵ�. @@@@@, ��ȣ�� �˻��̶� �̸� ��ġ�� ���� �ذ� �ǰڴ�.
		
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("[Edit Item Section]\n"
				+ "enter the number of the item you want to update > ");
		int num = sc.nextInt();
		sc.nextLine();
		
		if (l.listSize()+1<num) {
			System.out.println("doesn't exist number");
			return;
		}
		
		System.out.printf("enter the new category > ");
		String new_category = sc.nextLine().trim();

		System.out.printf("enter the new title of the item > ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("title can't be duplicate");
			return;
		}
		
		System.out.printf("enter the new description > ");
		String new_description = sc.nextLine().trim();
		
		System.out.printf("enter the new dueDate > ");
		String new_dueDate = sc.nextLine().trim();//���� ���׵� �߰�
		
		for (TodoItem item : l.getList()) {
			if ( num == item.getNum()) { 
				l.deleteItem(item);
				TodoItem t = new TodoItem(num, new_category, new_title, new_description, new_dueDate); //��¥ ���� �ȵż� due_date ������
				l.addItem(t);
				System.out.println("item updated");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[" + "complete list, total : " + l.listSize() + "]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.getNum() +". "+ "[" + item.getCategory() + "]" +" "+ item.getTitle()+ " - " + item.getDesc() +" - "+ item.getDue_date() + " - "+ item.getDateToStr()); // �ð��߰�
			//System.out.println(item.toSaveString());
		}
	}
	
	public static void listAll_txt(TodoList l) {
		for (TodoItem item : l.getList()) {
			//System.out.println("[" + item.getTitle() + "]" + " " + item.getDesc()); // �ð��߰�
			System.out.println(item.toSaveString());
		}
	}
	
	public static void find(TodoList l, String tok) {
		
		int count = 0;
		Scanner sc = new Scanner(System.in);
		
		for (TodoItem item : l.getList()) {
			if((item.getTitle().contains(tok))||(item.getDesc().contains(tok))) {
				System.out.println(item.getNum() +". "+ "[" + item.getCategory() + "]" +" "+ item.getTitle()+ " - " + item.getDesc() +" - "+ item.getDue_date() + " - "+ item.getDateToStr());
				count++;
			}
		}
		System.out.println("�� "+count+"���� �׸��� ã�ҽ��ϴ�.");
		
	}
	
	public static void loadList(TodoList l, String string) { 
		try {
			 File file = new File(string);
			 FileReader filereader = new FileReader(file);
			 BufferedReader bufReader = new BufferedReader(filereader);
			 String line = "";
			 
	         while((line = bufReader.readLine()) != null){ // ������ ����
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
	            //.readLine()�� ���� ���๮�ڸ� ���� �ʴ´�.            
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
