package it.polimi.ingsw;

import java.util.EnumSet;

import static it.polimi.ingsw.Card.*;

public class CommonObj {
    private final int objNum;
    private final int[] pointCount = new int[4];
    private int indexPoint = 0;
    private String description;

    /**
     * @param numOfPlayer, the number of Player, is usefully for the Point set
     *                     Constructor of common goal: take a random number of goal and set the array-Point and the description
     */
    public CommonObj(int numOfPlayer, int objNum) {

        this.objNum = objNum;
        switch (objNum) {
            case 1 -> this.description = "6 coppie distinte di tessere adiacenti dello stesso tipo";
            case 2 -> this.description = "5 tessere in diagonale dello stesso tipo";
            case 3 -> this.description = "4 angoli dello stesso tipo";
            case 4 -> this.description = "4 righe da 5 tessere ciascuna con non più di 3 tipi differenti";
            case 5 -> this.description = "4 gruppi distinti di 4 tessere adiacenti dello stesso tipo";
            case 6 -> this.description = "2 colonne di tessere ciascuna di 6 tipi diversi";
            case 7 -> this.description = "2 quadrati 2x2 di tessere dello stesso tipo (entrambi i quadrati stesso tipo)";
            case 8 -> this.description = "2 righe di tessere ciascuna di 5 tipi diversi";
            case 9 -> this.description = "3 colonne di tessere ciascuna con non più di tre tipi diversi";
            case 10 -> this.description = "5 tessere dello stesso tipo che formano una X";
            case 11 -> this.description = "8 tessere dello stesso tipo (nessuna restrizione sulla posizione)";
            case 12 -> this.description = "cinque colonne di altezza crescente (+1 ad ogni column) a partire dagli estremi (destra o sinistra)";
        }

        switch (numOfPlayer) {
            case 2 -> {
                this.pointCount[0] = 8;
                this.pointCount[1] = 4;
                this.pointCount[2] = 0;
                this.pointCount[3] = 0;
            }
            case 3 -> {
                this.pointCount[0] = 8;
                this.pointCount[1] = 6;
                this.pointCount[2] = 4;
                this.pointCount[3] = 0;
            }
            default -> {
                this.pointCount[0] = 8;
                this.pointCount[1] = 6;
                this.pointCount[2] = 4;
                this.pointCount[3] = 2;
            }
        }
    }

    /**
     * @return objNum, the identification number of the goal
     */
    public int getObjNum() {
        return objNum;
    }

    /**
     * @return descrizione, the description of the goal
     */
    public String getDescrizione() {
        return description;
    }

    /**
     * @return Points, the points to give for complete the goal
     */
    public int getPointCount() {
        indexPoint ++;
        return pointCount[indexPoint - 1];
    }

    /**
     * @param lib, library to check for the goal
     * @return result, the result of the check of the goal
     * Method that checks in a personalized way every goal and returns the result
     */
    public boolean checkObj(Library lib) throws Exception {
        switch (this.objNum) {
            case 1 -> {return check1(lib);}
            case 2 -> {return check2(lib);}
            case 3 -> {return check3(lib);}
            case 4 -> {return check4(lib);}
            case 5 -> {return check5(lib);}
            case 6 -> {return check6(lib);}
            case 7 -> {return check7(lib);}
            case 8 -> {return check8(lib);}
            case 9 -> {return check9(lib);}
            case 10 -> {return check10(lib);}
            case 11 -> {return check11(lib);}
            case 12 -> {return check12(lib);}
            default -> throw new Exception();
        }
    }

