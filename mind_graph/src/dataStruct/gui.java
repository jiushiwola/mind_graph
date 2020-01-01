package dataStruct;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.border.StandardBorderPainter;
import org.jvnet.substance.painter.gradient.StandardGradientPainter;
import org.jvnet.substance.shaper.ClassicButtonShaper;
import org.jvnet.substance.skin.EmeraldDuskSkin;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;


import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Vector;
@SuppressWarnings("serial")

public class gui extends JFrame  {
	JPanel board;
	Panel toolBar;
	Panel remark;
	int current_id;
	int last_id;
	client client;
	//static int id_count;
	JMenuBar menuBar;
	Table_implement all_nodes; //������ݽṹ��ʵ�洢���ǽڵ�id֮��Ĺ�ϵ��û��ֱ�Ӵ洢�ڵ�
	myButton [] buttons;
	JTextField [] textFields;//ÿ��buttonΨһ��Ӧһ���ı���
	gui() throws creat_fail, remove_fail, UnknownHostException, IOException {
		super("Mind Mapping");
		current_id = 0; //���������idΪ0
		//id_count = 0;
		all_nodes = new Table_implement();
		buttons = new myButton[128];
		for (int i = 0; i < 128; ++i) {
			buttons[i] = null;
		}
		textFields = new JTextField[128];
		for (int i = 0; i < 128; ++i) {
			textFields[i] = null;
			
		}
		setSize(900,600);
		menuBar = addMenuBar(); //�˵�
		board = addBoard();   //��Ҫ��ʾ����
		JScrollPane scroll_board = new JScrollPane(board);
		Container container = getContentPane();
		container.add(scroll_board, BorderLayout.CENTER);
		scroll_board.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll_board.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add_create_new_button();
		toolBar = addToolBar();    //�Ҳ๤����
		JScrollPane scroll_board1 = new JScrollPane(toolBar);
		container.add(scroll_board1, BorderLayout.EAST);
		scroll_board1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		remark = addRemark();  //���·���ע��
		addPopMenu();  //�һ���굯���˵�
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		client = new client();
	}
	public static void main(String[] args) throws creat_fail, remove_fail, UnknownHostException, IOException {
		// װ�ؿ�ѡ�������  

		try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            SubstanceLookAndFeel.setSkin(new EmeraldDuskSkin());        
        } catch (Exception e) {
            System.err.println("Something went wrong!");
        }
		new gui().setVisible(true);
		
	}
	
	JMenuBar addMenuBar() {
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		JMenu file = new JMenu("�ļ�(F)");
		file.setMnemonic(KeyEvent.VK_F);    //���ÿ��ٷ��ʷ�
		JMenuItem open = new JMenuItem("��(O)", KeyEvent.VK_O);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("C:\\Users\\24749\\Desktop\\ziliao\\java�������\\����ҵ"));
                int val = fc.showOpenDialog(null);    //�ļ��򿪶Ի���
                if (val == JFileChooser.APPROVE_OPTION) {//ȷ�����ļ�
                	File file = fc.getSelectedFile();
                	 
					try {
						BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
						//BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
	                    String str=null;
	                    String data="";
	                    while((str=br.readLine())!=null) {
	                        data=data+str+"\n";
	                    }
	                    //��ӵ����ݽṹ��
	                    all_nodes.create_child_node_from_file(data);
	                    //���¸�������
	                    all_nodes.creat_position(1, 0);
	                    //���button
	                    create_buttons_from_file();
	                    
					} 
					catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
        
            		System.out.println("�ļ�:"+file.getAbsolutePath());
            		
            		//System.out.println(fc.getSelectedFile().getName());
          
                };
            }
        });
		
		JMenuItem newfile = new JMenuItem("�½�(N)", KeyEvent.VK_N);
		newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		newfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
                buttons[0].doClick();
            }
		});
		JMenuItem save = new JMenuItem("����(S)", KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("C:\\Users\\24749\\Desktop\\ziliao\\java�������\\����ҵ"));
                int val = fc.showSaveDialog(null);    //�ļ�����Ի���
                if (val == JFileChooser.APPROVE_OPTION) {//ȷ�������ļ�
                	File file = fc.getSelectedFile();
                	 
					try {
						//BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
						//BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
	                    
	                    String data="{\n\t \"nodes\": [\n\t\t";
	                    for (int i = 1; i < 128; ++i) {
	                    	if (buttons[i] != null) {
	                    		data += "{";
	                    		data += "\"id\": " + i + ",";
	                    		data += "\"message\": " + "\"" + all_nodes.table[i].get_message() + "\",";
	                    		
	                    		data += "\"childlist\": [";
	                    		for (int j = 0; j < all_nodes.table[i].child_len(); ++j) {
	                    			data += all_nodes.table[i].childlist.get(j) + ",";
	                    		}
	                    		if (all_nodes.table[i].child_len() > 0) {
	                    			data = data.substring(0, data.length() - 1);
	                    		}
	                    		data += "], ";
	                    		data += "\"father_id\": " + all_nodes.table[i].father_id + ",";
	                    		data += "\"valid\": " + all_nodes.table[i].valid + ",";
	                    		data += "\"next_node\": " + all_nodes.table[i].next_node ;
	                    		data += "},\n\t\t";
	                    	}
	                    }
	                    data = data.substring(0, data.length() - 4);
	                    data += "\n\t],\n";
	                    data += "\t\"last_used\": " + all_nodes.last_used + ",\n";
	                    data += "\t\"valiable\": " + all_nodes.valiable + "\n}";
	                    //bw.write(data);
	                    FileOutputStream fos= null;
	                    
	                        fos=new FileOutputStream(file.getAbsolutePath());
	                        //fos.write(textArea.getText().getBytes());
	                        fos.write(data.getBytes());
	                        fos.close();
	                        System.out.println("�ѱ���");
	                    
	                    
					} 
					catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
                    
        
            		System.out.println("�ļ�:"+file.getAbsolutePath());
            		
            		//System.out.println(fc.getSelectedFile().getName());
          
                
                }
            }
        });
		JMenuItem saveAs = new JMenuItem("���Ϊ");
		saveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fc = new JFileChooser();
                int val = fc.showSaveDialog(null);    //�ļ��򿪶Ի���
            }
        });
		JMenuItem upload = new JMenuItem("�ϴ����ƶ�");
		upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("C:\\Users\\24749\\Desktop\\ziliao\\java�������\\����ҵ"));
                int val = fc.showOpenDialog(null);    //�ļ��򿪶Ի���
                if (val == JFileChooser.APPROVE_OPTION) {//ȷ�����ļ�
                	File file = fc.getSelectedFile();
                	try {
						client.upload(file.getAbsolutePath(), file.getName());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
			}
		});
		JMenuItem download1 = new JMenuItem("���ƶ�����");
		download1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Vector<String> list = client.get_down_load_list();
					if (list.size() == 0) return;
					//System.out.println(list.size());
					//menuBar.setVisible(false);
					//JComboBox files = new JComboBox(list.toArray());
					JLabel label = new JLabel("ѡ��Ҫ���ص��ļ�");
	            	label.setBounds(0, 0, 100, 30);
	            	board.add(label);
					JComboBox files = new JComboBox(list.toArray());
					
