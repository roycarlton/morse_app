package com.morse;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean success;
    ToneGenerator ditDah = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    ToneGenerator pause = new ToneGenerator(AudioManager.STREAM_MUSIC, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private int binSearch(char[] sList, char sItem, int high, int low) {
        int mid = (int) ((low + high) / 2);

        while (high >= low) {

            if (sList[mid] == sItem) {
                return mid;
            } else if (sList[mid] < sItem) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
            mid = (int) ((low + high) / 2);
        }

        return -1;

    }


    String[] code = new String[]{"/", ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---"
            , "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "..."
            , "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};

    char[] letters = new char[]{' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'
            , 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};


    public void translateButton(View view){

        EditText plainTextInput = findViewById(R.id.plainTextInput);
        TextView morseOutput = findViewById(R.id.morseOutput);
        String plainText = plainTextInput.getText().toString().toLowerCase();
        String cypherText = "";

        Log.e("myTag", "plaintext: " + plainText);

        int i = 0;

        while (i <= plainText.length()-1){

            success = true;

            Log.e("myTag", "i: " + i);
            int temp = binSearch(letters, plainText.charAt(i), 26, 0);

            if (temp == -1){
                Log.e("myTag", "Character not found");
                cypherText = "Sorry, you entered a character that can't be translated.";
                success = false;
                break;
            }

            cypherText = cypherText + code[temp];
            Log.e("myTag", cypherText);
            cypherText = cypherText + " ";

            i++;
        }

        if (plainText.length() == 0){
            cypherText = "Please enter some text to be translated.";
            success = false;
        }

        Log.e("myTag", "cyphertext:" + cypherText);
        morseOutput.setText(cypherText);


        if (success){
            i = 0;
            while (i <=cypherText.length()-1){
                if (cypherText.charAt(i) == '.'){
                    ditDah.startTone(ToneGenerator.TONE_CDMA_DIAL_TONE_LITE, 100);
                    synchronized (ditDah){
                        try {
                            ditDah.wait(100);
                        }catch (InterruptedException e){
                        }
                    }
                }
                else if (cypherText.charAt(i) == '-'){
                    ditDah.startTone(ToneGenerator.TONE_CDMA_DIAL_TONE_LITE, 300);
                    synchronized (ditDah){
                        try {
                            ditDah.wait(300);
                        }catch (InterruptedException e){
                        }
                    }
                }
                else if (cypherText.charAt(i) == '/'){
                    pause.startTone(ToneGenerator.TONE_CDMA_DIAL_TONE_LITE, 700);
                    synchronized (ditDah){
                        try {
                            ditDah.wait(700);
                        }catch (InterruptedException e){
                        }
                    }
                }
                else if (cypherText.charAt(i) == ' '){
                    pause.startTone(ToneGenerator.TONE_CDMA_DIAL_TONE_LITE, 300);
                    synchronized (ditDah){
                        try {
                            ditDah.wait(300);
                        }catch (InterruptedException e){
                        }
                    }
                }
                i++;
            }
        }
    }
}
