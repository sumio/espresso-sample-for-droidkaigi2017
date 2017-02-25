/*
 * Copyright (C) 2017 TOYAMA Sumio <jun.nama@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package jp.jun_nama.droidkaigi2017.qiitabrowsersample;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import jp.jun_nama.droidkaigi2017.qiitabrowsersample.databinding.ViewQiitaItemBinding;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.model.QiitaItem;


public class QiitaItemsAdapter extends RecyclerView.Adapter<QiitaItemsAdapter.QiitaItemsViewHolder> {

    private static final String TAG = QiitaItemsAdapter.class.getSimpleName();
    private List<QiitaItem> qiitaItemList;

    public QiitaItemsAdapter() {
        this.qiitaItemList = Collections.emptyList();
    }

    public void setQiitaItemList(@NonNull List<QiitaItem> qiitaItemList) {
        Log.d(TAG, "setQiitaItemList: " + qiitaItemList);
        this.qiitaItemList = qiitaItemList;
        notifyDataSetChanged();
    }

    @Override
    public QiitaItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewQiitaItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_qiita_item, parent, false);
        return new QiitaItemsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(QiitaItemsViewHolder holder, int position) {
        QiitaItem item = qiitaItemList.get(position);
        holder.binding.setQiitaItem(item);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return qiitaItemList.size();
    }

    public static class QiitaItemsViewHolder extends RecyclerView.ViewHolder {

        private final ViewQiitaItemBinding binding;

        public QiitaItemsViewHolder(ViewQiitaItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
