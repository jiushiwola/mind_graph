package dataStruct;


import java.awt.Graphics;
  
import javax.swing.JFrame;
  
public class DrawUI {
 public void show(){
 JFrame jframe = new JFrame();
 jframe.setSize(1000, 900);
 jframe.setLocationRelativeTo(null);
 jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 jframe.setTitle("��ͼ��v2.0");
  
 //����������
 Listener draw = new Listener(); 
 //��Ӽ�����
 //jframe.addMouseListener(draw);
  
  
 jframe.setVisible(true);
 //��ȡGraphics
 Graphics graph = jframe.getGraphics();
 graph.drawLine(0, 0, 50, 50);
 //���������Ļ�������ֵ
 //draw.setGraphics(graph);
 }
 //������
 public static void main(String[] args){
 DrawUI UI = new DrawUI();
 UI.show();
 }
  
}