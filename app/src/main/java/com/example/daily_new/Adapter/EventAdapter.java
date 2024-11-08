package com.example.daily_new.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.daily_new.Controllers.EventController;
import com.example.daily_new.DAO.Event;
import com.example.daily_new.R;
import com.example.daily_new.View.ItemInfo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class EventAdapter extends BaseAdapter {
    private List<Event> events;
    private Context context;
    private EventController eventController;

    public EventAdapter(List<Event> eventList, Context context, EventController eventController) {
        this.events = eventList;
        this.context = context;
        this.eventController = eventController;
    }
    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_layout,viewGroup,false);
        }
        TextView title =  view.findViewById(R.id.title);
        title.setText(events.get(i).getTitle());
        TextView end =  view.findViewById(R.id.end);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault());;
        end.setText(simpleDateFormat.format(events.get(i).getEndtime().longValue()));
        TextView im = view.findViewById(R.id.im);
        im.setTextSize(45);
        if (!events.get(i).isImportant()) {
            im.setVisibility(View.GONE);
        }
        TextView content = view.findViewById(R.id.content);
        content.setText(events.get(i).getContent());
        // set click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemInfo.class);
                intent.putExtra("item_data", events.get(i));
                context.startActivity(intent);
            }
        });
        // set longclick
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick( View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setMessage("是否删除此条目?").setPositiveButton("是",new DialogInterface.OnClickListener(){
                    int position = viewGroup.indexOfChild(view);
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eventController.DeleteEvent(events.get(position));
                        events.remove(position);
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("否",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
                return true;
            }
        });
        return view;
    }

}
