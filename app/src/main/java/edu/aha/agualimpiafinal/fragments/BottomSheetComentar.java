package edu.aha.agualimpiafinal.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.BottomSheetComentarBinding;
import edu.aha.agualimpiafinal.models.Action;
import edu.aha.agualimpiafinal.providers.ActionProvider;


public class BottomSheetComentar extends BottomSheetDialogFragment {

    private BottomSheetComentarBinding binding;

    private ActionProvider mActionProvider;
    private Action mAction;

    private String id, id_token, token;

    public BottomSheetComentar() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupRatio(bottomSheetDialog);
            }
        });
        return  dialog;
    }


    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        //id = com.google.android.material.R.id.design_bottom_sheet for Material Components
        //id = android.support.design.R.id.design_bottom_sheet for support librares
        FrameLayout bottomSheet = (FrameLayout)
                bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = getBottomSheetDialogDefaultHeight();
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    private int getBottomSheetDialogDefaultHeight() {
        return getWindowHeight() * 85 / 100;
    }
    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static BottomSheetComentar newInstance(String id, String id_token, String token) {
        BottomSheetComentar fragment = new BottomSheetComentar();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("id_token", id_token);
        args.putString("token", token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            id = getArguments().getString("id");
            token = getArguments().getString("token");

            Log.e("TOken", ""+ token);
            Log.e("id", ""+ id);
        }

        putKeywordOverLayout();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    binding = BottomSheetComentarBinding.inflate(getLayoutInflater());
    View view = binding.getRoot();

    mActionProvider = new ActionProvider();



    validateComment();



    return view;
    }

    private void validateComment() {

       String texto = binding.editTextMessage.getText().toString();

       if(!texto.equals(""))
       {
           createComment();
       }

    }

    private void createComment() {



    }


    private void putKeywordOverLayout() {
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }



}