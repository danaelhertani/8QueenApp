package shiwei.lab2;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridLayout;


public class MainActivity extends AppCompatActivity {
    GridLayout myGrid;
    int[][] board = new int[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myGrid = (GridLayout) findViewById(R.id.gridLayout);

        showPop("App Started");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    public void giveUpClick(View v){
        showPop("Testing");
    }


    public void onClick(View v) {
        //TextView textview = (TextView) findViewById(R.id.textView);


        ImageButton b = (ImageButton) findViewById(v.getId());

        String string = v.getResources().getResourceName(v.getId());
        String row = string.substring(string.length() - 3);
        String column = string.substring(string.length() - 1);
        int x = Character.getNumericValue(row.charAt(0));
        int y = Character.getNumericValue(column.charAt(0));


        Log.d("HIIII", "row:" + x + "column: " + y);

        boolean bool = checkAttack(v,b,x,y);

        Log.d("BOOLEAN EXPRESSION"," "+bool);

        Log.d("board", " " + board[x][y]);
    }

    public void reset(View v){
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if ((board[i][j]) == 1){
                    String tag = i + "-" + j;
                    ImageButton b = (ImageButton) myGrid.findViewWithTag(tag);
                    b.setBackgroundResource(R.drawable.light);
                }
            }
        }


    }

    public boolean checkAttack(View v, ImageButton b, int x, int y){

        if (board[x][y] != 1) {
            //showPop("testing");
            b.setBackgroundResource(R.drawable.crown);
            //v.setTag(getString(R.string.int2));
            board[x][y] = 1;

            for (int i = 0; i < 8; i++) {
                Log.d("CHECK HERE", "x " + x + " i " + i + " " + board[x][i]);
                if ((board[x][i] == 1) && (i != y)) {
                    //System.out.println("Invalid move on row");
                    showPop("Invalid move- row");
                    b.setBackgroundResource(R.drawable.light);
                    v.setTag(getString(R.string.int1));
                    board[x][y] = 0;
                    return false;
                }
            }


            for (int i = 0; i < 8; i++) {
                if ((board[i][y] == 1) && (i != x)) {
                    //System.out.println("Invalid move on column");
                    showPop("Invalid move- column");
                    b.setBackgroundResource(R.drawable.light);
                    v.setTag(getString(R.string.int1));
                    board[x][y] = 0;
                    return false;
                }
            }


            int j = y;
            for(int k = x-1; k >= 0 && j > 0; k--){
                j--;
                if ((board[k][j] == 1) && (k != x && j != y)) {
                    Log.d("INVALID BC", "x " + x + " y " + y + " k " + k + " j " + j);
                    showPop("Invalid move - DTL");
                    b.setBackgroundResource(R.drawable.light);
                    v.setTag(getString(R.string.int1));
                    board[x][y] = 0;
                    return false;

                }
            }

            j = y;
            for(int i = x+1; ((i<8)&&(j>0)); i++){
                j--;
                if ((board[i][j] == 1) ) {
                    Log.d("INVALID BC", "x " + x + " y " + y + " i " + i + " j " + j);
                    showPop("Invalid move - DBL");
                    b.setBackgroundResource(R.drawable.light);
                    v.setTag(getString(R.string.int1));
                    board[x][y] = 0;
                    return false;

                }
            }



        } else {
            b.setBackgroundResource(R.drawable.light);
            //v.setTag(getString(R.string.int1));
            board[x][y] = 0;
        }
        return true;
    }

    public boolean giveUp(int board[][], int col) {
        if (col >= 8)
            return true;
        for (int i = 0; i < 8; i++) {
            if (placingQueen(board, i, col)) {
                board[i][col] = 1;
                if (giveUp(board, col + 1))
                    return true;
                board[i][col] = 0;
            }

        }
        //buildChess(board);

        return false;
    }

    public boolean placingQueen(int board[][], int row, int col) {
        int i, j;
        for (i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1)
                return false;
        }
        for (i = row, j = col; j >= 0 && i < 8; i++, j--) {
            if (board[i][j] == 1)
                return false;
        }
        return true;


    }

    public void gClick(View v){
        boolean hasOne = false;
        int col = 0;
        for(int i = 0; i < 8; i++){
            for(int j=0; j < 8; j++){
                if(board[i][j] == 1){
                    hasOne = true;
                    col = j +1;
                }
            }
        }
        Log.d("col is", " " + col);
        //giveUp(board, col);
        if (!giveUp(board,col)){
          showPop("No solution");
        }
        else{
            buildChess(board);
        }

    }


    public void buildChess(int board[][]){

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++)
                if((board[i][j] == 1)){
                    String tag = i + "-" + j;
                    ImageButton b = (ImageButton) myGrid.findViewWithTag(tag);
                    b.setBackgroundResource(R.drawable.crown);
                }
        }

    }




    //for (int i = 0; i < 8; i++){
    //  for(int j = 0; j < 8; j++){

    //    Log.d("CHECK HERE", "x " + x + " i " + i + " " + board[i][j]);
    //if (j==7){}
    // }
    //}

