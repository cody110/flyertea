package com.ideal.flyerteacafes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ideal.flyerteacafes.R;
import com.ideal.flyerteacafes.model.entity.CreditCardBackBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 信用卡查询结果返回
 *
 * @author fly
 */
public class CreditCardResultAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<CreditCardBackBean> backList;
    private Context context;

    public CreditCardResultAdapter(Context context,
                                   List<CreditCardBackBean> backList) {
        inflater = LayoutInflater.from(context);
        this.backList = backList;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return backList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return backList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(
                    R.layout.listview_creditcard_query_results_item, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CreditCardBackBean bean = (CreditCardBackBean) getItem(position);
        holder.bank.setText(bean.getBankname());
        holder.payType.setText(bean.getPay_type());
        holder.time.setText(bean.getDate());
        holder.user.setText(bean.getUsername());
        holder.scores.setText(bean.getTypename());
        if (bean.getTypename().equals("有积分"))
            holder.scores.setTextColor(context.getResources().getColor(
                    R.color.app_bg_title));
        else if (bean.getTypename().equals("多倍积分"))
            holder.scores.setTextColor(context.getResources().getColor(
                    R.color.app_body_yellow));
        else if (bean.getTypename().equals("无积分"))
            holder.scores.setTextColor(context.getResources().getColor(
                    R.color.card_not_integral));
        else if (bean.getTypename().equals("可能没积分"))
            holder.scores.setTextColor(context.getResources().getColor(
                    R.color.card_maybe_not_integral));
        return convertView;
    }

    ViewHolder holder = null;

    private class ViewHolder {
        @ViewInject(R.id.query_results_bank)
        TextView bank;
        @ViewInject(R.id.query_results_author)
        TextView user;
        @ViewInject(R.id.query_results_time)
        TextView time;
        @ViewInject(R.id.query_results_pay_type)
        TextView payType;
        @ViewInject(R.id.query_results_scores)
        TextView scores;
    }

}
