import java.util.Random;
import java.util.Scanner;
public class XOGamefinal {

    //переменные (поля)
    static final int SIZE = 5;
    static final char DOT_X = 'X'; //-крестик
    static final char DOT_O = 'O'; //- нолик
    static final char DOT_field = '.'; //-пустое поле
    static char[][] map; //- поле это массив точек
    static Scanner sc = new Scanner(System.in);//для вводимого хода человека
    static Random random = new Random(); //для рандомного хода компьютера

    static final int dotsToWin = 5;//количество элементов победной линии

    //поток для игры:
    public static void main(String[] args) {

        //начальный ход инициализация поля
        initMap();
        //распечатаем
        printMap();

        while (true) {
            humanTurn();
            printMap();
            //добавляем проверку на заполненность всех ячеек поля
// сначала проверка на победу
            if (checkWin(DOT_X,dotsToWin)){
                System.out.println("You win!");
                break;
            }

            if (isFull()) {
                System.out.println("DRAW!");
                break;
            }


            aiTurn();
            printMap();

            if (checkWin(DOT_O,dotsToWin)){
                System.out.println("Computer win!");
                break;
            }
            if (isFull()) {
                System.out.println("DRAW!");
                break;
            }

        }


    }
    //класс для методов

    //инциализация поля
    public static void initMap() {
//нужно создать объект массив размером SIZExSIZE
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_field;
            }
        }
    }

    //метод для распечатывания поля
    public static void printMap() {
        System.out.print("  "); //в нулевой строке нулевого столбца 2 пробела
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");//вывели номера элементов в нулевой строке
        }
        System.out.println();//перешли на следующую строку
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%c ", map[i][j]); //для вывода переменной символьного типа char в методе printf используется управляющий символ %c
            }
            System.out.println();// в конце каждой строки i (с набором элементов j в ней) переходим на новую строку и повторяем алгоритм
        }
    }

    //ход игрока
    public static void humanTurn() {
        //будем вводить координаты
        int x, y; //-переменные обозначающие вводимые координаты
//используем в методе цикл do-while чтобы человек вводил координаты пока не введет верные
        //пример с паролем аккаунта

        do {
            System.out.println("Введите x и y: ");
            y = sc.nextInt() - 1;//ссылка на объект (sc) типа Scanner (создали в классе XO) через точку вызывает метод nextInt() который позволяет вводить числа в пределах int
            //-1 потому что нумерация элементов массива идет с 0, а мы считаем с 1
            x = sc.nextInt() - 1;
            //добавим метод валидности вводимых значений, чтобы исключить ввод отрицательных чисел и т.п.
        } while (!isCellValid(y, x));//будем вводить x и y пока
        map[y][x] = DOT_X; //как только выходим из цикла элементу вводимого поля присваиваем значение Х
    }

    //наша проверка валидности значений будет от обратного, если условия НЕ выполнятся,
// значит можно будет присвоить Х или О вводимому элементу массива
    //добавим проверку в условие хода человека
    public static boolean isCellValid(int y, int x) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            return false;
        }
        return map[y][x] == DOT_field;
        //если значения <0 или >SIZE возвращаем false (ложное выражение)
        //если значения true (верны) - элементу массива присваиваем значение символа поля (пустого)
    }

    //ход компьютера (пока рандом)
    public static void aiTurn() {
        int x, y;


//продумаем логику хода компьютера, чтобы перекрывал очевидные ходы, когда уже 4 в ряд стоит например

      //сначала пробуем победить компьютером просто прогоняя метод по всем ячейкам массива, после каждого хода проверяет победную линию
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
               if(isCellValid(i,j)){ //если ячейка валидна, присваиваем ей значение "О" и проверяем выполняется ли метод победной линии
                   map[i][j]=DOT_O;
                   if(checkWin(DOT_O, dotsToWin)){ //если выполняется метод победной линии выходим из цикла через команду return (уточнить, не понял)
                       return;
                   }
                   map[i][j]=DOT_field;//если условия не выполняются при выходе из цикла присваиваем ячейке значение пустого поля DOT_field
               }
                }
            }


        //теперь добавим метод который будет ставить свой символ, когда у человека остается 1 ход до победы
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
              //суть метода в том что компьютер присваивает всем ячейкам значение X и если в этом случае выполняется победа человека, тогда на это место ставится ячейка О
                if(isCellValid(i,j)){
                    if(checkWin(DOT_X,dotsToWin)){
                        map[i][j]=DOT_O;
                        return;//нужен для выхода из метода
                    }
                    map[i][j] =DOT_field;//выходим из условия при не выполнении и присваиваем ячейке обратно значение пустого поля
                }
            }
        }

//        теперь добавим метод который будет ставить свой символ, когда у человека остается 1 ход до победы
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(isCellValid(i,j)){
                    map[i][j]=DOT_X;
                    if(checkWin(DOT_X,dotsToWin-1)&& Math.random()<0.5){
                        map[i][j]=DOT_O;
                        return;//выход из метода
                    }
                    map[i][j]=DOT_field;
                }
            }
        }

                do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);//посмотри реализацию класса nextInt(int bond) должен быть параметр который вводится
        } while (!isCellValid(y, x));
        map[y][x] = DOT_O;//как только выходим из цикла элементу вводимого поля присваиваем значение 0
        }


    //нужно чтобы игра заканчивалась если все поля заняты, метод ничья
    //метод будет логический да/нет
    public static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_field) {
                    return false;
                }
            }
        }
        return true;
    }
//т.е. если каждый из элементов массива равен элементу пустого поля DOT_field тогда вернем false, иначе true

    //победный алгоритм
    public static boolean checkLine(int y, int x, int vary, int varx, char symbol, int dotsToWin) {
        if (x + varx * (dotsToWin - 1) > SIZE - 1 ||
                y + vary * (dotsToWin - 1) > SIZE - 1 ||
                y + vary * (dotsToWin - 1) < 0) {
            return false;
        }
        //проверка чтобы в самой победной линии были одинаковые победные символы Х или О

        for (int i = 0; i < dotsToWin; i++) {
            if (map[y + i * vary][x + i * varx] != symbol) {
                return false;
            }
        }
        return true;
    }

//непосредственно проверка победы при условии выполнения метода checkLine()
    public static boolean checkWin(char symbol, int dotsToWin) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkLine(i, j, -1, 1, symbol, dotsToWin)||
                        checkLine(i, j, 1, 1, symbol, dotsToWin)||
                        checkLine(i, j, 0, 1, symbol, dotsToWin)||
                        checkLine(i, j, -1, 0, symbol, dotsToWin)
                        ) {
                    return  true;
                }
            }
        }
        return  false;
    }

}