package it.polimi.ingsw;

import java.util.Random;

import static it.polimi.ingsw.Card.NONE;
import static it.polimi.ingsw.Card.NOT;

public class CommonObj {
    private final int objNum;
    private int[] pointCount = new int[4];
    private int indexPoint = 0;
    private String descrizione ;


    /**
     *
     * @param numOfPlayer, the number of Player, is usefully for the Point set
     * Constructor of common goal: take a random number of goal and set the array-Point and the description
     */
    public CommonObj(int numOfPlayer){

        //creo un numero randomico tra 1 e 12 per definire quale obiettivo creare
        Random random = new Random();
     //   this.objNum = random.nextInt(12)+1;             //problema se esce lo stesso obiettivo per entrambi gli obiettivi ( forse posso spostare la randomicità e metterla come parametro)
        this.objNum = 5;
        switch (objNum) {
            case 1 -> this.descrizione = "6 coppie distinte di tessere adiacenti dello stesso tipo";
            case 2 -> this.descrizione = "5 tessere in diagonale dello stesso tipo";
            case 3 -> this.descrizione = "4 angoli dello stesso tipo";
            case 4 -> this.descrizione = "4 righe da 5 tessere ciascuna con non più di 3 tipi differenti";
            case 5 -> this.descrizione = "4 gruppi distinti di 4 tessere adiacenti dello stesso tipo";
            case 6 -> this.descrizione = "2 colonne di tessere ciascuna di 6 tipi diversi";
            case 7 -> this.descrizione = "2 quadrati 2x2 di tessere dello stesso tipo (entrambi i quadrati stesso tipo)";
            case 8 -> this.descrizione = "2 righe di tessere ciascuna di 5 tipi diversi";
            case 9 -> this.descrizione = "3 colonne di tessere ciascuna con non più di tre tipi diversi";
            case 10 -> this.descrizione = "5 tessere dello stesso tipo che formano una X";
            case 11 -> this.descrizione = "8 tessere dello stesso tipo (nessuna restrizione sulla posizione)";
            case 12 -> this.descrizione = "cinque colonne di altezza crescente (+1 ad ogni colonna) a partire dagli estremi (destra o sinistra)";
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
     *
     * @return objNum, the identification number of the goal
     */
    public int getObjNum() {
        return objNum;
    }

    /**
     *
     * @return descrizione, the description of the goal
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     *
     * @return Points, the points to give for complete the goal
     */
    public int getPointCount() {
        setPoint();
        return pointCount[indexPoint-1];
    }

    /**
     * Method that update the array point when someone complete the goal
     */
    private void setPoint(){
        indexPoint++;
    }

    /**
     *
     * @param lib, library to check for the goal
     * @return result, the result of the check of the goal
     * Method that check in a personalized way every goal and return the result
     */
    public boolean checkObj(Library lib) throws Exception{
        switch (this.objNum) {
         /*   case 1 -> {

                /*
                 * 6 coppie distinte di tessere adiacenti dello stesso tipo
                 *
                 * scorro la matrice della libreria
                 * controllo le coppie di tessere
                 * se trovo controllo che non contenga posizioni già segnate
                 * se passa il check aumento il contatore e segno la posizione più avanti
                 *
                 *

                int count1 = 0;

                int[] checkOld = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
                int i;

                for (int riga = 0; riga < 5; riga++) {

                    for (int colonna = 0; colonna < 5; colonna++) {

                        if (lib.getPos(riga, colonna) != Card.NONE) {

                            outer1 : if (lib.getPos(riga, colonna) == lib.getPos(riga + 1, colonna)) {

                                for (i=0; i<30; i=i+2){
                                    if (checkOld[i]==-1) break;
                                    if ((riga == checkOld[i]) && (colonna ==checkOld [i+1])) break outer1;
                                    if ((riga + 1 == checkOld[i]) && (colonna ==checkOld [i+1])) break outer1;

                                }

                                checkOld[i] = riga + 1;
                                checkOld[i + 1] = colonna;
                                count1++;

                            }else outer2 : if (lib.getPos(riga, colonna) == lib.getPos(riga, colonna + 1)){

                                for (i=0; i<30; i=i+2){

                                    if (checkOld[i]==-1) break;
                                    if ((riga == checkOld[i]) && (colonna ==checkOld [i+1])) break outer2;
                                    if ((riga== checkOld[i]) && (colonna + 1 ==checkOld [i+1])) break outer2;

                                }

                                checkOld[i] = riga;
                                checkOld[i + 1] = colonna + 1;
                                count1++;

                            }
                        }
                    }
                }
                return count1 >= 6;
            }   */
            case 1 -> {
       /*         // set the temporally grid
                int[][] grid = new int[6][5];
                int count = 0;

                for(int i = 0; i < 6; i++){
                    for(int j = 0; j < 5; j++){
                        grid[i][j] = 0;
                    }
                }

                // check row couple
                for(int i = 0; i < 6; i++){
                    for(int j = 0; j < 4; j++){
                        if(lib.getPos(i, j) != NONE && (lib.getPos(i, j) == lib.getPos(i, j + 1) && (grid[i][j] == 0 && grid[i][j+1] == 0))){
                            grid[i][j] = 1;
                            grid[i][j+1] = 1;
                            count ++;
                        }
                    }
                }

                // check column couple
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 5; j++){
                        if(lib.getPos(i, j) != NONE && (lib.getPos(i, j) == lib.getPos(i+1, j) && (grid[i][j] == 0 && grid[i+1][j] == 0))){
                            grid[i][j] = 1;
                            grid[i+1][j] = 1;
                            count ++;
                        }
                    }
                }

                if(count >= 6){
                    return true;
                }
                return false;   */
                Library temp = new Library(lib);
                int count = 0;
                for(int i = 0; i < 6; i++){
                    for(int j = 0; j < 5; j++){
                        if(temp.getPos(i, j) != NONE){
                            temp.i = 0;
                            temp.group(i, j, j, temp.getPos(i, j));
                            if(temp.i >= 2){
                                count ++;
                            }
                        }
                    }
                }
                if(count >= 6){
                    return true;
                }
            }
            case 2 -> {
                /*
                 * 5 tessere in diagonale dello stesso tipo
                 *
                 * 4 casi possibili
                 * due diagonali destre e due sinistre
                 * 4 for che escono quando non trovano corrispondenza
                 * ed ritornano subito true se completano il for
                 */

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
                    if (lib.getPos(i, 4 - i) != lib.getPos(i + 1, 4 - i - 1 )) break;
                }
                for (int i = 0; i < 5; i++) {
                    if (i == 4) return true;
                    if ((lib.getPos(i + 1, 4 - i) == NONE)) break;
                    if (lib.getPos(i + 1, 4 - i) != lib.getPos(i + 2, 4 - i  - 1)) break;
                }
                return false;
            }
            case 3 -> {
                /*
                 * 4 angoli dello stesso tipo
                 *
                 * controllo i 4 angoli
                 */

                return ((lib.getPos(0, 0) == lib.getPos(0, 4)) &&
                        (lib.getPos(0, 4) == lib.getPos(5, 4)) &&
                        (lib.getPos(5, 4) == lib.getPos(5, 0)) &&
                        (lib.getPos(0, 0) != NONE));
            }
            case 4 -> {
                /*
                 * 4 righe da 5 tessere ciascuna con non più di 3 tipi differenti
                 *
                 * 2 for
                 * uno scorre le righe e l'altro le colonne
                 * per ogni riga controllo che non ci siano più di tre tessere diverse
                 * salvando i tipi che incontro fino a 3, altrimenti passo alla prossima riga
                 * conto che abbia successo almeno 4 volte
                 */

                int count4 = 0;
                Card[] countType = {NONE, NONE, NONE};

                for (int riga = 0; riga < 6; riga++) {
                    for (int i=0; i<3; i++){
                        countType[i]= NONE;
                    }

                    outer: for (int colonna = 0; colonna < 5; colonna++) {

                        if (lib.getPos(riga, colonna) == NONE) break;

                        for (int i=0; i<3; i++){

                            if(countType[i]== NONE) {

                                countType[i] = lib.getPos(riga, colonna);
                                break;
                            }

                            else if(lib.getPos(riga,colonna) == countType[i]) break;

                            else if (( i == 2 ) && (lib.getPos(riga,colonna) != countType[i])) break outer;
                        }
                        if (colonna == 4)count4++;
                    }
                }
                return count4 >= 4;
            }

            case 5 ->{
                /*
                 * 4 gruppi distinti di 4 tessere adiacenti dello stesso tipo
                 *
                 * scorro la matrice e catalogo tutte le tessere mettendole nei rispettivi contatori
                 * cerco tra le tessere uguali gruppi di tessere adiacenti
                 * utilizzo metodi privati per gestire le caselle già utilizzate in gruppi dello stesso tipo e l'adiacenza delle caselle
                 */

    /*            int count5 = 0;
                int[] posChecked = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
                int ic;     //indexChecked

                int countWHITE = 0;
                int [] posWHITE = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
                int countBLUE = 0;
                int [] posBLUE = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
                int countLIGHTBLUE = 0;
                int [] posLIGHTBLUE = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
                int countGREEN = 0;
                int [] posGREEN = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
                int countYELLOW = 0;
                int [] posYELLOW = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
                int countPURPLE = 0;
                int [] posPURPLE = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

                for (int riga=0; riga<6; riga++){
                    for (int colonna=0; colonna<5; colonna++){
                        switch (lib.getPos(riga,colonna)){
                            case WHITE ->{
                                posWHITE[countWHITE]=riga;
                                posWHITE[countWHITE+1]=colonna;
                                countWHITE += 2;
                            }
                            case BLUE ->{
                                posBLUE[countBLUE]=riga;
                                posBLUE[countBLUE+1]=colonna;
                                countBLUE += 2;
                            }
                            case LIGHTBLUE ->{
                                posLIGHTBLUE[countLIGHTBLUE]=riga;
                                posLIGHTBLUE[countLIGHTBLUE+1]=colonna;
                                countLIGHTBLUE += 2;
                            }
                            case GREEN ->{
                                posGREEN[countGREEN]=riga;
                                posGREEN[countGREEN+1]=colonna;
                                countGREEN += 2;
                            }
                            case YELLOW ->{
                                posYELLOW[countYELLOW]=riga;
                                posYELLOW[countYELLOW+1]=colonna;
                                countYELLOW += 2;
                            }
                            case PURPLE ->{
                                posPURPLE[countPURPLE]=riga;
                                posPURPLE[countPURPLE+1]=colonna;
                                countPURPLE += 2;
                            }
                        }
                    }
                }

// non serve dato che è il primo
//                for (int i=0; i<56; i++){
//                    if (posChecked[i]==-1) break;
//                    posChecked[i]=-1;
//                }

                ic=0;

                for (int i = 0; i<countWHITE; i += 2){
                    outer : for (int j = i+2; j<countWHITE; j += 2){
                        if ((checkNearObj5(posWHITE[i],posWHITE[i+1],posWHITE[j],posWHITE[j+1]))&&(!checkOldObj5(posWHITE[j],posWHITE[j+1],posChecked,ic))){
                            for (int k = 0; k<countWHITE; k += 2){
                                if ((checkNearObj5(posWHITE[j],posWHITE[j+1],posWHITE[k],posWHITE[k+1]))&&(!checkSameObj5(posWHITE[i],posWHITE[i+1],posWHITE[k],posWHITE[k+1]))&&(!checkOldObj5(posWHITE[k],posWHITE[k+1],posChecked,ic))){
                                    for (int w = 0; w<countWHITE; w += 2){
                                        if ((checkNearObj5(posWHITE[k],posWHITE[k+1],posWHITE[w],posWHITE[w+1]))&&(!checkSameObj5(posWHITE[j],posWHITE[j+1],posWHITE[w],posWHITE[w+1]))&&(!checkOldObj5(posWHITE[w],posWHITE[w+1],posChecked,ic))){
                                            count5++;
                                            ic= setCheckedObj5(posWHITE[i],posWHITE[i+1],posWHITE[j],posWHITE[j+1],posWHITE[k],posWHITE[k+1],posWHITE[w],posWHITE[w+1],posChecked,ic);
                                            break outer;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                for (int i=0; i<56; i++){
                    if (posChecked[i]==-1) break;
                    posChecked[i]=-1;
                }
                ic=0;

                for (int i = 0; i<countBLUE; i += 2){
                    outer:for (int j = i+2; j<countBLUE; j += 2){
                        if ((checkNearObj5(posBLUE[i],posBLUE[i+1],posBLUE[j],posBLUE[j+1]))&&(!checkOldObj5(posBLUE[j],posBLUE[j+1],posChecked,ic))){
                            for (int k = 0; k<countBLUE; k += 2){
                                if ((checkNearObj5(posBLUE[j],posBLUE[j+1],posBLUE[k],posBLUE[k+1]))&&(!checkSameObj5(posBLUE[i],posBLUE[i+1],posBLUE[k],posBLUE[k+1]))&&(!checkOldObj5(posBLUE[k],posBLUE[k+1],posChecked,ic))){
                                    for (int w = 0; w<countBLUE; w += 2){
                                        if ((checkNearObj5(posBLUE[k],posBLUE[k+1],posBLUE[w],posBLUE[w+1]))&&(!checkSameObj5(posBLUE[j],posBLUE[j+1],posBLUE[w],posBLUE[w+1]))&&(!checkOldObj5(posBLUE[w],posBLUE[w+1],posChecked,ic))){
                                            count5++;
                                            ic= setCheckedObj5(posBLUE[i],posBLUE[i+1],posBLUE[j],posBLUE[j+1],posBLUE[k],posBLUE[k+1],posBLUE[w],posBLUE[w+1],posChecked,ic);
                                            break outer;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                for (int i=0; i<56; i++){
                    if (posChecked[i]==-1) break;
                    posChecked[i]=-1;
                }
                ic=0;

                for (int i = 0; i<countLIGHTBLUE; i += 2){
                    outer:for (int j = i+2; j<countLIGHTBLUE; j += 2){
                        if ((checkNearObj5(posLIGHTBLUE[i],posLIGHTBLUE[i+1],posLIGHTBLUE[j],posLIGHTBLUE[j+1]))&&(!checkOldObj5(posLIGHTBLUE[j],posLIGHTBLUE[j+1],posChecked,ic))){
                            for (int k = 0; k<countLIGHTBLUE; k += 2){
                                if ((checkNearObj5(posLIGHTBLUE[j],posLIGHTBLUE[j+1],posLIGHTBLUE[k],posLIGHTBLUE[k+1]))&&(!checkSameObj5(posLIGHTBLUE[i],posLIGHTBLUE[i+1],posLIGHTBLUE[k],posLIGHTBLUE[k+1]))&&(!checkOldObj5(posLIGHTBLUE[k],posLIGHTBLUE[k+1],posChecked,ic))){
                                    for (int w = 0; w<countLIGHTBLUE; w += 2){
                                        if ((checkNearObj5(posLIGHTBLUE[k],posLIGHTBLUE[k+1],posLIGHTBLUE[w],posLIGHTBLUE[w+1]))&&(!checkSameObj5(posLIGHTBLUE[j],posLIGHTBLUE[j+1],posLIGHTBLUE[w],posLIGHTBLUE[w+1]))&&(!checkOldObj5(posLIGHTBLUE[w],posLIGHTBLUE[w+1],posChecked,ic))){
                                            count5++;
                                            ic= setCheckedObj5(posLIGHTBLUE[i],posLIGHTBLUE[i+1],posLIGHTBLUE[j],posLIGHTBLUE[j+1],posLIGHTBLUE[k],posLIGHTBLUE[k+1],posLIGHTBLUE[w],posLIGHTBLUE[w+1],posChecked,ic);
                                            break outer;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                for (int i=0; i<56; i++){
                    if (posChecked[i]==-1) break;
                    posChecked[i]=-1;
                }
                ic=0;

                for (int i = 0; i<countGREEN; i += 2){
                    outer:for (int j = i+2; j<countGREEN; j += 2){
                        if ((checkNearObj5(posGREEN[i],posGREEN[i+1],posGREEN[j],posGREEN[j+1]))&&(!checkOldObj5(posGREEN[j],posGREEN[j+1],posChecked,ic))){
                            for (int k = 0; k<countGREEN; k += 2){
                                if ((checkNearObj5(posGREEN[j],posGREEN[j+1],posGREEN[k],posGREEN[k+1]))&&(!checkSameObj5(posGREEN[i],posGREEN[i+1],posGREEN[k],posGREEN[k+1]))&&(!checkOldObj5(posGREEN[k],posGREEN[k+1],posChecked,ic))){
                                    for (int w = 0; w<countGREEN; w += 2){
                                        if ((checkNearObj5(posGREEN[k],posGREEN[k+1],posGREEN[w],posGREEN[w+1]))&&(!checkSameObj5(posGREEN[j],posGREEN[j+1],posGREEN[w],posGREEN[w+1]))&&(!checkOldObj5(posGREEN[w],posGREEN[w+1],posChecked,ic))){
                                            count5++;
                                            ic= setCheckedObj5(posGREEN[i],posGREEN[i+1],posGREEN[j],posGREEN[j+1],posGREEN[k],posGREEN[k+1],posGREEN[w],posGREEN[w+1],posChecked,ic);
                                            break outer;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                for (int i=0; i<56; i++){
                    if (posChecked[i]==-1) break;
                    posChecked[i]=-1;
                }
                ic=0;

                for (int i = 0; i<countYELLOW; i += 2){
                    outer:for (int j = i+2; j<countYELLOW; j += 2){
                        if ((checkNearObj5(posYELLOW[i],posYELLOW[i+1],posYELLOW[j],posYELLOW[j+1]))&&(!checkOldObj5(posYELLOW[j],posYELLOW[j+1],posChecked,ic))){
                            for (int k = 0; k<countYELLOW; k += 2){
                                if ((checkNearObj5(posYELLOW[j],posYELLOW[j+1],posYELLOW[k],posYELLOW[k+1]))&&(!checkSameObj5(posYELLOW[i],posYELLOW[i+1],posYELLOW[k],posYELLOW[k+1]))&&(!checkOldObj5(posYELLOW[k],posYELLOW[k+1],posChecked,ic))){
                                    for (int w = 0; w<countYELLOW; w += 2){
                                        if ((checkNearObj5(posYELLOW[k],posYELLOW[k+1],posYELLOW[w],posYELLOW[w+1]))&&(!checkSameObj5(posYELLOW[j],posYELLOW[j+1],posYELLOW[w],posYELLOW[w+1]))&&(!checkOldObj5(posYELLOW[w],posYELLOW[w+1],posChecked,ic))){
                                            count5++;
                                            ic= setCheckedObj5(posYELLOW[i],posYELLOW[i+1],posYELLOW[j],posYELLOW[j+1],posYELLOW[k],posYELLOW[k+1],posYELLOW[w],posYELLOW[w+1],posChecked,ic);
                                            break outer;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                for (int i=0; i<56; i++){
                    if (posChecked[i]==-1) break;
                    posChecked[i]=-1;
                }
                ic=0;

                for (int i = 0; i<countGREEN; i += 2){
                    outer:for (int j = i+2; j<countGREEN; j += 2){
                        if ((checkNearObj5(posPURPLE[i],posPURPLE[i+1],posPURPLE[j],posPURPLE[j+1]))&&(!checkOldObj5(posPURPLE[j],posPURPLE[j+1],posChecked,ic))){
                            for (int k = 0; k<countGREEN; k += 2){
                                if ((checkNearObj5(posPURPLE[j],posPURPLE[j+1],posPURPLE[k],posPURPLE[k+1]))&&(!checkSameObj5(posPURPLE[i],posPURPLE[i+1],posPURPLE[k],posPURPLE[k+1]))&&(!checkOldObj5(posPURPLE[k],posPURPLE[k+1],posChecked,ic))){
                                    for (int w = 0; w<countGREEN; w += 2){
                                        if ((checkNearObj5(posPURPLE[k],posPURPLE[k+1],posPURPLE[w],posPURPLE[w+1]))&&(!checkSameObj5(posPURPLE[j],posPURPLE[j+1],posPURPLE[w],posPURPLE[w+1]))&&(!checkOldObj5(posPURPLE[w],posPURPLE[w+1],posChecked,ic))){
                                            count5++;
                                            ic= setCheckedObj5(posPURPLE[i],posPURPLE[i+1],posPURPLE[j],posPURPLE[j+1],posPURPLE[k],posPURPLE[k+1],posPURPLE[w],posPURPLE[w+1],posChecked,ic);
                                            break outer;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return count5 >=4;  */
                Library temp = new Library(lib);
                int count = 0;
                for(int i = 0; i < 6; i++){
                    for(int j = 0; j < 5; j++){
                        if(temp.getPos(i, j) != NONE){
                            temp.i = 0;
                            temp.group(i, j, j, temp.getPos(i, j));
                            if(temp.i >= 4){
                                count ++;
                            }
                        }
                    }
                }
                if(count >= 4){
                    return true;
                }
            }

            case 6 -> {
                /*
                 * 2 colonne di tessere ciascuna di 6 tipi diversi (tutti i tipi)
                 *
                 * 3 for
                 * un per colonne e due per righe
                 * ad ogni posizione controllo che sia diversa da tutte le altre nella colonna
                 * se trovo uguali esco dai cicli di riga (outer)
                 * se trovo almeno 2 con successo ritorno true
                 */

                int count6 = 0;
                for (int colonna = 0; colonna < 5; colonna++) {

                    outer:
                    for (int riga1 = 0; riga1 < 6; riga1++) {

                        if (lib.getPos(riga1, colonna) == NONE) break;

                        for (int riga2 = riga1 + 1; riga2 < 6; riga2++) {

                            if (lib.getPos(riga1, colonna) == lib.getPos(riga2, colonna)) break outer;
                        }

                        if (riga1 == 4) count6++;
                    }
                }
                return count6 >= 2;
            }

            case 7 ->{
                /*
                 * 2 quadrati 2x2 di tessere dello stesso tipo (//entrambi i quadrati stesso tipo, cambia il return)
                 *
                 * 2 for per scorrere la matrice
                 * controllo un quadrato alla volta
                 * controllo che non abbia preso pezzi già utilizzati
                 *
                 * mi segno le coordinate della casella in basso a sinistra di ogni quadrato, in modo da non contare doppio
                 * aumento il contatore del tipo adeguato
                 */

                int count7WHITE = 0;
                int count7BLUE = 0;
                int count7LIGHTBLUE = 0;
                int count7YELLOW = 0;
                int count7GREEN = 0;
                int count7PURPLE = 0;

                int[] checkOld = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
                int i;

                for (int riga=0; riga<6; riga++){

                    outer : for (int colonna=0; colonna<5; colonna++){

                        if(lib.getPos(riga,colonna) != NONE){

                            if((lib.getPos(riga,colonna) == lib.getPos(riga,colonna+1) )&&
                                    (lib.getPos(riga,colonna) == lib.getPos(riga+1,colonna)) &&
                                    (lib.getPos(riga,colonna) == lib.getPos(riga+1,colonna+1))){

                                for (i=0; i<12; i=i+2){

                                    if (checkOld[i]==-1) break;
                                    if ((riga == checkOld[i]) && (colonna ==checkOld [i+1])) break outer;
                                    if ((riga+1 == checkOld[i]) && (colonna ==checkOld [i+1])) break outer;
                                    if ((riga == checkOld[i]) && (colonna+1 ==checkOld [i+1])) break outer;
                                    if ((riga+1 == checkOld[i]) && (colonna+1 ==checkOld [i+1])) break outer;
                                }

                                checkOld[i]= riga+1;
                                checkOld[i+1]= colonna+1;

                                switch (lib.getPos(riga,colonna)){
                                    case WHITE -> count7WHITE++;
                                    case BLUE -> count7BLUE++;
                                    case LIGHTBLUE -> count7LIGHTBLUE++;
                                    case YELLOW -> count7YELLOW++;
                                    case GREEN -> count7GREEN++;
                                    case PURPLE -> count7PURPLE++;
                                }

                            }
                        }
                    }
                }
//                if (count7WHITE >= 2) return true;
//                else if (count7BLUE >= 2) return true;
//                else if (count7LIGHTBLUE >= 2) return true;
//                else if (count7YELLOW >= 2) return true;
//                else if (count7GREEN >= 2) return true;
//                else return count7PURPLE >= 2;

                return count7WHITE+count7BLUE+count7LIGHTBLUE+count7YELLOW+count7GREEN+count7PURPLE >=2;
            }

            case 8 -> {
                /*
                 * 2 righe di tessere ciascuna di 5 tipi diversi
                 *
                 * analogo al 6
                 */

                int count8 = 0;
                for (int riga = 0; riga < 6; riga++) {
                    outer:for (int colonna1 = 0; colonna1 < 5; colonna1++) {
                        if (lib.getPos(riga, colonna1) == NONE) break;
                        for (int colonna2 = 0; colonna2 < 5; colonna2++) {
                            if (lib.getPos(riga, colonna1) == lib.getPos(riga, colonna2) && colonna1 != colonna2) break outer;
                        }
                        if (colonna1 == 4) count8++;
                    }
                }
                return count8 >= 2;
            }

            case 9 -> {
                /*
                 * 3 colonne di tessere ciascuna con non più di tre tipi diversi
                 *
                 * analogo al 4
                 */

                int count9 = 0;
                Card[] countType = {NONE, NONE, NONE};

                for (int colonna = 0; colonna < 5; colonna++) {
                    for (int i=0; i<3; i++){
                        countType[i]= NONE;
                    }
                    outer: for (int riga = 0; riga < 6; riga++) {

                        if (lib.getPos(riga, colonna) == NONE) break;

                        for (int i=0; i<3; i++){

                            if(countType[i]== NONE) {

                                countType[i] = lib.getPos(riga, colonna);
                                break;
                            }

                            else if(lib.getPos(riga,colonna) == countType[i]) break;

                            else if (( i == 2 ) && (lib.getPos(riga,colonna) != countType[i])) break outer;
                        }
                        if (riga == 5) count9++;
                    }
                }
                return count9 >= 3;
            }

            case 10 -> {
                /*
                 * 5 tessere dello stesso tipo che formano una X
                 *
                 * scorro alla ricerca di una X tutta uguale
                 */

                for (int riga = 0; riga < 4; riga++) {
                    for (int colonna = 0; colonna < 3; colonna++) {
                        if ((lib.getPos(riga, colonna) != NONE) &&
                                (lib.getPos(riga, colonna) == lib.getPos(riga + 1, colonna + 1)) &&
                                (lib.getPos(riga, colonna) == lib.getPos(riga + 2, colonna + 2)) &&
                                (lib.getPos(riga, colonna) == lib.getPos(riga, colonna + 2)) &&
                                (lib.getPos(riga, colonna) == lib.getPos(riga + 2, colonna))) {
                            return true;
                        }
                    }
                }
                return false;
            }
            case 11 -> {
                /*
                 * 8 tessere dello stesso tipo (nessuna restrizione sulla posizione)
                 *
                 * scorro la matrice e conto le tessere uguali
                 */

                int count11WHITE = 0;
                int count11BLUE = 0;
                int count11LIGHTBLUE = 0;
                int count11YELLOW = 0;
                int count11GREEN = 0;
                int count11PURPLE = 0;
                for (int riga = 0; riga < 6; riga++) {
                    for (int colonna = 0; colonna < 5; colonna++) {
                        switch (lib.getPos(riga, colonna)) {
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

            case 12 -> {
                /*
                 * cinque colonne di altezza crescente (+1 ad ogni colonna) a partire dagli estremi (destra o sinistra)
                 *
                 * 4 for,2 destra e 2 sinistra
                 * guardo la riga prima e la colonna dopo che siano NONE e che la pos corr sia non vuota
                 */

                for (int i = 0; i < 5; i++) {
                    if (i == 0){
                        if (lib.getPos(i, i) == NONE) break;
                    }else if (i == 4) return true;

                    else if ((lib.getPos(i, i) == NONE) || (lib.getPos(i - 1, i) != NONE) || (lib.getPos(i, i + 1) != NONE)) break;
                }
                for (int i = 0; i < 5; i++) {

                    if (i == 4) return true;

                    else if ((lib.getPos(i + 1, i) == NONE) || (lib.getPos(i, i) != NONE) || (lib.getPos(i + 1, i + 1) != NONE) ) break;
                }
                for (int i = 0; i < 5; i++) {

                    if (i == 0){

                        if (lib.getPos(i,4 - i) == NONE) break;

                    }else if (i == 4) return true;

                    else if ((lib.getPos(i, 4 - i) == NONE) || (lib.getPos(i - 1, 4 - i) != NONE) || (lib.getPos(i, 4 - i - 1) != NONE)) break;
                }
                for (int i = 0; i < 5; i++) {

                    if (i == 4) return true;

                    else if ((lib.getPos(i + 1, 4 - i) == NONE) || (lib.getPos(i, 4 - i) != NONE) || (lib.getPos(i + 1, 4 - i - 1) != NONE)) break;
                }
                return false;
            }
        }
        return false;           // non dovrebbe mai andare qui
//        System.out.println("errore");
    }


    /**
     *
     * @param x1, x-position Card 1
     * @param y1, y-position Card 1
     * @param x2, x-position Card 2
     * @param y2, y-position Card 2
     * @return result, if Card 1 and 2 are near
     */
    private boolean checkNearObj5(int x1, int y1, int x2, int y2) {
        return (x1 == x2) && (y1 + 1 == y2) || (x1 + 1 == x2) && (y1 == y2) ||
                (x1 == x2) && (y1 - 1 == y2) || (x1 - 1 == x2) && (y1 == y2);
    }

    /**
     *
     * @param x1, x-position Card 1
     * @param y1, y-position Card 1
     * @param x2, x-position Card 2
     * @param y2, y-position Card 2
     * @return result, if Card 1 and 2 are the same Card
     */
    private boolean checkSameObj5(int x1, int y1, int x2, int y2){
        return (x1 == x2) && (y1 == y2);
    }

    /**
     *
     * @param x1, x-position Card 1
     * @param y1, y-position Card 1
     * @param x2, x-position Card 2
     * @param y2, y-position Card 2
     * @param x3, x-position Card 3
     * @param y3, y-position Card 3
     * @param x4, x-position Card 4
     * @param y4, y-position Card 4
     * @param old, the collection of position already used
     * @param iOld, the length of the collection
     * @return iOldNew, the new length of the collection
     * Method that update the Old collection
     */
    private int setCheckedObj5(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int[] old, int iOld){

        old[iOld]=x1;
        old[iOld+1]=y1;
        old[iOld+2]=x2;
        old[iOld+3]=y2;
        old[iOld+4]=x3;
        old[iOld+5]=y3;
        old[iOld+6]=x4;
        old[iOld+7]=y4;
        return iOld+8;
    }

    /**
     *
     * @param x1, x-position Card
     * @param y1, y-position Card
     * @param old, the collection of position already used
     * @param iOld, the length of the collection
     * @return result, if Card is already in Old collection
     */
    private boolean checkOldObj5(int x1, int y1, int[] old, int iOld){

        for (int i = 0; i<iOld; i += 2){
            if ((x1==old[i]) && (y1==old[i+1]))return true;
        }
        return false;
    }
}

