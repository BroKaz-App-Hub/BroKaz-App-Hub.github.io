package nz.brokaz.mycreativenotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.ValueCallback;
import android.widget.EditText;
import android.widget.Toast;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
    private static final int REQUEST_RESTORE = 1001;
    private static final int REQUEST_BACKUP = 1002;
    private WebView webView;
    private ValueCallback<Uri[]> fileCallback;
    private String backupContents;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(17, 46, 76));
        getWindow().setNavigationBarColor(Color.rgb(17, 46, 76));
        webView = new WebView(this);
        webView.setBackgroundColor(Color.WHITE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(false);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setSupportZoom(false);
        webView.addJavascriptInterface(new BackupBridge(), "AndroidNotes");
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if ("file".equals(uri.getScheme()) && uri.toString().startsWith("file:///android_asset/")) return false;
                if ("https".equals(uri.getScheme()) || "mailto".equals(uri.getScheme())) {
                    try { startActivity(new Intent(Intent.ACTION_VIEW, uri)); } catch (Exception ignored) {}
                }
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> callback, FileChooserParams params) {
                if (fileCallback != null) fileCallback.onReceiveValue(null);
                fileCallback = callback;
                try {
                    Intent picker = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    picker.addCategory(Intent.CATEGORY_OPENABLE);
                    picker.setType("*/*");
                    picker.putExtra(Intent.EXTRA_MIME_TYPES,
                        new String[]{"application/json", "text/plain", "application/octet-stream"});
                    startActivityForResult(picker, REQUEST_RESTORE);
                    return true;
                } catch (Exception e) {
                    fileCallback = null;
                    return false;
                }
            }
            @Override public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(MainActivity.this).setMessage(message)
                    .setPositiveButton("OK", (d,w)-> result.confirm())
                    .setOnCancelListener(d->result.confirm()).show();
                return true;
            }
            @Override public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(MainActivity.this).setMessage(message)
                    .setPositiveButton("OK", (d,w)->result.confirm())
                    .setNegativeButton("Cancel", (d,w)->result.cancel())
                    .setOnCancelListener(d->result.cancel()).show();
                return true;
            }
            @Override public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                EditText entry = new EditText(MainActivity.this);
                entry.setSingleLine(true);
                entry.setText(defaultValue);
                new AlertDialog.Builder(MainActivity.this).setMessage(message).setView(entry)
                    .setPositiveButton("OK", (d,w)-> result.confirm(entry.getText().toString()))
                    .setNegativeButton("Cancel", (d,w)->result.cancel())
                    .setOnCancelListener(d->result.cancel()).show();
                return true;
            }
        });
        setContentView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }

    public class BackupBridge {
        @JavascriptInterface public void saveBackup(String json, String name) {
            if (json == null || json.length() > 20_000_000) {
                runOnUiThread(()->Toast.makeText(MainActivity.this, "Backup is too large.", Toast.LENGTH_LONG).show());
                return;
            }
            runOnUiThread(()->{
                backupContents = json;
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/json");
                intent.putExtra(Intent.EXTRA_TITLE,
                    name != null && name.matches("[a-zA-Z0-9._-]{1,100}") ? name : "MyCreativeNotes-Backup.json");
                try { startActivityForResult(intent, REQUEST_BACKUP); }
                catch(Exception e) { backupContents = null; Toast.makeText(MainActivity.this, "Could not choose a backup location.", Toast.LENGTH_LONG).show(); }
            });
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RESTORE && fileCallback != null) {
            Uri[] chosen = resultCode == RESULT_OK && data != null && data.getData() != null
                ? new Uri[]{data.getData()} : null;
            fileCallback.onReceiveValue(chosen);
            fileCallback = null;
        } else if (requestCode == REQUEST_BACKUP) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null && backupContents != null) {
                try (OutputStream output = getContentResolver().openOutputStream(data.getData())) {
                    if (output == null) throw new IllegalStateException("No file output");
                    output.write(backupContents.getBytes(StandardCharsets.UTF_8));
                    Toast.makeText(this, "Notes backup saved.", Toast.LENGTH_SHORT).show();
                } catch(Exception e) {
                    Toast.makeText(this, "Could not save backup: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            backupContents = null;
        }
    }
    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
    @Override protected void onDestroy() {
        if (fileCallback != null) { fileCallback.onReceiveValue(null); fileCallback = null; }
        if (webView != null) { webView.destroy(); webView = null; }
        super.onDestroy();
    }
}
