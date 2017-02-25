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

import java.io.IOException;
import java.io.InputStream;

import jp.jun_nama.droidkaigi2017.qiitabrowsersample.BuildConfig;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.MainApplication;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QiitaService {

    public static final String BASE_URL = "https://qiita.com/api/v2/";

    public static class AuthorizationInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .header("Authorization", "Bearer " + BuildConfig.API_KEY)
                    .build();
            return chain.proceed(request);
        }
    }

}