/*
    public void clearClick(View v){
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if ((board[i][j]) == 1){

                }
            }
        }
    } */

        /*i = x;
        //check collisions on diagnol top left
        while(i > 0){
            for (int j = y; j > 0 ; j--){
                //System.out.println("position " + i + ", " + j);
                if((board[i][j] == 1) && (i != x && j !=y)){
                    //System.out.println("i= " + i + " j= " +j + " Invalid move on DTL");
                    showPop("Invalid move");

                }
                i--;
            }
        }

    }

/*
        i = x;
        //check collisions on diagnol bottom right
        //System.out.println("DBR");
        while(i < 7){
            for (int j = y; j < 8 ; j++){
                //System.out.println("position " + i + ", " + j);
                if((board[i][j] == 1) && (i != x && j !=y)){
                    //System.out.println("i= " + i + " j= " +j + " Invalid move on DBR");
                    showPop("Invalid move");

                }
                i++;
            }
        }


        i = x;
        //System.out.println("this is i: " + i);
        //check collisions on diagnol top right
        while(i > 0){ //went from i > 0 to i > 1
            for (int j = y; j < 8 ; j++){
                //System.out.println("position " + i + ", " + j);
                if((board[i][j] == 1) && (i != x && j !=y)){
                    //System.out.println("i= " + i + " j= " +j + " Invalid move on DTR");
                    showPop("Invalid move");

                }
                i--;
            }
        }



        i = x;
        //check collisions on diagnol top left
        while(i > 0){
            for (int j = y; j > 0 ; j--){
                //System.out.println("position " + i + ", " + j);
                if((board[i][j] == 1) && (i != x && j !=y)){
                    //System.out.println("i= " + i + " j= " +j + " Invalid move on DTL");
                    showPop("Invalid move");

                }
                i--;
            }
        }




    }

      /*  int a = 10;
        if(v.getId() == R.id.imageButton){
            textview.setText("Button 1");
        }
        else{
            textview.setText("Button 2");
        } */



        /*else  {
            b.setBackgroundResource(R.drawable.red);
            v.setTag("1");
        }


/*
            if (v.getTag().equals("1")) {
                b.setBackgroundResource(R.drawable.crown);
                v.setTag("1");
            } else if (v.getTag() == 1 && v.getBackground().equals(R.drawable.crown)) {
                b.setBackgroundResource(R.drawable.light);
                v.setTag("1");
            }


        if (v.getTag().equals("2")) {
            b.setBackgroundResource(R.drawable.crown);
            v.setTag("2");
        } else if (v.getTag()== 2 ) {
            b.setBackgroundResource(R.drawable.red);
            v.setTag("2");
        }

        /*
            if (v.getTag().equals("2")) {
                b.setBackgroundResource(R.drawable.crown);
                v.setTag(getString(R.string.int1));
            } else {
                b.setBackgroundResource(R.drawable.red);
                v.setTag(getString(R.string.int2));
            }




        ImageButton b1 = (ImageButton) findViewById(R.id.imageButton);
        ImageButton b2 = (ImageButton) findViewById(R.id.imageButton2);
        if(b1.getTag().equals(b2.getTag())){
            showPop("Same Image");
        }

    }

                i = x;
            //check collisions on diagnol top left
            while (i >= 0) {
                for (int j = y; j >= 0; j--) {
                    if (i == -1) {
                        break;
                    }
                    Log.d("DTL", "x " + x + " y " + y + " i " + i + " j " + j);
                    if ((board[i][j] == 1) && (i != x && j != y)) {
                        Log.d("MADE IT INVALID", "x " + x + " y " + y + " i " + i + " j " + j);
                        showPop("Invalid move -DTL");

                    }
                    i--;
                }
            }

            //4:45
                       int j = y;
           for(int k = x-1; k > 0 && j > 0; k--){
               j--;
               if ((board[k][j] == 1) && (k != x && j != y)) {
                   showPop("Invalid move - DTL");

               }
           }

           /*
            int i = x;
            //check collisions on diagnol bottom left
            while (i < 7) {
                for (int j = y; j >= 0; j--) {
                    if (i == 8) {
                        break;
                    }
                    Log.d("DBL", "x " + x + " y " + y + " i " + i + " j " + j);
                    if ((board[i][j] == 1) && (i != x && j != y)) {
                        Log.d("INVALID BC", "x " + x + " y " + y + " i " + i + " j " + j);
                        showPop("Invalid move - DBL");
                        b.setBackgroundResource(R.drawable.light);
                        v.setTag(getString(R.string.int1));
                        board[x][y] = 0;

                    }
                    i++;
                }
            }

*/





    public void showPop(String s){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();

    }
}