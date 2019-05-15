package com.zhang.paintboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itheima.paintboard.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.renderscript.Script;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private Bitmap srcBitmap;
	private ImageView iv;
	private Bitmap copyBitmap;
	private Paint paint;
	private Canvas canvas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ������ʾ���ǻ�������

		iv = (ImageView) findViewById(R.id.iv);
		// [1]��ȡԭͼ bg
		srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);	

		// [2]��ȡԭͼ�ĸ��� �൱����һ���հ� �İ�ֽ
		copyBitmap = Bitmap.createBitmap(srcBitmap.getWidth(),
				srcBitmap.getHeight(), srcBitmap.getConfig());
		// ��������
		paint = new Paint();
		// ����һ������
		canvas = new Canvas(copyBitmap);
		// ��ʼ����
		canvas.drawBitmap(srcBitmap, new Matrix(), paint);
		// canvas.drawLine(20, 30, 50, 80, paint);
		iv.setImageBitmap(copyBitmap);

		// [3]��iv����һ�������¼�
		iv.setOnTouchListener(new OnTouchListener() {
			int startX = 0;
			int startY = 0;

			public boolean onTouch(View v, MotionEvent event) {
				// ��ȡ��ǰ�¼�����
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN: // ����
					System.out.println("��view");
					// ��ȡ��ʼλ�� (����)

					startX = (int) event.getX();
					startY = (int) event.getY();

					break;

				case MotionEvent.ACTION_MOVE:// �ƶ�
					System.out.println("�ƶ�");
					// ��ȡ����λ��
					int stopX = (int) event.getX();
					int stopY = (int) event.getY();
					// [��ͣ�Ļ���]
					canvas.drawLine(startX, startY, stopX, stopY, paint);
					// �ڴ���ʾ��iv��
					iv.setImageBitmap(copyBitmap);

					// ����һ�¿�ʼ����ͽ�������
					startX = stopX;
					startY = stopY;

					break;

				case MotionEvent.ACTION_UP:// ̧��
					System.out.println("̧��");
					break;
				}

				// True if the listener has consumed the event, false otherwise
				return true; // true �������������¼���
			}
		});

	}

	// �����ť �ı仭�ʵ���ɫ
	public void click1(View v) {

		paint.setColor(Color.RED);
	}

	// �����ť �Ի��ʼӴ�
	public void click2(View v) {
		paint.setStrokeWidth(15);

	}
	
	// �����ť ����ͼƬ�����
	public void click3(View v) {

		/**
		 * format ����ͼƬ�ĸ�ʽ
		 * quality ����ͼƬ������ 
		 * SystemClock.uptimeMillis() ����ǵ�ǰ�ֻ��Ŀ���ʱ��
		 */
		try {
			File file = new File(Environment.getExternalStorageDirectory().getPath(),SystemClock.uptimeMillis()+".png");
			FileOutputStream fos = new FileOutputStream(file);		
			copyBitmap.compress(CompressFormat.PNG, 100, fos);
			
			//����һ���㲥 ��ƭϵͳͼ���Ӧ�� 
			Intent intent = new Intent();
			//����action
			intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
			intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
			//����һ���㲥
			sendBroadcast(intent);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
