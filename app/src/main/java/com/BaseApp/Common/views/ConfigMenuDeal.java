package com.BaseApp.Common.views;

import com.BaseApp.R;
import com.BaseApp.Common.model.ConfigType;
import com.BaseApp.Common.model.DataItemDetail;
import com.BaseApp.Common.model.DataItemResult;

/*
 * 左边菜单栏的配置处理
 */
public class ConfigMenuDeal {

    /**
     * 底部tab类型菜单选项
     *
     * @return
     */
    public DataItemResult getButtomTabTypeList() {
        return getBottomMenuList();

    }


    /**
     * 根据配置文件条件返回底部菜单的一些选项
     *
     * @return
     */
    private DataItemResult getBottomMenuList() {
        DataItemResult items = new DataItemResult();
        DataItemDetail temp;
        items.clear();

        temp = new DataItemDetail();
        temp.setIntValue("iconID", R.mipmap.tab_icon_board_def);
        temp.setIntValue("iconSelectID", R.mipmap.tab_icon_board_slc);
        temp.setStringValue(ConfigType.CONFIG_TYPE_TITLE_TAG, "TAB1");
        temp.setStringValue(ConfigType.CONFIG_TYPE_KEY_TAG, ConfigType.TAB_1);
        items.addItem(temp);
        temp = new DataItemDetail();
        temp.setIntValue("iconID", R.mipmap.tab_icon_account_def);
        temp.setIntValue("iconSelectID", R.mipmap.tab_icon_account_slc);
        temp.setStringValue(ConfigType.CONFIG_TYPE_TITLE_TAG, "TAB2");
        temp.setStringValue(ConfigType.CONFIG_TYPE_KEY_TAG, ConfigType.TAB_2);
        items.addItem(temp);
        temp = new DataItemDetail();
        temp.setIntValue("iconID", R.mipmap.tab_icon_increase_def);
        temp.setIntValue("iconSelectID", R.mipmap.tab_icon_increase_slc);
        temp.setStringValue(ConfigType.CONFIG_TYPE_TITLE_TAG, "TAB3");
        temp.setStringValue(ConfigType.CONFIG_TYPE_KEY_TAG, ConfigType.TAB_3);
        items.addItem(temp);
        temp = new DataItemDetail();
        temp.setIntValue("iconID", R.mipmap.tab_icon_note_def);
        temp.setIntValue("iconSelectID", R.mipmap.tab_icon_note_slc);
        temp.setStringValue(ConfigType.CONFIG_TYPE_TITLE_TAG, "TAB4");
        temp.setStringValue(ConfigType.CONFIG_TYPE_KEY_TAG, ConfigType.TAB_4);
        items.addItem(temp);
        temp = new DataItemDetail();
        temp.setIntValue("iconID", R.mipmap.tab_icon_calculation_def);
        temp.setIntValue("iconSelectID", R.mipmap.tab_icon_calculation_slc);
        temp.setStringValue(ConfigType.CONFIG_TYPE_TITLE_TAG, "TAB5");
        temp.setStringValue(ConfigType.CONFIG_TYPE_KEY_TAG, ConfigType.TAB_5);
        items.addItem(temp);

        return items;
    }

}
