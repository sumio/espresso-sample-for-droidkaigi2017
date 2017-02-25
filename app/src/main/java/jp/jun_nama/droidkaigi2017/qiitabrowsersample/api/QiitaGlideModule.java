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

package jp.jun_nama.droidkaigi2017.qiitabrowsersample.api;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import jp.jun_nama.droidkaigi2017.qiitabrowsersample.MainApplication;
import okhttp3.OkHttpClient;

/**
 * Created by sumio on 2017/02/25.
 */
public class QiitaGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // do nothing
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        MainApplication app = (MainApplication) context.getApplicationContext();
        OkHttpClient client = app.getGlideOkHttpClient();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }
}
