package com.hq.expandablelistviewexample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.hq.expandablelistviewexample.R;
import com.hq.expandablelistviewexample.adapter.CollocationListAdapter;
import com.hq.expandablelistviewexample.adapter.XListViewNew;
import com.hq.expandablelistviewexample.bean.CollocationPackageBean;
import com.hq.expandablelistviewexample.bean.CollocationSkuBean;
import com.hq.expandablelistviewexample.bean.CollocationSkuChildBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * TT609549345
 */
public class MainActivity extends AppCompatActivity implements CollocationListAdapter.OnCheckBoxListener {
    private XListViewNew elv_collocation;
    private List<CollocationPackageBean> collocationList;
    private boolean isExpanded = false, isCKBAll = false;
    private CollocationListAdapter collocationListAdapter;
    private CheckBox ckb_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        elv_collocation = (XListViewNew) findViewById(R.id.elv_collocation);
        initData();

        final TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }

            private void setData() {
                try {
                    isExpanded = !isExpanded;
                    tv_title.setText("点击我ＭＭＰ" + isExpanded + "");
                    for (int a = 0; a < collocationList.size(); a++) {
                        List<CollocationSkuBean> collocationSkuBeans = collocationList.get(a).getCollocationSkuDoList();
                        for (int ii = 0; ii < collocationSkuBeans.size(); ii++) {
                            collocationSkuBeans.get(ii).setShow(isExpanded);
                        }
                    }
                    collocationListAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ckb_all = (CheckBox) findViewById(R.id.ckb_all);
        ckb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }

            private void setData() {
                try {
                    isCKBAll = !isCKBAll;
                    for (int a = 0; a < collocationList.size(); a++) {
                        CollocationPackageBean collocationPackageBea = collocationList.get(a);
                        collocationPackageBea.setCheckBox(isCKBAll);

                        List<CollocationSkuBean> collocationSkuBeans = collocationPackageBea.getCollocationSkuDoList();
                        for (int b = 0; b < collocationSkuBeans.size(); b++) {
                            CollocationSkuBean collocationSkuBean = collocationSkuBeans.get(b);
                            collocationSkuBean.setCheckBox(isCKBAll);

                            List<CollocationSkuChildBean> collocationSkuChildBeans = collocationSkuBean.getCollocationSkuChildBeans();
                            for (int ii = 0; ii < collocationSkuChildBeans.size(); ii++) {
                                CollocationSkuChildBean collocationSkuChildBean = collocationSkuChildBeans.get(ii);
                                collocationSkuChildBean.setCheckBox(isCKBAll);
                            }
                        }
                    }
                    collocationListAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initData() {
        collocationList = new ArrayList<>();
        CollocationPackageBean collocation_1 = new CollocationPackageBean();
        CollocationPackageBean collocation_2 = new CollocationPackageBean();
///==============
        collocation_1.setTotalPrice(new BigDecimal(897));
        collocation_1.setDiscountFee(new BigDecimal(20));
        collocation_1.setName("818国货套餐3");
        collocation_1.setCheckBox(false);

        List<CollocationSkuChildBean> collocationSkuChildBeans_1 = new ArrayList<>();
        collocationSkuChildBeans_1.add(new CollocationSkuChildBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", 0, false));
        collocationSkuChildBeans_1.add(new CollocationSkuChildBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", 1, false));
        collocationSkuChildBeans_1.add(new CollocationSkuChildBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", 2, false));

        List<CollocationSkuChildBean> collocationSkuChildBeans_1_0 = new ArrayList<>();
        collocationSkuChildBeans_1_0.add(new CollocationSkuChildBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", 2, false));
        collocationSkuChildBeans_1_0.add(new CollocationSkuChildBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", 2, false));
        collocationSkuChildBeans_1_0.add(new CollocationSkuChildBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", 2, false));

        List<CollocationSkuBean> goodsList_1 = new ArrayList<>();
        goodsList_1.add(new CollocationSkuBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", false, collocationSkuChildBeans_1, false));
        goodsList_1.add(new CollocationSkuBean("VR PLUS 智能眼镜vr虚拟现实头盔 3D沉浸式 暴风魔镜 vr plus 智能头盔 白色", "http://img15.hqbcdn.com/product/c6/10/c610075082199955a8d5dcf2aa765b17.jpg", false, collocationSkuChildBeans_1_0, false));
        collocation_1.setCollocationSkuDoList(goodsList_1);
///==============
        collocation_2.setTotalPrice(new BigDecimal(1034));
        collocation_2.setDiscountFee(new BigDecimal(26));
        collocation_2.setName("超值套餐");
        collocation_2.setCheckBox(false);

        List<CollocationSkuChildBean> collocationSkuChildBeans_2 = new ArrayList<>();
        collocationSkuChildBeans_2.add(new CollocationSkuChildBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", 2, false));
        collocationSkuChildBeans_2.add(new CollocationSkuChildBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", 2, false));
        collocationSkuChildBeans_2.add(new CollocationSkuChildBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", 2, false));

        List<CollocationSkuChildBean> collocationSkuChildBeans_2_1 = new ArrayList<>();
        collocationSkuChildBeans_2_1.add(new CollocationSkuChildBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", 2, false));

        List<CollocationSkuBean> goodsList_2 = new ArrayList<>();
        goodsList_2.add(new CollocationSkuBean("Meizu/魅族 魅蓝 note3 全网通 手机 银白 16GB", "http://img11.hqbcdn.com/product/07/0a/070ac7abd57c6d9251d89547f3d62501.jpg", false, collocationSkuChildBeans_2, false));
        goodsList_2.add(new CollocationSkuBean("Uka/优加 Meizu/魅族 魅蓝 note3全覆盖全屏钢化玻璃膜 白色", "http://img8.hqbcdn.com/product/9c/15/9c15571aa92905ea1edafb0a288f1ebb.jpg", false, collocationSkuChildBeans_2_1, false));
        collocation_2.setCollocationSkuDoList(goodsList_2);
///==============
        collocationList.add(collocation_1);
        collocationList.add(collocation_2);
        collocationListAdapter = new CollocationListAdapter(this, elv_collocation, collocationList);
        collocationListAdapter.setOnCheckBoxListener(this);
        elv_collocation.setAdapter(collocationListAdapter);
        try {
            int groupCount = elv_collocation.getCount();
            for (int i = 0; i < groupCount; i++) {
                elv_collocation.expandGroup(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCustomViewClick(int isStura, int iNum, int groupPosition, int childPosition, int childchildPosition) {
        if(iNum<=1 && isStura==0){//当ｎｕｍ为１且为减号　那么不执行
            Toast.makeText(getApplicationContext(), "至少选择一个数量"  , Toast.LENGTH_LONG).show();
            return;
        }
        switch (isStura) {
            case 0://=============================================================减号
                iNum--;
                collocationList.get(groupPosition).getCollocationSkuDoList().get(childPosition).getCollocationSkuChildBeans().get(childchildPosition).setNumber(iNum);
                break;
            case 1://=============================================================数字
                Toast.makeText(getApplicationContext(), "当前数量=" + iNum, Toast.LENGTH_LONG).show();
                break;
            case 2://=============================================================加号
                iNum++;
                collocationList.get(groupPosition).getCollocationSkuDoList().get(childPosition).getCollocationSkuChildBeans().get(childchildPosition).setNumber(iNum);
                break;
        }
        collocationListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckBoxClick(View v, boolean isCheckBox, int desID, String des, int groupPosition, int childPosition, int childchildPosition) {
        switch (desID) {
            case 0: {//============getGroupView
                CollocationPackageBean collocationPackageBea = collocationList.get(groupPosition);
                collocationPackageBea.setCheckBox(isCheckBox);

                List<CollocationSkuBean> collocationSkuBeans = collocationPackageBea.getCollocationSkuDoList();
                for (int b = 0; b < collocationSkuBeans.size(); b++) {
                    CollocationSkuBean collocationSkuBean = collocationSkuBeans.get(b);
                    collocationSkuBean.setCheckBox(isCheckBox);

                    List<CollocationSkuChildBean> collocationSkuChildBeans = collocationSkuBean.getCollocationSkuChildBeans();
                    for (int ii = 0; ii < collocationSkuChildBeans.size(); ii++) {
                        collocationSkuChildBeans.get(ii).setCheckBox(isCheckBox);
                    }
                }

                //===============处理是否全选
                setSelectAll(isCheckBox);
            }
            break;
            case 1: {//============getChildView
                CollocationSkuBean collocationSkuBea = collocationList.get(groupPosition).getCollocationSkuDoList().get(childPosition);
                collocationSkuBea.setCheckBox(isCheckBox);
                List<CollocationSkuChildBean> skuChildBeans = collocationSkuBea.getCollocationSkuChildBeans();
                for (int ii = 0; ii < skuChildBeans.size(); ii++) {
                    skuChildBeans.get(ii).setCheckBox(isCheckBox);
                }

                //===============处理是否全选
                setChildFormGroup(isCheckBox, groupPosition);
                setSelectAll(isCheckBox);
            }
            break;
            case 2://============getIsShowChildChild
                collocationList.get(groupPosition).getCollocationSkuDoList().get(childPosition).getCollocationSkuChildBeans().get(childchildPosition).setCheckBox(isCheckBox);
                //===============处理是否全选
                setChildFormChildFormGroup(isCheckBox, groupPosition, childPosition);
                setChildFormGroup(isCheckBox, groupPosition);
                setSelectAll(isCheckBox);
                break;
        }
        collocationListAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), des + "－" + groupPosition + "－" + childPosition + "－" + childchildPosition + "-" + isCheckBox, Toast.LENGTH_LONG).show();
    }

    private void setChildFormChildFormGroup(boolean isCheckBox, int groupPosition, int childPosition) {
        if (isCheckBox) {
            boolean isAll = true;
            List<CollocationSkuChildBean> collocationSkuChildBeans = collocationList.get(groupPosition).getCollocationSkuDoList().get(childPosition).getCollocationSkuChildBeans();
            for (int jj = 0; jj < collocationSkuChildBeans.size(); jj++) {
                if (!collocationSkuChildBeans.get(jj).isCheckBox()) {
                    isAll = false;
                    collocationList.get(groupPosition).getCollocationSkuDoList().get(childPosition).setCheckBox(false);
                    break;
                }
            }
            collocationList.get(groupPosition).getCollocationSkuDoList().get(childPosition).setCheckBox(isAll);
        } else {
            collocationList.get(groupPosition).getCollocationSkuDoList().get(childPosition).setCheckBox(false);
        }
    }

    private void setChildFormGroup(boolean isCheckBox, int groupPosition) {
        if (isCheckBox) {
            boolean isAll = true;
            for (int jj = 0; jj < collocationList.get(groupPosition).getCollocationSkuDoList().size(); jj++) {
                if (!collocationList.get(groupPosition).getCollocationSkuDoList().get(jj).isCheckBox()) {
                    isAll = false;
                    collocationList.get(groupPosition).setCheckBox(false);
                    break;
                }
            }
            collocationList.get(groupPosition).setCheckBox(isAll);
        } else {
            collocationList.get(groupPosition).setCheckBox(false);
        }
    }

    private void setSelectAll(boolean isCheckBox) {
        if (isCheckBox) {
            boolean isAll = true;
            for (int jj = 0; jj < collocationList.size(); jj++) {
                if (!collocationList.get(jj).isCheckBox()) {
                    isAll = false;
                    ckb_all.setChecked(false);
                    break;
                }
            }
            ckb_all.setChecked(isAll);
        } else {
            ckb_all.setChecked(false);
        }
    }
}
