package com.droidrank.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int[][] moves = new int[3][3];

    boolean inProgress = false;

    Button block1, block2, block3, block4, block5, block6, block7, block8, block9, restart;

    TextView result;

    int nextMove = R.string.player_1_move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        block1 = (Button) findViewById(R.id.bt_block1);
        block2 = (Button) findViewById(R.id.bt_block2);
        block3 = (Button) findViewById(R.id.bt_block3);
        block4 = (Button) findViewById(R.id.bt_block4);
        block5 = (Button) findViewById(R.id.bt_block5);
        block6 = (Button) findViewById(R.id.bt_block6);
        block7 = (Button) findViewById(R.id.bt_block7);
        block8 = (Button) findViewById(R.id.bt_block8);
        block9 = (Button) findViewById(R.id.bt_block9);
        result = (TextView) findViewById(R.id.tv_show_result);
        restart = (Button) findViewById(R.id.bt_restart_game);

        block1.setOnClickListener(new OnBlockClickListener(0, 0));
        block2.setOnClickListener(new OnBlockClickListener(1, 0));
        block3.setOnClickListener(new OnBlockClickListener(2, 0));
        block4.setOnClickListener(new OnBlockClickListener(0, 1));
        block5.setOnClickListener(new OnBlockClickListener(1, 1));
        block6.setOnClickListener(new OnBlockClickListener(2, 1));
        block7.setOnClickListener(new OnBlockClickListener(0, 2));
        block8.setOnClickListener(new OnBlockClickListener(1, 2));
        block9.setOnClickListener(new OnBlockClickListener(2, 2));

        /**
         * Restarts the game
         */
        restart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (!inProgress) {

                    inProgress = true;

                    restart.setText(R.string.restart_button_text_in_middle_of_game);

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle(R.string.app_name);
                    builder.setMessage(R.string.restart_message);

                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            restartGame(true);
                        }
                    });

                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                }
            }
        });
    }

    private void restartGame(boolean clearLabels) {

        restart.setText(R.string.restart_button_text_initially);

        inProgress = false;

        if(clearLabels) {

            block1.setText("");
            block2.setText("");
            block3.setText("");
            block4.setText("");
            block5.setText("");
            block6.setText("");
            block7.setText("");
            block8.setText("");
            block9.setText("");
        }

        for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < moves.length; j++) {
                moves[i][j] = 0;
            }
        }

        nextMove = R.string.player_1_move;
    }

    private boolean isPlayerWinner(int player) {

        if (moves[0][0] == player) {

            if (moves[1][1] == player && moves[2][2] == player) {
                return true;
            }
        }

        if (moves[0][2] == player) {

            if (moves[1][1] == player && moves[2][0] == player) {
                return true;
            }
        }


        for (int i = 0; i < moves.length; i++) {

            boolean win = true;

            for (int j = 0; j < moves.length; j++) {
                if (moves[i][j] != player) {
                    win = false;
                }
            }

            if (win) {
                return true;
            }

        }

        for (int i = 0; i < moves.length; i++) {

            boolean win = true;

            for (int j = 0; j < moves.length; j++) {
                if (moves[j][i] != player) {
                    win = false;
                }
            }

            if (win) {
                return true;
            }

        }

        return false;
    }

    private class OnBlockClickListener implements View.OnClickListener {

        private int posX;
        private int posY;

        OnBlockClickListener(int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
        }

        @Override
        public void onClick(View v) {

            if (!inProgress || moves[posX][posY] != 0) {
                return;
            }

            moves[posX][posY] = nextMove;

            ((Button) v).setText(nextMove);

            if (isPlayerWinner(nextMove)) {

                result.setText(nextMove == R.string.player_1_move ? R.string.player_1_wins : R.string.player_2_wins);

                restartGame(false);
            }

            nextMove = (nextMove == R.string.player_1_move) ? R.string.player_2_move : R.string.player_1_move;
        }
    }
}
