package cn.xxt.game;
/*
 * Explode ��ը�࣬�ṩ��ײ��ı�ըЧ������16��ͼƬƴ�Ӷ��ɣ�
*/
import java.awt.Graphics;
import java.awt.Image;

public class Explode {
	double x,y;
	static Image[] imgs = new Image[16];//
	static{
		for (int i = 0; i < 16; i++) {
			imgs[i] = GameUtil.getImage("images/explode/e"+(i+1)+".gif");
			imgs[i].getWidth(null);
		}
	}
	
	int count;
	public void draw(Graphics g) {//��дdraw����
		if (count<=15) {
			g.drawImage(imgs[count], (int)x, (int)y, null);//����ըЧ��ͼ��λ��
			count++;
		}
	}

	public Explode(double x, double y) {//���췽�����ṩx y����λ��
		super();
		this.x = x;
		this.y = y;
	}
}
