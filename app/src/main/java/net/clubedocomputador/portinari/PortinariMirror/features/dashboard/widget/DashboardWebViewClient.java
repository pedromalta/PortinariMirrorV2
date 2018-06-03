package net.clubedocomputador.portinari.PortinariMirror.features.dashboard.widget;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class DashboardWebViewClient extends WebViewClient {

    private final ProgressBar progressBar;

    public DashboardWebViewClient(ProgressBar progressBar) {
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        showProgress();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        hideProgress();
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        hideProgress();
    }

    private void showProgress(){
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgress(){
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

    }

}

