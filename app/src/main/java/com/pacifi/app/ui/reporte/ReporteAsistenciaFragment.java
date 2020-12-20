package com.pacifi.app.ui.reporte;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pacifi.app.R;
import com.pacifi.app.api.AdapterRetrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReporteAsistenciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReporteAsistenciaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private WebView webView;

    // TODO: Rename and change types of parameters


    public ReporteAsistenciaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reporte_asistencia, container, false);
        webView = (WebView) v.findViewById(R.id.web_asistencia_reporte);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(AdapterRetrofit.DOMINIO + "fragments/asistencia/reporte");
        webView.setWebViewClient(new WebViewClient());

        return v;
    }
}