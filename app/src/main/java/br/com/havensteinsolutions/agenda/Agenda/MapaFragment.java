package br.com.havensteinsolutions.agenda.Agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.room.Room;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.havensteinsolutions.agenda.Agenda.Infra.Dao.AlunoDAO;
import br.com.havensteinsolutions.agenda.Agenda.Infra.database.AgendaDatabase;
import br.com.havensteinsolutions.agenda.Agenda.modelo.Aluno;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    private AlunoDAO dao;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        dao = Room.databaseBuilder(getContext(), AgendaDatabase.class, "agenda.db")
                .allowMainThreadQueries()
                .build()
                .getRoomAlunoDAO();
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng posicaoDaEscola = pegaCoordenadoDoEndereco("Rua Jacarepagua 216, Salto do Norte, Santa Catarina");
        if (posicaoDaEscola != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoDaEscola, 17);
            googleMap.moveCamera(update);
        }

        for (Aluno aluno : dao.todos()) {
            LatLng coordenada = pegaCoordenadoDoEndereco(aluno.getEndereco());
            if (coordenada != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }

        new Localizador(getContext(), googleMap);
    }

    private LatLng pegaCoordenadoDoEndereco(String endereco) {
        Geocoder geoCoder = new Geocoder(getContext());
        try {
            List<Address> resultados = geoCoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()) {
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
