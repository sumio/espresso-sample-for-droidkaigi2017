# Espresso Sample for DroidKaigi 2017

DroidKaigi 2017のトーク「[変更に強いEspressoテストコードを効率良く書こう](https://droidkaigi.github.io/2017/timetable.html#session-26)」
で利用予定のサンプルアプリです。

# アプリの概要

- [Qiita](http://qiita.com/)の最新の投稿を20件取得してリスト表示します。
- リスト表示された記事のうち、好きなものをを「お気に入り」に登録することができます。
- 「お気に入り」に登録した記事は「Local Favorites」メニューから確認することができます。
- このアプリで登録した「お気に入り」は永続化されません。アプリを終了すると消去されます。

# ビルド方法

- Qiitaの「[アプリケーション設定](https://qiita.com/settings/applications)」のページで、「個人用アクセストークン」を発行します。
- 発行したアクセストークンを、`local.properties`の`apiKey`プロパティに設定してください。
- Android Studio 2.3でインポートし、ビルドしてください。

# テストコードについて

当日の発表で実演したときのテストコードを`app/src/androidTest` 配下に配置しています。
実演内容を見直してみたいときに活用してください。  
※ デモシナリオに沿って、事前に作成したものなので、実演内容とは一部異なる箇所があります。

- [`Step1.java`](app/src/androidTest/java/jp/jun_nama/droidkaigi2017/qiitabrowsersample/Step1.java):
  操作・検証内容を、Espresso Test Recorderを使って記録した直後のものです。
  当日スライドpp. [60](https://speakerdeck.com/sumio/droidkaigi2017-lets-write-sustainable-espresso-test-rapidly?slide=60)-[64](https://speakerdeck.com/sumio/droidkaigi2017-lets-write-sustainable-espresso-test-rapidly?slide=64)に対応します。
- [`Step2.java`](app/src/androidTest/java/jp/jun_nama/droidkaigi2017/qiitabrowsersample/Step2.java):
  Step1で記録した内容で、そのままでは動作しない箇所を手直ししたものです。
  当日スライドpp. [67](https://speakerdeck.com/sumio/droidkaigi2017-lets-write-sustainable-espresso-test-rapidly?slide=67)-[70](https://speakerdeck.com/sumio/droidkaigi2017-lets-write-sustainable-espresso-test-rapidly?slide=70)に対応します。
- [`Step3.java`](app/src/androidTest/java/jp/jun_nama/droidkaigi2017/qiitabrowsersample/Step3.java):
  Step2で手直しした内容を元に、メソッド抽出したものです。
  当日スライドp. [71](https://speakerdeck.com/sumio/droidkaigi2017-lets-write-sustainable-espresso-test-rapidly?slide=71)に対応します。
- [`Step4.java`](app/src/androidTest/java/jp/jun_nama/droidkaigi2017/qiitabrowsersample/Step3.java):
  Step3で抽出したメソッドを使って、テストケースを実装したものです。
  当日スライドp. [72](https://speakerdeck.com/sumio/droidkaigi2017-lets-write-sustainable-espresso-test-rapidly?slide=72)に対応します。

その他、[`jp.jun_nama.espresso.commons`](app/src/androidTest/java/jp/jun_nama/espresso/commons)パッケージに、補助的なクラスを用意していますので、あわせて活用してください。

- [`RecyclerViewUtils.java`](app/src/androidTest/java/jp/jun_nama/espresso/commons/RecyclerViewUtils.java):
当日スライドpp. [67](https://speakerdeck.com/sumio/droidkaigi2017-lets-write-sustainable-espresso-test-rapidly?slide=67)-[69](https://speakerdeck.com/sumio/droidkaigi2017-lets-write-sustainable-espresso-test-rapidly?slide=69)で紹介したユーティリティメソッドを定義しています。
- [`RxEspressoTestRunner.java`](app/src/androidTest/java/jp/jun_nama/espresso/commons/RxEspressoTestRunner.java):
当日スライドp. [73](https://speakerdeck.com/sumio/droidkaigi2017-lets-write-sustainable-espresso-test-rapidly?slide=73)の③で紹介した、RxJava1の`Schedulers.io()`を`AsyncTask`のスレッドプールに差し替えることで、Espressoの非同期待ち合わせを実現するサンプルです。この方法を利用する場合は、以下の点に注意してください。
  - RxJava2では、`RxJavaHooks`クラスが存在しないため、このままでは動作しません。
    代わりに[`RxJavaPlugins`](http://reactivex.io/RxJava/2.x/javadoc/io/reactivex/plugins/RxJavaPlugins.html)クラスを使えば、同様な方法で実現できると思います。
  - カスタムテストランナーとして実装されているため、[`build.gradle`の`testInstrumentationRunner`](app/build.gradle#L14)を修正する必要があります。
  - RxJavaで、`Schedulers.computation()`などの、メインスレッドでも`Schedulers.io()`でもないスケジューラを使っている場合は、そのままでは動作しないと思われます。  
  利用している全てのスケジューラのスレッドプールを、同様な方法で`AsyncTask`のスレッドプールに差し替えることで対応できる可能性があります。
  - このクラスは、Joshua Kovach氏によるブログ記事「[Retrofitting Espresso](https://collectiveidea.com/blog/archives/2016/10/13/retrofitting-espresso)」で紹介されている、Kotlinで書かれたサンプルコードを参考に、Javaで実装し直したものです。
- [`MoreViewMatchers.java`](app/src/androidTest/java/jp/jun_nama/espresso/commons/MoreViewMatchers.java): 当日スライドp. [46](https://speakerdeck.com/sumio/droidkaigi2017-lets-write-sustainable-espresso-test-rapidly?slide=46)で紹介した、Espresso Test Recorderのバグ([Issue 231461](https://code.google.com/p/android/issues/detail?id=231461))を回避した`childAtPosition()`メソッドの実装です。

# License

Copyright 2017 TOYAMA Sumio &lt;jun.nama@gmail.com&gt;  
Licensed under the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
