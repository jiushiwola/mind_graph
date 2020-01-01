package dataStruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Nodes extends Node{
	public boolean valid;
	public int next_node;
	public int x;
	public int y;
	public int width;
	public Nodes() {
		super();
		valid = false;
		next_node = -1;
	}
	public Nodes(String message) {
		super(message);
		valid = false;
		next_node = -1;
	}
	public Nodes(String message, Vector<Integer> list) {
		super(message, list);
		valid = false;
		next_node = -1;
	}
	public Nodes(String message, int father_id) {
		super(message, father_id);
		valid = false;
		next_node = -1;
	}
	public Nodes(String message, int father_id,Vector<Integer> list) {
		super(message, father_id,list);
		valid = false;
		next_node = -1;
	}
	public Nodes(Node temp) {
		super(temp);
		valid = false;
		next_node = -1;
	}
}
