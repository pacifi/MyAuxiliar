package com.pacifi.app.ui.asistencia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pacifi.app.R;
import com.pacifi.app.api.AdapterRetrofit;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AsistenciaFragment extends Fragment implements ActividadDialog.CursoFormDialogListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private WebView webView;
    private FloatingActionButton fab_main, fab_add_asistencia, fab_other;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private TextView txt_crear_asistencia, text_other;
    Boolean isOpen = false;

    public AsistenciaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_asistencia, container, false);
        webView = (WebView) v.findViewById(R.id.web_asistencia);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(AdapterRetrofit.DOMINIO + "fragments/asistencia/listar");
        webView.setWebViewClient(new WebViewClient());

        fab_main = v.findViewById(R.id.fab_option_asistencia);
        fab_add_asistencia = v.findViewById(R.id.fab_crear_asistencia);
        fab_other = v.findViewById(R.id.fab_other);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_rotate_anticlock);
        txt_crear_asistencia = (TextView) v.findViewById(R.id.textview_crear_asistencia);
        text_other = (TextView) v.findViewById(R.id.textview_other);


        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {

                    txt_crear_asistencia.setVisibility(View.INVISIBLE);
                    text_other.setVisibility(View.INVISIBLE);
                    fab_add_asistencia.startAnimation(fab_close);
                    fab_other.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
                    fab_add_asistencia.setClickable(false);
                    fab_other.setClickable(false);
                    isOpen = false;
                } else {
                    txt_crear_asistencia.setVisibility(View.VISIBLE);
                    text_other.setVisibility(View.VISIBLE);
                    fab_add_asistencia.startAnimation(fab_open);
                    fab_other.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fab_add_asistencia.setClickable(true);
                    fab_other.setClickable(true);
                    isOpen = true;
                }
            }
        });

        fab_add_asistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddActividad();
            }
        });


        return v;
    }

    public void openDialogAddActividad() {
        Bundle args = new Bundle();


        ActividadDialog actividadDialog = new ActividadDialog();
        actividadDialog.setArguments(args);
        actividadDialog.setTargetFragment(AsistenciaFragment.this, 1);
        actividadDialog.show(getFragmentManager(), "Form Actividad");

    }

    @Override
    public void refreshWebViewListener() {
        webView.reload();
    }

}