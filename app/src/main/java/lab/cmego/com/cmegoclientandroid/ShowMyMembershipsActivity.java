package lab.cmego.com.cmegoclientandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import lab.cmego.com.cmegoclientandroid.adapters.MembershipsRecyclerViewAdapter;
import lab.cmego.com.cmegoclientandroid.model.Membership;
import lab.cmego.com.cmegoclientandroid.model.User;

public class ShowMyMembershipsActivity extends AppCompatActivity implements MembershipsRecyclerViewAdapter.ItemClickListener {

    private MembershipsRecyclerViewAdapter adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_memberships);

        mAuth = FirebaseAuth.getInstance();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.membershipsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MembershipsRecyclerViewAdapter(this, new ArrayList<Membership>());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        String profileId = mAuth.getCurrentUser().getUid();

        Query mUserReference = FirebaseDatabase.getInstance().getReference().child
                ("users").orderByChild("profileId").equalTo(profileId);

        mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getChildren().iterator().next().getValue(User.class);

                fetchMembershipsForUser(user.getId());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void fetchMembershipsForUser(String id) {

        Query mMembershipReference = FirebaseDatabase.getInstance().getReference().child
                ("memberships").orderByChild("userId").equalTo(id);

        mMembershipReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Membership> items = new ArrayList<>();

                Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    Membership post = i.next().getValue(Membership.class);
                    items.add(post);
                }


                adapter.setData(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
