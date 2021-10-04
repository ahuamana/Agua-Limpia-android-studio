package edu.aha.agualimpiafinal.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Date;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapters.CommentariosAdapter;
import edu.aha.agualimpiafinal.databinding.BottomSheetComentarBinding;
import edu.aha.agualimpiafinal.models.Comment;
import edu.aha.agualimpiafinal.providers.CommentProvider;


public class BottomSheetComentar extends BottomSheetDialogFragment {

    private BottomSheetComentarBinding binding;

    private CommentProvider mCommentProvider;
    private Comment mComment;

    private String id, id_token, token;

    CommentariosAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;

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
            id_token = getArguments().getString("id_token");

            Log.e("Token", ""+ token);
            Log.e("id", ""+ id);
            Log.e("id_token", ""+ id_token);

        }

        putKeywordOverLayout();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    binding = BottomSheetComentarBinding.inflate(getLayoutInflater());
    View view = binding.getRoot();

        mCommentProvider = new CommentProvider();

        validateComment();

        getCommentariosFromPost();



    return view;
    }

    private void getCommentariosFromPost() {

        mLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewComentarios.setLayoutManager( mLinearLayoutManager);

        FirestoreRecyclerOptions<Comment> options = new FirestoreRecyclerOptions.Builder<Comment>()
                .setQuery(mCommentProvider.getCommentsByIdPhoto(id),Comment.class)
                .build();

        //enviar los datos al adapter
        mAdapter=new CommentariosAdapter(options, getContext());
        //asignar datos al recyclerView
        binding.recyclerViewComentarios.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    private void validateComment() {

       binding.fabSend.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String texto = binding.editTextMessage.getText().toString();
               Log.e("TEXTO",""+texto);

               if(!texto.equals(""))
               {
                   createComment();
               }


           }
       });

    }

    private void createComment() {

        mComment = new Comment();
        mComment.setId_photo(id);
        mComment.setStatus(true);
        mComment.setToken(token);
        mComment.setType("comment");
        mComment.setTimestamp(new Date().getTime());
        mComment.setMessage(binding.editTextMessage.getText().toString());

        mCommentProvider.create(mComment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Log.e("Comment","Message Added");
                    binding.editTextMessage.setText("");
                }else
                {
                    Log.e("Comment","ERROR CREANDO COMENTARIO");
                }

            }
        });


    }


    private void putKeywordOverLayout() {
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }



}