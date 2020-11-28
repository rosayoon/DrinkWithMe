package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.drinkwithme.not_yet.Posting_New_PostActivity;
import com.example.drinkwithme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CommunityFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_community,null);

        FloatingActionButton btn_write_community_fragment = view.findViewById(R.id.btn_write_community_fragment);

        btn_write_community_fragment.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        Intent intent = new Intent(getActivity(), Posting_New_PostActivity.class);
        startActivity(intent);
          }
        });

        return view;
    }
}