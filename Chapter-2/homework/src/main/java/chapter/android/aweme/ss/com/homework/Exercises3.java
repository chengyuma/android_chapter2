package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.io.InputStream ;
import java.util.List;

import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.model.PullParser;

/**
 * 大作业:实现一个抖音消息页面,
 * 1、所需的data数据放在assets下面的data.xml这里，使用PullParser这个工具类进行xml解析即可
 *  * <p>如何读取assets目录下的资源，可以参考如下代码</p>
 *  * <pre class="prettyprint">
 *  *
 *  *         @Override
 *  *     protected void onCreate(@Nullable Bundle savedInstanceState) {
 *  *         super.onCreate(savedInstanceState);
 *  *         setContentView(R.layout.activity_xml);
 *  *         //load data from assets/data.xml
 *  *         try {
 *             InputStream assetInput = getAssets().open("data.xml");
 *             List<Message> messages = PullParser.pull2xml(assetInput);
 *  *             for (Message message : messages) {
 *  *
 *  *             }
 *  *         } catch (Exception exception) {
 *             exception.printStackTrace();
 *         }
 *     }
 * </pre>
 * 2、所需UI资源已放在res/drawable-xxhdpi下面
 *
 * 3、作业中的会用到圆形的ImageView,可以参考 widget/CircleImageView.java
 */


public class Exercises3 extends AppCompatActivity implements ItemAdapter.ListItemClickListener {

    private static final String TAG = "wangyi";
    private static final int NUM_LIST_ITEMS = 100;

    private ItemAdapter mAdapter;
    private RecyclerView mNumbersListView;
    private List<Message> message;

    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tips);
        mNumbersListView = findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mNumbersListView.setLayoutManager(layoutManager);
        mNumbersListView.setHasFixedSize(true);

        //        load data from assets/data.xml
        try {
            InputStream assetInput = getAssets().open("data.xml");
            List<Message> messages = PullParser.pull2xml(assetInput);
            message = messages;
//              for (Message message : messages) {
//                    mLifecycleDisplay.append(message.toString());
//              }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        mAdapter = new ItemAdapter(message, this);
        mNumbersListView.setAdapter(mAdapter);
        mNumbersListView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // 最后一个完全可见项的位置
            private int lastCompletelyVisibleItemPosition;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
//                        Toast.makeText(Exercises3.this, "已滑动到底部!,触发loadMore", Toast.LENGTH_SHORT).show();
//                    }
//                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                }
                Log.d(TAG, "onScrolled: lastVisiblePosition=" + lastCompletelyVisibleItemPosition);
            }
        });

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d(TAG, "onListItemClick: ");
//        if (mToast != null) {
//            mToast.cancel();
//        }
//        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
//        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
//
//        mToast.show();
//        Intent intent = new Intent();
//        intent.setClass(Exercises3.this, intent.class);
//        intent.putExtra("num", clickedItemIndex);
//        startActivity(Chatroom.class);

        Intent intent = new Intent();
        intent.putExtra("num", String.valueOf(clickedItemIndex));
        intent.setClass(this, Chatroom.class);
        startActivity(intent);
//        startActivity(new Intent(this, Chatroom.class));
    }
}

