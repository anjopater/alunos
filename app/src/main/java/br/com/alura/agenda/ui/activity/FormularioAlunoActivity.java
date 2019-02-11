package br.com.alura.agenda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        final AlunoDAO dao = new AlunoDAO(this);

        final EditText campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        final EditText campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        final EditText campoEmail = findViewById(R.id.activity_formulario_aluno_email);
        final ImageView campoImagen = findViewById(R.id.image_view_foto);

        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Aluno alunoCriado = criaAluno(campoNome, campoTelefone, campoEmail, campoImagen);
                salva(alunoCriado, dao);
            }
        });

        FloatingActionButton botaoFoto = findViewById(R.id.activity_lista_aluno_novo_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //pass param to intent, in this case uma cont
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";

                File arquivoFoto = new File(caminhoFoto);

                Uri fileUri = FileProvider.getUriForFile(
                        getApplicationContext(),
                        "br.com.alura.agenda.fileprovider",
                        arquivoFoto);

                intentCamara.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intentCamara,567);
            }
        });

        dao.todos();

    }

    private void salva(Aluno alunoCriado, AlunoDAO dao) {
        dao.salva(alunoCriado);
        finish();
    }

    private Aluno criaAluno(EditText campoNome, EditText campoTelefone, EditText campoEmail, ImageView campoImagen) {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();
        String image = " ";//campoImagen.getTag().toString();

        return new Aluno(nome,telefone,email, image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 567) {
                //Abrir foto
                ImageView foto = findViewById(R.id.image_view_foto);
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapeReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                foto.setImageBitmap(bitmapeReduzido);

                foto.setScaleType(ImageView.ScaleType.FIT_XY);
                foto.setTag(caminhoFoto);
            }

            //super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

