package com.rdxer.xxlibrary.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 *  基础的适配器
 * 
 * @ClassName: BaseListAdapter
 * @param <E> item 对应的 data 类型
 */
public abstract class BaseListAdapter<E> extends BaseAdapter {

	public List<E> list;

	public Context context;

	public LayoutInflater inflater;

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void add(E e) {
		this.list.add(e);
		notifyDataSetChanged();
	}

	public void addAll(List<E> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	public void remove(int position) {
		this.list.remove(position);
		notifyDataSetChanged();
	}

	public void remove(E item) {
		this.list.remove(item);
		notifyDataSetChanged();
	}

	public void clear() {
		this.list.clear();
		notifyDataSetChanged();
	}

	public BaseListAdapter(Context context, List<E> list) {
		super();
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(list==null||list.size()==0){
			return 0;
		}
		return list.size();
	}

	@Override
	public E getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = bindView(position, convertView, parent);
		addInternalClickListener(convertView, position, list.get(position));
		return convertView;
	}
	public abstract View bindView(int position, View convertView,
			ViewGroup parent);  
	public Map<Integer, onInternalClickListener> canClickItem;
	private void addInternalClickListener(final View itemV,
			final Integer position, final Object valuesMap) {  
		if (canClickItem != null) {
			for (Integer key : canClickItem.keySet()) {
				View inView = itemV.findViewById(key);
				final onInternalClickListener inviewListener = canClickItem
						.get(key); 
				if (inView != null && inviewListener != null) {
					inView.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							inviewListener.OnClickListener(itemV, v, position,
									valuesMap);
						}
					});
				}
			}
		}
	}

	public void setOnInViewClickListener(Integer key,
			onInternalClickListener onClickListener) {
		if (canClickItem == null)
			canClickItem = new HashMap<Integer, onInternalClickListener>();
		canClickItem.put(key, onClickListener);
	}

	public interface onInternalClickListener {
		public void OnClickListener(View parentV, View v, Integer position,
				Object values);
	}

}
