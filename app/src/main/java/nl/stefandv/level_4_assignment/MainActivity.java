package nl.stefandv.level_4_assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    ArrayList<Item> itemList = new ArrayList<>();
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private ItemRoomDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.BucketList);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, addItem.class);
                startActivityForResult(i, 1);

            }
        });

        db = ItemRoomDatabase.getDatabase(this);


        recyclerView = findViewById(R.id.recyclerv_view);
        adapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = recyclerView.getChildAdapterPosition(child);
                    deleteItem(itemList.get(adapterPosition));
                }
            }
        });
        recyclerView.addOnItemTouchListener(this);
        getAllItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");


                Item item = new Item(title, description);
                insertItem(item);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_item) {
            deleteAllProducts(itemList);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI(List<Item> items) {
        itemList.clear();
        itemList.addAll(items);
        adapter.notifyDataSetChanged();

    }

    private void getAllItems() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final List<Item> items = db.itemDao().getAllItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(items);
                    }
                });
            }
        });
    }


    private void insertItem(final Item item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.itemDao().insert(item);
                getAllItems();
            }
        });
    }

    public void deleteItem(final Item item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.itemDao().delete(item);
                getAllItems();
            }
        });
    }

    private void deleteAllProducts(final List<Item> products) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.itemDao().delete(products);
                getAllItems();
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}