//					files.addItem("111");
//					files.addItem("222");
					Container container = getContentPane();
	            	container.setVisible(false);
	            	board.add(files);
	            	files.setBounds(0, 40, 100, 20);
//	            	for (int i = 0; i < files.getItemCount(); ++i) {
//	            		Object item = files.getItemAt(i);
//	            		item.
//	            	}
	            	files.addItemListener(new ItemListener() {
	            		public void itemStateChanged(ItemEvent e) {
	            			String item = e.getItem().toString();
	            			System.out.println(item);
	            			Container container = getContentPane();
	            			container.setVisible(false);
	            			board.remove(files);
	            			board.remove(label);
	            			container.setVisible(true);
	            			try {
								client.download(item);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	            		}
	            	});
	            	
	            	container.setVisible(true);
					
//					menuBar.setVisible(false);
//					menuBar.setVisible(true);
					
				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		file.add(open);
		file.add(newfile);
		file.add(save);
		file.add(saveAs);
		file.add(upload);
		file.add(download1);
		JMenu insert = new JMenu("����(I)");
		file.setMnemonic(KeyEvent.VK_I);  
		JMenuItem insert_child = new JMenuItem("����������" );
		JMenuItem insert_after = new JMenuItem("��������");
		JMenuItem insert_before = new JMenuItem("�������⣨֮ǰ��");
		insert.add(insert_child);
		insert.add(insert_before);
		insert.add(insert_after);
		mb.add(file);
		mb.add(insert);
		return mb;
	}
	public void create_buttons_from_file() {
		board.setVisible(false);
		//�����
		for (int i = 0; i < 128; ++i) {
			if (buttons[i] != null) {
				board.remove(buttons[i]);
			}
			buttons[i] = null;
		}
		
		//���������ɵĽڵ���������ť
		for (int i = 1; i < 128; ++i) {
			if (all_nodes.table[i].valid == true) {
				System.out.println("node: " + i);
				myButton button = new myButton(all_nodes.table[i].get_message(), i);
				button.setBounds(all_nodes.table[i].x, all_nodes.table[i].y, all_nodes.table[i].width, 40);
				button.addKeyListener(new myButtonListener());
				button.addActionListener(new myButtonActionListener());
				buttons[i] = button;
				board.add(buttons[i]);
			}
		}
		//board.repaint();
		
		board.setVisible(true);
		//buttons[1].requestFocusInWindow();
		//current_id = 1;
	}
	myPanel addBoard() throws creat_fail, remove_fail {
		myPanel board = new myPanel();
		
		board.setBackground(Color.white);
		board.setLayout(null);
		board.setPreferredSize(new Dimension(3000,3000));
		return board;
	}
	class myPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.green);
			super.paintComponent(g);
			paint_helper(1, g);
		}
		void paint_helper(int root, Graphics g)
		{
			int id;
			int height;
			for (int i = 0; i < all_nodes.table[root].child_len(); ++i)
			{
				id = all_nodes.table[root].childlist.get(i);
				height = all_nodes.table[id].y - all_nodes.table[root].y;
				height *= 2;
				if (height < 0) {
					height = -height;
					g.drawArc(all_nodes.table[root].x + all_nodes.table[root].width - 5, all_nodes.table[id].y + 20, all_nodes.button_Xdistance * 2 + 13 , height, 90, 90);
				}
				else {
					g.drawArc(all_nodes.table[root].x + all_nodes.table[root].width - 5, all_nodes.table[id].y - height + 20, all_nodes.button_Xdistance * 2 + 13, height, -90, -90);
				}
				//System.out.println(id + ":" + all_nodes.table[id].x + ":" + all_nodes.table[id].y);
				if (all_nodes.table[id].child_len() != 0) {
					paint_helper(id, g);
				}
			}
		}

	}
	void add_create_new_button() {
		myButton initial_btn = new myButton("�½��հ�ͼ", 0);
		buttons[0] = initial_btn;
		initial_btn.setBounds(500, 300, 200, 80);
		initial_btn.setForeground(new Color(128,0,0));
		initial_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	
                board.remove(initial_btn);
            	Container container = getContentPane();
            	container.setVisible(false);
            	try {
            		last_id = 0;
					current_id = all_nodes.creat_child_node(0); //0�ڵ���һ������ʾ�Ľڵ�
					//System.out.println(current_id);
				} catch (creat_fail e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                myButton theme = createNode("��������", 200, 500, 160, 60, 20, current_id);
                buttons[current_id] = theme;
                board.add(theme);
                container.setVisible(true);
                buttons[current_id].setBackground(new Color(135,206,250));
                boolean i = theme.requestFocusInWindow();
                //theme.doClick();
                //System.out.println("i:" + i);
            }
		});
		board.add(initial_btn);
	}
	
	Panel addToolBar() {
		Panel toolBar_container = new Panel();
		
		toolBar_container.setBackground(Color.gray);
		JButton outline = new JButton("���");
		outline.addActionListener(new outlineListener());
		//toolBar_container.setLayout(null);
		toolBar_container.add(outline);
		//toolBar_container.setLayout(null);
		//toolBar_container.setPreferredSize(new Dimension(50, 1000));
		return toolBar_container;
	}
	Panel addRemark() {
		Panel bottom = new Panel();
		Container container = getContentPane();
		container.add(bottom, BorderLayout.SOUTH);
		bottom.setBackground(new Color(230,230,230));
		JTextArea jta = new JTextArea("��ע  ",1,30);
		jta.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(jta);
        Dimension size=jta.getPreferredSize();   
        jsp.setBounds(0,0,size.width,size.height);
        bottom.add(jsp);
        return bottom;
	}
	
	
	class myButton extends JButton{
		public int id;
		public myButton(String message, int id) {
			// TODO Auto-generated constructor stub
			super(message);
			//tContentAreaFilled(false);// �Ƿ���ʾ��Χ�������� ѡ��
			this.id = id;
			setContentAreaFilled(false);
		}
		public void paintComponent(Graphics g) {
		  //���Բ�Ǿ������� Ҳ����Ϊ������ͼ��
		  g.setColor(new Color(0,255,0));
		  g.fillRoundRect(5, 5, getSize().width - 10, getSize().height - 10, 20, 20);
		  //���������� ���򻭲���
		  super.paintComponent(g);
		 }
		 public void paintBorder(Graphics g) {
		  //���߽�����
		  g.setColor(Color.green);
		  g.drawRoundRect(5, 5, getSize().width - 10, getSize().height - 10, 20, 20);
		  if (this.id == current_id) {
			  g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
		  }
		 }
	}

    	
    
	

	myButton createNode(String str, int x, int y, int width, int height, int font_size, int id) {
		myButton button = new myButton(str, id);
        button.setBounds(x, y, width, height);
        //button.setBorder(BorderFactory.createLoweredBevelBorder());
        button.setFont(new Font("�����п�", 1, font_size));
        button.setForeground(new Color(128,0,0));
        button.setBorderPainted(true); 
        button.setBorder(null);
        button.addActionListener(new myButtonActionListener());
        button.addKeyListener(new myButtonListener());
        	
        return button;
        
	}
