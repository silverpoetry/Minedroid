package com.b502.minedroid.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.b502.minedroid.MyApplication;
import com.b502.minedroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapManager {
    public enum GameDifficulty {
        EASY, MIDDLE, HARD
    }

    public enum GameState {
        WAIT, PLAYING, OVER
    }

    static final int[][] mapsize = {{9, 9}, {16, 16}, {16, 30}};
    static final int[] minecount = {10, 40, 99};

    int width;
    int height;
    int buttonwidth;

    int count;
    int leftblock;
    int leftflag;

    GameState gameState = GameState.WAIT;
    MapItem[][] map;
    GameDifficulty difficulty;

    Activity context;
    private TextView txtTime;
    private Button btnsmile;
    private TextView txtleftmines;
    int gametime;

    TimeManagementMaster timeManagementMaster;

    public TimeManagementMaster getTimeManagementMaster() {
        return timeManagementMaster;
    }

    public void reset() {
        timeManagementMaster.stop();
        gameState = GameState.WAIT;
        count = minecount[this.difficulty.ordinal()];
        leftflag = count;
        leftblock = width * height - count;
        gametime = 0;
        txtTime.setText("00:00");
        txtleftmines.setText(Integer.toString(leftflag));
        btnsmile.setText(":)");
        generateMap();
    }

    public MapManager(Activity context, GameDifficulty difficulty) {
        map = new MapItem[50][50];
        this.context = context;
        this.difficulty = difficulty;
        width = mapsize[this.difficulty.ordinal()][0];
        height = mapsize[this.difficulty.ordinal()][1];
        count = minecount[this.difficulty.ordinal()];
        leftflag = count;
        leftblock = width * height - count;
        buttonwidth = this.difficulty == GameDifficulty.EASY ? 40 : 25;
        gametime = 0;
//        for (int i = 0; i <= width + 1; i++) {
//            for (int j = 0; j <= height + 1; j++) {
//                map[i][j] = new MapItem(false);
//                //map[i][j].buttonState = MapItem.State.DEFAULT;
//            }
//        }
        txtTime = context.findViewById(R.id.txtTime);
        btnsmile = context.findViewById(R.id.btnsmile);
        txtleftmines = context.findViewById(R.id.txtleftmines);

        timeManagementMaster = new TimeManagementMaster(new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                gametime++;
                txtTime.setText(String.format("%02d:%02d", (gametime / 60), (gametime % 60)));
            }
        }, 10);

        txtTime.setText("00:00");
        txtleftmines.setText(Integer.toString(leftflag));
        btnsmile.setText(":)");
        generateButtons();
        generateMap();
    }

    public static int b2i(boolean val) {
        return val ? 1 : 0;
    }

    private int getPixelsFromDp(int size) {

        DisplayMetrics metrics = new DisplayMetrics();

        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

    }

    void generateMap() {

        for (int i = 0; i <= width + 1; i++) {
            for (int j = 0; j <= height + 1; j++) {
                map[i][j].setMine(false);
                map[i][j].setButtonState(MapItem.State.DEFAULT);
            }
        }
        //生成地雷编号
        int tot = width * height;
        List<Integer> numlist = new ArrayList<>();
        for (int i = 0; i < tot; i++) numlist.add(i);
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(numlist.size());
            int ind = numlist.get(index);
            numlist.remove(index);
            map[(ind % width) + 1][(ind / width) + 1].setMine(true);
        }
        for (int i = 1; i <= width; i++) {
            for (int j = 1; j <= height; j++) {
                //统计非地雷块周围地雷数目
                if (!map[i][j].isMine()) {
                    map[i][j].setMineCount(
                            b2i(map[i - 1][j + 1].isMine()) +
                                    b2i(map[i][j + 1].isMine()) +
                                    b2i(map[i + 1][j + 1].isMine()) +
                                    b2i(map[i - 1][j].isMine()) +
                                    b2i(map[i + 1][j].isMine()) +
                                    b2i(map[i - 1][j - 1].isMine()) +
                                    b2i(map[i][j - 1].isMine()) +
                                    b2i(map[i + 1][j - 1].isMine())
                    );
                }
            }
        }
    }

    void gameWin() {
        timeManagementMaster.stop();
        for (int i = 1; i <= width; i++) {
            for (int j = 1; j <= height; j++) {
                if (map[i][j].getButtonState() == MapItem.State.DEFAULT) {
                    map[i][j].setButtonState(MapItem.State.FLAGED);
                    leftflag--;
                    txtleftmines.setText(Integer.toString(leftflag));
                }
            }
        }
        MyApplication.Instance.sqlHelper.addRecord(difficulty, SqlHelper.getCurrentDate(), gametime);
        Toast.makeText(context, "游戏胜利", Toast.LENGTH_SHORT).show();
        gameState = GameState.OVER;
    }

    void gameLose() {
        timeManagementMaster.stop();
        for (int i = 1; i <= width; i++) {
            for (int j = 1; j <= height; j++) {
                if (map[i][j].getButtonState() == MapItem.State.FLAGED && !map[i][j].isMine()) {
                    map[i][j].setButtonState(MapItem.State.MISFLAGED);
                } else if (map[i][j].getButtonState() == MapItem.State.DEFAULT && map[i][j].isMine()) {
                    map[i][j].setButtonState(MapItem.State.BOOM);
                }
            }
        }
        btnsmile.setText(":(");
        Toast.makeText(context, "游戏结束", Toast.LENGTH_SHORT).show();
        gameState = GameState.OVER;
        //todo: get score
    }

    void extendBlockAt(int x, int y) {

        if (x == 0 || y == 0) return;
        if (x == width + 1 || y == height + 1) return;
        if (map[x][y].getButtonState() != MapItem.State.DEFAULT) return;

        if (!map[x][y].isMine()) {
            map[x][y].setButtonState(MapItem.State.OPENED);
            leftblock--;
            if (leftblock == 0) {
                gameWin();
            }
            if (map[x][y].getMineCount() == 0) {
                extendBlockAt(x, y - 1);
                extendBlockAt(x, y + 1);
                extendBlockAt(x - 1, y);
                extendBlockAt(x + 1, y);
                extendBlockAt(x - 1, y - 1);
                extendBlockAt(x + 1, y - 1);
                extendBlockAt(x - 1, y + 1);
                extendBlockAt(x + 1, y + 1);
            }
        } else {
            gameLose();
        }
    }

    //gaoshiqing
