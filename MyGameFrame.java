package cn.xxt.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Date;

import javax.print.attribute.standard.PrinterInfo;
import javax.swing.JFrame;
import javax.xml.crypto.Data;

import org.ietf.jgss.Oid;

/**
 * 游戏的主窗口
 * @author andan
 *
 */
//MyGameFrame类主要用来画游戏窗口
public class MyGameFrame extends JFrame{
	
	//读取背景图片和飞机图片定义为成员变量
	Image bgImg = GameUtil.getImage("images/bg.jpg");
	Image planeImg = GameUtil.getImage("images/plane.png");
	Plane plane = new Plane(planeImg, 230, 450,10);
	
	//增加一个容器ArrayList来管理多个炮弹对象
	ArrayList<Shell> shellList = new ArrayList<Shell>();
	
	//创建爆炸对象
	Explode bao;
	//创建时间对象
	Date startTime = new Date();//游戏开始时间
	Date endtime;
	
	static int count = 0;
	
	int planeX = 230;//飞机图标的初始位置X
	int planeY = 450;//飞机图标的初始位置Y
	@Override
	//paint--窗口绘制
	//paint方法的主要作用是：画出整个窗口机器内部内容。
	public void paint(Graphics g) {//自动调用，g相当于画笔
		// TODO Auto-generated method stub
		g.drawImage(bgImg, 0, 0, null);
		plane.drawMySelf(g);
		for (int i = 0; i < shellList.size(); i++) {
			Shell b = shellList.get(i);
			b.draw(g);
			//飞机和所有的炮弹对象进行矩形检测
			boolean peng = b.getRect().intersects(plane.getRect());
			if (peng) {
				plane.live = false;
				
				if (bao == null) {//在飞机消失的位置创建爆炸对象
					bao = new Explode(plane.x, plane.y);
				}
				bao.draw(g);
			}
		}
		
		if (!plane.live) {
			if (endtime == null) {
				endtime = new Date();
			}
			int period = (int)((endtime.getTime()-startTime.getTime())/1000);
			printInfo(g,"time:"+period+"s",50,120,200,Color.white);
		}
		System.out.println("调用paint,重画窗口，次数："+(count++));
	}

	public void printInfo(Graphics g,String str,int size,int x,int y,Color color){
        Color c = g.getColor();
        g.setColor(color);
        Font f = new Font("宋体",Font.BOLD,size);
        g.setFont(f);
        g.drawString(str,x,y);
        g.setColor(c);
    }

	/*双缓冲技术：在内存中创建一个与屏幕绘图区域一致的对象，
	先将图形绘制到内存中的这个对象上，再一次性将这个对象上的
	图形拷贝到屏幕上，这样能大大加快绘图的速度
	*/
	private Image offScreenImage = null;
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(500, 500);
		}
		Graphics goff = offScreenImage.getGraphics();
		paint(goff);
		g.drawImage(offScreenImage, 500, 500, null);
	}
	
	//初始化窗口
	public void launchFrame(){
		this.setTitle("andan");//为窗口加一个标题
		this.setVisible(true);//将窗口设为可见
		this.setSize(500, 500);//设置窗口大小
		this.setLocation(300, 300);//设置窗口在屏幕中的位置
		new PaintThread().start();//启动重画线程
		//匿名内部类，用于关闭窗口
		//增加窗口监听事件
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
//				super.windowClosed(e);
				System.exit(0);//结束虚拟机运行，0表示正常结束，负数表示异常结束
			}
		});
		this.addKeyListener(new KeyMonitor());
		for (int i = 0; i < 50; i++) {
			Shell b = new Shell();
			shellList.add(b);
		}
	}
	
	//定义一个重画窗口的线程类，是一个内部类
	class PaintThread extends Thread{
		public void run() {
			while (true) {
				repaint();//awt类中定义的重画窗口的方法
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}
	
	class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}
		
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
	}
	
	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.launchFrame();
	}
}
