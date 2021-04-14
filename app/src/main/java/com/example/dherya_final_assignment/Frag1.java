package com.example.dherya_final_assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Frag1 extends Fragment {
    public static ListView listView;
    public static ArrayAdapter<String> stringArrayAdapter;
    public static View popupView;
    ArrayList<String> arrayList = new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag1_layout,container,false);
        listView = view.findViewById(R.id.todo_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupView = inflater.inflate(R.layout.add_todo, null);
                int width = 800;
                int height = 500;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                EditText editText = popupView.findViewById(R.id.add_todo_popup);
                String toChangeValue = arrayList.get(position);
                editText.setText(toChangeValue);

                Button button = popupView.findViewById(R.id.CustomButton);
                button.setText("Update TODO");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseClass databaseClass = new DatabaseClass(getContext());
                        databaseClass.updateTodo(toChangeValue,editText.getText().toString());
                        Intent intent =  new Intent(getContext(),HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(getContext(), "Value Updated", Toast.LENGTH_SHORT).show();
                    }
                });
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseClass databaseClass = new DatabaseClass(getContext());
                if(databaseClass.deleteTodo(arrayList.get(position)) >= 1){
                    Toast.makeText(getContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(getContext(),HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        changeData();
        return view;
    }

    public void changeData(){
        try {
            DatabaseClass databaseClass = new DatabaseClass(getContext());
            Cursor c = databaseClass.getTodoDetails();
            while(c.moveToNext())
            {
                arrayList.add(c.getString(1));
            }

            stringArrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.todo_list,arrayList);
            //SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.todo_list, c, column, id, 0);

                listView.setAdapter(stringArrayAdapter);
                stringArrayAdapter.notifyDataSetChanged();
        }
        catch (Exception exception)
        {
            Toast.makeText(getContext(), "Error.. Try Again!!", Toast.LENGTH_SHORT).show();
        }
    }
}
