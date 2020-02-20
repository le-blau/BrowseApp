package com.example.blau.browseapp4;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private EditText urlText;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.myWebView);
        urlText = (EditText) findViewById(R.id.urlText);
        //JavaScriptを有効化
        myWebView.getSettings().setJavaScriptEnabled(true);
        //BrowseApp4内でのみブラウズさせるためのメソッド（デフォルトのブラウザに飛ばない）
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                getSupportActionBar().setSubtitle(view.getTitle());  //ページ読み込み後にタイトル表示
                urlText.setText(url);                                //urlTextに対し、サイトのurlをわたす
            }
        });
        myWebView.loadUrl("https://www.google.co.jp/");
    }

    public void showWebsite(View view) {
        String url = urlText.getText().toString().trim();
        myWebView.loadUrl(url);
    }

    //urlバーをタップした時に入力済みののurlを消す
    public void clearUrl(View view) {
        urlText.setText("");
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myWebView != null) {
            myWebView.stopLoading();
            myWebView.setWebViewClient(null);
            myWebView.destroy();
        }
        myWebView = null;
    }

    //進むや戻るができない時、メニューをグレーアウト
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem forwardItem = (MenuItem) menu.findItem(R.id.action_forward);
        MenuItem backItem = (MenuItem) menu.findItem(R.id.action_back);
        forwardItem.setEnabled(myWebView.canGoForward());     //有効無効切り替え
        backItem.setEnabled(myWebView.canGoBack());           //有効無効切り替え
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //メニュー分岐
        switch (id) {
            case R.id.action_reload:
                myWebView.reload();
                return true;          //re
            case R.id.action_forward:
                myWebView.goForward();
                return true;
            case R.id.action_back:
                myWebView.goBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}