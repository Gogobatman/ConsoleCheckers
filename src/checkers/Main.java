package checkers;

import java.util.Scanner;

public class Main {
    Scanner input = new Scanner(System.in);
    private char gameMatrix[][]=new char[10][10];
    private static final int X_SIZE=8;
    private static final int Y_SIZE=8;
    private char sign;
    private String writing="White player's turn";
    private char decision;
    private int roundNumber=0;
    private int xCoordinate=80;
    private int tmpXCoordinate;
    private int tmpYCoordinate;
    private int yCoordinate=10;
    private void emptyMatrixInit(){
        char letter='A';
        char number='8';
        for(int z=1; z<X_SIZE+1; z++){
            gameMatrix[0][z]=letter++;
        }
        for(int i=0+1; i<Y_SIZE+1;i++){
            gameMatrix[i][0]=number--;
            for(int j=0+1; j<X_SIZE+1; j++){
                gameMatrix[i][j]=' ';
            }
        }
    }
    private void boardInit(){
        char signTMP='B';
        for(int i=0+1; i<Y_SIZE+1; i+=2){
            if(i==5){
                signTMP='W';
                continue;
            }else{
                for(int j=2; j<X_SIZE+1; j+=2){
                    gameMatrix[i][j]=signTMP;
                }
            }
        }
        signTMP='B';
        for(int i=2; i<Y_SIZE+1; i+=2){
            if(i==4){
                signTMP='W';
                continue;
            }else{
                for(int j=1; j<X_SIZE+1; j+=2){
                    gameMatrix[i][j]=signTMP;
                }
            }
        }
    }
    private void printBoard(){
        for(int i=0; i<Y_SIZE+1;i++){
            for(int j=0; j<X_SIZE+1; j++){
                System.out.print(gameMatrix[i][j]);
            }
            System.out.println(" ");
        }
    }
    private int invertY(int number){
        number=9-number;
        return number;
    }
    private int invertX(int number){
        number=number-64;
        return number;
    }
    private void resetCoordinates(){
        xCoordinate=80;
        yCoordinate=10;
    }
    private boolean checkIfCheckerExists(int x, int y, char c){
        if(xCoordinate==x && yCoordinate==y && gameMatrix[yCoordinate][xCoordinate]==c){
            return true;
        }else{
            return false;
        }
    }
    private boolean checkIfFieldIsEmpty(int x, int y){
        if(xCoordinate==x && yCoordinate==y && gameMatrix[yCoordinate][xCoordinate]==' '){
            return true;
        }else{
            return false;
        }
    }
    private boolean checkIfCorrectMove(int x, int y, char c){
        if((x==tmpXCoordinate-1)||(x==tmpXCoordinate+1)){
            if((sign=='W')&&(y==tmpYCoordinate-1)){
                return true;
            }
            else if((sign=='B')&&(y==tmpYCoordinate+1)){
                return true;
            }else{
                return false;
            }
        }else if((x==tmpXCoordinate+2)){
            if(((sign=='W')&&(y==tmpYCoordinate-2))&&(gameMatrix[tmpYCoordinate-1][tmpXCoordinate+1]=='B')){
                gameMatrix[tmpYCoordinate-1][tmpXCoordinate+1]=' ';
                return true;
            }
            else if(((sign=='B')&&(y==tmpYCoordinate+2))&&(gameMatrix[tmpYCoordinate+1][tmpXCoordinate+1]=='W')){
                gameMatrix[tmpYCoordinate+1][tmpXCoordinate+1]=' ';
                return true;
            }else{
                return false;
            }
        }else if((x==tmpXCoordinate-2)){
            if(((sign=='W')&&(y==tmpYCoordinate-2))&&(gameMatrix[tmpYCoordinate-1][tmpXCoordinate-1]=='B')){
                gameMatrix[tmpYCoordinate-1][tmpXCoordinate-1]=' ';
                return true;
            }
            else if(((sign=='B')&&(y==tmpYCoordinate+2))&&(gameMatrix[tmpYCoordinate+1][tmpXCoordinate-1]=='W')){
                gameMatrix[tmpYCoordinate+1][tmpXCoordinate-1]=' ';
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    private boolean askIfChangeChecker(){
        while(decision!='y' && decision!='n'){
            System.out.println("Do you want to change checker? y/n");
            decision=input.next().charAt(0);
        }
        if(decision=='y'){
            return true;
        }else{
            return false;
        }
    }
    private boolean checkIfWin(char c){
        for(int i=0+1; i<Y_SIZE+1;i++){
            for(int j=0+1; j<X_SIZE+1; j++){
                if(gameMatrix[i][j]==c){
                    return false;
                }
            }
        }
        return true;
    }
    private void enterCoordinatesAndValue(String str){
        if(roundNumber%2==0){
            sign = 'W';
            writing="White player's turn";
        }else{
            sign = 'B';
            writing="Black player's turn";
        }
        System.out.println(writing);
        while(xCoordinate<65 || xCoordinate>72){
            System.out.println("Write first coordinate A-H " + str);
            xCoordinate = input.next().charAt(0);
            break;
        }
        while(yCoordinate>8){
            System.out.println("Write second coordinate 8-1 " + str);
            yCoordinate = input.nextInt();
        }
        yCoordinate=invertY(yCoordinate);
        xCoordinate=invertX(xCoordinate);
    }
    private void doMove(){
        gameMatrix[tmpYCoordinate][tmpXCoordinate]=' ';
        gameMatrix[yCoordinate][xCoordinate]=sign;
        roundNumber++;
        printBoard();
    }
    public static void main(String[] args) {
        Main m = new Main();
        m.emptyMatrixInit();
        m.boardInit();
        m.printBoard();
        while((!(m.checkIfWin('B')))&&(!(m.checkIfWin('W')))){ //check win status
            m.enterCoordinatesAndValue("of a checker"); //enter coordinates of a checker you want to move
            if(m.checkIfCheckerExists(m.xCoordinate,m.yCoordinate,m.sign)){ //check if there is a checker on that position with specific color B or W
                m.tmpXCoordinate=m.xCoordinate;
                m.tmpYCoordinate=m.yCoordinate;
                System.out.println("Correct checker");
                while(true){
                    m.decision=' ';
                    if(m.askIfChangeChecker()){ //ask player if he wants to change checker which he selected before
                        break;
                    }
                    m.resetCoordinates();
                    m.enterCoordinatesAndValue("of checker's new destination"); //enter coordinates of selected checker's new destination
                    if((m.checkIfFieldIsEmpty(m.xCoordinate,m.yCoordinate)) &&(m.checkIfCorrectMove(m.xCoordinate,m.yCoordinate,m.sign))){ //check if field is empty and if rules allow to make the move
                        m.doMove();
                        break;
                    }
                    else{
                        System.out.println("You can't do that move!");
                    }
                }

            }else{
                System.out.println("NOT Correct checker");
            }
            m.resetCoordinates();
        }
        if(m.checkIfWin('B')){
            System.out.println("White player won");
        }else if(m.checkIfWin('W')){
            System.out.println("Black player won");
        }
    }
}