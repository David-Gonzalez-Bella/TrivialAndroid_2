package com.example.loltrivial

object ListaPreguntas {

    fun getPreguntas(): ArrayList<Pregunta>{
        val lista = ArrayList<Pregunta>();

        val preg1 = Pregunta(
                1,
                "¿Cuál es la región más fría de Runeterra?",
                "Noxus",
                "Freljord",
                "Demacia",
                "Ionia",
                2
        )
        val preg2 = Pregunta(
                2,
                "¿Cuál de estos campeones no forma parte del grupo 'Pentakill'?",
                "Karthus",
                "Olaf",
                "Mordekaiser",
                "Katarina",
                4
        )
        val preg3 = Pregunta(
                3,
                "¿Cuáles son los apodos cariñosos con los que se refieren entre sí Xayah y Rakkan?",
                "Mieli y miella",
                "Melli y miala",
                "Melia y mehali",
                "Meeli y malli",
                1
        )
        val preg4 = Pregunta(
                3,
                "¿Cómo se llama la pistola de Jhin?",
                "Bloom",
                "Pum-pum",
                "Trigger",
                "Whisper",
                4
        )
        val preg5 = Pregunta(
                4,
                "¿Cómo se llama el tiburón de Fizz?",
                "Teethy",
                "Sharky",
                "Chomper",
                "Finn",
                3
        )
        lista.add(preg1);
        lista.add(preg2);
        lista.add(preg3);
        lista.add(preg4);
        lista.add(preg5);
        return lista;
    }
}