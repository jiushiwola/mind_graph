package dataStruct;


import java.awt.Graphics;
  
import javax.swing.JFrame;
  
public class DrawUI {
 public void show(){
 JFrame jframe = new JFrame();
 jframe.setSize(1000, 900);
 jframe.setLocationRelativeTo(null);
 jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 jframe.setTitle("画图板v2.0");
  
 //创建监听器
 Listener draw = new Listener(); 
 //添加监听器
 //jframe.addMouseListener(draw);
  
  
 jframe.setVisible(true);
 //获取Graphics
 Graphics graph = jframe.getGraphics();
 graph.drawLine(0, 0, 50, 50);
 //给监听器的画布对象赋值
 //draw.setGraphics(graph);
 }
 //主方法
 public static void main(String[] args){
 DrawUI UI = new DrawUI();
 UI.show();
 }
  
}