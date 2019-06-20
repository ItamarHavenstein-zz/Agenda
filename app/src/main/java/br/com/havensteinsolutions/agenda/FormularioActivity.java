package br.com.havensteinsolutions.agenda;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

import br.com.havensteinsolutions.agenda.dao.AlunoDAO;
import br.com.havensteinsolutions.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {
    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private String caminhoFoto;
    private File storageDir;
    private Uri uriForFile;
    private File newfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        helper = new FormularioHelper(this);

        Intent i = getIntent();
        Aluno aluno = (Aluno) i.getSerializableExtra("ALUNO");
        if (aluno != null) {
            helper.preencheFormulario(aluno);
        }

        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM) + "/Camera/";
                File newDir = new File(dir);

                String file = dir + helper.pegaAluno().getId();

                newfile = new File(file);
                try {
                    newfile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                uriForFile = FileProvider.getUriForFile(FormularioActivity.this,
                        "br.com.havensteinsolutions.agenda",
                        newfile);
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                startActivityForResult(intent, CODIGO_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                helper.carregaImage(newfile.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                AlunoDAO dao = new AlunoDAO(this);
                Aluno aluno = helper.pegaAluno();
                if (aluno.getId() != 0) {
                    dao.altera(aluno);
                } else {
                    dao.inseri(aluno);
                }
                dao.close();

                Toast.makeText(FormularioActivity.this, "aluno " + aluno.getNome() + " salvo", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
