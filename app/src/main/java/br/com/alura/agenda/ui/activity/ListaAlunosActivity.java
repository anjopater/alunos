package br.com.alura.agenda.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;

import br.com.alura.agenda.EnviaAlunosTask;
import br.com.alura.agenda.R;
import br.com.alura.agenda.WebClient;
import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

//this class can be extended of android however we are going extend from AppCompaActivity
//because add automatlycate a bar e help with the diferente types of android
public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //este e pra enlazar la vista con la activity
        setContentView(R.layout.activity_lista_alunos);



        //este metudo es pra mudar e titulo
        setTitle("Lista de alunos");

        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class));
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();
        AlunoDAO dao = new AlunoDAO(this);

        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        listaDeAlunos.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dao.todos()));

       registerForContextMenu(listaDeAlunos);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem visitar = menu.add("Visitar site");
        //open browser
        Intent intenSite  = new Intent(Intent.ACTION_VIEW);
        intenSite.setData(Uri.parse("http://google.com"));
        visitar.setIntent(intenSite);

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:+554499451594"));
        itemSMS.setIntent(intentSMS);

        MenuItem localisacao = menu.add("Visualisar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q="));
        localisacao.setIntent(intentMapa);

        //this it's for allow permissions and access to call
        MenuItem ligar = menu.add("Ligar");
            ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        !=PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{ Manifest.permission.CALL_PHONE},123);

                    } else {
                        Intent intentLigar= new Intent(Intent.ACTION_CALL);
                        intentLigar.setData(Uri.parse("tel:+554499451594"));
                        startActivity(intentLigar);

                    }
                    return false;
                }
            });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.action_favorite:
                //eu posso passar params execute(a, b, c)
            new EnviaAlunosTask(this).execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
