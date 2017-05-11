package com.androiddev.zf.devframe.base;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by greedy on 17/3/14.
 */

public abstract class BaseMultiRecyclerFragment<T extends MultiItemEntity, V extends BaseMultiItemQuickAdapter<T, BaseViewHolder>> extends BaseRecyclerFragment<T, V> {
}
