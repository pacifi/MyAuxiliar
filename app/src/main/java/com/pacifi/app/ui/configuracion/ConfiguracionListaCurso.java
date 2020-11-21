package com.pacifi.app.ui.configuracion;


import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pacifi.app.MainActivity;
import com.pacifi.app.R;
import com.pacifi.app.api.AdapterRetrofit;
import com.pacifi.app.api.CursoApi;

import com.pacifi.app.models.Curso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfiguracionListaCurso#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfiguracionListaCurso extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView cursoListView;
    private Curso curso;
    private List<Curso> cursoList = new ArrayList<>();
    private Retrofit retrofit;
    CursoApi cursoApi;

    private FloatingActionButton fab_main, fab_add_curso, fab_upload_estudiantes;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView textview_agregar_curso, textview_subir_estudiante;

    Boolean isOpen = false;


    public ConfiguracionListaCurso() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfiguracionCurso.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfiguracionListaCurso newInstance(String param1, String param2) {
        ConfiguracionListaCurso fragment = new ConfiguracionListaCurso();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracion_lista_curso, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        retrofit = new AdapterRetrofit().getAdapter();
        cursoApi = retrofit.create(CursoApi.class);
        fab_main = getView().findViewById(R.id.fab);
        fab_add_curso = getView().findViewById(R.id.fab1);
        fab_upload_estudiantes = getView().findViewById(R.id.fab2);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_rotate_anticlock);
        textview_agregar_curso = (TextView) getView().findViewById(R.id.textview_agregar_curso);
        textview_subir_estudiante = (TextView) getView().findViewById(R.id.textview_subir_estudiante);

        cursoListView = (ListView) getView().findViewById(R.id.cursoListView);
        listarCursoApi(cursoApi);
        registerForContextMenu(cursoListView);


        cursoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                curso = (Curso) parent.getItemAtPosition(position);
                return false;
            }
        });

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {

                    textview_agregar_curso.setVisibility(View.INVISIBLE);
                    textview_subir_estudiante.setVisibility(View.INVISIBLE);
                    fab_upload_estudiantes.startAnimation(fab_close);
                    fab_add_curso.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
                    fab_upload_estudiantes.setClickable(false);
                    fab_add_curso.setClickable(false);
                    isOpen = false;
                } else {
                    textview_subir_estudiante.setVisibility(View.VISIBLE);
                    textview_agregar_curso.setVisibility(View.VISIBLE);
                    fab_upload_estudiantes.startAnimation(fab_open);
                    fab_add_curso.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fab_upload_estudiantes.setClickable(true);
                    fab_add_curso.setClickable(true);
                    isOpen = true;
                }
            }
        });

        fab_upload_estudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Boton", "Boton share");

            }
        });

        fab_add_curso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openFormDialog();
            }
        });


    }

    public void openFormDialog() {
        Bundle args = new Bundle();


        CursoFormDialog cursoFormDialog = new CursoFormDialog();
        cursoFormDialog.setArguments(args);

        cursoFormDialog.show(getActivity().getSupportFragmentManager(), "Form curso");
    }

    public void openEditFormDialog(String idCurso, String nombreCurso, String detalleCurso, String codigoCurso) {
        Bundle args = new Bundle();
        args.putString("idCurso", idCurso);
        args.putString("nombreCurso", nombreCurso);
        args.putString("detalleCurso", detalleCurso);
        args.putString("codigoCurso", codigoCurso);

        CursoFormDialog cursoFormDialog = new CursoFormDialog();
        cursoFormDialog.setArguments(args);

        cursoFormDialog.show(getActivity().getSupportFragmentManager(), "Form curso");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem editar = menu.add("Editar");
        MenuItem eliminar = menu.add("Eliminar");

        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openEditFormDialog(curso.getId(), curso.getNombre(), curso.getDetalle(), curso.getCodigo());
                return false;
            }
        });

        eliminar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                eliminarCursoApi(cursoApi, curso.getId());

                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);

    }

    public void listarCursoApi(CursoApi api) {
        cursoList.clear();
        Call<List<Curso>> call = api.list();
        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                cursoList = new ArrayList<Curso>(response.body());

                ArrayAdapter<Curso> adapter = new ArrayAdapter<Curso>(getActivity().getApplicationContext(), android.R.layout.simple_expandable_list_item_1, cursoList);
                cursoListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {
                Log.e("error api curso", t.getMessage());

            }
        });
    }

    public void eliminarCursoApi(final CursoApi api, String id) {
        cursoList.clear();
        Call<Void> call = api.delete(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getActivity(), "Se elimino correctamente", Toast.LENGTH_SHORT).show();
                listarCursoApi(cursoApi);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }


}