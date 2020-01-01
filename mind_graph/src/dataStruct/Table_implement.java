package dataStruct;

import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Table_implement implements Table{
	static int dx;
	static int dy;//
	static int cu_level;
	int valiable;
	int last_used;
	final public int button_Xdistance = 50;
	final public int init_font_size = 28;
	final int first_node_x = 200;
	final int first_node_y = 500;
	public Nodes [] table;
	public Table_implement() {
		dx = 300;
		dy = 200;
		cu_level = -1;
		valiable = -1;
		last_used = 1;
		table = new Nodes[128];
		for (int i = 0; i < 128; ++i) {
			table[i] = new Nodes();
		}
//		table[0].set_message("Root");
//		table[0].valid = true;
	}
	
	@Override
	public int creat_child_node(int father_id) throws creat_fail {
		// TODO Auto-generated method stub
		if (valiable != -1) {
			int id = valiable;
			valiable = table[valiable].next_node;
			table[father_id].add_child(id);
			table[id].father_id = father_id;
			table[id].valid = true;
			table[id].clear_child();
			return id;
		}
		else {
			if (last_used < 128) {
				int id = last_used++;
				//System.out.println("father_id: " + father_id);
				//System.out.println("table[father_id]: " + table[father_id]);
				table[father_id].add_child(id);
				table[id].father_id = father_id;
				table[id].valid = true;
				return id;
			}
			else throw (new creat_fail());
		}
	}
	public void create_child_node_from_file(String data) {
		
		
		//先清除所有节点，再重新添加节点
		for (int j = 0; j < 128; ++j) {
			table[j] = new Nodes();
		}
		table[0].valid = true;
		try {
			JSONObject dataJson = new JSONObject(data);
			JSONArray nodes = dataJson.getJSONArray("nodes");
			
			for (int i = 0; i < nodes.length(); ++i) {
				JSONObject info = nodes.getJSONObject(i);
				int id = info.getInt("id");
				String message = info.getString("message");
				
				JSONArray childlist = info.getJSONArray("childlist");
				int father_id = info.getInt("father_id");
				boolean valid1 = info.getBoolean("valid");
				int next_node1 = info.getInt("next_node");
				table[id].set_message(message);
				table[id].father_id = father_id;
				table[id].valid = valid1;
				table[id].next_node = next_node1;
				for (int j = 0; j < childlist.length(); ++j) {
					int child = (int)childlist.get(j);
					//System.out.println(child);
					table[id].add_child(child);
				}
				
			}
			last_used = dataJson.getInt("last_used");
			valiable = dataJson.getInt("valiable");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public int creat_brother_node(int current_id) throws creat_fail {
		// TODO Auto-generated method stub
		if (valiable != -1) {
			int id = valiable;
			valiable = table[valiable].next_node;
			table[table[current_id].father_id].add_child(id);
			table[id].father_id = table[current_id].father_id;
			table[id].valid = true;
			table[id].clear_child();
			return id;
		}
		else {
			if (last_used < 128) {
				int id = last_used++;
				table[table[current_id].father_id].add_child(id);
				table[id].father_id = table[current_id].father_id;
				table[id].valid = true;
				return id;
			}
			else throw (new creat_fail());
		}
	}

	@Override
	public void remove_node(int id) throws remove_fail {
		// TODO Auto-generated method stub
		int len = table[id].child_len();
		System.out.println("id: " + id);
		System.out.println("len: " + len);
		for (int i = 0; i < len; ++i) {
			System.out.println("i: " + i);
			int cid = table[id].childlist.get(0);
			remove_node(cid);
		}
		int temp = valiable;
		valiable = id;
		table[id].next_node = temp;
		table[id].valid = false;
		Integer Id = Integer.valueOf(id);
		table[table[id].father_id].childlist.remove(Id);
	}
	public int get_brother_before(int root) {
		int father = table[root].father_id;
		int brother = -1;
		if (father != -1 && table[father].child_len() > 1) {//父节点存在且有兄弟节点
			
			for (int i = 1; i < table[father].child_len(); ++i) {
				if (table[father].childlist.get(i) == root) {
					brother = table[father].childlist.get(i-1);
					break;
				}
			}
			
		}
		return brother;
		
	}
	public int get_brother_after(int root) {
		int father = table[root].father_id;
		int brother = -1;
		if (father > 0 && table[father].child_len() > 1) {//父节点存在
			
			for (int i = 0; i < table[father].child_len() - 1; ++i) {
				if (table[father].childlist.get(i) == root) {
					brother = table[father].childlist.get(i+1);
					break;
				}
			}
			
		}
		return brother;
		
	}
	@Override
	public Nodes retreive(int id) {
		// TODO Auto-generated method stub
		return table[id];
	}
	public void move_up(int id) {
		int grandpa = table[table[id].father_id].father_id;
		int father = table[id].father_id;
		table[id].father_id = grandpa;
		table[grandpa].add_child(id);
		table[father].remove_child(id);
	}
	
	
	public void retraverse(int root, MyPanel UI) {
		// TODO Auto-generated method stub
		
	}
	public void correct_sub_root(int root, int width)
	{
		for (int i = 0; i < table[root].child_len(); ++i) {
			int id = table[root].childlist.get(i);
			table[id].x += width;
			if (table[id].child_len() > 0) {
				correct_sub_root(id, width);
			}
		}
	}
	public void creat_position_helper(int root, int level) {
		// TODO Auto-generated method stub
		table[root].x = dx + level*button_Xdistance;
		String message = table[root].get_message();
		table[root].width = message.length() * init_font_size;
		
		table[root].width = (int)(Math.pow(0.8, Math.min(3,level + 1)) * table[root].width);
		table[root].width = Math.max(table[root].width, 128);
		//System.out.println("width:" + table[root].width);
		if (table[root].width < Math.pow(0.8, level + 1) * 200) {
			table[root].width = (int)(Math.pow(0.8, level + 1) * 200);
		}
		if (table[root].child_len() == 0) {
			table[root].y = dy;
			dy += 100;
			return;
		} 
		else {
			for (int i = 0; i < table[root].child_len(); ++i) {
				creat_position_helper(table[root].childlist.get(i), level+1);
			}
			int total_y = 0;
			for (int i = 0; i < table[root].child_len(); ++i) {
				total_y = total_y + table[table[root].childlist.get(i)].y;
			}
			correct_sub_root(root, table[root].width);
			table[root].y = total_y/table[root].child_len();
		}
	}
	public void creat_position(int root, int level) {
		creat_position_helper(root, level);
		dy = 200;
		int x_offset = first_node_x - table[1].x;
		int y_offset = first_node_y - table[1].y;
		//System.out.println(x_offset + ":" + y_offset);
		for (int i = 1; i < last_used; ++i) {
			table[i].x += x_offset;
			table[i].y += y_offset;
		}
	}

}