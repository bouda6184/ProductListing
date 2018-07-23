package david.example.com.productlisting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewer extends AppCompatActivity {
    private WebView webViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewer);
        String websiteURL = this.getIntent().getStringExtra("url");
        webViewer = (WebView)findViewById(R.id.webviewer);
        webViewer.setWebViewClient(new WebViewClient());
        webViewer.loadUrl(websiteURL);
        webViewer.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed(){
        if(webViewer.canGoBack()){
            webViewer.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
