package com.example.larutadelsabor.usuario.auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.larutadelsabor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class EditarUsuario extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;

    private LinearLayout btnVolver;
    private ImageView imgPerfil, btnEditarFoto;
    private EditText txtNombre;
    private LinearLayout btnGuardar;

    private Uri imageUri; // Imagen elegida

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_usuario);

        // Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Enlaces UI
        btnVolver = findViewById(R.id.btnVolver);
        imgPerfil = findViewById(R.id.imgPerfil);
        btnEditarFoto = findViewById(R.id.btnEditarFoto);
        txtNombre = findViewById(R.id.txtNombre);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Botón volver
        btnVolver.setOnClickListener(v -> finish());

        // Elegir nueva foto
        btnEditarFoto.setOnClickListener(v -> abrirGaleria());

        // Guardar cambios
        btnGuardar.setOnClickListener(v -> guardarCambios());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_editar_usuario), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // -------------------------------
    // ABRIR GALERÍA
    // -------------------------------
    private void abrirGaleria() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imgPerfil.setImageURI(imageUri);
        }
    }

    // -------------------------------
    // GUARDAR CAMBIOS (NOMBRE + FOTO)
    // -------------------------------
    private void guardarCambios() {

        String nuevoNombre = txtNombre.getText().toString().trim();

        if (nuevoNombre.isEmpty()) {
            Toast.makeText(this, "Escribe un nombre válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Primero actualiza el nombre
        actualizarNombre(nuevoNombre);

        // Si hay imagen nueva → súbela
        if (imageUri != null) {
            subirImagenAFirebase();
        } else {
            Toast.makeText(this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void actualizarNombre(String nombre) {
        String correoUsuario = auth.getCurrentUser().getEmail();

        db.collection("Usuarios")
                .document(correoUsuario)
                .update("nombre", nombre)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Nombre actualizado", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al actualizar nombre", Toast.LENGTH_SHORT).show()
                );
    }

    private void subirImagenAFirebase() {

        String correoUsuario = auth.getCurrentUser().getEmail();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference filePath = storageRef.child("usuarios/" + correoUsuario + "_perfil.jpg");

        filePath.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot ->
                        filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                            String downloadUrl = uri.toString();
                            actualizarFotoEnFirestore(downloadUrl);
                        })
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al subir imagen", Toast.LENGTH_SHORT).show()
                );
    }

    private void actualizarFotoEnFirestore(String url) {

        String correoUsuario = auth.getCurrentUser().getEmail();

        db.collection("Usuarios")
                .document(correoUsuario)
                .update("fotoPerfil", url)
                .addOnSuccessListener(unused ->
                        Toast.makeText(this, "Foto actualizada correctamente", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al guardar foto", Toast.LENGTH_SHORT).show()
                );
    }
}