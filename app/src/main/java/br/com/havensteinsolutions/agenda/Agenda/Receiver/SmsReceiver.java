package br.com.havensteinsolutions.agenda.Agenda.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import br.com.havensteinsolutions.agenda.R;
import br.com.havensteinsolutions.agenda.Agenda.dao.AlunoDAO;

public class SmsReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);

        String Telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);
        if (dao.ehAluno(Telefone)) {
            Toast.makeText(context, "Chegou um sms de aluno", Toast.LENGTH_SHORT).show();
            MediaPlayer m = MediaPlayer.create(context, R.raw.msg);
            m.start();
        }
        dao.close();
    }

}
