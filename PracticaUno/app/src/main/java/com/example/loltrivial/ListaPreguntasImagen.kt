package com.example.loltrivial

object ListaPreguntasImagen {
    fun getPreguntasImagen(): ArrayList<PreguntaImagen>{
        val lista = ArrayList<PreguntaImagen>();

        val preg1 = PreguntaImagen(
                1,
                "¿Cuál de estas es la region de Piltover?",
                R.drawable.ionia,
                R.drawable.noxus,
                R.drawable.piltover,
                R.drawable.targon,
                3
        )
        val preg2 = PreguntaImagen(
                2,
                "¿Qué campeona fue la primera en ser reconocida como lesbiana oficialmente?",
                R.drawable.caitlyn,
                R.drawable.fiora,
                R.drawable.neeko,
                R.drawable.vi,
                3
        )
        val preg3 = PreguntaImagen(
                3,
                "¿Qué campeón dice la frase: 'Sólo los divinos pueden juzgar'?",
                R.drawable.kayle,
                R.drawable.aurelion,
                R.drawable.garen,
                R.drawable.soraka,
                1
        )
        val preg4 = PreguntaImagen(
                4,
                "¿Cuál de estos campeones está más desatendido por la compañía desarrolladora?",
                R.drawable.maestroyi,
                R.drawable.ezreal,
                R.drawable.diana,
                R.drawable.quinn,
                4
        )
        val preg5 = PreguntaImagen(
                5,
                "¿Qué equipo ganó el mundial en el año 2019?",
                R.drawable.uol,
                R.drawable.fpx,
                R.drawable.g2,
                R.drawable.fnatic,
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