package com.micropole.sxwine.module.personal.Bean;

import android.app.Activity;

import com.blankj.utilcode.util.Utils;
import com.micropole.sxwine.R;
import com.micropole.sxwine.module.personal.ContactServiceActivity;
import com.micropole.sxwine.module.personal.FQAActivity;
import com.micropole.sxwine.module.personal.MyCollectActivity;
import com.micropole.sxwine.module.personal.MyTeamActivity;
import com.micropole.sxwine.module.personal.MyWalletActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JohnnyH on 2018/6/8.
 * 我的收藏
 */

public class MineItemEntity {
    private int icon;
    private String name;
    private Class<? extends Activity> activity;

    public MineItemEntity(int icon, String name, Class<? extends Activity> activity) {
        this.icon = icon;
        this.name = name;
        this.activity = activity;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Activity> getActivity() {
        return activity;
    }

    public void setActivity(Class<? extends Activity> activity) {
        this.activity = activity;
    }

    public static List<MineItemEntity> createList(){
        List<MineItemEntity> data = new ArrayList<>();
        data.add(new MineItemEntity(R.mipmap.my_btn_wallet, Utils.getApp().getString(R.string.my_wallet), MyWalletActivity.class));
        data.add(new MineItemEntity(R.mipmap.my_btn_team,Utils.getApp().getString(R.string.my_team), MyTeamActivity.class));
        data.add(new MineItemEntity(R.mipmap.my_btn_collection,Utils.getApp().getString(R.string.my_collect), MyCollectActivity.class));
        data.add(new MineItemEntity(R.mipmap.my_btn_problem,Utils.getApp().getString(R.string.tv_issue), FQAActivity.class));
        data.add(new MineItemEntity(R.mipmap.my_btn_customer_service,Utils.getApp().getString(R.string.call_serivce), ContactServiceActivity.class));
        return data;
    }
}
