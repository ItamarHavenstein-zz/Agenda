package br.com.havensteinsolutions.agenda.Agenda;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.AlunoDAO;
import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.TelefoneDAO;
import br.com.havensteinsolutions.agenda.Agenda.Infra.database.AgendaDatabase;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Telefone;
import br.com.havensteinsolutions.agenda.Agenda.modelo.TipoTelefone;
import br.com.havensteinsolutions.agenda.R;

public class FormularioActivity extends AppCompatActivity {
    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private Uri uriForFile;
    private File newfile;
    private AlunoDAO alunoDAO;
    private EditText campoTelefoneFixo;
    private EditText campoTelefoneCelular;
    private AgendaDatabase database;
    private TelefoneDAO telefoneDAO;
    private List<Telefone> telefonesDoAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        campoTelefoneFixo = (EditText) findViewById(R.id.formulario_telefone_fixo);
        campoTelefoneCelular = (EditText) findViewById(R.id.formulario_telefone_celular);

        database = AgendaDatabase.getInstance(FormularioActivity.this);
        alunoDAO = database.getRoomAlunoDAO();
        telefoneDAO = database.getTelefoneDAO();
        helper = new FormularioHelper(this);

        Intent i = getIntent();
        Aluno aluno = (Aluno) i.getSerializableExtra("ALUNO");
        if (aluno != null) {
            helper.preencheFormulario(aluno);
            preencheCamposDeTelefone(aluno);
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

    private void preencheCamposDeTelefone(Aluno aluno) {
        telefonesDoAluno = telefoneDAO.buscaTodosTelefonesDoAluno(aluno.getId());
        for (Telefone telefone : telefonesDoAluno) {
            if (telefone.getTipo() == TipoTelefone.FIXO) {
                campoTelefoneFixo.setText(telefone.getNumero());
            } else {
                campoTelefoneCelular.setText(telefone.getNumero());
            }
        }
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
                Aluno aluno = helper.pegaAluno();

                Telefone telefoneFixo = criaTelefone(campoTelefoneFixo, TipoTelefone.FIXO);
                Telefone telefoneCelular = criaTelefone(campoTelefoneCelular, TipoTelefone.CELULAR);

                if (aluno.getId() != 0) {
                    editaAluno(aluno, telefoneFixo, telefoneCelular);

                } else {
                    salvaAluno(aluno, telefoneFixo, telefoneCelular);
                }

                Toast.makeText(FormularioActivity.this, "aluno " + aluno.getNome() + " salvo", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private Telefone criaTelefone(EditText campoTelefoneCelular, TipoTelefone celular) {
        String numeroCelular = campoTelefoneCelular.getText().toString();
        return new Telefone(numeroCelular, celular);
    }

    private void salvaAluno(Aluno aluno, Telefone telefoneFixo, Telefone telefoneCelular) {
        int alunoId = alunoDAO.salva(aluno).intValue();
        vinculaAlunoComTelefone(alunoId, telefoneFixo, telefoneCelular);
        telefoneDAO.salva(telefoneFixo, telefoneCelular);
    }

    private void editaAluno(Aluno aluno, Telefone telefoneFixo, Telefone telefoneCelular) {
        alunoDAO.altera(aluno);
        vinculaAlunoComTelefone((int) aluno.getId(), telefoneFixo, telefoneCelular);

        atualizaIdDosTelefones(telefoneFixo, telefoneCelular);
        telefoneDAO.atualiza(telefoneFixo, telefoneCelular);
    }

    private void atualizaIdDosTelefones(Telefone telefoneFixo, Telefone telefoneCelular) {
        for (Telefone telefone : telefonesDoAluno) {
            if (telefone.getTipo() == TipoTelefone.FIXO) {
                telefoneFixo.setId(telefone.getId());
            } else {
                telefoneCelular.setId(telefone.getId());
            }
        }
    }

    private void vinculaAlunoComTelefone(int alunoId, Telefone... telefones) {
        for (Telefone telefone : telefones) {
            telefone.setAlunoId(alunoId);
        }
    }
}
