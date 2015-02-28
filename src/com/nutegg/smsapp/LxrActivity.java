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
		
		//��ListViewÿ����ϵ�˵���Ŀ���õ���¼�
		/**
		 * ע��һ��ListView��Ŀ������ĵ���¼�����:setOnItemClickListener
		 */
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			/*
			 * ��һ������AdapterView:�����ڱ������ListView����
			 * �ڶ�������View:����ǰListView��ÿ��Item����Ŀ��View����
			 * �������������������ǵڼ�����Ŀ
			 * ���ĸ�����
			 * (non-Javadoc)
			 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
			 */
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				ContactInfo contactInfo = contactInfos.get(position);
				String phoneNumber = contactInfo.getNumber();
				//�������ݸ����ô�activity��activity
				Intent intent = new Intent();
				intent.putExtra("number", phoneNumber);
				/*
				 * ��һ������ �����:�ᴫ�ݵ�������activity��activity��onActivityResult������
				 * ��������onActivityResult�п��Ը��ݲ�ͬ�� ����� ���в�ͬ�߼��Ĵ���.
				 */
				setResult(0, intent);
				//����finish��.��ǰactivity�������ر�
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
			//����layoutInflater����Ͳ����һ��View����
			View view = View.inflate(LxrActivity.this, R.layout.lxr_item, null);
			
			TextView tv_name = (TextView)view.findViewById(R.id.tv_lxrname);
			tv_name.setText("������"+contactInfo.getName());
			
			TextView tv_phone = (TextView)view.findViewById(R.id.tv_lxrphone);
			tv_phone.setText("�绰��"+contactInfo.getNumber());
			
			return view;
			
		}
		
	}
	
	

}
