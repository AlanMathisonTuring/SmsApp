package com.nutegg.smsapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et;
	private EditText et2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et = (EditText)findViewById(R.id.editText1);
		et2 = (EditText)findViewById(R.id.editText2);
	}
	public void selectLxr(View view){
		Intent intent = new Intent();
		intent.setAction("com.nutegg.sms");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("sms:XXX"));
		//����һ���µ�activity
		//startActivity(intent);
		//����һ���µ�activity,���һ�ȡactivity�Ľ��
		/*
		 * �ڶ������� 0:������,��Ӧ����onActivityResult�����е�requestCode
		 * ��Ҫ������onActivityResult���������ж�
		 * �������activity�ж������ȥ���������activity��ȡ����,������Ϳ�����onActivityResult�ж�Ӧ֪�����ĸ�����activity�ķ������صĽ����
		 */
		startActivityForResult(intent, 0);
	}
	/**
	 * ����һ��activity�����Ǽ����ر�ʱ�ᱻ���ô˷���
	 * ����requestCode:��ӦstartActivityForResult�����еĵڶ�������,�������ֵ��ô˷�������Ҫִ�е��߼�
	 * ����resultCode:��Ӧ��������activity�ر�ʱ,���ؽ�����ݷ���setResult(resultCode,intent)�еĵ�һ������resultCode,������Ը��Ӵ��ݵ�resultCode���в�ͬ���߼�����
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			String phoneNumber = data.getStringExtra("number");
			String str = et.getText().toString().trim();
			Log.i("NUTEGG", str);
			if(TextUtils.isEmpty(str)){
				et.setText(phoneNumber+";");
			}else{
				str=str.replace(",",";");
				String str2 = str.substring(str.length()-1, str.length()) ;
				if(!";".equals(str2)){
					str = str+";";
				}
				str = str+phoneNumber+";";
				et.setText(str);
			}
			
		}
	}
	
	public void sendSms(View v) {
		// TODO Auto-generated method stub
			
		String SMSnumber = et.getText().toString().trim();//�õ��绰�����ȥ�ո�
		
		String SMScontent = et2.getText().toString().trim();//�õ��绰�����ȥ�ո�
		
		if(TextUtils.isEmpty(SMSnumber) || TextUtils.isEmpty(SMScontent)){
			//Toast �ڽ����ϸ��û���ʾ ���� content ������,text ��ʾ����,duration ��ʾʱ�� SHORT1�� LONG2�� 
			Toast toast = Toast.makeText(MainActivity.this, "�绰�����������ݲ���Ϊ��", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}else{
			//���Ź�����
			//SmsManager����new,˵��SmsManager�����û���ṩ���췽��,һ��û���ṩ���췽��������һ��getDefault����getInstence�ķ���ȥ�õ����ʵ��
			SmsManager smsManager = SmsManager.getDefault();
			
			//�������������������޶�,���޷����ͳ�ȥ,��������޶���70������,Ӣ��160���ַ�,
			//smsManager.divideMessage ��������Ž��в��,�����������޶�,��ֶ��Ž��з���
			ArrayList<String> SMScontents = smsManager.divideMessage(SMScontent);
			for(String str : SMScontents){
				//���Ͳ���
				//smsManager.sendDataMessage(destinationAddress, scAddress, destinationPort, data, sentIntent, deliveryIntent);
				//�����ж�����ֵĶ���
				//smsManager.sendMultipartTextMessage(destinationAddress, scAddress, parts, sentIntents, deliveryIntents);
				//����һ���ı�����
				/*
				 * ��һ������,���Ž�����
				 * �ڶ�������,���ŷ��ͺ���,�й���Ӫ�̲�֧���������(����΢��,���ŵ����ֹ�˾�ṩ������������޸ķ�����),�������ʹ���������,һ��α��110,10086�Ⱥ��뷢�͵Ķ��ž���ʹ�����������
				 * ����������,��������
				 * ���ĸ�����,�������͵Ĳ���,��ʱ�Ż��ȡ������Ϣ,�ж϶����Ƿ���ʧ��(������,���ݵ�)
				 * ���������,�������͵Ĳ���,��ʱ�Ż��ȡ������Ϣ,�ʹﱨ��,�Է��ֻ����յ��Ż᷵�صĺ���
				 * �ô�API���Ͷ��Ų��������ʷ��¼,Ҳ�����ڷ������޷��鵽��������,���һЩӦ�ÿ��ܻ��ں�̨���ⷢ�Ͷ�����ȡ����
				 */
				//����Ⱥ��,��","Ϊ�ֽ�
				//String[] SMSnumbers1;
				String[] SMSnumbers2;
				SMSnumber=SMSnumber.replace(",",";");
				//SMSnumbers1 = SMSnumber.split(",");
				SMSnumbers2 = SMSnumber.split(";");
//				if(SMSnumbers1.length > SMSnumbers2.length){
//					for(String number : SMSnumbers1){
//						smsManager.sendTextMessage(number, null, str, null, null);
//					}
//				}else 
				if(SMSnumbers2.length>1){
					for(String number : SMSnumbers2){
						if(!TextUtils.isEmpty(SMSnumber) ){
							smsManager.sendTextMessage(number, null, str, null, null);
						}						
					}
				}else{
					smsManager.sendTextMessage(SMSnumber, null, str, null, null);
				}
				
				
			}

		
			
		}
		
	}
	
}
