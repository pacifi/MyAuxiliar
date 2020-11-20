package com.pacifi.app.ui.configuracion;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
public class ConfiguracionListaCurso extends Fragment  {

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
    }


    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {

            mainActivity.showFloatingActionButton(); //fuerza la visibilidad

            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);

            fab.setImageResource(R.drawable.ic_baseline_add_24); //Cambiar icono

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mainActivity, "Estamos en Curso", Toast.LENGTH_SHORT).show();
                    openFormDialog();
                }
            });
        }
    }


    public void openFormDialog(){
        CursoFormDialog cursoFormDialog = new CursoFormDialog();



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.add("Matricular");
        menu.add("Enviar un SMS");
        menu.add("Visitar Sitio Web");
        MenuItem eliminar = menu.add("Eliminar");


        menu.add("Ver en el mapa");
        menu.add("Enviar un Email");


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
                Log.e("Cursos", cursoList.toString());
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