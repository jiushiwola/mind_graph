package dataStruct;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	public static void main(String args[]) throws creat_fail, remove_fail {
		Table_implement test = new Table_implement();
		test.creat_child_node(0);
		test.creat_child_node(0);
		Nodes item1 = test.retreive(1);
		Nodes item2 = test.retreive(2);
		int id1 = test.creat_brother_node(1);
		int id2 = test.creat_child_node(1);
		int id3 = test.creat_brother_node(id2);
		int id4 = test.creat_child_node(id2);
		int id5 = test.creat_child_node(id4);
//		int id6 = test.creat_brother_node(1);
//		System.out.println(id1);
//		System.out.println(id2);
//		System.out.println(id3);
//		System.out.println(id4);
//		System.out.println(id5);
//		System.out.println(id6);
		Nodes titem1 = test.retreive(id1);
		Nodes titem2 = test.retreive(id2);
		Nodes titem3 = test.retreive(id3);
		Nodes titem4 = test.retreive(id4);
		Nodes titem5 = test.retreive(id5);
//		Nodes titem6 = test.retreive(id6);
//		System.out.println(item1.valid);
//		System.out.println(item2.valid);
//		System.out.println(titem1.valid);
//		System.out.println(titem2.valid);
//		System.out.println(titem3.valid);
//		System.out.println(titem4.valid);
//		System.out.println(titem5.valid);
//		System.out.println(titem6.valid);
//		System.out.println(item1.father_id);
//		System.out.println(item2.father_id);
//		System.out.println(titem1.father_id);
//		System.out.println(titem2.father_id);
//		System.out.println(titem3.father_id);
//		System.out.println(titem4.father_id);
//		System.out.println(titem5.father_id);
//		System.out.println(titem6.father_id);
		item1.set_message("hello,");
		item2.set_message("world");
		titem1.set_message("1");
		titem2.set_message("2");
		titem3.set_message("3");
		titem4.set_message("4");
		titem5.set_message("5");
//		System.out.println(item1.get_message());
//		System.out.println(item2.get_message());
		test.remove_node(id4);
//		System.out.println(item1.valid);
//		System.out.println(item2.valid);
//		System.out.println(titem1.valid);
//		System.out.println(titem2.valid);
//		System.out.println(titem3.valid);
//		System.out.println(titem4.valid);
//		System.out.println(titem5.valid);
//		System.out.println(titem6.valid);
//		System.out.println("after delete:");
		id4 = test.creat_child_node(id3);
		id5 = test.creat_brother_node(id4);
//		System.out.println(id4);
//		System.out.println(id5);
    	titem4 = test.retreive(id4);
		titem5 = test.retreive(id5);
		titem4.set_message("good");
		titem5.set_message("bad");
		test.move_up(id5);
//		System.out.println(item1.father_id);
//		System.out.println(item2.father_id);
//		System.out.println(titem1.father_id);
//		System.out.println(titem2.father_id);
//		System.out.println(titem3.father_id);
//		System.out.println(titem4.father_id);
//		System.out.println(titem5.father_id);
//		System.out.println(titem6.father_id);
		JFrame test_ui = new JFrame();
		MyPanel test_panel = new MyPanel();
		test_ui.setSize(1000, 1000);
		test_ui.setVisible(true);
		test.retraverse(0, test_panel);		
		test_ui.add(test_panel);
	}
}
class MyFrame extends Frame{
	
}
class MyPanel extends JPanel{
	public int [] table;
	public int [] table_x;
	public int [] table_y;
	public MyPanel() {
		super();
		table = new int [128];
		table_x = new int [128];
		table_y = new int [128];
		for (int i = 0; i < 128; ++i) {
			table[i] = 0;
			table_x[i] = 0;
			table_y[i] = 0;
		}
		this.setLayout(null);
		this.setSize(1000, 1000);
		//this.g = g;
	}
	public void view(String message, int father_id, int current_id, double d_x, double d_y) {
		if (father_id == -1) {
			JLabel text = new JLabel(message, JLabel.CENTER);
			text.setSize(80, 40);
			text.setLocation(500, 500);
			text.setOpaque(true);
			text.setBackground(Color.green);
			text.setForeground(Color.white);
			this.add(text);
			table_x[current_id] = 500;
			table_y[current_id] = 500;
		}
		else {
			JLabel text = new JLabel(message, JLabel.CENTER);
			text.setSize(80, 40);
			int x = 0;
			int y = 0;
			switch(table[father_id]) {
				case 0:
					x = table_x[father_id];
					y = table_y[father_id] - 50;
					break;
				case 1:
					x = table_x[father_id]- 100;
					y = table_y[father_id];
					break;
				case 2:
					x = table_x[father_id] + 100;
					y = table_y[father_id];
					break;
				case 3:
					x = table_x[father_id] + 100;
					y = table_y[father_id] + 50;
					break;
			}
			text.setLocation(x, y);
			text.setOpaque(true);
			text.setBackground(Color.green);
			text.setForeground(Color.white);
			this.add(text);
			table_x[current_id] = x;
			table_y[current_id] = y;
			table[father_id]++;
		}
		
	}
}
