package com.nutegg.smsapp.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.nutegg.smsapp.domain.ContactInfo;

public class LxrLoadService {
	public static List<ContactInfo> loadPeople(Context context){
		List<ContactInfo> contactList = new ArrayList<ContactInfo>();
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri url = Uri.parse("content://com.android.contacts/data");
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(uri, null, null, null, null);
		if(cursor != null){
			while(cursor.moveToNext()){	
				String id = cursor.getString(cursor.getColumnIndex("contact_id"));
				Cursor cursor2 = resolver.query(url, null, "raw_contact_id=?", new String[]{id}, null);
				ContactInfo contactInfo = new ContactInfo();
				if(cursor2 != null){
					while(cursor2.moveToNext()){
						String data1 = cursor2.getString(cursor2.getColumnIndex("data1"));
						//因为在数据库中用的很多的视图,所以这边取mimetype_id的时候直接取mimetype_id无法找到
						String mimetype = cursor2.getString(cursor2.getColumnIndex("mimetype"));
						//Log.i("NUTEGG", mimetype);
						if("vnd.android.cursor.item/name".equals(mimetype)){
							contactInfo.setName(data1);									
						}else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){

							contactInfo.setNumber(data1);
						}					
					}
					contactList.add(contactInfo);
					cursor2.close();
				}
			}			
		}
		cursor.close();
		//Log.i("NUTEGG", contactList.get(0).getNumber());
		return contactList;
	}
}
