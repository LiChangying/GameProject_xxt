package cn.xxt.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class GameObject {
	Image img;//�������Ӧ��ͼƬ����
	double x,y;//���������
	int speed;//����������ٶ�
	int width,height;//�������ھ�������Ŀ�Ⱥ͸߶�
	
	public void drawMySelf(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
	}
	
	//���췽��1
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
	//���췽��2
	public GameObject(Image img, double x, double y, int speed, int width, int height) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
	}
	//���췽��3
	public GameObject() {
		// TODO Auto-generated constructor stub
	}
	
	//����ͨ��������߼̳и��ഴ���Ķ��󣬶�����������ľ��ζ���
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
