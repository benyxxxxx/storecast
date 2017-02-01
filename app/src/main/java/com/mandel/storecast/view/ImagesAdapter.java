package com.mandel.storecast.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MotionEvent;

import android.widget.TextView;
import android.widget.ImageView;

import android.app.Activity;

import com.bumptech.glide.Glide;

import com.mandel.storecast.R;

import com.mandel.storecast.model.ImageItem;
import com.mandel.storecast.model.ImagesModel;
import com.mandel.storecast.model.GettyPageBuilder;

import com.nhaarman.supertooltips.*;

import android.os.Handler;

import android.support.v7.widget.RecyclerView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import android.util.Log;


/**
 * An Adapter for RecyclerView.
 *
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder>
implements ImagesModel.ImagesModelListener {
	
	private final Activity mContext;
	ImagesModel mItems;
	Handler mHandler = new Handler();
	
	public ImagesAdapter(Activity context, int itemsPerPage) {
		this.mContext = context;
		mItems = new ImagesModel(new GettyPageBuilder(context), itemsPerPage, this);
	}
	
	
	public class MyViewHolder extends RecyclerView.ViewHolder {
		public TextView mID, mTitle;
		public ImageView mImageView;
		public MyViewHolder(View view) {
			super(view);
			mTitle = (TextView) view.findViewById(R.id.title);
			mID = (TextView) view.findViewById(R.id.ID);
			mImageView = (ImageView) view.findViewById(R.id.img);
		}
	}

	/***
	 * Sets up a new query string
	 * @param searchWord: a new query to handle
	 */
	public void setupNewQuery(String searchWord) {
		mItems.setupNewQuery(searchWord);
		notifyDataSetChanged();
	}
	
	@Override
	public void dataChanged(int from, int to) {

		mHandler.postDelayed(new Runnable() {
				public void run() {
					if (getItemCount() >= to) 
						notifyItemRangeChanged(from, to);
					else
						notifyDataSetChanged();	
				}
			}, 5);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.item_offer1, parent, false);
		
		return new MyViewHolder(itemView);
	}
	
	private static ToolTipView mToolTipView = null;

	
	private final  class ToolTipManager implements View.OnClickListener, View.OnTouchListener {
		public ToolTipManager(ImageView view, ToolTip toolTip, ToolTipRelativeLayout toolTipRelativeLayout) {
			mView = view;
			mToolTip = toolTip;
			mToolTipRelativeLayout = toolTipRelativeLayout;
		}
		
		private ImageView mView = null;
		private ToolTip mToolTip = null;
		private Timer mTimer = null;
		private TimerTask mTask = null;
		ToolTipRelativeLayout mToolTipRelativeLayout;
		
		private void setupTimer() {
			if (mTimer != null)
				mTimer.cancel();
			
			mTimer = new Timer();
			mTask = new TimerTask() {
					@Override
					public void run() {
						mContext.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									dismissToolTip();
								}
							});
					}
				};
			mTimer.schedule(mTask, 3000);	
		}
		
		private void dismissToolTip() {
			if (mToolTipView != null) {
				mToolTipView.remove();
				mToolTipView = null;
				if (mTimer != null)
					mTimer.cancel();
			}
		}
		
		@Override
		public void onClick(View v) {
			dismissToolTip();
			mToolTipView =
				mToolTipRelativeLayout.showToolTipForView(mToolTip, mView);
			
			setupTimer();
			
		}
		
		public boolean onTouch(View v, MotionEvent event) {
			
			if (event.getAction() == MotionEvent.ACTION_DOWN)
				onClick(v);
			return true;
		}
	}
	
	
	
	private void setupPopupHelp(ImageItem item, MyViewHolder holder) {
		ToolTipRelativeLayout toolTipRelativeLayout =
			(ToolTipRelativeLayout)
			holder.itemView.findViewById(R.id.activity_main_tooltipRelativeLayout);
		
		ToolTip toolTip = new ToolTip()
                        .withText(item.getCaption())
                        .withColor(android.graphics.Color.YELLOW)
                        .withShadow();

		ToolTipManager toolTipManager = new ToolTipManager(holder.mImageView, toolTip, toolTipRelativeLayout);
		holder.mImageView.setOnClickListener(toolTipManager);
		holder.mImageView.setOnTouchListener(toolTipManager);
		
		
	}
	
	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		ImageItem item = mItems.getElementAt(position);
		/***
		 * When item is loaded dislay the content, otherwise show its Loading 
		 */
		if (item.isReady()) { 
			holder.mTitle.setText(item.getTitle());
			holder.mID.setText(item.getId());
			Glide.with(holder.mImageView.getContext()).load( item.getUri()).into(holder.mImageView);
			setupPopupHelp(item, holder);
		} else {
			holder.mTitle.setText("Loading...");
		}
	}
	
	
	@Override
	public int getItemCount() {
		return mItems.getSize();
	}	
}
