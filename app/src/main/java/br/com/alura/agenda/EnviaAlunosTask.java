package br.com.alura.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    private Context context;
    ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... objects) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);
//Toast.makeText(context,"enviando..." + resposta, Toast.LENGTH_LONG).show();
        return  resposta;
    }

    @Override
    protected void onPostExecute(String o) {
        dialog.dismiss();
        Toast.makeText(context,"enviando..." + (String) o, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute() {
      dialog = ProgressDialog.show(context, "aguarde", "enviando alunos..", true);
    }
}
