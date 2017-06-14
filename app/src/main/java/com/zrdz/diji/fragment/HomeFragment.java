package com.zrdz.diji.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrdz.diji.MyApplication;
import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseFragment;
import com.zrdz.diji.bean.MenuInfo;
import com.zrdz.diji.home.pilequery.activity.QrcodeActivity;
import com.zrdz.diji.home.testmanage.TestManageActivity;
import com.zrdz.diji.listener.RecyclerViewClickListener;
import com.zrdz.diji.home.pilequery.PileManageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, RecyclerViewClickListener {


    private View inflate;
    private List<ImageView> adList = new ArrayList<>();//存放广告
    private List<ImageView> pointList = new ArrayList<>();//广告点
    private int[] imageId = {R.drawable.ban, R.drawable.ban2, R.drawable.ban3};
    private int[] points = {R.id.home_point2, R.id.home_point1, R.id.home_point0};
    private ViewPager viewPager;
    private boolean isDrag = false;//用于判断是否有滑动
    private int beforeInt = 0;
    private List<MenuInfo> menulist = new ArrayList();//菜单


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == inflate) {
            inflate = inflater.inflate(R.layout.fragment_home, container, false);
            initView();
        }
        return inflate;
    }

    private void initView() {
        initPoint();
        initMenu();
        viewPager = (ViewPager) inflate.findViewById(R.id.home_ad);
        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(this);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.home_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setHasFixedSize(true);
        GrideMenuAdapter adapter = new GrideMenuAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        postDelayAd();

    }

    /**
     * 对菜单初始化
     */
    private void initMenu() {
        menulist.add(0, new MenuInfo("0", getString(R.string.pilesearch), R.drawable.pile));
        menulist.add(1, new MenuInfo("1", getString(R.string.topbox), R.drawable.set_top_box));

        menulist.add(2, new MenuInfo("2", getString(R.string.piletest), R.drawable.icon_piletest));
        menulist.add(3, new MenuInfo("3", getString(R.string.detectionmanage), R.drawable.icon_detection));
    }

    /**
     * 初始化点点
     */
    private void initPoint() {
        for (int i = 0; i < points.length; i++) {
            ImageView imageView = (ImageView) inflate.findViewById(points[i]);
            pointList.add(imageView);
        }
    }


    /**
     * 广告自动跳动
     */
    private void postDelayAd() {
        viewPager.postDelayed(new Runnable() {

            @Override
            public void run() {
                int curItem = viewPager.getCurrentItem();
                if (!isDrag) {
                    curItem++;
                    viewPager.setCurrentItem(curItem);
                }
                viewPager.postDelayed(this, 3000);
            }
        }, 3000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (isAdded()) {
            int pi = position % 3;
            pointList.get(pi).setBackground(getResources().getDrawable(R.drawable.white_point));
            pointList.get(beforeInt).setBackground(getResources().getDrawable(R.drawable.gray_point));
            beforeInt = pi;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE://默認状态
                isDrag = false;
                break;
            case ViewPager.SCROLL_STATE_DRAGGING://滑动
                isDrag = true;
                break;
            case ViewPager.SCROLL_STATE_SETTLING://滑动结束
                isDrag = false;
                break;
        }
    }

    @Override
    public void setOnItemClick(View v, int position) {
        if (MyApplication.isLogin) {
            switch (position) {
                case 0:
                    Intent intent = new Intent(getActivity(), PileManageActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    break;
                case 2:
                    Intent intent1 = new Intent(getActivity(), TestManageActivity.class);
                    startActivity(intent1);
                    break;
                case 3:
                    startActivity(new Intent(getActivity(), QrcodeActivity.class));
                    break;
            }
        } else {
            showToast(getString(R.string.pleaseloginfirst));
        }
    }


    /**
     * 广告适配器
     */
    class MyAdapter extends PagerAdapter {
        ImageView imageView = null;

        @Override
        public int getCount() {
            return 300000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            adList.add((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int size = adList.size();
            if (size == 0) {
                imageView = new ImageView(getActivity());
            } else {
                imageView = adList.get(0);
                adList.remove(imageView);
            }
            imageView.setBackground(getResources().getDrawable(imageId[position % 3]));
            container.addView(imageView);
            return imageView;
        }
    }

    /**
     * 菜单适配器
     */
    class GrideMenuAdapter extends RecyclerView.Adapter<GrideMenuAdapter.ViewHolder> {
        private RecyclerViewClickListener listener;


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_recycleview, parent, false);
            ViewHolder viewHolder = new ViewHolder(view, listener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String id = menulist.get(position).getId();
            holder.mTextView.setText(menulist.get(position).getName());
            holder.mImageView.setImageResource(menulist.get(position).getIcon_id());
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return menulist.size();
        }

        public void setListener(RecyclerViewClickListener listener) {
            this.listener = listener;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mTextView;
            private ImageView mImageView;
            private RecyclerViewClickListener listener;

            public ViewHolder(View v, RecyclerViewClickListener listener) {
                super(v);
                this.listener = listener;
                mTextView = (TextView) v.findViewById(R.id.tv_menuName);
                mImageView = (ImageView) v.findViewById(R.id.img_menuIcon);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (null != listener) {
                    Log.e("======onclick===", "===onclick===");
                    listener.setOnItemClick(view, getPosition());
                }
            }
        }
    }
}
