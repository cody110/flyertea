package com.ideal.flyerteacafes.ui.activity;

import java.io.Serializable;
import java.util.List;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.callback.JsonAnalysis;
import com.ideal.flyerteacafes.callback.StringCallback;
import com.ideal.flyerteacafes.model.entity.CreditCardBean;
import com.ideal.flyerteacafes.model.entity.ListObjectBean;
import com.ideal.flyerteacafes.ui.activity.base.BaseActivity;
import com.ideal.flyerteacafes.utils.Utils;
import com.ideal.flyerteacafes.utils.xutils.FlyRequestParams;
import com.ideal.flyerteacafes.utils.xutils.XutilsHttp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 信用卡积分
 *
 * @author fly
 */
@ContentView(R.layout.activity_creditcard_point)
public class CreditCardPointActivity extends BaseActivity {

    @ViewInject(R.id.include_title_text)
    private TextView titleView;
    @ViewInject(R.id.creditcard_point_bank_name_text)
    private TextView bankText;
    @ViewInject(R.id.creditcard_point_back_point_text)
    private TextView backText;
    @ViewInject(R.id.creditcard_point_business_type_text)
    private TextView typeText;

    ListObjectBean banklist;
    ListObjectBean scoreslist;
    ListObjectBean mcclist;

    List<CreditCardBean> listBank;
    List<CreditCardBean> listScores;
    List<CreditCardBean> listMcc;

    int type_id = 0;
    int scores = 0;
    int code = 0;

    private int index = 0;//
    private int pos = 0;
    private int typePos = 0;
    private int scoresPos = 0;
    private int codePos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        request();
    }

    private void initView() {
        titleView.setText(getString(R.string.title_name_creditcard_point));
    }

    @Event(R.id.include_title_menu_btn)
    private void closeMenu(View v) {
        finish();
    }

    @Event({R.id.creditcard_point_bank_name_layout, R.id.creditcard_point_back_point_layout, R.id.creditcard_point_business_type_layout})
    private void chooseType(View v) {
        Intent intent = new Intent(CreditCardPointActivity.this, CreditCardChooseActivity.class);
        switch (v.getId()) {
            case R.id.creditcard_point_bank_name_layout:
                index = 0;
                if (banklist == null)
                    return;
                intent.putExtra("list", (Serializable) listBank);
                intent.putExtra("titleName", getString(R.string.creditcard_point_bank_name));
                intent.putExtra("pos", typePos);
                break;

            case R.id.creditcard_point_back_point_layout:
                index = 1;
                if (scoreslist == null)
                    return;
                intent.putExtra("list", (Serializable) listScores);
                intent.putExtra("titleName", getString(R.string.creditcard_point_back_point));
                intent.putExtra("pos", scoresPos);
                break;

            case R.id.creditcard_point_business_type_layout:
                index = 2;
                if (mcclist == null)
                    return;
                intent.putExtra("list", (Serializable) listMcc);
                intent.putExtra("titleName", getString(R.string.creditcard_point_business_type));
                intent.putExtra("pos", codePos);
                break;
        }
        startActivityForResult(intent, 12);
    }

    @Event(R.id.creditcard_point_commit_btn)
    private void inquiry(View v) {
        Intent intent = new Intent(CreditCardPointActivity.this, CreditCardResultsActivity.class);
        intent.putExtra("type_id", type_id);
        intent.putExtra("scores", scores);
        intent.putExtra("mcc", code);
        startActivity(intent);
    }

    /**
     * 信用卡基础数据
     */
    private void request() {
        XutilsHttp.Get(new FlyRequestParams(Utils.HttpRequest.HTTP_CREDIT_BASIC), new StringCallback() {
            @Override
            public void flySuccess(String result) {
                banklist = JsonAnalysis.jsonToCreditCardList(result, "banklist");
                scoreslist = JsonAnalysis.jsonToCreditCardList(result, "scoreslist");
                mcclist = JsonAnalysis.jsonToCreditCardList(result, "mcclist");
                CreditCardBean bean = new CreditCardBean();
                bean.setName(getString(R.string.def_all));
                bean.setId(0);
                listBank = banklist.getDataList();
                listScores = scoreslist.getDataList();
                listMcc = mcclist.getDataList();
                if (listBank != null)
                    listBank.add(0, bean);
                if (listScores != null)
                    listScores.add(0, bean);
                if (listMcc != null)
                    listMcc.add(0, bean);
            }
        });
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 12 && arg1 == RESULT_OK) {
            pos = arg2.getIntExtra("pos", 0);
            CreditCardBean creditCardBean = null;
            switch (index) {
                case 0:
                    creditCardBean = listBank.get(pos);
                    bankText.setText(creditCardBean.getName());
                    type_id = creditCardBean.getId();
                    typePos = pos;
                    break;

                case 1:
                    creditCardBean = listScores.get(pos);
                    backText.setText(creditCardBean.getName());
                    scores = creditCardBean.getId();
                    scoresPos = pos;
                    break;

                case 2:
                    creditCardBean = listMcc.get(pos);
                    typeText.setText(creditCardBean.getName());
                    code = creditCardBean.getId();
                    codePos = pos;
                    break;
            }

        }
    }


}
