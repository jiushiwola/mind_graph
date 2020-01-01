package dataStruct;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
  
  
public class Listener implements MouseListener{
 //记录坐标
 int x1,y1,x2,y2;
  
 //定义Graphics
 Graphics graph1;
  
 //传画布值的方法
 public void setGraphics(Graphics graph2){
 graph1 = graph2;
  
  
 }
  
  public void mouseClicked(MouseEvent e){}
  
  public void mousePressed(MouseEvent e){
  //获取按下信息
  x1=e.getX();
  y1=e.getY();
  }
  
  public void mouseReleased(MouseEvent e){
  //获取松开信息
  x2=e.getX();
  y2=e.getY();
  //画线
  graph1.drawLine(x1,y1,x2,y2);
  }
  
  
  public void mouseEntered(MouseEvent e){}
  
  public void mouseExited(MouseEvent e){}
}
