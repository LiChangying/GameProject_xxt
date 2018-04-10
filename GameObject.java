package cn.xxt.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class GameObject {
	Image img;//该物体对应的图片对象
	double x,y;//物体的坐标
	int speed;//物体的运行速度
	int width,height;//物体所在矩形区域的宽度和高度
	
	public void drawMySelf(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
	}
	
	//构造方法1
	public GameObject(Image img, double x, double y) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		if (img != null) {
			this.width = img.getWidth(null);
			this.height = img.getHeight(null);
		}
	}
	//构造方法2
	public GameObject(Image img, double x, double y, int speed, int width, int height) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
	}
	//构造方法3
	public GameObject() {
		// TODO Auto-generated constructor stub
	}
	
	//所有通过该类或者继承该类创建的对象，都会获得其自身的矩形对象
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
