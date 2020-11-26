package com.pacifi.app.ui.configuracion;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.pacifi.app.MainActivity;
import com.pacifi.app.R;

public class CursoUploadDialog extends DialogFragment {

    private EditText editTextUsername;
    private EditText editTextPassword;
    public CursoUploadDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_configuracion_curso_upload, null);

        builder.setView(view)
                .setTitle("Upload")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();
                        Log.e("ERRR", username);
                        Log.e("ERRR", password);
                        listener.applyTexts(username, password);
                    }
                });

        editTextUsername = view.findViewById(R.id.edit_username);
        editTextPassword = view.findViewById(R.id.edit_password);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {

            listener = (CursoUploadDialogListener) getTargetFragment();

        } catch (ClassCastException e) {
            Log.e("errordddddddd", e.getMessage());
            throw new ClassCastException(context.toString() +
                    "must implement DialogUtilListener");

        }
    }

    //
    public interface CursoUploadDialogListener {
        void applyTexts(String username, String password);
    }
}
