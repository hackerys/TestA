package com.example.administrator.testa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.math.BigDecimal;
import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter implements AdapterEvent {
	protected Context mContext;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	private int layoutId;
	private AdapterEvent adapterEvent;

	public void setAdapterEvent(AdapterEvent adapterEvent) {
		this.adapterEvent = adapterEvent;
	}

	public AdapterEvent getAdapterEvent() {
		return adapterEvent;
	}

	public CommonAdapter(Context context, List<T> datas, int layoutId){
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
		this.layoutId = layoutId;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(null!=mDatas)
			return mDatas.size();
		else
			return 0;
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		if(null!=mDatas&&mDatas.size()>0)
			return mDatas.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
		holder.setAdapterEvent(this);
		convert(holder, getItem(position), position);
		return holder.getConverView();
	}
	
	public abstract void convert(ViewHolder holder,T t , int position);



	public boolean isNull(String str) {
		if (null == str || "".equals(str) || "null".equalsIgnoreCase(str)) {
			return true;
		} else {
			return false;
		}
	}


	public boolean isNull(Integer str) {
		if (null == str ) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNull(Double str) {
		if (null == str ) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNull(Long str) {
		if (null == str ) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isNull(BigDecimal str) {
		if (null == str ) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setViewEvent(ViewHolder holder) {
		getAdapterEvent().setViewEvent(holder);
	}
}
