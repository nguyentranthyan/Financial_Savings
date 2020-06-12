package com.example.financial_savings.controllers.categories;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.interfaces.IMappingView;

public class CateIconsActivity extends AppCompatActivity implements IMappingView {
    private ImageButton buttonCancel;
    private GridView gridView;
    private int[] imageIDs = {
            R.drawable.muasam, R.drawable.nganhang, R.drawable.maybay, R.drawable.dichuyen,
            R.drawable.xemphim, R.drawable.dulich, R.drawable.kinhdoanh, R.drawable.dienthoai,
            R.drawable.chupanh, R.drawable.concai, R.drawable.yte, R.drawable.tienxang,
            R.drawable.nguoiyeu, R.drawable.thehinh, R.drawable.banbe, R.drawable.amnhac,
            R.drawable.giadinh, R.drawable.khachsan, R.drawable.luongthuc, R.drawable.anuong,
            R.drawable.tailieu, R.drawable.giaitri, R.drawable.giaoduc, R.drawable.hoadon,
            R.drawable.luong, R.drawable.suachua, R.drawable.cafe, R.drawable.wifi,
            R.drawable.quatang, R.drawable.noithat, R.drawable.muasach, R.drawable.suadienthoai,
            R.drawable.dongho, R.drawable.movie, R.drawable.bus, R.drawable.vetranh,
            R.drawable.banhoa, R.drawable.giaohang, R.drawable.thanhtich, R.drawable.tienthuocla,
            R.drawable.chungkhoan, R.drawable.radio, R.drawable.dipha, R.drawable.sinhnhat,
            R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4,
            R.drawable.icon5, R.drawable.icon6, R.drawable.icon7, R.drawable.icon8,
            R.drawable.icon9, R.drawable.icon10, R.drawable.icon11, R.drawable.icon12,
            R.drawable.icon13, R.drawable.icon14, R.drawable.icon15, R.drawable.icon16,
            R.drawable.icon17, R.drawable.icon18, R.drawable.icon19, R.drawable.icon20,
            R.drawable.icon21, R.drawable.icon22, R.drawable.icon23, R.drawable.icon24
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_icons);
        init();
        eventReturn();
        eventSelectIcon();
    }

    private void eventSelectIcon() {
        gridView.setAdapter(new ImageAdapterGridView(CateIconsActivity.this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String icon = getBaseContext().getResources().getResourceEntryName(imageIDs[position]);
                Intent intent = new Intent();
                intent.putExtra("icon", icon);
                setResult(RESULT_OK, intent);
                onBackPressed();
            }
        });
    }

    private void eventReturn() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void init() {
        buttonCancel = findViewById(R.id.buttonCancel_category_icons);
        gridView = findViewById(R.id.gridView_category_icons);
        getSupportActionBar().hide();
    }

    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context c) {
            mContext = c;
        }

        public int getCount() {
            return imageIDs.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(100, 100, 100, 100);
                mImageView.setBackground(getResources().getDrawable(R.drawable.border_radius_icon));
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(imageIDs[position]);
            return mImageView;
        }
    }
}
