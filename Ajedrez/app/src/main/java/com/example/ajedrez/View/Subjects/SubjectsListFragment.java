package com.example.ajedrez.View.Subjects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Model.Subject;
import com.example.ajedrez.R;
import com.example.ajedrez.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link SubjectsListener}
 * interface.
 */
public class SubjectsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SubjectsListAdapter adapter;
    private SubjectsListener mListener;
    private List<Subject> subjectList;

    public static SubjectsListFragment newInstance() {
        return new SubjectsListFragment();
    }

    public void setListener (MainActivity activity) {
        mListener = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subjects_list, container, false);
        recyclerView = view.findViewById(R.id.subjectsList);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SubjectsListAdapter(new ArrayList<>(), mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        loadSubjects();
    }

    public void loadSubjects() {
        Query studentsQuery = FirebaseDatabase.getInstance().getReference().child("subjects").child("Principiante");
        studentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjectList = new ArrayList<>();
                for(DataSnapshot subject :dataSnapshot.getChildren()){
                    Subject newSubject = subject.getValue(Subject.class);
                    //Subject subject = new Subject(subjects.getKey());
                    subjectList.add(newSubject);
                }
                adapter.setSubjectsList(subjectList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface SubjectsListener {
    }
}