//remove
	public void remove_button(int id) {
		int father = all_nodes.table[id].father_id;
		if (father > 0) {
			current_id = father;
			buttons[current_id].setBackground(new Color(135,206,250));
			buttons[current_id].requestFocusInWindow();
		}
		else if (father == 0) {//Ҫɾ��������������ڵ�
			add_create_new_button();
		}
		for (int i = 0; i < buttons.length; ++i) {
			if (buttons[i] != null && buttons[i].id == id && all_nodes.table[id].valid == true) {
				int len = all_nodes.table[id].child_len();
				//System.out.println("button len:" + len);
				for (int j = 0; j < len; ++j) {
//					System.out.println("button j: " + j);
//					System.out.println("get: " + all_nodes.table[id].childlist.get(j));
					remove_button(all_nodes.table[id].childlist.get(j));
				}
				board.remove(buttons[i]);
				
				break;
			}
		}
		Container container = getContentPane();
    	container.setVisible(false);
    	container.setVisible(true);
	}
	void addPopMenu() {
        JPopupMenu jPopupMenuOne = new JPopupMenu();    
        ButtonGroup buttonGroupOne = new ButtonGroup();
        JMenu fileMenu = new JMenu("�ļ�");
        JMenuItem openFile = new JMenuItem("��");
        openFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fc = new JFileChooser();
                int val = fc.showOpenDialog(null);    //�ļ��򿪶Ի���
            }
        });
        JMenuItem closeFile = new JMenuItem("�ر�");
        JMenuItem newFile = new JMenuItem("�½�");
        JMenuItem save = new JMenuItem("����");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fc = new JFileChooser();
                int val = fc.showSaveDialog(null);    //�ļ��򿪶Ի���
            }
        });
        JMenuItem saveAs = new JMenuItem("���Ϊ");
        saveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fc = new JFileChooser();
                int val = fc.showSaveDialog(null);    //�ļ��򿪶Ի���
            }
        });
        fileMenu.add(openFile);
        fileMenu.add(closeFile);
        fileMenu.add(newFile);
        fileMenu.add(save);
        fileMenu.add(saveAs);
        jPopupMenuOne.add(fileMenu);
        //��ӷָ��
        jPopupMenuOne.addSeparator();
        //������ѡ�˵������ӵ�ButtonGroup������
        JRadioButtonMenuItem copyFile = new JRadioButtonMenuItem("����");
        JRadioButtonMenuItem pasteFile = new JRadioButtonMenuItem("ճ��");
        buttonGroupOne.add(copyFile);
        buttonGroupOne.add(pasteFile);
        jPopupMenuOne.add(copyFile);
        jPopupMenuOne.add(pasteFile);
        jPopupMenuOne.addSeparator();
        JMenu insert = new JMenu("����");
        JMenuItem insert_child = new JMenuItem("����������");
        JMenuItem insert_before = new JMenuItem("�������⣨֮ǰ��");
        JMenuItem insert_after = new JMenuItem("��������");
        insert.add(insert_child);
        insert.add(insert_before);
        insert.add(insert_after);
        jPopupMenuOne.add(insert);
        //��������������
        MouseListener popupListener = new PopupListener(jPopupMenuOne);
        //��������ע�������
        board.addMouseListener(popupListener);
        
        
	}
	
    class PopupListener extends MouseAdapter
    {
        JPopupMenu popupMenu;
        PopupListener(JPopupMenu popupMenu)
        {
            this.popupMenu=popupMenu;
        }
        public void mousePressed(MouseEvent e)
        {
            showPopupMenu(e);
        }
        public void mouseReleased(MouseEvent e)
        {
            showPopupMenu(e);
        }
        private void showPopupMenu(MouseEvent e)
        {
            if(e.isPopupTrigger())
            {
                //�����ǰ�¼�������¼���أ��򵯳��˵�
                popupMenu.show(e.getComponent(),e.getX(),e.getY());
            }
        }
    }
    
    class myButtonListener extends KeyAdapter {
    	@Override
    	public void keyPressed(KeyEvent e) {
    		super.keyPressed(e);
    		//System.out.println(e.getKeyCode());
    		
    		if (e.getKeyCode() == 10) {//Enter
    			myButton s = (myButton)e.getSource();
    			try {
    				last_id = current_id;
    				current_id = all_nodes.creat_child_node(current_id);
    				all_nodes.creat_position(1, 0);
    				all_nodes.dy = 200;
    				System.out.println(current_id);
    			} catch (creat_fail e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    			int fittable_font_size = Math.max(13, s.getFont().getSize() * 4 / 5);
    			int fittable_height = Math.max(40, s.getHeight() * 4 / 5);
    			myButton child = createNode("������", all_nodes.table[current_id].x , all_nodes.table[current_id].y, s.getWidth(), fittable_height, fittable_font_size, current_id);
    			buttons[current_id] = child;
    			//child.set
    			Container container = getContentPane();
            	container.setVisible(false);
            	board.add(child);
            	//ÿ�����½ڵ��������Ҫ�ı�֮ǰ�����ɵ����нڵ��λ��
            	for (int i = 1; i < buttons.length; ++i) {
            		if (buttons[i] != null && all_nodes.table[buttons[i].id].valid == true) {
            			Nodes node = all_nodes.table[buttons[i].id];
            			buttons[i].setBounds(node.x, node.y, node.width, buttons[i].getHeight());
            		}
            	}
                container.setVisible(true);
                buttons[last_id].setBackground(child.getBackground());
        		child.setBackground(new Color(135,206,250));
                //�������Ƶ���ǰ�Ľڵ���
                child.requestFocusInWindow();
            	
    		}
    		else if (e.getKeyCode() == 38) {//��
    			myButton s = (myButton)e.getSource();
    			int brother_before = all_nodes.get_brother_before(s.id);
    			if (brother_before != -1) {
        			current_id = brother_before;
        			s.setBackground(buttons[current_id].getBackground());
            		buttons[current_id].setBackground(new Color(135,206,250));
        			buttons[current_id].requestFocusInWindow();
        			
    			}
    			
    		}
    		else if (e.getKeyCode() == 40) {//��
    			myButton s = (myButton)e.getSource();
    			int brother_after = all_nodes.get_brother_after(s.id);
    			if (brother_after != -1) {
        			current_id = brother_after;
        			s.setBackground(buttons[current_id].getBackground());
            		buttons[current_id].setBackground(new Color(135,206,250));
        			buttons[current_id].requestFocusInWindow();
        			
    			}
    		}
    		else if (e.getKeyCode() == 37) {//��
    			//moveToFather();
    			myButton s = (myButton)e.getSource();
    			if (all_nodes.table[s.id].father_id > 0) {//���ڵ����
        			current_id = all_nodes.table[s.id].father_id;
//    				
        			s.setBackground(buttons[current_id].getBackground());
            		buttons[current_id].setBackground(new Color(135,206,250));
        			buttons[current_id].requestFocusInWindow();
    			}
    			
    			
    		}
    		else if (e.getKeyCode() == 39) {//��
    			//moveToChild();
    			myButton s = (myButton)e.getSource();
    			if (all_nodes.table[s.id].child_len() > 0) {//�ӽڵ����
        			current_id = all_nodes.table[s.id].childlist.get(0);
    				s.setBackground(buttons[current_id].getBackground());
            		buttons[current_id].setBackground(new Color(135,206,250));
        			buttons[current_id].requestFocusInWindow();
    			}
    		}
    		else if (e.getKeyCode() == 32) {//�ո�����༭text
    			myButton s = (myButton)e.getSource();
    			JTextField text = new JTextField();
    			Nodes node = all_nodes.table[s.id];
    			text.setBounds(node.x + 3, node.y + s.getHeight() / 4, node.width, s.getHeight() / 2);
    			text.setText(s.getText());
    			Container container = getContentPane();
            	container.setVisible(false);
            	board.add(text,0);//����0ʹ�ú���ӵ�text��ʾ�����ϲ�
    			text.addKeyListener(new KeyAdapter() {
    	        	@Override
    	        	public void keyPressed(KeyEvent k) {
    	        		super.keyPressed(k);
    	        		JTextField ts = (JTextField)k.getSource();
    	        		if (k.getKeyCode() == 10) {//�ո񣬽��ı����ֵ����button��ɾ���ı���
    	        			all_nodes.table[s.id].set_message(ts.getText());
    	        			s.setText(ts.getText());
    	        			
    	        			container.setVisible(false);
    	        			board.remove(ts);
    	        			//ÿ���нڵ㳤�ȱ仯����Ҫ�ı�֮ǰ�����ɵ����нڵ��λ��
    	        			all_nodes.creat_position(1,0);
    	                	for (int i = 1; i < buttons.length; ++i) {
    	                		if (buttons[i] != null && all_nodes.table[buttons[i].id].valid == true) {
    	                			Nodes node = all_nodes.table[buttons[i].id];
    	                			buttons[i].setBounds(node.x, node.y, node.width, buttons[i].getHeight());
    	                		}
    	                	}
    	        			container.setVisible(true);
    	        			s.requestFocusInWindow();
    	        		}
    	        		
    	        		
    	        	}
    			});
    			
                container.setVisible(true);
                //�������Ƶ���ǰ�Ľڵ���
                text.requestFocusInWindow();
    		}
    		else if (e.getKeyCode() == 127) {//delete����ɾ����ǰ�ڵ㼰���ӽڵ�
    			myButton s = (myButton)e.getSource();
    			//��button���в���
    			remove_button(s.id);
    			//��table���в���
    			try {
    				all_nodes.remove_node(s.id);
    			} catch (remove_fail e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    			all_nodes.creat_position(1, 0);
    			all_nodes.dy = 200;//����һ�����һ�����
    			board.setVisible(false);
    			//ÿ�����½ڵ��������Ҫ�ı�֮ǰ�����ɵ����нڵ��λ��
            	for (int i = 2; i < buttons.length; ++i) {
            		if (buttons[i] != null && all_nodes.table[buttons[i].id].valid == true) {
            			Nodes node = all_nodes.table[buttons[i].id];
            			buttons[i].setBounds(node.x, node.y, buttons[i].getWidth(), buttons[i].getHeight());
            		}
            	}
            	
    			board.setVisible(true);
    			board.repaint();
    		}
    		
    	}

    }

    class myButtonActionListener implements ActionListener {
    	
    	public void actionPerformed(ActionEvent e) {
    		
    		myButton s = (myButton)e.getSource();
    		//System.out.println(current_id);
    		last_id = current_id;
    		current_id = s.id;
//    		System.out.println(current_id);
//    		Border etchedBorder = new EtchedBorder(EtchedBorder.RAISED);// ���ñ߿�͹��
//    		s.setBorder(etchedBorder);
//   		buttons[last_id].setBorder(null);
    		buttons[last_id].setBackground(s.getBackground());
    		s.setBackground(new Color(135,206,250));
    		
    	}
    }
    class outlineListener implements ActionListener {
    	boolean expanded = false;
    	JTree tree;
    	public void actionPerformed(ActionEvent e) {
    		if (expanded == true) {
    			Container container = getContentPane();
            	container.setVisible(false);
    			toolBar.remove(tree);
    			expanded = false;
    			container.setVisible(true);
    			return;
    		}
    		expanded = true;
    		JButton s = (JButton)e.getSource();
    		int parent = 1;
    		DefaultMutableTreeNode root = new DefaultMutableTreeNode(all_nodes.table[parent].get_message());
    		//�ݹ�
    		build_tree(parent, root);
    		tree = new JTree(root);
    		Container container = getContentPane();
        	container.setVisible(false);
    		
    		
//    		int x = toolBar.getLocation().x == 1220 ? 820 : 1220;
//    		int width = toolBar.getWidth() == 50 ? 450 : 50;
    		
    		//System.out.println("x: " + toolBar.getParent().getLocation().x + " y: " + toolBar.getParent().getLocation().y + " width: " + toolBar.getWidth() + " height: " + toolBar.getHeight());
    		//toolBar.setBounds(x, toolBar.getLocation().y, width, toolBar.getHeight());
    		//tree.setBounds(x, toolBar.getLocation().y + 100, width, toolBar.getHeight());
    		//System.out.println("x: " + toolBar.getLocation().x + " y: " + toolBar.getLocation().y + " width: " + toolBar.getWidth() + " height: " + toolBar.getHeight());
//    		JPanel temp = new JPanel();
//    		temp.add(tree);
        	//tree.setRootVisible(false);//����ʾ���ĸ��ڵ�
    		tree.setRowHeight(20);//���ڵ���и�Ϊ20����
    		tree.setFont(new Font("����", Font.BOLD, 14));//��������������
    		//�ڵ�䲻����������
    		tree.putClientProperty("JTree.lineStyle", "None");
    		DefaultTreeCellRenderer treeCellRenderer;// ������ڵ�Ļ��ƶ���
    		treeCellRenderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
    		treeCellRenderer.setLeafIcon(null);// ����Ҷ�ӽڵ㲻����ͼ��
    		treeCellRenderer.setClosedIcon(null);// ���ýڵ��۵�ʱ������ͼ��
    		treeCellRenderer.setOpenIcon(null);// ���ýڵ�չ��ʱ������ͼ��
    		Enumeration<?> enumeration; // ��ǰ������������ڵ�
    		enumeration = root.preorderEnumeration();
    		
    		while (enumeration.hasMoreElements()) {
    			DefaultMutableTreeNode node;
    			node = (DefaultMutableTreeNode) enumeration.nextElement();
    			if (!node.isLeaf()) {// �ж��Ƿ�ΪҶ�ӽڵ�
    				// �����ýڵ��·��
    				TreePath path = new TreePath(node.getPath());
    				tree.expandPath(path);// ���������չ���ýڵ�
    			}
    		}
    
    		toolBar.add(tree, BorderLayout.CENTER);
    		container.setVisible(true);
    	}
    	void build_tree(int parent, DefaultMutableTreeNode parent_node) {
//    		if (buttons[parent] == null || all_nodes.table[parent].valid == false) {
//    			return;
//    		}
    		System.out.println("parent: " + parent);
    		for (int i = 0; i < all_nodes.table[parent].child_len(); ++i) {
    			int id = all_nodes.table[parent].childlist.get(i);
    			System.out.println("child_id: " + id);
    			DefaultMutableTreeNode sub_node = new DefaultMutableTreeNode(all_nodes.table[id].get_message());
    			parent_node.add(sub_node);
    			if (all_nodes.table[id].child_len() > 0) {
    				build_tree(id, sub_node);
    			}
    			
    		}
    	}
    }

}
