package sv.udb.edu.investigacion_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private EditText texto;
    private Button btnHablar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlazando el boton de la activity con una variable local para poder manipular sus valores
        btnHablar = findViewById(R.id.btnReproducir);
        //Enlazando el Edit Text de la activity con una variable local para poder manipular sus valores
        texto = findViewById(R.id.txtTexto);

        //Creando e inicializando el objeto TextToSpeach
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //Lenguaje objetivo
                Locale spanish = new Locale("es", "ES");
                //validando inicializacion
                if(status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(spanish);

                    //validando el idioma, que exista la voz que soporte el lenguaje especificado
                    if(result == TextToSpeech.LANG_MISSING_DATA || result== TextToSpeech.LANG_NOT_SUPPORTED){
                        //mensajes para depurar la app
                        Log.e("TTS", "Lenguaje no soportado");
                    }else {
                        btnHablar.setEnabled(true);
                    }
                }else {
                    //mensajes para depurar la app
                    Log.e("TTS", "Inicializacion fallo");
                }
            }
        });



        //Asociando el evento Click al boton responsable de ejecutar al metodo hablar()
        btnHablar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                hablar();
            }
        });
    }

    //ciclo onDestroy, liberando recursos al finalizar la aplicación
    @Override
    protected void onDestroy() {
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    //Funcion responsable de agarrar el texto del Edit Text y leer el contenido
    private void hablar(){
        String text = texto.getText().toString();

        tts.setSpeechRate(2.2f);

        //pasando texto a hablar
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}