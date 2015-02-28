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
		//开启一个新的activity
		//startActivity(intent);
		//开启一个新的activity,并且获取activity的结果
		/*
		 * 第二个参数 0:请求码,对应下面onActivityResult方法中的requestCode
		 * 主要用来在onActivityResult方法中做判断
		 * 可能这个activity有多个方法去开启另外的activity获取数据,请求码就可以在onActivityResult中对应知道是哪个调用activity的方法返回的结果了
		 */
		startActivityForResult(intent, 0);
	}
	/**
	 * 当另一个activity被我们激活后关闭时会被调用此方法
	 * 参数requestCode:对应startActivityForResult方法中的第二个参数,用来区分调用此方法后需要执行的逻辑
	 * 参数resultCode:对应被开启的activity关闭时,返回结果数据方法setResult(resultCode,intent)中的第一个参数resultCode,这里可以更加传递的resultCode进行不同的逻辑处理
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
			
		String SMSnumber = et.getText().toString().trim();//拿到电话号码后去空格
		
		String SMScontent = et2.getText().toString().trim();//拿到电话号码后去空格
		
		if(TextUtils.isEmpty(SMSnumber) || TextUtils.isEmpty(SMScontent)){
			//Toast 在界面上给用户提示 参数 content 上下文,text 提示文字,duration 显示时常 SHORT1秒 LONG2秒 
			Toast toast = Toast.makeText(MainActivity.this, "电话号码或短信内容不能为空", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}else{
			//短信管理者
			//SmsManager不能new,说明SmsManager这个类没有提供构造方法,一般没有提供构造方法都会有一个getDefault或者getInstence的方法去得到这个实例
			SmsManager smsManager = SmsManager.getDefault();
			
			//如果短信字数超过最大限度,则无法发送出去,中文最大限度是70个汉字,英文160个字符,
			//smsManager.divideMessage 将这个短信进行拆分,如果超过最大限度,拆分短信进行发送
			ArrayList<String> SMScontents = smsManager.divideMessage(SMScontent);
			for(String str : SMScontents){
				//发送彩信
				//smsManager.sendDataMessage(destinationAddress, scAddress, destinationPort, data, sentIntent, deliveryIntent);
				//发送有多个部分的短信
				//smsManager.sendMultipartTextMessage(destinationAddress, scAddress, parts, sentIntents, deliveryIntents);
				//发送一个文本短信
				/*
				 * 第一个参数,短信接收人
				 * 第二个参数,短信发送号码,中国运营商不支持这个参数(对于微信,易信等这种公司提供了这个参数来修改发送人),国外可以使用这个参数,一般伪造110,10086等号码发送的短信就是使用了这个参数
				 * 第三各参数,短信内容
				 * 第四各参数,延期类型的参数,延时才会获取到的消息,判断短信是否发送失败(地下室,电梯等)
				 * 第五个参数,延期类型的参数,延时才会获取到的消息,送达报告,对方手机接收到才会返回的函数
				 * 用此API发送短信不会产生历史记录,也就是在发件箱无法查到所发短信,因此一些应用可能会在后台恶意发送短信收取费用
				 */
				//短信群发,以","为分界
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
