package com.example.loltrivial

object ListaPreguntasHibridas {

    fun getPreguntasHibridas(): ArrayList<PreguntaHibrida>{
        val lista = ArrayList<PreguntaHibrida>();

        val preg1 = PreguntaHibrida(
                1,
                "¿Cómo se llama este campeón?",
                R.drawable.zac,
                "Jayce",
                "Zac",
                "Sion",
                "Veigar",
                2
        )
        val preg2 = PreguntaHibrida(
                1,
                "¿Cuántas skins tiene esta campeona?",
                R.drawable.katarina,
                "8",
                "10",
                "12",
                "14",
                3
        )
        val preg3 = PreguntaHibrida(
                3,
                "¿Cómo se llama el guardian de Soraka guardiana de las estrellas?",
                R.drawable.soraka_guardian,
                "Shisa",
                "Saki",
                "Baki",
                "Mimi",
                1
        )
        val preg4 = PreguntaHibrida(
                3,
                "¿Cómo se llama esta skin de Rengar?",
                R.drawable.rengar,
                "Rengar guardián de las arenas",
                "Rengar tormenta de arena",
                "Rengar azote de las arenas",
                "Rengar cazador de arena",
                1
        )
        val preg5 = PreguntaHibrida(
                3,
                "¿Cómo se llama este objeto?",
                R.drawable.deathfire_grasp,
                "Empalador de Atma",
                "Garra de la Muerte Ígnea",
                "Voluntad de los Ancestros",
                "Fervor de Stark",
                2
        )
        lista.add(preg1);
        lista.add(preg2);
        lista.add(preg3);
        lista.add(preg4);
        lista.add(preg5);
        return lista;
    }
}