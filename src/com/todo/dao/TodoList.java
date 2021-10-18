package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;


public class TodoList {
	Connection conn;
	
	private List<TodoItem> list;

	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
	
	public int listSize() {
		return this.list.size();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date)"
				+"values(?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getDateToStr()); // 문자로 싹 바꿔줘야겠네...
			pstmt.setString(5,t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();			
		} catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}
	
	public Boolean isDuplicate(String title) {
		for (TodoItem item : getList()) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int editItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?"
				+" where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getDateToStr());
			pstmt.setString(5,t.getDue_date());
			pstmt.setInt(6,t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int checkComp(TodoItem t) {
		String sql = "update list set is_completed= ?" + " where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,t.getComp());
			pstmt.setInt(2,t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp =rs.getInt("is_completed");	
				TodoItem t = new TodoItem(title, description, category, due_date); 
				t.setId(id); // 이건 또 뭐고
				t.setComp(is_comp);
				t.setDateToStr(current_date); //일단 조금 있다가 생각하자
				list.add(t);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ? or category like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyword);
			pstmt.setString(2,keyword);
			pstmt.setString(3,keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp =rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, description, category, due_date); 
				t.setId(id); // 이건 또 뭐고
				t.setComp(is_comp);
				t.setDateToStr(current_date); //일단 조금 있다가 생각하자
				list.add(t);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}	
	
	public ArrayList<TodoItem> getList(int comp) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE is_completed = '1'";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp =rs.getInt("is_completed");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id); // 이건 또 뭐고
				t.setComp(is_comp);
				t.setDateToStr(current_date); //일단 조금 있다가 생각하자
				list.add(t);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}	
	
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, currunt_date, due_date)"
					+"values(?,?,?,?,?);";
		int records = 0;
		while((line = br.readLine())!=null) {
			StringTokenizer st =  new StringTokenizer(line,"##");
			String category = st.nextToken();
			String title = st.nextToken();
			String description = st.nextToken();
			String due_date = st.nextToken(); // 저장할 떄 toString이 문제인가.
			String current_date = st.nextToken();
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,title);
			pstmt.setString(2,description);
			pstmt.setString(3,category);
			pstmt.setString(4,current_date);
			pstmt.setString(5,due_date);
			int count = pstmt.executeUpdate();
			if(count>0) records++;
			pstmt.close();
		}
		System.out.println(records+" records read!!");
		br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby,int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY "+ orderby;
			if (ordering==0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(title, description, category, due_date); 
				t.setId(id); // 이건 또 뭐고
				t.setDateToStr(current_date); //일단 조금 있다가 생각하자
				list.add(t);
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	
}
