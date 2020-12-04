package com.pacifi.app.ui.actividad;

import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.pacifi.app.R;
import com.pacifi.app.api.ActividadApi;
import com.pacifi.app.api.AdapterRetrofit;
import com.pacifi.app.api.AsistenciaApi;
import com.pacifi.app.api.CursoApi;
import com.pacifi.app.models.Actividad;
import com.pacifi.app.models.AsistenciaEstudianteApoderado;
import com.pacifi.app.models.Curso;

import java.util.ArrayList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActividadFragment extends Fragment {

    private Retrofit retrofit;
    ActividadApi actividadApi;
    CursoApi cursoApi;
    AsistenciaApi asistenciaApi;
    Spinner spinnerCursos;
    private Curso cursoSelect;
    private List<Curso> cursoList = new ArrayList<>();
    private ListView listViewActividad;
    private List<Actividad> actividadList = new ArrayList<>();
    private Actividad actividadSelect;

    public ActividadFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actividad, container, false);
    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        retrofit = new AdapterRetrofit().getAdapter();
        cursoApi = retrofit.create(CursoApi.class);
        listViewActividad = (ListView) getView().findViewById(R.id.listViewActividad);
        spinnerCursos = (Spinner) getView().findViewById(R.id.spinnerSelectCursos);
        registerForContextMenu(listViewActividad);
        cargarSpinnier();

        listViewActividad.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                actividadSelect = (Actividad) parent.getItemAtPosition(position);

                return false;
            }
        });

        spinnerCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cursoSelect = (Curso) parent.getItemAtPosition(position);
                listarActividades(cursoSelect);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("Boooo", "no hay selected");
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem editar = menu.add("Enviar SMS");


        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                envierSms();
                return false;
            }
        });


        super.onCreateContextMenu(menu, v, menuInfo);

    }

    public void envierSms() {

        retrofit = new AdapterRetrofit().getAdapter();
        asistenciaApi = retrofit.create(AsistenciaApi.class);
        Call<List<AsistenciaEstudianteApoderado>> call = asistenciaApi.listasistencia(actividadSelect.getId());
        call.enqueue(new Callback<List<AsistenciaEstudianteApoderado>>() {
            @Override
            public void onResponse(Call<List<AsistenciaEstudianteApoderado>> call, Response<List<AsistenciaEstudianteApoderado>> response) {
                List<AsistenciaEstudianteApoderado> lista = (List<AsistenciaEstudianteApoderado>) response.body();
                for (AsistenciaEstudianteApoderado dato : lista) {
                    if (dato.getEstado().equals("T")) {
                        String phone = dato.getApoderado().getCelular();
                        String text = "Su Hijo LLego tarde";
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(phone, null, text, null, null);
                        Log.e("Tarde", "Alumno en Tarde");
                    }
                    if (dato.getEstado().equals("F")) {
                        Log.e("Falta", "Alumno en falta");
                        String phone = dato.getApoderado().getCelular();
                        String text = "Su Hijo Nunca Llego";
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(phone, null, text, null, null);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<AsistenciaEstudianteApoderado>> call, Throwable t) {

            }
        });
    }


    public void cargarSpinnier() {

        Call<List<Curso>> call = cursoApi.list();

        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {

                cursoList = new ArrayList<Curso>(response.body());

                ArrayAdapter<Curso> adapter = new ArrayAdapter<Curso>(getActivity().getApplicationContext(), R.layout.color_spinner, cursoList);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCursos.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {

            }
        });


    }


    public void listarActividades(Curso curso) {
        retrofit = new AdapterRetrofit().getAdapter();
        actividadApi = retrofit.create(ActividadApi.class);
        Call<List<Actividad>> call = actividadApi.list(curso.getId());
        call.enqueue(new Callback<List<Actividad>>() {
            @Override
            public void onResponse(Call<List<Actividad>> call, Response<List<Actividad>> response) {
                actividadList = new ArrayList<Actividad>(response.body());
                ArrayAdapter<Actividad> adapter = new ArrayAdapter<Actividad>(getActivity().getApplicationContext(), android.R.layout.simple_expandable_list_item_1, actividadList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        // Get the Item from ListView
                        View view = super.getView(position, convertView, parent);

                        // Initialize a TextView for ListView each Item
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);

                        // Set the text color of TextView (ListView Item)
                        tv.setTextColor(Color.rgb(130, 130, 130));

                        // Generate ListView Item using TextView
                        return view;
                    }
                };
                listViewActividad.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Actividad>> call, Throwable t) {

            }
        });

    }


}