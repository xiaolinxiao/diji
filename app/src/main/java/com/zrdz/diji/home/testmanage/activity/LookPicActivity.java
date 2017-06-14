package com.zrdz.diji.home.testmanage.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zrdz.diji.R;
import com.zrdz.diji.baseui.BaseBackTitleActivity;
import com.zrdz.diji.bean.Photo;
import com.zrdz.diji.utils.CommonUtils;
import com.zrdz.diji.utils.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * 桩号对应图片界面
 */
public class LookPicActivity extends BaseBackTitleActivity implements AdapterView.OnItemClickListener {

    private String photopath;
    private TextView text_nopic;//无图片
    private List<Photo> list = new ArrayList<>();
    private ImageView imageview;//大图片
    private TextView text_time;
    private TextView text_place;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photopath = getIntent().getStringExtra("photopath");
        initArgument(R.layout.activity_look_pic, getString(R.string.lookpic));
        imageLoader = ImageLoader.getInstance();
        initView();
    }

    private void initView() {
        text_nopic = (TextView) findViewById(R.id.pic_text_info);
        Gallery gallery = (Gallery) findViewById(R.id.pic_gallery);
        imageview = (ImageView) findViewById(R.id.pic_text_imageview);
        text_time = (TextView) findViewById(R.id.pic_text_time);
        text_place = (TextView) findViewById(R.id.pic_text_place);
        MyAdapter adapter = new MyAdapter();
        gallery.setAdapter(adapter);
        List<Photo> result = CommonUtils.paringPhotoUrl(photopath);
        if (null == result || result.size() == 0) {
            text_nopic.setVisibility(View.VISIBLE);
            showToast(getString(R.string.nopicnopush));
        } else {
            list.addAll(result);
            adapter.notifyDataSetChanged();
            //默认显示第一张图
            gallery.setSelection(0);
            showPhoto(0);
        }
        gallery.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showPhoto(position);
    }

    /**
     * 展示图片及相关信息
     *
     * @param position
     */
    private void showPhoto(int position) {
        Photo photo = list.get(position);
        String url = Const.MAIN_URL + Const.PHOTO_MOUDLE + photo.filePath;
        imageLoader.displayImage(url, imageview);
        if ("0".equals(photo.againUp)) {
            text_time.setText(getString(R.string.pushtime) + ":" + photo.photoTime);
        } else {
            text_time.setText(getString(R.string.reupdatetime) + ":" + photo.photoTime);
        }
        if ("1".equals(photo.place)) {
            text_place.setText(R.string.topboxconnectstatus);
        } else if ("2".equals(photo.place)) {
            text_place.setText(R.string.positivefigure);
        } else if ("3".equals(photo.place)) {
            text_place.setText(R.string.profile);
        } else if ("4".equals(photo.place)) {
            text_place.setText(R.string.above);
        } else {
            text_place.setText(R.string.pilerecordword);
        }
    }


    /**
     * 适配器
     */
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.item_photo, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_photo_image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String url = Const.MAIN_URL + Const.PHOTO_MOUDLE + list.get(position).filePath;
            imageLoader.displayImage(url, viewHolder.imageView);
            return convertView;
        }
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
