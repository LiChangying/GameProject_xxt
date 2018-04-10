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
 * ��Ϸ��������
 * @author andan
 *
 */
//MyGameFrame����Ҫ��������Ϸ����
public class MyGameFrame extends JFrame{
	
	//��ȡ����ͼƬ�ͷɻ�ͼƬ����Ϊ��Ա����
	Image bgImg = GameUtil.getImage("images/bg.jpg");
	Image planeImg = GameUtil.getImage("images/plane.png");
	Plane plane = new Plane(planeImg, 230, 450,10);
	
	//����һ������ArrayList���������ڵ�����
	ArrayList<Shell> shellList = new ArrayList<Shell>();
	
	//������ը����
	Explode bao;
	//����ʱ�����
	Date startTime = new Date();//��Ϸ��ʼʱ��
	Date endtime;
	
	static int count = 0;
	
	int planeX = 230;//�ɻ�ͼ��ĳ�ʼλ��X
	int planeY = 450;//�ɻ�ͼ��ĳ�ʼλ��Y
	@Override
	//paint--���ڻ���
	//paint��������Ҫ�����ǣ������������ڻ����ڲ����ݡ�
	public void paint(Graphics g) {//�Զ����ã�g�൱�ڻ���
		// TODO Auto-generated method stub
		g.drawImage(bgImg, 0, 0, null);
		plane.drawMySelf(g);
		for (int i = 0; i < shellList.size(); i++) {
			Shell b = shellList.get(i);
			b.draw(g);
			//�ɻ������е��ڵ�������о��μ��
			boolean peng = b.getRect().intersects(plane.getRect());
			if (peng) {
				plane.live = false;
				
				if (bao == null) {//�ڷɻ���ʧ��λ�ô�����ը����
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
		System.out.println("����paint,�ػ����ڣ�������"+(count++));
	}

	public void printInfo(Graphics g,String str,int size,int x,int y,Color color){
        Color c = g.getColor();
        g.setColor(color);
        Font f = new Font("����",Font.BOLD,size);
        g.setFont(f);
        g.drawString(str,x,y);
        g.setColor(c);
    }

	/*˫���弼�������ڴ��д���һ������Ļ��ͼ����һ�µĶ���
	�Ƚ�ͼ�λ��Ƶ��ڴ��е���������ϣ���һ���Խ���������ϵ�
	ͼ�ο�������Ļ�ϣ������ܴ��ӿ��ͼ���ٶ�
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
	
	//��ʼ������
	public void launchFrame(){
		this.setTitle("andan");//Ϊ���ڼ�һ������
		this.setVisible(true);//��������Ϊ�ɼ�
		this.setSize(500, 500);//���ô��ڴ�С
		this.setLocation(300, 300);//���ô�������Ļ�е�λ��
		new PaintThread().start();//�����ػ��߳�
		//�����ڲ��࣬���ڹرմ���
		//���Ӵ��ڼ����¼�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
//				super.windowClosed(e);
				System.exit(0);//������������У�0��ʾ����������������ʾ�쳣����
			}
		});
		this.addKeyListener(new KeyMonitor());
		for (int i = 0; i < 50; i++) {
			Shell b = new Shell();
			shellList.add(b);
		}
	}
	
	//����һ���ػ����ڵ��߳��࣬��һ���ڲ���
	class PaintThread extends Thread{
		public void run() {
			while (true) {
				repaint();//awt���ж�����ػ����ڵķ���
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
