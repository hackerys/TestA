package com.example.administrator.testa;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *  通用ViewHolder
* @ClassName: ViewHolder 
* @Description: TODO
* @author LiLong
* @date 2015-4-27 下午4:39:28 
*
 */
public class ViewHolder 
{
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConverView;
	private AdapterEvent adapterEvent;

	public void setAdapterEvent(AdapterEvent adapterEvent) {
		this.adapterEvent = adapterEvent;
	}

	public AdapterEvent getAdapterEvent() {
		return adapterEvent;
	}

	public ViewHolder(Context context, ViewGroup parent, int layoutId, int position)
	{
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConverView = LayoutInflater.from(context).inflate(layoutId, parent,false);
		mConverView.setTag(this);
		if (null!=getAdapterEvent())
			getAdapterEvent().setViewEvent(this);
	}

	public static ViewHolder get(Context context,View convertView , ViewGroup parent ,int layoutId,int position)
	{
		if(convertView == null){
			return new ViewHolder(context, parent, layoutId, position);
		}else{  
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}

	/**
	 * 通过viewId 获取控件
	* @Title: getView 
	* @Description: TODO
	* @param @param viewID
	* @param @return    设定文件 
	* @return T    返回类型 
	* @throws
	 */
	public <T extends View> T getView(int viewID)
	{
		View view = mViews.get(viewID);
		
		if(view == null)
		{
			view = mConverView.findViewById(viewID);
			mViews.put(viewID, view);
		}
		return (T) view;
	}
	
	public View getConverView() {
		return mConverView;
	}

	public ViewHolder setText(int viewId,String text){
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	
    /** 
     * 为ImageView设置图片 
     *  
     * @param viewId 
     * @param drawableId 
     * @return 
     */  
    public ViewHolder setImageResource(int viewId, int drawableId)  
    {  
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);  
  
        return this;  
    }  
  
    /** 
     * 为ImageView设置图片 
     *  
     * @param viewId 
     * @param bm
     * @return 
     */  
    public ViewHolder setImageBitmap(int viewId, Bitmap bm)
    {  
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;  
    }

	/**
	 * 设置ImageView可见
	 *
	 * @param viewId
	 * @return
	 */
	public ViewHolder setImageVisible(int viewId)
	{
		ImageView view = getView(viewId);
		view.setVisibility(View.VISIBLE);
		return this;
	}
	/**
	 * 设置ImageView不可见
	 *
	 * @param viewId
	 * @return
	 */
	public ViewHolder setImageInvisible(int viewId)
	{
		ImageView view = getView(viewId);
		view.setVisibility(View.INVISIBLE);
        return this;
	}

	public int getPosition()
    {  
        return mPosition;  
    }  
  
    
}
