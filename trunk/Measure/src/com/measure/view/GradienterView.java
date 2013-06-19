package com.measure.view;

import com.measure.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class GradienterView extends View {

	Paint paint = new Paint(); //����
	public Bitmap shangBitmap1; //����Ĵ����ͼ
	public Bitmap shangBitmap2; //���������
	public Bitmap zuoBitmap1; //����Ĵ����ͼ
	public Bitmap zuoBitmap2; //����ͼ������
	public Bitmap zhongBitmap1; //�м�Ĵ�Բͼ
	public Bitmap zhongBitmap2; //�м��С����
	public Bitmap xiaBitmap1; //���µľ���ͼ
	public Bitmap xiaBitmap2; //���µ�����
	public Bitmap background;
	
	//�������ε�λ������
	
	public int shang1_X = 10; //����Ĵ����ͼ
	public int shang1_Y = 40;
	public int zuo1_X = 10; //����Ĵ����ͼ
	public int zuo1_Y = 140;
	public int zhong1_X = 100; //�м�Ĵ�Բͼ
	public int zhong1_Y = 120;
	public int shang2_X; //���������XY ���
	public int shang2_Y;
	public int zuo2_X; //����ͼ������XY ���
	public int zuo2_Y;
	public int zhong2_X; //�м��С����XY ���
	public int zhong2_Y;
	
	public GradienterView(Context context, AttributeSet attrs){
		super(context, attrs);
		initBitmap(); //��ʼ��ͼƬ��Դ
		initLocation(); //��ʼ�����ݵ�λ��
		setBackgroundResource(R.drawable.gradienter_bg);
	}

	private void initBitmap(){ //��ʼ��ͼƬ�ķ���
		//�ô�ʡ���˲��ִ��룬���ں�����н���
		shangBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.level_up);
		shangBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.level_up_bubble);
		zuoBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.level_left);
		zuoBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.level_left_bubble);
		zhongBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.level_mid);
		zhongBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.level_mid_bubble);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.osx_hero);
	}

	private void initLocation(){ //��ʼ������λ�õķ���
		//�ô�ʡ���˲��ִ��룬���ں�����н���
		shang2_X = shang1_X + shangBitmap1.getWidth()/2- shangBitmap2.getWidth()/2;
		shang2_Y = shang1_Y + shangBitmap1.getHeight()/2- shangBitmap2.getHeight()/2;
		zuo2_X = zuo1_X + zuoBitmap1.getWidth()/2- zuoBitmap2.getWidth()/2;
		zuo2_Y = zuo1_Y + zuoBitmap1.getHeight()/2- zuoBitmap2.getHeight()/2;
		zhong2_X = zhong1_X + zhongBitmap1.getWidth()/2- zhongBitmap2.getWidth()/2;
		zhong2_Y = zhong1_Y + zhongBitmap1.getHeight()/2- zhongBitmap2.getHeight()/2;
		
	}

	@Override
	protected void onDraw(Canvas canvas){//��д�Ļ��Ʒ���
		
		//�ô�ʡ���˲��ִ��룬���ں�����н���
		//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.gradienter_bg), 0,0, paint);
		canvas.drawColor(Color.WHITE); //���ñ���ɫΪ��ɫ
		canvas.drawBitmap(background, 0, 0, paint);
		paint.setColor(Color.BLUE); //���û�����ɫ
		paint.setStyle(Style.STROKE); //���û���Ϊ�����
		//canvas.drawRect(5, 5, 200, 200, paint);//������߿����

		//����������
		canvas.drawBitmap(shangBitmap1, shang1_X,shang1_Y, paint); //��
		canvas.drawBitmap(zuoBitmap1, zuo1_X,zuo1_Y, paint); //��
		canvas.drawBitmap(zhongBitmap1, zhong1_X,zhong1_Y, paint); //��
	

		//��ʼ��������
		canvas.drawBitmap(shangBitmap2, shang2_X,shang2_Y, paint); //��
		canvas.drawBitmap(zuoBitmap2, zuo2_X,zuo2_Y, paint); //��
		canvas.drawBitmap(zhongBitmap2, zhong2_X,zhong2_Y, paint); //��

		paint.setColor(Color.GRAY);//���û�����ɫ�������ƿ̶�

//		//�������淽���еĿ̶�
//		canvas.drawLine (shang1_X+shangBitmap1.getWidth()/2-7,shang1_Y, shang1_X+shangBitmap1.getWidth()/2-7,shang1_Y+shangBitmap1.getHeight()-2, paint);
//		canvas.drawLine (shang1_X+shangBitmap1.getWidth()/2+7,shang1_Y, shang1_X+shangBitmap1.getWidth()/2+7,shang1_Y+shangBitmap1.getHeight()-2, paint);
//
//		//�������淽���еĿ̶�
//		canvas.drawLine(zuo1_X,zuo1_Y+zuoBitmap1.getHeight()/2-7,zuo1_X+zuoBitmap1.getWidth()-2,zuo1_Y+zuoBitmap1.getHeight()/2-7, paint);canvas.drawLine(zuo1_X,zuo1_Y+zuoBitmap1.getHeight()/2+7,zuo1_X+zuoBitmap1.getWidth()-2,zuo1_Y+zuoBitmap1.getHeight()/2+7, paint);
		
		
		//�м�ԲȦ�еĿ̶�(СԲ)
//		RectF oval = new RectF(zhong1_X+zhongBitmap1.getWidth()/2-10,zhong1_Y+zhongBitmap1.getHeight()/2-10,zhong1_X+zhongBitmap1.getWidth()/2+10,zhong1_Y+zhongBitmap1.getHeight()/2+10);
//		
//		canvas.drawOval(oval, paint);//���ƻ�׼��(Բ)
		
		
		super.onDraw(canvas);
	}
}