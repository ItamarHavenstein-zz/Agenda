package br.com.havensteinsolutions.agenda.Agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.havensteinsolutions.agenda.Agenda.Infra.database.AgendaDatabase;
import br.com.havensteinsolutions.agenda.Agenda.asynktask.BuscaPrimeiroTelefoneDoAluno;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Telefone;
import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.TelefoneDAO;
import br.com.havensteinsolutions.agenda.R;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;

public class AlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Context contexto;
    private final TelefoneDAO dao;

    public AlunosAdapter(Context context, List<Aluno> aluno) {
        alunos = aluno;
        contexto = context;
        dao = AgendaDatabase.getInstance(contexto).getTelefoneDAO();
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);
        LayoutInflater inflater = LayoutInflater.from(contexto);
        //feito para reaproveitar os itens  da lista e não ficar inflando toda vez
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }


        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());
        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
            new BuscaPrimeiroTelefoneDoAluno(dao,campoTelefone,(int)aluno.getId()).execute();

        TextView campoEndereco = (TextView) view.findViewById(R.id.item_endereco);
        if (campoEndereco != null) {
            campoEndereco.setText(aluno.getEndereco());
        }

        TextView campoSite = (TextView) view.findViewById(R.id.item_site);
        if (campoSite != null) {
            campoSite.setText(aluno.getSite());
        }

        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
