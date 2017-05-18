package com.example.foolishguy.fcm_chatting;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    private static FirebaseAuth firebaseAuth;
    private EditText input;
    private FloatingActionButton fab_send;

    private RecyclerView recyclerView;

    LinearLayoutManager recylerViewLayoutManager;

    private FirebaseRecyclerAdapter<Message, ListAdapter> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getIntent().hasExtra("r_id") &&
                getIntent().getExtras().getString("r_id") != null &&
                !getIntent().getExtras().getString("r_id").isEmpty()) {

            firebaseAuth = FirebaseAuth.getInstance();
            fab_send = (FloatingActionButton) findViewById(R.id.fab_send);
            input = (EditText) findViewById(R.id.input);

            fab_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message message = new Message();
                    message.setMsg_Text(input.getText().toString());
                    message.setR_id(getIntent().getExtras().getString("r_id"));
                    message.setS_id(firebaseAuth.getCurrentUser().getUid());
                    //message.setSid_rid(firebaseAuth.getCurrentUser().getUid() + "_" + getIntent().getExtras().getString("r_id"));
                    //message.setRid_sid(getIntent().getExtras().getString("r_id") + "_" + firebaseAuth.getCurrentUser().getUid());

                    final DatabaseReference reference = dbRefSpace.chats.push();
                    reference.setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            reference.child("msg_time").setValue(ServerValue.TIMESTAMP);
                        }
                    });
                    input.setText("");
                }
            });
            recylerViewLayoutManager = new LinearLayoutManager(ChatActivity.this);
            displayChatMessage();
        } else {
            finish();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        displayChatMessage();
    }

    private void displayChatMessage () {

        Log.e ("MSG :", "DISP");
        recyclerView = (RecyclerView) findViewById(R.id.msg);
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("chats").orderByChild("s_id")
                .equalTo(firebaseAuth.getCurrentUser().getUid());
                //.equalTo(getIntent().getExtras().getString("r_id") + "_" + firebaseAuth.getCurrentUser().getUid());
        Log.e ("MSG :", "QRY");
        adapter = new FirebaseRecyclerAdapter<Message, ListAdapter>(Message.class,
                R.layout.message, ListAdapter.class, query) {
            @Override
            protected void populateViewHolder(ListAdapter viewHolder, Message model, int position) {
                if (model.getR_id().equalsIgnoreCase(getIntent().getExtras().getString("r_id"))) {
                    viewHolder.createView(adapter.getRef(position), model);
                    Log.e("MSG :", "POP");
                }
            }
        };
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public static class ListAdapter extends RecyclerView.ViewHolder {

        View view;
        DatabaseReference ref;
        Message msg;
        TextView name;

        public ListAdapter(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void createView (DatabaseReference ref, Message msg) {
            this.ref = ref;
            this.msg = msg;
            setData ();
        }

        private void setData () {
            name = (TextView) view.findViewById(R.id.user_name);
            TextView text = (TextView) view.findViewById(R.id.msg_text);
            TextView time = (TextView) view.findViewById(R.id.msg_time);

            if (firebaseAuth.getCurrentUser().getUid().equalsIgnoreCase(msg.getS_id())) {
                text.setTextColor(Color.DKGRAY);
                time.setTextColor(Color.DKGRAY);
                name.setTextColor(Color.DKGRAY);

                name.setGravity(Gravity.RIGHT);
                time.setGravity(Gravity.RIGHT);
                text.setGravity(Gravity.RIGHT);

                FirebaseDatabase.getInstance().getReference().child("users").child(msg.getS_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        name.setText(user.getEmail());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                text.setTextColor(Color.BLACK);
                time.setTextColor(Color.BLACK);
                name.setTextColor(Color.BLACK);

                name.setGravity(Gravity.LEFT);
                time.setGravity(Gravity.LEFT);
                text.setGravity(Gravity.LEFT);
                FirebaseDatabase.getInstance().getReference().child("users").child(msg.getR_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        name.setText(user.getEmail());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            text.setText(msg.getMsg_Text());
            time.setText(android.text.format.DateFormat.format("dd-MM-yy [HH:mm:ss]",
                        msg.getMsg_time()));
        }

    }
}
