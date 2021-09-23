package com.todo.menu;

import java.util.Scanner;

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
        System.out.println("5. sort the list by name ( ls_name_asc )");
        System.out.println("6. sort the list by name ( ls_name_desc )");
        System.out.println("7. sort the list by date ( ls_date )");
        System.out.println("8. exit (Or press escape key to exit)");
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
			String choice = sc.next();
			switch (choice) {

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

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
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
			
			if(isList) l.listAll();
		} while (!quit);
    }
}
