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

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.Collections;
import java.util.List;

import jp.jun_nama.droidkaigi2017.qiitabrowsersample.api.QiitaService;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.model.QiitaItem;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.model.User;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.viewmodel.MyProfile;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

public class MainApplication extends Application {
    private static long RETROFIT_CACHE_SIZE = 1024 * 1024 * 16; // 16MB

    private OkHttpClient commonOkHttpClient;
    private OkHttpClient retrofitOkHttpClient;
    private OkHttpClient glideOkHttpClient;
    private Retrofit retrofit;


    private Subject<List<QiitaItem>, List<QiitaItem>> qiitaItemsSubject;
    private Subject<List<QiitaItem>, List<QiitaItem>> qiitaFavsSubject;
    private Subject<User, User> myProfileSubject;


    public synchronized OkHttpClient getRetrofitOkHttpClient() {
        if (retrofitOkHttpClient == null) {
            File cacheDir = new File(getCacheDir(), "okhttp3");
            cacheDir.mkdirs();
            OkHttpClient.Builder builder = getCommonOkHttpClient().newBuilder();
            builder.cache(new Cache(cacheDir, RETROFIT_CACHE_SIZE));
            retrofitOkHttpClient = builder.build();

        }
        return retrofitOkHttpClient;
    }

    public synchronized OkHttpClient getGlideOkHttpClient() {
        if (glideOkHttpClient == null) {
            glideOkHttpClient = getCommonOkHttpClient();
        }
        return glideOkHttpClient;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Subject<List<QiitaItem>, List<QiitaItem>> getQiitaItemsSubject() {
        return qiitaItemsSubject;
    }

    public Subject<List<QiitaItem>, List<QiitaItem>> getQiitaFavsSubject() {
        return qiitaFavsSubject;
    }

    public Subject<User, User> getMyProfileSubject() {
        return myProfileSubject;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
        qiitaItemsSubject = BehaviorSubject.create(Collections.<QiitaItem>emptyList()).toSerialized();
        qiitaFavsSubject = BehaviorSubject.create(Collections.<QiitaItem>emptyList()).toSerialized();
        myProfileSubject = BehaviorSubject.create(new User("android.resource://" + getPackageName() + "/" + R.mipmap.ic_launcher, "unknown", "unknown")).toSerialized();
    }

    private synchronized void initRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(QiitaService.BASE_URL)
                    .client(getRetrofitOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

    private synchronized OkHttpClient getCommonOkHttpClient() {
        if (commonOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (!TextUtils.isEmpty(BuildConfig.API_KEY)) {
                builder.addNetworkInterceptor(new QiitaService.AuthorizationInterceptor());
            }
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addNetworkInterceptor(loggingInterceptor);
            commonOkHttpClient = builder.build();
        }
        return commonOkHttpClient;
    }
}
