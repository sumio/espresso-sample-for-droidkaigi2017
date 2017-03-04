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

# License

Copyright 2017 TOYAMA Sumio &lt;jun.nama@gmail.com&gt;  
Licensed under the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
