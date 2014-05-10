package com.matrix.mym.controller.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.matrix.mym.R;
import com.matrix.mym.model.CompanyShare;

@SuppressLint("ViewConstructor")
public class CompanyShareAdapter extends SupportArrayAdapter<CompanyShare> {

	public CompanyShareAdapter(Context context,
			ArrayList<CompanyShare> companyShares) {
		super(context, companyShares);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = getLayoutInflater().inflate(R.layout.share_list_item,
					null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView
					.findViewById(R.id.tvShareName);

			viewHolder.priceTextView = (TextView) convertView
					.findViewById(R.id.tvSharePrice);

			viewHolder.priceChangeTextView = (TextView) convertView
					.findViewById(R.id.tvSharePriceChange);

			viewHolder.industryTextView = (TextView) convertView
					.findViewById(R.id.tvShareIndustry);

			viewHolder.menuImageButton = (ImageButton) convertView
					.findViewById(R.id.ibMenuButton);
			convertView.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) convertView.getTag();
		CompanyShare companyShare = getItem(position);
		viewHolder.nameTextView.setText(companyShare.getName());
		DecimalFormat df = new DecimalFormat("###.##");
		viewHolder.priceTextView.setText(df.format(companyShare.getPrice()));
		viewHolder.priceChangeTextView.setText(df.format(companyShare
				.getLastPriceChange()));
		int color;
		if (companyShare.isPositiveChange())
			color = getContext().getResources()
					.getColor(R.color.share_positive);
		else
			color = getContext().getResources()
					.getColor(R.color.share_negative);
		viewHolder.priceChangeTextView.setTextColor(color);
		viewHolder.industryTextView.setText(companyShare.getIndustry());
		return convertView;
	}

	private static class ViewHolder {
		public TextView nameTextView, priceTextView, priceChangeTextView,
				industryTextView;
		public ImageButton menuImageButton;
	}

}