//    void flagBlockAround(int x, int y) {
//        if (x == 0 || y == 0) return;
//        if (x == width + 1 || y == height + 1) return;
//
//        MapItem block = map[x][y];
//        int Count = 0;
//        for (int i = x - 1; i <= x + 1; i++) {
//            for (int j = y - 1; j <= y + 1; j++) {
//                if (i == x && j == y) {
//                    continue;
//                }
//                MapItem.State state = map[i][j].getButtonState();
//                if (state == MapItem.State.FLAGED
//                        || state == MapItem.State.DEFAULT) {
//                    Count++;
//                }
//            }
//        }
//        if (block.getMineCount() == Count) {
//            for (int i = x - 1; i <= x + 1; i++) {
//                for (int j = y - 1; j <= y + 1; j++) {
//                    if ((i == x && j == y) || x == 0 || y == 0 || x == width + 1 || y == height + 1) {
//                        continue;
//                    }
//                    if (map[i][j].getButtonState() == MapItem.State.DEFAULT) {
//                        map[i][j].setButtonState(MapItem.State.FLAGED);
//                        leftflag--;
//                    }
//                }
//            }
//        }
//        txtleftmines.setText(Integer.toString(leftflag));
//    }

    void openBlockAround(int x, int y) {
        if (x == 0 || y == 0) return;
        if (x == width + 1 || y == height + 1) return;

        MapItem block = map[x][y];
        int flagCount = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i == x && j == y) {
                    continue;
                }
                if (map[i][j].getButtonState() == MapItem.State.FLAGED) {
                    flagCount++;
                }
            }
        }

        if (block.getMineCount() == flagCount) {
            extendBlockAt(x, y - 1);
            extendBlockAt(x, y + 1);
            extendBlockAt(x - 1, y);
            extendBlockAt(x + 1, y);
            extendBlockAt(x - 1, y - 1);
            extendBlockAt(x + 1, y - 1);
            extendBlockAt(x - 1, y + 1);
            extendBlockAt(x + 1, y + 1);
        }
    }

