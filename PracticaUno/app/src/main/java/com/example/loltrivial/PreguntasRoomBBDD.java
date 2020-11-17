package com.example.loltrivial;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
            //Creamos la BBDD en caso de estar vacia
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
            super.onCreate(db); //Crear la BBDD
            new PopulateDbAsync(INSTANCIA_COMPARTIDA).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PreguntaDao preguntaDao;

        private PopulateDbAsync(PreguntasRoomBBDD db) {
            preguntaDao = db.preguntaDao();
        }

        @Override
        protected Void doInBackground(final Void... params) { //Operaciones de la BBDD - Borrar y despues llenarla con las preguntas
            preguntaDao.deleteAll(); //Vaciar la BBDD para evitar duplicados

//            preguntaDao.insert(new PreguntaEntidad(
//                                                    "Video",
//                                                    "¿Cómo se llama esta canción?",
//                                                    "legends_never_die",
//                                                    "Legends never die",
//                                                    "Warriors",
//                                                    "Rise",
//                                                    "We were legends",
//                                                    1));
//
//            preguntaDao.insert(new PreguntaEntidad(
//                                                    "Video",
//                                                    "¿Cuál es el grupo que canta esta canción?",
//                                                    "pentakill",
//                                                    "KDA",
//                                                    "Pentakill",
//                                                    "True Damage",
//                                                    "A Day to Remember",
//                                                    2));
//
//            preguntaDao.insert(new PreguntaEntidad(
//                                                    "Video",
//                                                    "¿A qué campeón está dedicada esta cinemática?",
//                                                    "sylas",
//                                                    "Garen",
//                                                    "Lux",
//                                                    "Sylas",
//                                                    "Galio",
//                                                    3));
//
//            preguntaDao.insert(new PreguntaEntidad(
//                                                    "Video",
//                                                    "¿Qué artista produjo la siguiente canción?",
//                                                    "ignite",
//                                                    "Avicii",
//                                                    "Duo Kie",
//                                                    "Yassuo",
//                                                    "Zedd",
//                                                    4));
//
//            preguntaDao.insert(new PreguntaEntidad(
//                                                    "Video",
//                                                    "¿A qué año pertenecen las siguientes skins arcade?",
//                                                    "arcade",
//                                                    "2010",
//                                                    "2015",
//                                                    "2019",
//                                                    "2020",
//                                                    2));
//
//            preguntaDao.insert(new PreguntaEntidad(
//                                                    "Video",
//                                                    "¿A qué modo de juego pertenece el siguiente login screen?",
//                                                    "urf",
//                                                    "U.R.F.",
//                                                    "Bots Malditos",
//                                                    "A.R.U.R.F.",
//                                                    "Hexakill",
//                                                    1));
//
//            preguntaDao.insert(new PreguntaEntidad(
//                                                    "Video",
//                                                    "¿Cómo se llamaba el siguiente modo de juego?",
//                                                    "ascension",
//                                                    "Dominion",
//                                                    "La Leyenda del Rey Poro",
//                                                    "Ascensión",
//                                                    "Asedio del Nexo",
//                                                    3));
//
//            preguntaDao.insert(new PreguntaEntidad(
//                                                    "Video",
//                                                    "¿Qué cantante interpreta a Ekko en esta canción?",
//                                                    "giants",
//                                                    "Eminem",
//                                                    "Thutmose",
//                                                    "DUCKWRTH",
//                                                    "Keke Palmer",
//                                                    2));
//
//            preguntaDao.insert(new PreguntaEntidad(
//                                                    "Video",
//                                                    "¿A que canción pertenece el siguiente videoclip?",
//                                                    "take_over",
//                                                    "As We Fall",
//                                                    "Awaken",
//                                                    "Phoenix",
//                                                    "Take Over",
//                                                    4));
//
//            preguntaDao.insert(new PreguntaEntidad(
//                                                    "Video",
//                                                    "¿Cómo se llama esta canción?",
//                                                    "villain",
//                                                    "Villain",
//                                                    "MORE",
//                                                    "THE BADDEST",
//                                                    "I´ll show you",
//                                                    1));





            //HEREEEEEE
            preguntaDao.insert(new PreguntaEntidad("Texto",
                                                    "¿Cuál es la región más fría de Runeterra?",
                                                    "",
                                                    "Noxus",
                                                    "Freljord",
                                                    "Demacia",
                                                    "Ionia",
                                                    2));

            preguntaDao.insert(new PreguntaEntidad("Texto",
                                                    "¿Cuál de estos campeones no forma parte del grupo 'Pentakill'?",
                    "",
                                                    "Karthus",
                                                    "Olaf",
                                                    "Mordekaiser",
                                                    "Katarina",
                                                    4));

            preguntaDao.insert(new PreguntaEntidad("Texto",
                                                    "¿Cuáles son los apodos cariñosos con los que se refieren entre sí Xayah y Rakkan?",
                    "",
                                                    "Mieli y miella",
                                                    "Melli y miala",
                                                    "Melia y mehali",
                                                    "Meeli y malli",
                                                    1));

            preguntaDao.insert(new PreguntaEntidad("Texto",
                                                    "¿Cómo se llama la pistola de Jhin?",
                    "",
                                                    "Bloom",
                                                    "Pum-pum",
                                                    "Trigger",
                                                    "Whisper",
                                                    4));

            preguntaDao.insert(new PreguntaEntidad("Texto",
                                                    "¿Cómo se llama el tiburón de Fizz?",
                    "",
                                                    "Teethy",
                                                    "Sharky",
                                                    "Chomper",
                                                    "Finn",
                                                    3));
            return null;
        }
    }
}
