package com.pacifi.app.ui.configuracion;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.pacifi.app.MainActivity;
import com.pacifi.app.R;
import com.pacifi.app.models.Curso;

import java.io.File;
import java.util.List;

public class CursoUploadDialog extends DialogFragment {

    TextView txt_pathShow;
    Button btn_filePick;
    Intent myFileIntent;
    public CursoUploadDialogListener listener;
    File file;
    public static final int PICK_IMAGE = 100;
    private Spinner spinnerCursos;
    String cursoCode;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_configuracion_curso_upload, null);
        Bundle arguments = getArguments();
        List<String> cursoList = (List<String>) arguments.getStringArrayList("cursos");
        spinnerCursos = (Spinner) view.findViewById(R.id.spinnerCursos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cursoList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCursos.setAdapter(adapter);

        spinnerCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cursoCode = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

                        listener.sendUpladFileListener(file, cursoCode);
                    }
                });

        txt_pathShow = (TextView) view.findViewById(R.id.txt_path);
        btn_filePick = (Button) view.findViewById(R.id.btn_filePick);

        btn_filePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                myFileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(myFileIntent, "Select Image"), PICK_IMAGE);
            }
        });

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        file = new File(data.getData().getPath());
        String name = data.getData().getPath().split("/")[data.getData().getPath().split("/").length - 1];
        file = new File("/storage/emulated/0/Download/" + name);
        txt_pathShow.setText(file.getName());


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {

            listener = (CursoUploadDialogListener) getTargetFragment();

        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString() +
                    "must implement DialogUtilListener");

        }
    }

    //
    public interface CursoUploadDialogListener {
        void sendUpladFileListener(File file, String CursoCode);
    }
}
