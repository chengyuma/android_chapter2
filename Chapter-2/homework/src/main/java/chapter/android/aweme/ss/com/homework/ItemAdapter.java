package chapter.android.aweme.ss.com.homework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chapter.android.aweme.ss.com.homework.widget.CircleImageView;
import chapter.android.aweme.ss.com.homework.model.Message;

/**
 * 适配器
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.NumberViewHolder> {

    private static final String TAG = "ItemAdapter";

    private int mNumberItems;
    private List<Message> messages;

    private final ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    public ItemAdapter(List<Message> message, ListItemClickListener listener) {
        mNumberItems = message.size();
        messages = message;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }


    /*
     * 一般会预留2~4个ViewHolder，off screen的数量由mCachedSize来决定
     *
     * The number of ViewHolders that have been created. Typically, you can figure out how many
     * there should be by determining how many list items fit on your screen at once and add 2 to 4
     * to that number. That isn't the exact formula, but will give you an idea of how many
     * ViewHolders have been created to display any given RecyclerView.
     *
     * Here's some ASCII art to hopefully help you understand:
     *
     *    ViewHolders on screen:
     *
     *        *-----------------------------*
     *        |         ViewHolder index: 0 |
     *        *-----------------------------*
     *        |         ViewHolder index: 1 |
     *        *-----------------------------*
     *        |         ViewHolder index: 2 |
     *        *-----------------------------*
     *        |         ViewHolder index: 3 |
     *        *-----------------------------*
     *        |         ViewHolder index: 4 |
     *        *-----------------------------*
     *        |         ViewHolder index: 5 |
     *        *-----------------------------*
     *        |         ViewHolder index: 6 |
     *        *-----------------------------*
     *        |         ViewHolder index: 7 |
     *        *-----------------------------*
     *
     *    Extra ViewHolders (off screen)
     *
     *        *-----------------------------*
     *        |         ViewHolder index: 8 |
     *        *-----------------------------*
     *        |         ViewHolder index: 9 |
     *        *-----------------------------*
     *        |         ViewHolder index: 10|
     *        *-----------------------------*
     *        |         ViewHolder index: 11|
     *        *-----------------------------*
     *
     *    index:12 from where?
     *
     *    Total number of ViewHolders = 12
     *
     *
     *    不做特殊处理：最多缓存多少个ViewHolder N(第一屏可见) + 2 mCachedSize + 5*itemType RecyclePool
     *
     *    找到position一致的viewholder才可以复用，新的位置由于position不一致，所以不能复用，重新创建新的
     *    这也是为什么 mCachedViews一开始缓存的是0、1    所以 8、9、10需要被创建，
     *    那为什么10 和 11也要被创建？
     *
     *    当view完全不可见的时候才会被缓存回收，这与item触发getViewForPosition不同，
     *    当2完全被缓存的时候，实际上getViewForPosition已经触发到11了，此时RecyclePool有一个viewholder(可以直接被复用)
     *    当12触发getViewForPosition的时候，由于RecyclePool里面有，所以直接复用这里的viewholder
     *    问题？复用的viewholder到底是 0 1 2当中的哪一个？
     *
     *
     *    RecycleView 对比 ListView 最大的优势,缓存的设计,减少bindView的处理
     */

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.im_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        Message temp = messages.get(viewHolderCount);
        if (temp.isOfficial())
            viewHolder.robot_notice.setVisibility(View.VISIBLE);
        else
            viewHolder.robot_notice.setVisibility(View.GONE);
        viewHolder.tv_time.setText(temp.getTime());
        viewHolder.tv_description.setText(temp.getDescription());
        viewHolder.tv_title.setText(temp.getTitle());
//        viewHolder.iv_avatar.setImageResource(R.drawable.session_system_notice);
//        viewHolder.iv_avatar.;
//        viewHolder.iv_avatar.setImageResource();
        switch (temp.getIcon()) {
            case "TYPE_ROBOT":
                viewHolder.iv_avatar.setImageResource(R.drawable.session_robot);
                break;
            case "TYPE_GAME":
                viewHolder.iv_avatar.setImageResource(R.drawable.icon_micro_game_comment);
                break;
            case "TYPE_SYSTEM":
                viewHolder.iv_avatar.setImageResource(R.drawable.session_system_notice);
                break;
            case "TYPE_USER":
                viewHolder.iv_avatar.setImageResource(R.drawable.icon_girl);
                break;
            case "TYPE_STRANGER":
                viewHolder.iv_avatar.setImageResource(R.drawable.session_stranger);
                break;
        }

//        int backgroundColorForViewHolder = ColorUtils
//                .getViewHolderBackgroundColorFromInstance(context, viewHolderCount);
//        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: " + viewHolderCount);
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder numberViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: #" + position);
        numberViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final ImageView robot_notice;
        private final TextView tv_title;
        private final TextView tv_description;
        private final TextView tv_time;
        private final CircleImageView iv_avatar;


        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
//            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            robot_notice = (ImageView) itemView.findViewById(R.id.robot_notice);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_avatar = (CircleImageView) itemView.findViewById(R.id.iv_avatar);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            ;
//            listItemNumberView.setText(String.valueOf(position));


//            viewHolderIndex.setText(String.format("ViewHolder index: %s", getAdapterPosition()));
//            int backgroundColorForViewHolder = ColorUtils.
//                    getViewHolderBackgroundColorFromInstance(itemView.getContext(), getAdapterPosition() % 10);
//            itemView.setBackgroundColor(backgroundColorForViewHolder);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
