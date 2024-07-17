
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Map {
    int width;
    int num_mine;
    int[][] mineMap;
    int[][] displayMap;
    private boolean[][] revealed;
    HashMap<Integer, Integer> minePosition;

    public Map(int width, int num_mine) {
        this.width = width;
        this.num_mine = num_mine;
        this.mineMap = new int[width][width];
        this.revealed = new boolean[width][width];

        // create map
        System.out.println("Create  "+ width+" X "+ width + "  map");
        mineMap = new int[width][width];
        displayMap = new int[width][width];
        for (int i=0; i<width*width; i++) {
            mineMap[i/width][i%width] = 0;
            displayMap[i/width][i%width] = 0;
        }

        // create mines
        System.out.println("Create  "+num_mine+"  mines");
        Random r = new Random();
        minePosition = new HashMap<>();
        for (int i = 0; i < num_mine; i++) {
            int position = r.nextInt(width * width);
            while (minePosition.containsValue(position))   // check repetition
                position = r.nextInt(width * width);
            minePosition.put(i, position);
        }

        // deploy mines
        System.out.println("mine positions");
        for (int i = 0; i < num_mine; i++) {
            int x = minePosition.get(i) / width;
            int y = minePosition.get(i) % width;
            System.out.println(x+", "+y);
            mineMap[x][y] = 1;
        }

        printMap(mineMap);

    }

    public String getMapStatus() {
        StringBuilder status = new StringBuilder();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                status.append(displayMap[i][j]); // 각 칸의 상태를 문자열에 추가
                if (j < width - 1) {
                    status.append(","); // 칸 사이에 쉼표 추가
                }
            }
            if (i < width - 1) {
                status.append(","); // 행 사이에 쉼표 추가
            }
        }
        return status.toString();
    }

    public int checkMine(int x, int y) {
        if (mineMap[x][y] == 1) {
            return 1; // 지뢰를 찾음
        } else {
            return -1; // 지뢰가 없음
        }
    }



    public void printMap(int[][] a) {
        System.out.println();
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i][0]);
            for (int j = 1; j < a[0].length; j++)
                System.out.print(" " + a[i][j]);
            System.out.println();
        }
    }

    public void updateMap(int x, int y) {
        displayMap[x][y]=1;//      printMap(displayMap);
        mineMap[x][y] = 0; // mineMap 업데이트
    }

    public boolean isGameOver() {
        int countRevealed = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (revealed[i][j]) {
                    countRevealed++;
                }
            }
        }
        return countRevealed == (width * width - num_mine);
    }

    public int getTile(int x, int y) {
        return revealed[y][x] ? mineMap[y][x] : -2;
    }

}