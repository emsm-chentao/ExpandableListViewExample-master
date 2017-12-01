package com.hq.expandablelistviewexample.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hq.expandablelistviewexample.R;
import com.hq.expandablelistviewexample.bean.CollocationBean;
import com.hq.expandablelistviewexample.bean.CollocationChildBean;
import com.hq.expandablelistviewexample.widget.CustomView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

public class CollocationListAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    private Context context;
    private XExpandableListViewNew elv_collocation;
    private List<CollocationBean> data;

    public CollocationListAdapter(Context context, XExpandableListViewNew elv_collocation, List<CollocationBean> data) {
        this.context = context;
        this.elv_collocation = elv_collocation;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getCollocationSkuDoList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getCollocationSkuDoList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;//如果子条目需要响应click事件,必需返回true
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.collocation_list_item_parent, parent, false);
            parentViewHolder = new ParentViewHolder(convertView);
            convertView.setTag(parentViewHolder);
            AutoUtils.auto(convertView);
        } else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }
        CollocationBean collocationPackageBean = data.get(groupPosition);
        parentViewHolder.tv_collocation_name.setText(TextUtils.isEmpty(collocationPackageBean.getName()) ? "优惠套餐" : collocationPackageBean.getName());
        parentViewHolder.tv_save_text.setText("立省￥" + collocationPackageBean.getDiscountFee());
        parentViewHolder.iv_status.setImageResource(isExpanded ? R.mipmap.icon_top : R.mipmap.icon_bottom);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        parentViewHolder.tv_onclick_mmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "MMP" + groupPosition, Toast.LENGTH_LONG).show();
            }
        });
        final CheckBox ckb_parent = parentViewHolder.ckb_parent;
        ckb_parent.setChecked(collocationPackageBean.isCheckBox());
        ckb_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxListener.onCheckBoxClick(v, ckb_parent.isChecked(), 0, "getGroupView", groupPosition, 0, 0);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.collocation_list_item_child, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
            AutoUtils.auto(convertView);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final CollocationChildBean collocationSkuBean = data.get(groupPosition).getCollocationSkuDoList().get(childPosition);
        childViewHolder.sdv_goods_img.setImageURI(Uri.parse(collocationSkuBean.getImageMd5()));
        childViewHolder.tv_goods_title.setText(collocationSkuBean.getSkuTitle());
        childViewHolder.ll_root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入商品详情页操作
            }
        });
        if (childPosition == data.get(groupPosition).getCollocationSkuDoList().size() - 1) {
            //当前套餐的最后一个商品
            childViewHolder.ll_bottom.setVisibility(View.VISIBLE);
            childViewHolder.tv_collocation_price.setText(data.get(groupPosition).getTotalPrice().toString());
            childViewHolder.tv_add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //把套餐商品加入购物车操作
                }
            });
        } else {
            childViewHolder.ll_bottom.setVisibility(View.GONE);
        }

        final CheckBox ckb_child = childViewHolder.ckb_child;
        ckb_child.setChecked(collocationSkuBean.isCheckBox());
        ckb_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxListener.onCheckBoxClick(v, ckb_child.isChecked(), 1, "getChildView", groupPosition, childPosition, 0);
            }
        });
        getIsShowChildChild(groupPosition, childPosition, collocationSkuBean, childViewHolder.ll_goods_list);
        return convertView;
    }

    private void getIsShowChildChild(final int groupPosition, final int childPosition, final CollocationChildBean collocationSkuBean, LinearLayout rootview) {
        if (collocationSkuBean.isShow()) {
            rootview.setVisibility(View.VISIBLE);
            rootview.removeAllViews();
            View collocationView;

            // SimpleDraweeView sdv_cart_image;
            for (int i = 0, len = collocationSkuBean.getCollocationSkuChildBeans().size(); i < len; i++) {
                collocationView = inflater.inflate(R.layout.item_gift_img, null);
                final CheckBox ckb_gif_img = (CheckBox) collocationView.findViewById(R.id.ckb_gif_img);
                ckb_gif_img.setChecked(collocationSkuBean.getCollocationSkuChildBeans().get(i).isCheckBox());
                final int finalI = i;
                ckb_gif_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckBoxListener.onCheckBoxClick(v, ckb_gif_img.isChecked(), 2, "getIsShowChildChild", groupPosition, childPosition, finalI);
                    }
                });

                final CustomView id_ct_customview = (CustomView) collocationView.findViewById(R.id.id_ct_customview);

                final int numb = collocationSkuBean.getCollocationSkuChildBeans().get(i).getNumber();
                id_ct_customview.setEditText(numb);
                id_ct_customview.setListener(new CustomView.ClickListener() {
                    @Override
                    public void click(int count) {
                        onCheckBoxListener.onCustomViewClick(count, numb, groupPosition, childPosition, finalI);
                    }
                });

                // sdv_cart_image = (SimpleDraweeView) collocationView.findViewById(R.id.sdv_cart_image);
                //  sdv_cart_image.setImageURI(Uri.parse(data.get(groupPosition).getCollocationSkuDoList().get(i).getImageMd5()));
                AutoUtils.auto(collocationView);
                rootview.addView(collocationView);
            }
        } else {
            rootview.setVisibility(View.GONE);
        }
    }

    private static class ParentViewHolder {
        //private View v_space;
        private ImageView iv_status;
        private TextView tv_collocation_name, tv_save_text, tv_onclick_mmp;
        private CheckBox ckb_parent;

        private ParentViewHolder(View view) {
            //v_space = view.findViewById(R.id.v_space);
            iv_status = (ImageView) view.findViewById(R.id.iv_status);
            tv_collocation_name = (TextView) view.findViewById(R.id.tv_collocation_name);
            tv_save_text = (TextView) view.findViewById(R.id.tv_save_text);
            tv_onclick_mmp = (TextView) view.findViewById(R.id.tv_onclick_mmp);
            ckb_parent = (CheckBox) view.findViewById(R.id.ckb_parent);
        }
    }

    private static class ChildViewHolder {
        private SimpleDraweeView sdv_goods_img;
        private LinearLayout ll_bottom, ll_root_view, ll_goods_list;
        private TextView tv_add_cart, tv_goods_title, tv_collocation_price;
        private CheckBox ckb_child;

        private ChildViewHolder(View view) {
            sdv_goods_img = (SimpleDraweeView) view.findViewById(R.id.sdv_goods_img);
            ll_bottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
            ll_root_view = (LinearLayout) view.findViewById(R.id.ll_root_view);
            ll_goods_list = (LinearLayout) view.findViewById(R.id.ll_goods_list);
            tv_add_cart = (TextView) view.findViewById(R.id.tv_add_cart);
            tv_goods_title = (TextView) view.findViewById(R.id.tv_goods_title);
            tv_collocation_price = (TextView) view.findViewById(R.id.tv_collocation_price);
            ckb_child = (CheckBox) view.findViewById(R.id.ckb_child);
        }
    }

    private OnCheckBoxListener onCheckBoxListener;

    public void setOnCheckBoxListener(OnCheckBoxListener onCheckBoxListener) {
        this.onCheckBoxListener = onCheckBoxListener;
    }

    public interface OnCheckBoxListener {
        /**
         * @param isStura            isStura  ==0   减号
         *                           isStura  ==1   数字
         *                           isStura  ==2   加号
         * @param iNum
         * @param groupPosition
         * @param childPosition
         * @param childchildPosition
         */
        void onCustomViewClick(int isStura, int iNum, int groupPosition, int childPosition, int childchildPosition);

        /**
         * @param v
         * @param desID              对应哪个点击
         *                           ０　　getGroupView
         *                           １　　getChildView
         *                           ２　　getIsShowChildChild
         * @param des
         * @param groupPosition
         * @param childPosition
         * @param childchildPosition
         */
        void onCheckBoxClick(View v, boolean isCheckBox, int desID, String des, int groupPosition, int childPosition, int childchildPosition);
    }

}
