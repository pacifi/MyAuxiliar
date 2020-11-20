package com.pacifi.app.ui.configuracion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.pacifi.app.R;

public class CursoFormDialog extends AppCompatDialogFragment {

    private EditText nombreCurso;
    private EditText detalleCurso;
    private EditText codigoCurso;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_configuracion_curso_form, null);
        builder.setView(view)
                .setTitle("Formulario Curso")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        nombreCurso = view.findViewById(R.id.edit_nombre_curso);
        detalleCurso = view.findViewById(R.id.edit_detalle_curso);
        codigoCurso = view.findViewById(R.id.edit_codigo_curso);
        return builder.create();
    }
}
