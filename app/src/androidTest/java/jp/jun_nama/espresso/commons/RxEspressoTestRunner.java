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

package jp.jun_nama.espresso.commons;

import android.os.AsyncTask;
import android.support.test.runner.AndroidJUnitRunner;

import rx.Scheduler;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * <p>
 * A Subclass of {@link AndroidJUnitRunner}
 * which replace the {@code Executor} used by {@code Schedulers.io()} with {@code AsyncTask.THREAD_POOL_EXECUTOR}.
 * </p>
 * <p>
 * It makes Espresso synchronize with background tasks of RxJava because Espresso is aware of {@code AsyncTask.THREAD_POOL_EXECUTOR}.
 * </p>
 * <p>
 * This class is inspired by <a href="https://collectiveidea.com/blog/archives/2016/10/13/retrofitting-espresso">Retrofitting Espresso</a> written by Joshua Kovach.
 * </p>
 */
public class RxEspressoTestRunner extends AndroidJUnitRunner {
    @Override
    public void onStart() {
        RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler originalScheduler) {
                return Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxJavaHooks.reset();
    }
}
