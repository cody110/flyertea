package com.ideal.flyerteacafes.ui.activity.interfaces;

import com.ideal.flyerteacafes.model.entity.BankBean;
import com.ideal.flyerteacafes.model.entity.CardGroupBean;
import com.ideal.flyerteacafes.model.entity.TaskNameBean;

import java.util.List;

/**
 * Created by fly on 2017/4/13.
 */

public interface ISwingCardTaskName {

    /**
     * 任务列表
     * @param datas
     */
    void bindDataTaskNameList(List<TaskNameBean> datas);

    /**
     * 卡组织
     * @param datas
     */
    void bindDataCardGroupList(List<CardGroupBean> datas);

    /**
     * 银行
     * @param datas
     */
    void bindDataBankList(List<BankBean> datas);

}
