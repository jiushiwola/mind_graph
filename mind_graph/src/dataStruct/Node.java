package dataStruct;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class Node {
	private String message;
	public Vector<Integer> childlist;
	public int father_id;
	public Node() {
		childlist = new Vector<Integer>();
		message = "×ÓÖ÷Ìâ";
		father_id = -1;
	}
	public Node(String message) {
		this.message = new String(message);
	}
	public Node(String message,  Vector<Integer> childlist) {
		this.message = new String(message);
		this.childlist = childlist;
	}

	public Node(String message,int father_id) {
		this.message = new String(message);
		this.father_id = father_id;
	}
	public Node(String message, int father_id, Vector<Integer> childlist) {
		this.message = new String(message);
		this.childlist = childlist;
		this.father_id = father_id;
	}
	public Node (Node temp) {
		this.message = temp.message;
		this.childlist = temp.childlist;
		this.father_id = temp.father_id;
	}
	public void set_message(String words) {
		this.message = words;
	}
	public String get_message() {
		return this.message;
	}
	public void remove_child(int id) {
		Integer Id = Integer.valueOf(id);
		this.childlist.remove(Id);
	}
	public void add_child(int id) {
		Integer Id = Integer.valueOf(id);
		this.childlist.add(Id);
	}
	public void set_father_id(int id) {
		this.father_id = id;
	}
	public int child_len() {
		return childlist.size();
	}
	public void clear_child() {
		this.childlist.clear();
	}
}
