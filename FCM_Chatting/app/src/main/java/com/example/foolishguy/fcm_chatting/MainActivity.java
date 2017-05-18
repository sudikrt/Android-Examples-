package com.example.foolishguy.fcm_chatting;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static com.example.foolishguy.fcm_chatting.dbRefSpace.wFBAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private static int SIGN_IN_REQUEST_CODE = 101;
    private FirebaseRecyclerAdapter<User, ListAdapter> adapter;

    private RecyclerView recyclerView;
    private SpotsDialog dialog;

    DatabaseReference userRef;

    LinearLayoutManager recylerViewLayoutManager;
    private static Boolean fAuthSet = false;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (dbRefSpace.hasUserSignedIn && userRef == null) {
            userRef = dbRefSpace.users;
        }
        displayChatList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseAuth = FirebaseAuth.getInstance();

        if (!fAuthSet) {
            wFBAuth = FirebaseAuth.getInstance();
            dbRefSpace.setwAuth(wFBAuth);//updates all variable
            fAuthSet = true;
        }
        if (firebaseAuth.getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                                .build(), SIGN_IN_REQUEST_CODE);

        } else {
            Toast toast = Toast.makeText(MainActivity.this, "Hi " +
                    firebaseAuth.getCurrentUser().getEmail(),
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            dbRefSpace.setwAuth(firebaseAuth);
            dbRefSpace.hasUserSignedIn = true;
            dbRefSpace.wEmail = wFBAuth.getCurrentUser().getEmail();

            recylerViewLayoutManager = new LinearLayoutManager(MainActivity.this);
            displayChatList ();
        }
    }

    public void displayChatList () {
        if (FirebaseDatabase.getInstance().getReference() != null) {

            recyclerView = (RecyclerView) findViewById(R.id.chat_list);
            Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("email");
            adapter = new FirebaseRecyclerAdapter<User, ListAdapter>(User.class,
                    R.layout.user_list, ListAdapter.class, query) {
                @Override
                protected void populateViewHolder(ListAdapter viewHolder, User model, int position) {
                    if (!adapter.getRef(position).getKey().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        viewHolder.createView(adapter.getRef(position), model);
                    }
                }
            };
            recyclerView.setLayoutManager(recylerViewLayoutManager);
            recyclerView.setAdapter(adapter);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                firebaseAuth = FirebaseAuth.getInstance();
                dbRefSpace.setwAuth(firebaseAuth);
                dbRefSpace.hasUserSignedIn = true;
                dbRefSpace.wEmail = wFBAuth.getCurrentUser().getEmail();

                Map map = new HashMap();
                map.put("email", dbRefSpace.wEmail);
                dbRefSpace.users.child(firebaseAuth.getCurrentUser().getUid()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d ("Alert", "User detail added");
                    }
                });

                Toast toast = Toast.makeText(MainActivity.this, "Welcome " +
                                firebaseAuth.getCurrentUser().getEmail(),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                displayChatList ();
                Log.d("Data :", String.valueOf(data.getData()));
            } else {
                Toast toast = Toast.makeText(MainActivity.this,
                        "Sorry error occurred try again!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            dialog = new SpotsDialog(MainActivity.this);
            dialog.show();
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast toast = Toast.makeText(MainActivity.this,
                                    "Logged out successfully",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            dialog.dismiss();
                            finish();
                        }
                    });
        }
        return true;
    }
    public static class ListAdapter extends RecyclerView.ViewHolder {

        View view;
        User user;
        DatabaseReference ref;
        public ListAdapter(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void createView (DatabaseReference ref, User user) {
            this.user = user;
            this.ref = ref;
            setUser(user.getEmail());
            viewClick();
        }

        private void setUser (String user) {
            TextView tvuser = (TextView) view.findViewById(R.id.user_email);
            tvuser.setText(user);
        }

        private void viewClick() {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), ChatActivity.class);
                    intent.putExtra("r_id", ref.getKey());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