//    void adjustMap(int x, int y) {
//        if (x >= 1 || x <= width || y >= 1 || y <= height) {
//            return;
//        }
//        int tmpx = x, tmpy = y;
//        for (int i = 1; i < 20; i++) {
//
//        }
//    }

    void generateButtons() {
        for (int i = 0; i <= width + 1; i++) {
            for (int j = 0; j <= height + 1; j++) {
                map[i][j] = new MapItem(false);
                //map[i][j].buttonState = MapItem.State.DEFAULT;
            }
        }

        View.OnClickListener tmpOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int[] pos = (int[]) view.getTag();
                int x = pos[0];
                int y = pos[1];
                switch (gameState) {
                    case WAIT:
                        while (map[x][y].isMine() || map[x][y].mineCount != 0)
                            generateMap();
//                            adjustMap(x, y);
                        timeManagementMaster.start();
                        gameState = GameState.PLAYING;
                    case PLAYING:
                        switch (map[x][y].getButtonState()) {
                            case DEFAULT:
                                extendBlockAt(x, y);
                                break;
                            case OPENED:
                                openBlockAround(x, y);
                                //flagBlockAround(x, y);//gaoshiqing
                                break;
                            case FLAGED:
                                break;
                        }
                        break;
                    case OVER:
                        break;
                }
                //   Toast.makeText(context,Integer.toString(pos[0])+","+Integer.toString(pos[1]),Toast.LENGTH_SHORT ).show();
            }
        };
        View.OnLongClickListener tmpOnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (gameState == GameState.PLAYING) {
                    int[] pos = (int[]) view.getTag();
                    // Toast.makeText(context,Integer.toString(pos[0])+","+Integer.toString(pos[1]),Toast.LENGTH_SHORT ).show();
                    if (map[pos[0]][pos[1]].getButtonState() == MapItem.State.DEFAULT) {
                        map[pos[0]][pos[1]].setButtonState(MapItem.State.FLAGED);
                        leftflag--;
                        txtleftmines.setText(Integer.toString(leftflag));
                    } else if (map[pos[0]][pos[1]].getButtonState() == MapItem.State.FLAGED) {
                        map[pos[0]][pos[1]].setButtonState(MapItem.State.DEFAULT);
                        leftflag++;
                        txtleftmines.setText(Integer.toString(leftflag));
                    }
                }
                return true;
            }
        };

        LinearLayout parent = context.findViewById(R.id.boxLayout);
        parent.removeAllViews();
        for (int j = 1; j <= height; j++) {
            LinearLayout ln = new LinearLayout(context);
            ln.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            ll.gravity = Gravity.CENTER;
            ln.setLayoutParams(ll);

            for (int i = 1; i <= width; i++) {
                AppCompatButton b = new AppCompatButton(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(getPixelsFromDp(buttonwidth - 2), getPixelsFromDp(buttonwidth + 3));
                b.setLayoutParams(lp);
                b.setTag(new int[]{i, j});
                //b.setPadding(1, 1, 1, 1);
                // if (map[i][j].isMine)b.setText("雷");
                //   else b.setText(Integer.toString(map[i][j].getMineCount()));

                b.setOnClickListener(tmpOnclickListener);
                b.setLongClickable(true);
                b.setOnLongClickListener(tmpOnLongClickListener);
                ln.addView(b);
                map[i][j].setViewButton(b);
            }
            parent.addView(ln);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        timeManagementMaster.stop();
        super.finalize();
    }
}


