package com.nikkorejz.retrofitnews;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<PostModel> mPosts;
    ListView mListView;
    NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.listView);

        mPosts = new ArrayList<>();
        mAdapter = new NewsAdapter(mPosts);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClipboardManager mClipboardManager = (ClipboardManager)
                        getSystemService(CLIPBOARD_SERVICE);
                ClipData mData = ClipData.newPlainText("",
                        Html.fromHtml(mPosts.get(i).getElementPureHtml()));
                if (mClipboardManager != null) {
                    mClipboardManager.setPrimaryClip(mData);
                }
                return true;
            }
        });

        onCreateChooseDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ActionChoose:
                onCreateChooseDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onCreateChooseDialog() {
        String[] sites = {
                "bash.im",
                "anekdot.ru",
                "New Stories (anekdot.ru)",
                "Новые Афоризмы (anekdot.ru)",
        };
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Выберите портал");
        mBuilder.setCancelable(false);
        mBuilder.setItems(sites, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        getPosts("bash", 50);
                        break;
                    case 1:
                        getPosts("new anekdot", 50);
                        break;
                    case 2:
                        getPosts("new story", 50);
                        break;
                    case 3:
                        getPosts("new aforizm", 50);
                        break;
                }
                dialogInterface.dismiss();
            }
        });
        mBuilder.create();
        mBuilder.show();
    }

    public void getPosts(String name, int count) {
        mPosts.clear();
        mAdapter.notifyDataSetChanged();
        MyApp.getApi().getData(name, count).enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                mPosts.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
