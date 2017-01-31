package com.mandel.fybertest.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.app.Activity;
import com.bumptech.glide.Glide;

import com.mandel.fybertest.R;
import com.mandel.fybertest.model.ImageItem;
import com.nhaarman.supertooltips.*;

import java.util.List;

import android.support.v7.widget.RecyclerView;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {
	
	private final Activity mContext;
	List<ImageItem> mItems;

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
 
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.item_offer1, parent, false);
		
		return new MyViewHolder(itemView);
	}
	private static ToolTipView mToolTipView = null;
	private void setupPopupHelp(ImageItem item, MyViewHolder holder) {
		ToolTipRelativeLayout toolTipRelativeLayout =
			(ToolTipRelativeLayout)
			holder.itemView.findViewById(R.id.activity_main_tooltipRelativeLayout);
		
		ToolTip toolTip = new ToolTip()
                        .withText(item.getCaption())
                        .withColor(android.graphics.Color.YELLOW)
                        .withShadow()
                        /*.withAnimationType(ToolTip.FROMTOP)*/;

		holder.mImageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Toast.makeText(YourActivityName.this,
					//"The favorite list would appear on clicking this icon",
					//Toast.LENGTH_LONG).show();
					if (mToolTipView != null) {
						mToolTipView.remove();
						mToolTipView = null;	
					}
					mToolTipView =
						toolTipRelativeLayout.showToolTipForView(toolTip, holder.mImageView);

				}
			});
		
		//myToolTipView.setOnToolTipViewClickedListener(holder.mImageView);
	}
	
	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		ImageItem item = mItems.get(position);
		if (item.isReady()) { 
			holder.mTitle.setText(item.getTitle());
			holder.mID.setText(item.getId());
			Glide.with(holder.mImageView.getContext()).load( item.getUri()).into(holder.mImageView);
			setupPopupHelp(item, holder);
		} else {
			holder.mTitle.setText("sssss");
		}
	}
	
	
	public ImagesAdapter(Activity context) {
		this.mContext = context;
	}
	
	@Override
	public int getItemCount() {
		return mItems.size();
	}
	
	public  void setItems(List<ImageItem> items)
	{
		this.mItems = items;
	}
	
	

		/*
		TextView teaser = (TextView) view.findViewById(R.id.teaser);
		teaser.setText(getItem(position).getTeaser());
		
		TextView payout = (TextView) view.findViewById(R.id.payout);
		payout.setText(getItem(position).getPayout());
		
		ImageView imageView = (ImageView) view.findViewById(R.id.img);
		Glide.with(imageView.getContext()).load(getItem(position).getThumbnailHires()).into(imageView);
		*/
		     

}
