package com.nutegg.smsapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nutegg.smsapp.domain.ContactInfo;
import com.nutegg.smsapp.service.LxrLoadService;


public class LxrActivity extends Activity {

	private ListView lv ;
	private List<ContactInfo> contactInfos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lxr);
		lv = (ListView)findViewById(R.id.lv_lxr);
		contactInfos = LxrLoadService.loadPeople(this);
		lv.setAdapter(new LxrAdapter());
		
		//给ListView每个联系人的条目设置点击事件
		/**
		 * 注册一个ListView条目被点击的点击事件方法:setOnItemClickListener
		 */
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			/*
			 * 第一个参数AdapterView:代表当期被点击的ListView对象
			 * 第二个参数View:代表当前ListView中每个Item的条目的View对象
			 * 第三个参数代表点击的是第几个条目
			 * 第四个参数
			 * (non-Javadoc)
			 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
			 */
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				ContactInfo contactInfo = contactInfos.get(position);
				String phoneNumber = contactInfo.getNumber();
				//传递数据给调用此activity的activity
				Intent intent = new Intent();
				intent.putExtra("number", phoneNumber);
				/*
				 * 第一个参数 结果码:会传递到开启此activity的activity的onActivityResult方法中
				 * 作用是在onActivityResult中可以根据不同的 结果码 进行不同逻辑的处理.
				 */
				setResult(0, intent);
				//调用finish后.当前activity立即被关闭
				finish();
			}
			
		});
	}
	
	private class LxrAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return contactInfos.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View lxrView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ContactInfo contactInfo = contactInfos.get(position);
			//利用layoutInflater打气筒创建一个View对象
			View view = View.inflate(LxrActivity.this, R.layout.lxr_item, null);
			
			TextView tv_name = (TextView)view.findViewById(R.id.tv_lxrname);
			tv_name.setText("姓名："+contactInfo.getName());
			
			TextView tv_phone = (TextView)view.findViewById(R.id.tv_lxrphone);
			tv_phone.setText("电话："+contactInfo.getNumber());
			
			return view;
			
		}
		
	}
	
	

}
