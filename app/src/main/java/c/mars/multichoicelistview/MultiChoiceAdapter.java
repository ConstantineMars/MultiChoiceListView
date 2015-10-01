package c.mars.multichoicelistview;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Constantine Mars on 10/1/15.
 */
public class MultiChoiceAdapter extends ArrayAdapter<String> {

    List<String> items;
    SparseBooleanArray selected = new SparseBooleanArray();

    public MultiChoiceAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        items=objects;
    }

    public void select(int index, boolean checked){
        selected.put(index, checked);
    }

    public void clearSelection(){
        selected.clear();
    }

    public void removeSelected(){
        int size=selected.size();
        List<String> toRemove=new ArrayList<>();
        for (int i=0;i<size;i++){
            boolean isChecked=selected.valueAt(i);
            if(isChecked){
                int index=selected.keyAt(i);
                String item = items.get(index);
                toRemove.add(item);
            }
        }
        items.removeAll(toRemove);
    }
}

