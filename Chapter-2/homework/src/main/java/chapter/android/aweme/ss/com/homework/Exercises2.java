package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chapter.android.aweme.ss.com.homework.R;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relativelayout);

        View view = this.getWindow().getDecorView();
        int a = getAllChildViewCount(view)-2;
        // 查阅相关资料知DecorView为整个Window界面的最顶层View。
        // DecorView只有一个子元素为LinearLayout。代表整个Window界面，包含通知栏，标题栏，内容显示栏三块区域。
        // LinearLayout里有两个FrameLayout子元素。
        // 第一个为标题栏显示界面。只有一个TextView显示应用的名称。也可以自定义标题栏，载入后的自定义标题栏View将加入FrameLayout中。
        // 第二个为内容栏显示界面。
        // 要去除通知栏和标题栏等，所以要在调用完减2
        final TextView tv_center = findViewById(R.id.tv_center);
        tv_center.setText(String.valueOf(a));
    }

    public int getAllChildViewCount(View view) {
        //todo 补全你的代码
        int num = 0;

        if (view == null)
            return 0;
        if (view instanceof ViewGroup) {
//            System.out.print("count: ");
//            System.out.println(((ViewGroup) view).getChildCount());
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//                System.out.print("i=");
//                System.out.println(i);
                View view1 = ((ViewGroup) view).getChildAt(i);
                if (view1 instanceof ViewGroup) {
//                    System.out.print("GroupView ");
                    num += getAllChildViewCount(view1);
//                    System.out.println(num);
                }
                else {
                    num++;
//                    System.out.print("else ");
//                    System.out.println(num);

                }
            }
        }
        else
            num++;

        return num;
    }
}
