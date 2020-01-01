package dataStruct;

import javax.swing.JPanel;

class creat_fail extends Exception{
	
}
class remove_fail extends Exception{
	
}
public interface Table {
	int creat_child_node(int father_id) throws creat_fail;//return id;
	int creat_brother_node(int current_id) throws creat_fail;//return id;
	void remove_node(int id) throws remove_fail;//delete the node and it`s childs;
	Nodes retreive(int id);//return the node the id refer to;
	void move_up(int id);//move the node to the upper level;
	void retraverse(int root, MyPanel UI);//还不能用
	void creat_position(int root, int level);//还不能用
}