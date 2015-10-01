package c.mars.multichoicelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

import rx.Observable;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.list)
    ListView listView;

    List<String> items = Observable.range(0, 10).map(i->String.valueOf(Math.round(Math.random()*100)))
            .toList().toBlocking().first();
    MultiChoiceAdapter adapter;

    @AfterViews void init() {
        adapter=new MultiChoiceAdapter(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(
                new AbsListView.MultiChoiceModeListener() {
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                        final int checkedCount = listView.getCheckedItemCount();
                        mode.setTitle(String.format("Selected %d", checkedCount));

                        adapter.select(position, checked);
                        View child = listView.getChildAt(position);
                        child.setBackgroundResource(checked ? android.R.color.holo_green_dark : android.R.color.transparent);
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.selection_menu, menu);
                        adapter.clearSelection();
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                adapter.removeSelected();
                                mode.finish();
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        int count = listView.getChildCount();
                        for (int i=0;i<count;i++) {
                            View view=listView.getChildAt(i);
                            if (view!=null) view.setBackgroundResource(android.R.color.transparent);
                        }
                    }
                }
        );
    }


}
