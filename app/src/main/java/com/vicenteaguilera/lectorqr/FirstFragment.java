package com.vicenteaguilera.lectorqr;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;

import net.glxn.qrgen.android.QRCode;

public class FirstFragment extends Fragment {

    private TextView textView;
    private ImageView imageView;
    private EditText editTextTextMultiLine;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity )
        {
            if(((MainActivity)getActivity()).result!=null) {
                textView.setText(((MainActivity) getActivity()).result.getContents());
            }
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.id_texto);
        imageView = view.findViewById(R.id.imageView);
        editTextTextMultiLine = view.findViewById(R.id.editTextTextMultiLine);
        textView.setText("Aqui se pondra el qr");
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                escanear();
            }
        });
        view.findViewById(R.id.button_crear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crear(editTextTextMultiLine.getText().toString());
            }
        });
    }

    private void crear(String texto)
    {
        Bitmap bitmap = QRCode.from(texto).withSize(300, 300).bitmap();
       // Suponiendo que tienes un ImageView con el id ivCodigoGenerado
        imageView.setImageBitmap(bitmap);
    }

    private void escanear()
    {
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Escanea QR");
        intentIntegrator.setOrientationLocked(false);//orientacion
        intentIntegrator.setCameraId(0);//camara
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
    }
}