    private boolean check1(Library lib) {
        Library temp = new Library(lib);
        int count = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (temp.getPos(i, j) != NONE) {
                    temp.resetI();
                    temp.group(i, j, j, temp.getPos(i, j));
                    if (temp.getI() >= 2) {
                        count++;
                    }
                }
            }
        }
        return count >= 6;
    }

    private boolean check2(Library lib) {
        for (int i = 0; i < 5; i++) {
            if (i == 4) return true;
            if ((lib.getPos(i, i) == NONE)) break;
            if (lib.getPos(i, i) != lib.getPos(i + 1, i + 1)) break;
        }
        for (int i = 0; i < 5; i++) {
            if (i == 4) return true;
            if ((lib.getPos(i + 1, i) == NONE)) break;
            if (lib.getPos(i + 1, i) != lib.getPos(i + 2, i + 1)) break;
        }
        for (int i = 0; i < 5; i++) {
            if (i == 4) return true;
            if ((lib.getPos(i, 4 - i) == NONE)) break;
            if (lib.getPos(i, 4 - i) != lib.getPos(i + 1, 4 - i - 1)) break;
        }
        for (int i = 0; i < 5; i++) {
            if (i == 4) return true;
            if ((lib.getPos(i + 1, 4 - i) == NONE)) break;
            if (lib.getPos(i + 1, 4 - i) != lib.getPos(i + 2, 4 - i - 1)) break;
        }
        return false;

    }

    private boolean check3(Library lib) {
        return ((lib.getPos(0, 0) == lib.getPos(0, 4)) &&
                (lib.getPos(0, 4) == lib.getPos(5, 4)) &&
                (lib.getPos(5, 4) == lib.getPos(5, 0)) &&
                (lib.getPos(0, 0) != NONE));
    }

    private boolean check4(Library lib) {
        int count4 = 0;
        Card[] countType = {NONE, NONE, NONE};

        for (int row = 0; row < 6; row++) {
            for (int i = 0; i < 3; i++) {
                countType[i] = NONE;
            }

            outer:
            for (int column = 0; column < 5; column++) {

                if (lib.getPos(row, column) == NONE) break;

                for (int i = 0; i < 3; i++) {

                    if (countType[i] == NONE) {

                        countType[i] = lib.getPos(row, column);
                        break;
                    } else if (lib.getPos(row, column) == countType[i]) break;

                    else if ((i == 2) && (lib.getPos(row, column) != countType[i])) break outer;
                }
                if (column == 4) count4++;
            }
        }
        return count4 >= 4;
    }

    private boolean check5(Library lib) {
        Library temp = new Library(lib);
        int count = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (temp.getPos(i, j) != NONE) {
                    temp.resetI();
                    temp.group(i, j, j, temp.getPos(i, j));
                    if (temp.getI() >= 4) {
                        count++;
                    }
                }
            }
        }
        return count >= 4;
    }

    private boolean check6(Library lib) {
        int count6 = 0;
        for (int column = 0; column < 5; column++) {

            outer:
            for (int row1 = 0; row1 < 6; row1++) {

                if (lib.getPos(row1, column) == NONE) break;

                for (int row2 = row1 + 1; row2 < 6; row2++) {

                    if (lib.getPos(row1, column) == lib.getPos(row2, column)) break outer;
                }

                if (row1 == 4) count6++;
            }
        }
        return count6 >= 2;
    }


    private boolean check7(Library lib) {
        Card[][] temp=new Card[8][7];
        int count=0;
        for (int k=0;k<8;k++){
            for (int h=0;h<7;h++){
                temp[k][h]=NONE;
            }
        }
        for (int k=1;k<7;k++){
            for (int h=1;h<6;h++){
                temp[k][h]=lib.getPos(k-1,h-1);
            }
        }
        for(Card i : EnumSet.range(WHITE,PURPLE)){
            for (int k=1;k<7;k++){
                for (int h=1;h<6;h++){
                    if(temp[k][h]==i && temp[k][h+1]==i && temp[k+1][h]==i && temp[k+1][h+1]==i){
                        temp[k][h]=NONE;
                        temp[k][h+1]=NONE;
                        temp[k+1][h]=NONE;
                        temp[k+1][h+1]=NONE;
                        boolean borders=(
                                    temp[k-1][h]!=i && temp[k-1][h+1]!=i && temp[k+2][h]!=i && temp[k+2][h+1]!=i &&
                                            temp[k][h-1]!=i && temp[k+1][h-1]!=i && temp[k][h+2]!=i && temp[k+1][h+2]!=i
                                );
                        if(borders){
                            count++;
                        }
                    }
                }
            }
        }
        if (count==2){
            return true;
        }
        return false;
    }


    private boolean check8(Library lib) {
        int count8 = 0;
        for (int row = 0; row < 6; row++) {
            outer:
            for (int column1 = 0; column1 < 5; column1++) {
                if (lib.getPos(row, column1) == NONE) break;
                for (int column2 = 0; column2 < 5; column2++) {
                    if (lib.getPos(row, column1) == lib.getPos(row, column2) && column1 != column2) break outer;
                }
                if (column1 == 4) count8++;
            }
        }
        return count8 >= 2;
    }

    private boolean check9(Library lib) {
        int count9 = 0;
        Card[] countType = {NONE, NONE, NONE};
        for (int column = 0; column < 5; column++) {
            for (int i = 0; i < 3; i++) {
                countType[i] = NONE;
            }
            outer:
            for (int row = 0; row < 6; row++) {

                if (lib.getPos(row, column) == NONE) break;
                for (int i = 0; i < 3; i++) {
                    if (countType[i] == NONE) {
                        countType[i] = lib.getPos(row, column);
                        break;
                    } else if (lib.getPos(row, column) == countType[i]) break;

                    else if ((i == 2) && (lib.getPos(row, column) != countType[i])) break outer;
                }
                if (row == 5) count9++;
            }
        }
        return count9 >= 3;
    }

    private boolean check10(Library lib) {
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 3; column++) {
                if ((lib.getPos(row, column) != NONE) &&
                        (lib.getPos(row, column) == lib.getPos(row + 1, column + 1)) &&
                        (lib.getPos(row, column) == lib.getPos(row + 2, column + 2)) &&
                        (lib.getPos(row, column) == lib.getPos(row, column + 2)) &&
                        (lib.getPos(row, column) == lib.getPos(row + 2, column))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check11(Library lib) {
        int count11WHITE = 0;
        int count11BLUE = 0;
        int count11LIGHTBLUE = 0;
        int count11YELLOW = 0;
        int count11GREEN = 0;
        int count11PURPLE = 0;
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 5; column++) {
                switch (lib.getPos(row, column)) {
                    case WHITE -> count11WHITE++;
                    case BLUE -> count11BLUE++;
                    case LIGHTBLUE -> count11LIGHTBLUE++;
                    case YELLOW -> count11YELLOW++;
                    case GREEN -> count11GREEN++;
                    case PURPLE -> count11PURPLE++;
                }
            }
        }
        if (count11WHITE >= 8) return true;
        else if (count11BLUE >= 8) return true;
        else if (count11LIGHTBLUE >= 8) return true;
        else if (count11YELLOW >= 8) return true;
        else if (count11GREEN >= 8) return true;
        else return count11PURPLE >= 8;
    }

    private boolean check12(Library lib) {
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                if (lib.getPos(i, i) == NONE) break;
            } else if (i == 4) return true;
            else if ((lib.getPos(i, i) == NONE) || (lib.getPos(i - 1, i) != NONE) || (lib.getPos(i, i + 1) != NONE))
                break;
        }
        for (int i = 0; i < 5; i++) {
            if (i == 4) return true;
            else if ((lib.getPos(i + 1, i) == NONE) || (lib.getPos(i, i) != NONE) || (lib.getPos(i + 1, i + 1) != NONE))
                break;
        }
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                if (lib.getPos(i, 4 - i) == NONE) break;
            } else if (i == 4) return true;
            else if ((lib.getPos(i, 4 - i) == NONE) || (lib.getPos(i - 1, 4 - i) != NONE) || (lib.getPos(i, 4 - i - 1) != NONE))
                break;
        }
        for (int i = 0; i < 5; i++) {
            if (i == 4) return true;
            else if ((lib.getPos(i + 1, 4 - i) == NONE) || (lib.getPos(i, 4 - i) != NONE) || (lib.getPos(i + 1, 4 - i - 1) != NONE))
                break;
        }
        return false;
    }
}
