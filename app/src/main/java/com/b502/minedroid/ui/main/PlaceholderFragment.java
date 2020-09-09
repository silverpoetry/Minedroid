package com.b502.minedroid.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.b502.minedroid.MyApplication;
import com.b502.minedroid.R;
import com.b502.minedroid.utils.MapManager;
import com.b502.minedroid.utils.RecordItem;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_toplist, container, false);
//        final TextView textView = root.findViewById(R.id.section_label);
        final ListView lstv = root.findViewById(R.id.lstv);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        List<RecordItem> lst;

        switch (index) {
            case 1:
                lst = MyApplication.Instance.sqlHelper.getRecords(MapManager.GameDifficulty.EASY);
                break;
            case 2:
                lst = MyApplication.Instance.sqlHelper.getRecords(MapManager.GameDifficulty.MIDDLE);
                break;
            case 3:
            default:
                lst = MyApplication.Instance.sqlHelper.getRecords(MapManager.GameDifficulty.HARD);
                break;
        }
        ArrayAdapter<RecordItem> arrayAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, lst);
        lstv.setAdapter(arrayAdapter);
//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}