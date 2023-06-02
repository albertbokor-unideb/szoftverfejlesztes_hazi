package boardgame;

import boardgame.model.BoardGameModel;
import boardgame.model.Position;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        System.out.print("""
                Egy 4 × 4 mezőből  álló tábla mezőire 16 darab kavicsot helyezünk, minden
                mezőre egyet-egyet. A két játékos felváltva vehet le a tábláról kavicsokat,
                legalább 1 és legfeljebb 4 darabot. Csak olyan kavicsokat lehet levenni,\s
                amelyek egy sorban vagy egy oszlopban vannak,  és nincs közöttük üres mező. Az
                veszít, aki utoljára lép.

                a kiválasztott mezőket `x1 y1 x2 y2...` formában adja meg (x=oszlop, y=sor)!
                kilépni `q`-val tud
                """);
        var model = new BoardGameModel();

        Scanner input = new Scanner(System.in);
        while(!model.isGameover()){
            System.out.println(model);
            while(true) {
                switch (model.getCurrentPlayer()){
                    case PLAYER_1 -> System.out.println("Játékos_1: ");
                    case PLAYER_2 -> System.out.println("Játékos_2: ");
                }
                String line = input.nextLine();
                if(line.equalsIgnoreCase("q")){
                    System.exit(0);
                }
                Scanner lineScanner = new Scanner(line);
                ArrayList<Position> coordinateList = new ArrayList<>();
                int newRow;
                int newCol;
                while(lineScanner.hasNextInt()){
                    newRow=lineScanner.nextInt()-1;
                    if(lineScanner.hasNextInt()){
                        newCol=lineScanner.nextInt()-1;
                        coordinateList.add(new Position(newRow,newCol));
                    }else{
                        break;
                    }
                }

                if(model.canSelect(coordinateList)){
                    model.takeFromBoard(coordinateList);
                    break;
                }
            }
        }
        System.out.println(model);
        switch (model.getCurrentPlayer()){
            case PLAYER_1 -> System.out.println("Játékos_2 vesztett.");
            case PLAYER_2 -> System.out.println("Játékos_1 vesztett.");
        }
    }
}
