package com.example.proyectofinal;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnadirReceta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnadirReceta extends Fragment {

    // TODO: Rename parameter arguments, choose names that matcc
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Uri imageUri;
    private ImageView iv_imagenComida;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnadirReceta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnadirReceta.
     */
    // TODO: Rename and change types and number of parameters
    public static AnadirReceta newInstance(String param1, String param2) {
        AnadirReceta fragment = new AnadirReceta();
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

        View v = inflater.inflate(R.layout.fragment_anadir_receta, container, false);

        Context context = getContext();

        iv_imagenComida = v.findViewById(R.id.iv_imagenComida);
        //Picasso.get().load(R.drawable.comidapordefecto).into(iv_imagenComida);
        Glide.with(v).load(R.drawable.comidapordefecto).into(iv_imagenComida);

        EditText et_nombreReceta = v.findViewById(R.id.et_nombreReceta);

        EditText et_ingredientes = v.findViewById(R.id.et_ingredientes);
        EditText et_instrucciones = v.findViewById(R.id.et_instrucciones);

        CheckBox checkBox_vegetariano = v.findViewById(R.id.checkBox_vegetariano);
        CheckBox checkBox_vegano = v.findViewById(R.id.checkBox_vegano);
        CheckBox checkBox_sinGluten = v.findViewById(R.id.checkBox_sinGluten);
        Boolean vegetariano, vegano, sinGluten;
        TextView tv_minutos = v.findViewById(R.id.tv_minutos);

        // Creo el seekBar de los minutos
        SeekBar seekBar = v.findViewById(R.id.seekBar_minutos);
        seekBar.setMax(60);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser) {
                tv_minutos.setText("Minutos: " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Receta por defecto
        et_nombreReceta.setText("Receta de ejemplo");
        et_ingredientes.setText("200 ml Leche, 200 gr cacahuetes, una cuchara sopera de aceite");
        et_instrucciones.setText("1. Reúne los ingredientes \n2. Blablabla");


        // Listener de la imagen, cuando se le da click se abre la camara
        iv_imagenComida.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Inicializamos Firebase
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();

                // Abrimos la camara
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);

                if (imageUri != null) {
                    StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                            + "." + getFileExtension(imageUri));
                    fileReference.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_SHORT).show();
                                    Log.d("imagen", "Se ha subido la imagen");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Upload failed", Toast.LENGTH_SHORT).show();
                                    Log.d("imagen", "FALLO NO SE ha subido la imagen");
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });








        // Listeners de los checkboxes de vegetariano, vegano y sinGluten
        checkBox_vegano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox_vegano.isChecked()){
                    // Si se selecciona vegano, entonces va a ser vegetariano si o si, asi que se marca automaticamente
                    checkBox_vegetariano.setChecked(true);
                }
            }
        });

        Button btn_guardarReceta = v.findViewById(R.id.btn_guardarReceta);
        btn_guardarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int progress = seekBar.getProgress(); // get the current progress of the SeekBar
                // Compruebo que no estan vacios los input
                if(TextUtils.isEmpty(et_nombreReceta.getText().toString())){
                    Toast.makeText(getContext(), "Introduce el nombre", Toast.LENGTH_SHORT).show();
                }
                // Compruebo los minutos
                else if(progress == 0){
                    Toast.makeText(getContext(), "Introduce los minutos", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(et_ingredientes.getText().toString())){
                    Toast.makeText(getContext(), "Introduce los ingredientes", Toast.LENGTH_SHORT).show();
                }
                // Si esta todo lo necesario se realiza la conexion

                else{
                    // Leo los valores introducidos
                    String nombreReceta = et_nombreReceta.getText().toString();
                    int minutos = seekBar.getProgress();

                    String ingredientes = et_ingredientes.getText().toString();

                    Boolean estadoVegano = checkBox_vegano.isChecked();
                    Boolean estadoVegetariano = false;
                    Boolean estadoSinGluten = checkBox_sinGluten.isChecked();

                    // Si se ha checkeado vegano siempre es vegetariano, da igual si se ha quitado el check de vegetariano.
                    if (estadoVegano) {
                        estadoVegetariano = true;
                    }
                    // Si no es vegano se lee el checkbox
                    else{
                        estadoVegetariano = checkBox_vegetariano.isChecked();
                    }



                    // Utilizo la libreria OkHttp
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");

                    // Crear un objeto JSON con los campos correspondientes
                    JSONObject json = new JSONObject();
                    try {
                        json.put("nombreReceta", nombreReceta);
                        json.put("minutos", minutos);
                        json.put("ingredientes", ingredientes);
                        json.put("instrucciones", "blabla");
                        json.put("vegetariano", estadoVegetariano ? 1 : 0); //Si es true mando 1, si false mando 0
                        json.put("vegano", estadoVegano ? 1 : 0);
                        json.put("sinGluten", estadoSinGluten ? 1 : 0);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    RequestBody body = RequestBody.create(mediaType, json.toString());

                    Log.d("anadirReceta", json.toString());



                    Request request = new Request.Builder()
                            .url("http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/eonate006/WEB/Proyecto_final/insert_Nueva_Receta.php")
                            .post(body)
                            .build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            // Error de conexión o I/O
                            ((MainActivity) getActivity()).showToast("No se ha podido añadir la receta");
                            Log.d("anadirReceta", "No ha podido añadir la receta");
                            Log.d("anadirReceta", e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                if (response.isSuccessful()) {
                                    // Solicitud exitosa
                                    Log.d("anadirReceta", "Se ha añadido la receta. "+ response.body().string());
                                    ((MainActivity) getActivity()).showToast("Se ha añadido la receta");

                                } else {
                                    // Solicitud fallida
                                    ((MainActivity) getActivity()).showToast("No se ha podido añadir la receta");
                                    Log.d("anadirReceta", "Error al añadir la receta: " + response);
                                }
                            } catch (Exception e) {
                                Log.e("anadirReceta", "Ha saltado una excepcion", e);
                            }
                            response.close();
                        }
                    });
                }
            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            iv_imagenComida.setImageURI(imageUri);
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }


}