package com.example.loltrivial;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {PreguntaEntidad.class}, version = 1)
public abstract class PreguntasRoomBBDD extends RoomDatabase {

    private static PreguntasRoomBBDD INSTANCIA_COMPARTIDA; //Singleton

    public abstract PreguntaDao preguntaDao();

    public static synchronized PreguntasRoomBBDD getInstanciaCompartida(final Context context) {
        if (INSTANCIA_COMPARTIDA == null) {
            //Creamos la BBDD
            INSTANCIA_COMPARTIDA = Room.databaseBuilder(context.getApplicationContext(),
                                    PreguntasRoomBBDD.class, "Preguntas_BBDD")
                                    .addCallback(RoomCallback)
                                    .build();
        }
        return INSTANCIA_COMPARTIDA; //Devolvemos la instancia de la BBDD
    }

    private static RoomDatabase.Callback RoomCallback = new RoomDatabase.Callback(){ //Callback de la BBDD
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCIA_COMPARTIDA).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PreguntaDao preguntaDao;

        private PopulateDbAsync(PreguntasRoomBBDD db) {
            preguntaDao = db.preguntaDao();
        }

        @Override
        protected Void doInBackground(final Void... params) { //Operaciones de la BBDD
            preguntaDao.deleteAll(); //Vaciar la BBDD para evitar duplicados
            preguntaDao.insert(new PreguntaEntidad("¿Cuál es la región más fría de Runeterra?",
                                                    "Noxus",
                                                    "Freljord",
                                                    "Demacia",
                                                    "Ionia",
                                                    2));

            preguntaDao.insert(new PreguntaEntidad("¿Cuál de estos campeones no forma parte del grupo 'Pentakill'?",
                                                    "Karthus",
                                                    "Olaf",
                                                    "Mordekaiser",
                                                    "Katarina",
                                                    4));

            preguntaDao.insert(new PreguntaEntidad("¿Cuáles son los apodos cariñosos con los que se refieren entre sí Xayah y Rakkan?",
                                                    "Mieli y miella",
                                                    "Melli y miala",
                                                    "Melia y mehali",
                                                    "Meeli y malli",
                                                    1));

            preguntaDao.insert(new PreguntaEntidad("¿Cómo se llama la pistola de Jhin?",
                                                    "Bloom",
                                                    "Pum-pum",
                                                    "Trigger",
                                                    "Whisper",
                                                    4));

            preguntaDao.insert(new PreguntaEntidad("¿Cómo se llama el tiburón de Fizz?",
                                                    "Teethy",
                                                    "Sharky",
                                                    "Chomper",
                                                    "Finn",
                                                    3));
            return null;
        }
    }
}
