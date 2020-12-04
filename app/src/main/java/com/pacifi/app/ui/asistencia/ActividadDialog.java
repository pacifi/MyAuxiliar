package com.pacifi.app.ui.asistencia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.pacifi.app.R;
import com.pacifi.app.api.AdapterRetrofit;

public class ActividadDialog extends DialogFragment {

    private WebView webView;
    CursoFormDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_form_actividad, null);
//
        webView = (WebView) view.findViewById(R.id.web_form_actividad);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(AdapterRetrofit.DOMINIO + "fragments/actividad/crear");
        webView.setWebViewClient(new WebViewClient());

        builder.setView(view)
                .setTitle("Formulario Actividad")
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.refreshWebViewListener();
                    }
                });


        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {

            listener = (CursoFormDialogListener) getTargetFragment();

        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString() +
                    "must implement DialogUtilListener");

        }
    }


    public interface CursoFormDialogListener {
        void refreshWebViewListener();
    }
}
