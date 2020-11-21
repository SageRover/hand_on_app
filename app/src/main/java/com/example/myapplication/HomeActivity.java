package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.example.myapplication.recyclerview.MyAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button but;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private TextView texts;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        LoginActivity.activityList.add(this);

        //Bmob默认初始化
        Bmob.initialize(this, "d26004a40bcb7e3f58360c2a17332282");
        //Recyclerview初始化及其实现
        initRecyclerview();
        //底部导航栏
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.setting, "Setting"))
                .addItem(new BottomNavigationItem(R.drawable.user, "User"))
                .addItem(new BottomNavigationItem(R.drawable.jiangshi, "Books"))
                .addItem(new BottomNavigationItem(R.drawable.international, "International"))
                .setFirstSelectedPosition(0)
                .initialise();


        texts = (TextView) findViewById(R.id.textView4);
        texts.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/方正清刻本悦宋简体.TTF"));
        texts.setEnabled(false);

        //Bundle bundle = this.getIntent().getExtras();
        but.setClickable(true);
        // 因为用TextView点击，将属性改为true，点击后返回封面
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(); //新建Intent对象
                intent2.setClass(HomeActivity.this, LoginActivity.class);
                startActivityForResult(intent2, 0);   //返回前一页
            }
        });
    }

    private void initRecyclerview() {
        //设置布局管理器 
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置Item增加、移除动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerview.addItemDecoration(new MyDecoration());

        MyAdapter adapter = new MyAdapter(R.layout.item_recyclerview, createImageIDData());
        recyclerview.setAdapter(adapter);
//      // 设置新的数据方法
//      adapter.setNewData(createImageIDData());
//      adapter.setAnimationWithDefault(AlphaAnimation);


        //条目点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemClick: ");
                Toast.makeText(HomeActivity.this, "点击了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
//            //大图预览
//            Intent intent = new Intent(HomeActivity.this,PictureDisplayActivity.class);
//            intent.putExtra("position",position);
//            intent.putIntegerArrayListExtra("enlargeImage", (ArrayList<Integer>)createImageIDData());
//            startActivity(intent);

                XPopup(position, adapter);
            }
        });


        //条目长按事件
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemLongClick: ");
                adapter.remove(position);
                Toast.makeText(HomeActivity.this, "长按了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void XPopup(int position, BaseQuickAdapter adapter) {
        // 图片加载器，XPopup不负责加载图片，需要你实现一个图片加载器传给我，这里以Glide为例（可直接复制到项目中）:
        class ImageLoader implements XPopupImageLoader {
            @Override
            public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView) {
                //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
                Glide.with(imageView).load(url).apply(new RequestOptions().override(Target.SIZE_ORIGINAL)).into(imageView);
            }

            //必须实现这个方法，返回uri对应的缓存文件，可参照下面的实现，内部保存图片会用到。如果你不需要保存图片这个功能，可以返回null。
            @Override
            public File getImageFile(@NonNull Context context, @NonNull Object uri) {
                try {
                    return Glide.with(context).downloadOnly().load(uri).submit().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        //当你点击图片的时候执行以下代码：
        // 多图片场景（你有多张图片需要浏览）
        //srcView参数表示你点击的那个ImageView，动画从它开始，结束时回到它的位置。
        new XPopup.Builder(this).asImageViewer((ImageView) adapter.getViewByPosition(position, R.id.Recycler_item_imageView), position, (ArrayList) createImageIDData(), new OnSrcViewUpdateListener() {
            @Override
            public void onSrcViewUpdate(ImageViewerPopupView popupView, int position) {
                // 作用是当Pager切换了图片，需要更新源View
                popupView.updateSrcView((ImageView) adapter.getViewByPosition(position,  R.id.Recycler_item_imageView));
            }
        }, new ImageLoader())
                .show();

//        // 单张图片场景
//        new XPopup.Builder(this)
//                .asImageViewer(null, createImageIDData().get(position), new ImageLoader())
//                .show();


    }

    @NotNull
    private List<Integer> createImageIDData() {

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                data.add(R.drawable.a2);
            } else {
                data.add(R.drawable.a1);
            }
        }
        return data;
    }

//    private void initRecyclerview() {
//        //？数据list初始化，生成一个包含100个数据的ArrayList-用于后续删除操作
//        initData();
//        //设置布局管理器 
//        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        //设置Item增加、移除动画
//        recyclerview.setItemAnimator(new DefaultItemAnimator());
////        //设置内边距
////        recyclerview.setPadding(8, 8, 8, 8);
//        //添加分割线
//        recyclerview.addItemDecoration(new MyDecoration());
//
//        //设置适配器 
//        adapter = new MyRecyclerAdapter(this, createData());   //输入整型列表List到适配器作为构造方法参数 ，List由本活动内的createData()方法生成
//        recyclerview.setAdapter(adapter);
//
//
//        //以下为设置监听 adapter，类似于设置按钮点击事件
//        adapter.setOnClickListener(new MyRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void ItemClickListener(View view, int position) {
//                Toast.makeText(HomeActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void ItemLongClickListener(View view, int position) {
//                //长按删除
//                lists.remove(position);
//                adapter.notifyItemRemoved(position);
//            }
//        });
//    }

//    /**
//     * 这个方法应该和长按删除有关
//     */
//    private void initData() {
//        lists = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            lists.add("" + i);  //数字前面加个双引号直接将数值化为String，这是利用java的toString机制来做的转换，任何类型在和String相加的时候，都会先转换成String。
//        }
//    }

//    @NotNull        //返回值data不能为空
//    //1到100交替存储图像资源
//    private List<Integer> createData() {
//        List<Integer> data = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            if (i % 2 == 0) {
//                data.add(R.drawable.a1);
//            } else {
//                data.add(R.drawable.a2);
//            }
//        }
//        return data;
//    }
//public void showPictureDialog(final int mPosition) {
//    //创建dialog
//    mDialog = new Dialog(this, R.style.PictureDialog);
//    final Window window1 = mDialog.getWindow() ;
//    WindowManager m = getWindowManager();
//    Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//    WindowManager.LayoutParams p = window1.getAttributes(); // 获取对话框当前的参数值
//    p.height = (int) (d.getHeight() * 1.0); // 改变的是dialog框在屏幕中的位置而不是大小
//    p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕
//    window1.setAttributes(p);
//    View inflate = View.inflate(QuestionDetailsActivity.this, R.layout.picture_dialog, null);//该layout在后面po出
//    int screenWidth = getResources().getDisplayMetrics().widthPixels;
//    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(screenWidth, ViewGroup.LayoutParams.MATCH_PARENT);
//    mDialog.setContentView(inflate, layoutParams);
//    pager = (ViewPagerFixed) inflate.findViewById(R.id.gallery01);
//    pager.setOnPageChangeListener(pageChangeListener);
//    PicturePageAdapter adapter = new PicturePageAdapter((ArrayList<String>) mListPicPath, this);
//    pager.setAdapter(adapter);
//    pager.setPageMargin(0);
//    pager.setCurrentItem(mPosition);        window1.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    mDialog.setOnKeyListener(new DialogOnKeyListener());
//    mDialog.show();
//    adapter.setOnPictureClickListener(new PicturePageAdapter.OnPictureClickListener() {
//        @Override
//        public void OnClick() {                window1.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            mDialog.dismiss();
//        }
//    });
//    //长按图片保存
//    adapter.setOnPictureLongClickListener(new PicturePageAdapter.OnPictureLongClickListener() {
//        @Override
//        public void OnLongClick() {
//            //展示保存取消dialog
//            showPicDialog();
//        }
//    });
//}

}


