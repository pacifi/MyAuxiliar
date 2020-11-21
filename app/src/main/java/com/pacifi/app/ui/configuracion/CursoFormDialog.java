package com.pacifi.app.ui.configuracion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.pacifi.app.R;
import com.pacifi.app.api.AdapterRetrofit;
import com.pacifi.app.api.CursoApi;
import com.pacifi.app.models.Curso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CursoFormDialog extends AppCompatDialogFragment {

    private EditText nombreCurso;
    private EditText detalleCurso;
    private EditText codigoCurso;

    private Retrofit retrofit;
    CursoApi cursoApi;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_configuracion_curso_form, null);

        retrofit = new AdapterRetrofit().getAdapter();
        cursoApi = retrofit.create(CursoApi.class);
        builder.setView(view)
                .setTitle("Formulario Curso")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nombreCurso = view.findViewById(R.id.edit_nombre_curso);
                        detalleCurso = view.findViewById(R.id.edit_detalle_curso);
                        codigoCurso = view.findViewById(R.id.edit_codigo_curso);
                        agregarUsuario(cursoApi, nombreCurso.getText().toString(), detalleCurso.getText().toString(), codigoCurso.getText().toString());
                        Toast.makeText(getActivity(), "Se Registro por completo.", Toast.LENGTH_SHORT).show();
                    }
                });
        nombreCurso = view.findViewById(R.id.edit_nombre_curso);
        detalleCurso = view.findViewById(R.id.edit_detalle_curso);
        codigoCurso = view.findViewById(R.id.edit_codigo_curso);
        return builder.create();
    }

    public void agregarUsuario(final CursoApi api, String nombre, String detalle, String codigo) {

        Curso curso = new Curso();
        curso.setNombre(nombre);
        curso.setDetalle(detalle);
        curso.setCodigo(codigo);

        Call<Void> call = api.add(curso);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }



}
