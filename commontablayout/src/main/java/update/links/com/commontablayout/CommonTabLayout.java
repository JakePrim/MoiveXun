package update.links.com.commontablayout;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/28 0028
 * 描    述：自定义底部导航栏
 * 修订历史：
 * ================================================
 */
public class CommonTabLayout extends FrameLayout {
    private Context mContext;

    public CommonTabLayout(@NonNull Context context) {
        super(context, null, 0);
    }

    public CommonTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CommonTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }
}
