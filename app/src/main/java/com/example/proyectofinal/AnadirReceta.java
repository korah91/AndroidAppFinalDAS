package com.example.proyectofinal;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Environment;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    private static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    private Uri imageUri;
    private ImageButton iv_imagenComida;
    StorageReference storageReference;
    String currentPhotoPath;
    String urlFirebase;





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
                storageReference = FirebaseStorage.getInstance().getReference();
                Log.d("imagen", "Se ha pulsado la imagen");
                askCameraPermissions();
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
                        json.put("urlFoto", urlFirebase);
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

    // Cuando se recibe la imagen desde la camara
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                //iv_imagen.setImageURI(Uri.fromFile(f));
                Log.d("imagen", "URL Absoluta de la imagen: " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                // contentUri contiene la direccion en el movil de la foto
                Uri contentUri = Uri.fromFile(f);

                // Guardamos la url en la variable global para guardarla en el objeto como url de la imagen
                urlFirebase = contentUri.toString();

                mediaScanIntent.setData(contentUri);
                getActivity().sendBroadcast(mediaScanIntent);

                uploadImageToFirebase(f.getName(), contentUri);
            }
        }
    }


    // Sube la imagen a Firebase
    private void uploadImageToFirebase(String name, Uri contentUri) {
        // Crea el directorio
        StorageReference image = storageReference.child("images/" + name);
        // El onSuccessListener se activa cuando se sube la imagen satisfactoriamente
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Obtengo el url de descarga de la imagen
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("firebase", "onSuccess: Uploaded IMG Url: " + uri.toString());
                        // Cargo la imagen en el imageView con Glide
                        Glide.with(getContext()).load(uri).into(iv_imagenComida);
                        urlFirebase = uri.toString();


                    }
                });
                Toast.makeText(getContext(), "Firebase upload SUCCEED", Toast.LENGTH_SHORT).show();
            }
            // El onFailureListener se activa cuando falla
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Firebase upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Referencia del Código: "SmallAcademy" de Youtube.
    // https://www.youtube.com/watch?v=dKX2V992pWI&list=PLlGT4GXi8_8eopz0Gjkh40GG6O5KhL1V1&index=4
    private void askCameraPermissions(){
        // Si no hay permisos de CAMARA y ALMACENAMIENTO, se piden
        if(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERM_CODE);
        }
        else{
            dispatchTakePictureIntent();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getContext(), "Se necesita permiso para acceder a la camara", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Se crea la imagen con un TimeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.ProyectoFinal.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
}