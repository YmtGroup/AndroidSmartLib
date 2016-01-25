package com.binary.smartlib.ui.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * Created by yaoguoju on 15-12-24.
 */
public abstract class SmartFragment extends Fragment implements OnClickListener{
    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater,container,savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        onViewClick(v);
    }


    /**
     * 启动应用内Activty
     * @param context
     * @param toClass
     * @param bundle
     */
    protected void toActivity(Context context,Class<?> toClass,Bundle bundle) {
        if(context == null || toClass == null) {
            return ;
        }
        Intent intent = new Intent(context,toClass);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 启动应用外Activty
     * @param pkgname
     * @param classname
     * @param bundle
     */
    protected void toActivty(String pkgname,String classname,Bundle bundle) {
        if(TextUtils.isEmpty(pkgname) || TextUtils.isEmpty(classname)) {
            return ;
        }
        ComponentName componentName = new ComponentName(pkgname,classname);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 初始化界面
     * @param inflater
     * @param container
     * @param saveInstanceState
     * @return
     */
    protected abstract View initView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState);

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 处理界面View点击事件
     * @param v
     */
    protected abstract void onViewClick(View v);
}
