package cn.xxt.game;
/*
 * Explode 爆炸类，提供碰撞后的爆炸效果（由16幅图片拼接而成）
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
	public void draw(Graphics g) {//重写draw方法
		if (count<=15) {
			g.drawImage(imgs[count], (int)x, (int)y, null);//画爆炸效果图的位置
			count++;
		}
	}

	public Explode(double x, double y) {//构造方法，提供x y坐标位置
		super();
		this.x = x;
		this.y = y;
	}
}
