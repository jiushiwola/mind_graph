package dataStruct;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
  
  
public class Listener implements MouseListener{
 //��¼����
 int x1,y1,x2,y2;
  
 //����Graphics
 Graphics graph1;
  
 //������ֵ�ķ���
 public void setGraphics(Graphics graph2){
 graph1 = graph2;
  
  
 }
  
  public void mouseClicked(MouseEvent e){}
  
  public void mousePressed(MouseEvent e){
  //��ȡ������Ϣ
  x1=e.getX();
  y1=e.getY();
  }
  
  public void mouseReleased(MouseEvent e){
  //��ȡ�ɿ���Ϣ
  x2=e.getX();
  y2=e.getY();
  //����
  graph1.drawLine(x1,y1,x2,y2);
  }
  
  
  public void mouseEntered(MouseEvent e){}
  
  public void mouseExited(MouseEvent e){}
}
