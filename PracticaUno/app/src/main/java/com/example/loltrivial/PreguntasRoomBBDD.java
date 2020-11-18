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


            //Preguntas de video
            preguntaDao.insert(new PreguntaEntidad(
                                                    "Video",
                                                    "¿Cómo se llama esta canción?",
                                                    R.raw.legends_never_die,
                                                    "Legends never die",
                                                    "Warriors",
                                                    "Rise",
                                                    "We were legends",
                                                    1));

            preguntaDao.insert(new PreguntaEntidad(
                                                    "Video",
                                                    "¿Cuál es el grupo que canta esta canción?",
                                                    R.raw.pentakill,
                                                    "KDA",
                                                    "Pentakill",
                                                    "True Damage",
                                                    "A Day to Remember",
                                                    2));

            preguntaDao.insert(new PreguntaEntidad(
                                                    "Video",
                                                    "¿A qué campeón está dedicada esta cinemática?",
                                                    R.raw.sylas,
                                                    "Garen",
                                                    "Lux",
                                                    "Sylas",
                                                    "Galio",
                                                    3));

            preguntaDao.insert(new PreguntaEntidad(
                                                    "Video",
                                                    "¿Qué artista produjo la siguiente canción?",
                                                    R.raw.ignite,
                                                    "Avicii",
                                                    "Duo Kie",
                                                    "Yassuo",
                                                    "Zedd",
                                                    4));

            preguntaDao.insert(new PreguntaEntidad(
                                                    "Video",
                                                    "¿A qué año pertenecen las siguientes skins arcade?",
                                                    R.raw.arcade,
                                                    "2010",
                                                    "2015",
                                                    "2019",
                                                    "2020",
                                                    2));

            preguntaDao.insert(new PreguntaEntidad(
                                                    "Video",
                                                    "¿A qué modo de juego pertenece el siguiente login screen?",
                                                    R.raw.urf,
                                                    "U.R.F.",
                                                    "Bots Malditos",
                                                    "A.R.U.R.F.",
                                                    "Hexakill",
                                                    1));

            preguntaDao.insert(new PreguntaEntidad(
                                                    "Video",
                                                    "¿Cómo se llamaba el siguiente modo de juego?",
                                                    R.raw.ascension,
                                                    "Dominion",
                                                    "La Leyenda del Rey Poro",
                                                    "Ascensión",
                                                    "Asedio del Nexo",
                                                    3));

            preguntaDao.insert(new PreguntaEntidad(
                                                    "Video",
                                                    "¿Qué cantante interpreta a Ekko en esta canción?",
                                                    R.raw.giants,
                                                    "Eminem",
                                                    "Thutmose",
                                                    "DUCKWRTH",
                                                    "Keke Palmer",
                                                    2));

            preguntaDao.insert(new PreguntaEntidad(
                                                    "Video",
                                                    "¿A que canción pertenece el siguiente videoclip?",
                                                    R.raw.take_over,
                                                    "As We Fall",
                                                    "Awaken",
                                                    "Phoenix",
                                                    "Take Over",
                                                    4));

            preguntaDao.insert(new PreguntaEntidad(
                                                    "Video",
                                                    "¿Cómo se llama esta canción?",
                                                    R.raw.villain,
                                                    "Villain",
                                                    "MORE",
                                                    "THE BADDEST",
                                                    "I´ll show you",
                                                    1));


            //Preguntas de texto
            preguntaDao.insert(new PreguntaEntidad("Texto",
                                                    "¿Cuál es la región más fría de Runeterra?",
                                                    0,
                                                    "Noxus",
                                                    "Freljord",
                                                    "Demacia",
                                                    "Ionia",
                                                    2));

            preguntaDao.insert(new PreguntaEntidad("Texto",
                                                    "¿Cuál de estos campeones no forma parte del grupo 'Pentakill'?",
                                                    0,
                                                    "Karthus",
                                                    "Olaf",
                                                    "Mordekaiser",
                                                    "Katarina",
                                                    4));

            preguntaDao.insert(new PreguntaEntidad("Texto",
                                                    "¿Cuáles son los apodos cariñosos con los que se refieren entre sí Xayah y Rakkan?",
                                                    0,
                                                    "Mieli y miella",
                                                    "Melli y miala",
                                                    "Melia y mehali",
                                                    "Meeli y malli",
                                                    1));

            preguntaDao.insert(new PreguntaEntidad("Texto",
                                                    "¿Cómo se llama la pistola de Jhin?",
                                                    0,
                                                    "Bloom",
                                                    "Pum-pum",
                                                    "Trigger",
                                                    "Whisper",
                                                    4));

            preguntaDao.insert(new PreguntaEntidad("Texto",
                                                    "¿Cómo se llama el tiburón de Fizz?",
                                                    0,
                                                    "Teethy",
                                                    "Sharky",
                                                    "Chomper",
                                                    "Finn",
                                                    3));

            //Preguntas de imagen
            preguntaDao.insert(new PreguntaEntidad("Imagen",
                                                    "¿Cuál de estas es la region de Piltover?",
                                                    0,
                                                    "drawable/ionia",
                                                    "drawable/noxus",
                                                    "drawable/piltover",
                                                    "drawable/targon",
                                                    3));

            preguntaDao.insert(new PreguntaEntidad("Imagen",
                                                    "¿Qué campeona fue la primera en ser reconocida como lesbiana oficialmente?",
                                                    0,
                                                    "drawable/caitlyn",
                                                    "drawable/fiora",
                                                    "drawable/neeko",
                                                    "drawable/vi",
                                                    3));

            preguntaDao.insert(new PreguntaEntidad("Imagen",
                                                    "¿Cuál de estos campeones está más desatendido por la compañía desarrolladora?",
                                                    0,
                                                    "drawable/maestroyi",
                                                    "drawable/ezreal",
                                                    "drawable/diana",
                                                    "drawable/quinn",
                                                    4));

            preguntaDao.insert(new PreguntaEntidad("Imagen",
                                                    "¿Qué equipo ganó el mundial en el año 2019?",
                                                    0,
                                                    "drawable/uol",
                                                    "drawable/fpx",
                                                    "drawable/g2",
                                                    "drawable/fnatic",
                                                    2));

            preguntaDao.insert(new PreguntaEntidad("Imagen",
                                                    "¿Qué campeón dice la frase: 'Sólo los divinos pueden juzgar'?",
                                                    0,
                                                    "drawable/kayle",
                                                    "drawable/aurelion",
                                                    "drawable/garen",
                                                    "drawable/soraka",
                                                    1));


            //Preguntas hibridas
            preguntaDao.insert(new PreguntaEntidad("Hibrida",
                                                    "¿Cómo se llama este campeón?",
                                                    R.drawable.zac,
                                                    "Jayce",
                                                    "Zac",
                                                    "Sion",
                                                    "Veigar",
                                                    2));

            preguntaDao.insert(new PreguntaEntidad("Hibrida",
                                                    "¿Cuántas skins tiene esta campeona?",
                                                    R.drawable.katarina,
                                                    "8",
                                                    "10",
                                                    "12",
                                                    "14",
                                                    3));

            preguntaDao.insert(new PreguntaEntidad("Hibrida",
                                                    "¿Cómo se llama el guardian de Soraka guardiana de las estrellas?",
                                                    R.drawable.soraka_guardian,
                                                    "Shisa",
                                                    "Saki",
                                                    "Baki",
                                                    "Mimi",
                                                    1));

            preguntaDao.insert(new PreguntaEntidad("Hibrida",
                                                    "¿Cómo se llama esta skin de Rengar?",
                                                    R.drawable.rengar,
                                                    "Rengar guardián de las arenas",
                                                    "Rengar tormenta de arena",
                                                    "Rengar azote de las arenas",
                                                    "Rengar cazador de arena",
                                                    1));

            preguntaDao.insert(new PreguntaEntidad("Hibrida",
                                                    "¿Cómo se llama este objeto?",
                                                    R.drawable.deathfire_grasp,
                                                    "Empalador de Atma",
                                                    "Garra de la Muerte Ígnea",
                                                    "Voluntad de los Ancestros",
                                                    "Fervor de Stark",
                                                    2));

            return null;
        }
    }
}
