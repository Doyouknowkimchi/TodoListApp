package com.todo.menu;

import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoList;
import com.todo.service.TodoUtil;

public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("1. Add a new item ( add )");
        System.out.println("2. Delete an existing item ( del )");
        System.out.println("3. Update an item  ( edit )");
        System.out.println("4. List all items ( ls )");
        System.out.println("5. List all completed items ( find )");
        System.out.println("5. List all completed items ( comp )");
        System.out.println("5. List all completed items ( ls_comp )");
        System.out.println("6. sort the list by name ( ls_name_asc )");
        System.out.println("7. sort the list by name ( ls_name_desc )");
        System.out.println("8. sort the list by date ( ls_date_asc )");
        System.out.println("9. sort the list by date ( ls_date_desc )");
        System.out.println("10. exit (Or press escape key to exit)");
        System.out.println("Enter your choice >");
    }
    
    
    
    public static void prompt() {
    	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		
		TodoUtil.loadList(l, "C:\\Users\\dragu\\Desktop\\Spring\\todolist.txt");
    	
    	do {
			
			System.out.printf("\nCommand > ");

			isList = false;
			String choice = sc.nextLine();
			String choice_1 = null;			
			String choice_2 = null;
			
			while(choice.equals(choice_2)) {
				choice = sc.nextLine();
			}
			
			StringTokenizer tok = new StringTokenizer(choice," ");
			choice_1 = tok.nextToken();
			
			if(tok.hasMoreTokens()) {
			choice_2 = tok.nextToken(); // choice_2 자체가 띄어쓰기 하나만 인식했구나 이걸 통으로 가져오려면?
			}
			
			switch (choice_1) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "ls_comp":
				TodoUtil.listAll(l, 1);
				break;	
				
			case "find":
				TodoUtil.find(l, choice_2);
				break;
				
			case "comp":
				TodoUtil.comp(l,choice);
				break;
			
			case "ls_name_asc":
				System.out.println("name_asc");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("name_desc");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date_asc":
				System.out.println("date_asc");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("date_asc");
				TodoUtil.listAll(l, "due_date", 0);
				break;	
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true; //TodoList에 저장하는 매소드 만들고 여기서 부르기	
				TodoUtil.saveList(l, "C:\\Users\\dragu\\Desktop\\Spring\\todolist.txt");
				break;

			default:
				System.out.println("please enter one of the above mentioned command");
				break;
			}
			
			if(isList) TodoUtil.listAll(l); // listAll(TodoList l), l 넣어줘야 한다.
		} while (!quit);
    }
}
