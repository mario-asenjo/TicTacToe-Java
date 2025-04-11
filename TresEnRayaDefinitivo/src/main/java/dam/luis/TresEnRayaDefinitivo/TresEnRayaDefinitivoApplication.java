package dam.luis.TresEnRayaDefinitivo;

import java.util.HashMap;
import java.util.Scanner;

public class TresEnRayaDefinitivoApplication {
	
    public static void main(String[] args) {
    	/*------------------------------------------------------------*/
    	//Inicialización de variables
    	
    	//Scanner para el manejo del input del usuario
    	Scanner myScanner = new Scanner(System.in);
    	
    	//Variables para valores del juego y del input
        int fila, columna, option;
        
        // declarar e inicializar el tablero de 3 x 3 
    	char[][] tablero = new char[3][3];
    	
    	//declaración de hashmap para la concordancia entre las coordenadas en
    	//el tablero y el input del user.
    	HashMap<Integer, int[]> concordancias = new HashMap<Integer, int[]>();	
    	
    	// establecer el caracter X para el jugador 1
        char j1 = 'X';
        // establecer el caracter O para el jugador 2
        char j2 = 'O';
        
        // Variable que guarda el jugador que está jugando ahora
        boolean jugador = true;
        
        // Variable para guardar cada caracter de usuario
        char caracter = Character.MIN_VALUE;
        
        // Variable booleana representando el estado del juego
        boolean gameOver = false;
        
        /*------------------------------------------------------------*/
        
        //Ejecución
        
    	//Inicialización del tablero vacío y de la estructura conteniendo
        //Las coordenadas asociadas al entero que va a introducir el user
        tablero = inicializarTablero(tablero);
    	concordancias = inicializarConcordancias(tablero, concordancias);
    	
        do {
            // solicitar jugada para obtener coordenadas donde poner el simbolo del jugador
            int[] coordenadas = jugarTurno(tablero, myScanner, concordancias);
            // asignamos las coordenadas a su respectiva variable
            fila = coordenadas[0];
            columna = coordenadas[1];
            // igualar el caracter al de cada jugador
            caracter = jugador ? j1 : j2;
            // Colocar la jugada 
            tablero[fila][columna] = caracter;
            // Mostrar tablero tras haber posicionado la ficha
            mostrarTablero(tablero);
            //Verificar si hay ganador
            if (hayGanador(concordancias, tablero)) {
            	System.out.println("[+] El jugador: "+caracter+" ha ganado!!!!");
            	gameOver = resetOrExit(myScanner);
            	if (!gameOver) {
            		jugador = resetGame(tablero, caracter, jugador);
            	}
            }
            // Verificar empate
            if (esEmpate(tablero)) {
            	System.out.println("[!!] Se ha empatado la partida......");
            	gameOver = resetOrExit(myScanner);
            	if (!gameOver) {
            		jugador = resetGame(tablero, caracter, jugador);
            	}
            }
            // Cambiar de jugador
            jugador = !jugador;
        } while(!gameOver);
        
    }
    
    /** 
    ** Vacía el tablero
    ** @return char[][] del tablero vacío en cada posición 
    **/
    public static char[][] inicializarTablero(char[][] tablero) {
        // aquí tu código
    	// Poner en todas las casillas del tablero un espacio
    	for (int i = 0; i < tablero.length; i++) {
    		for (int j = 0; j < tablero[0].length; j++) {
    			tablero[i][j] = ' ';
    		}
    	}
    	return tablero;
    }
    
    /** 
     ** Inicializa el hashmap de las coordenadas e input del user 
     ** @return HashMap<> de las coordenadas asociadas a los valores del input
     **/
    public static HashMap<Integer, int[]> inicializarConcordancias(char[][] tablero, HashMap<Integer, int[]> concordancias) {
    	int c = 1;
    	for (int i = 0; i < tablero.length; i++) {
    		for (int j = 0; j < tablero[0].length; j++) {
    			int[] coordenadas = {i,j};
    			concordancias.put(c, coordenadas);
    			c++;
    		}
    	}
    	return concordancias;
    }
    
    /**
    ** Muestra el tablero tal cual está en el momento actual 
    **/
    public static void mostrarTablero(char[][] tablero) {
        // aquí tu código
    	System.out.println(" "+tablero[0][0]+" | "+tablero[0][1]+" | "+tablero[0][2]+"\n"
	     		   +"---+---+---\n"
	     		   +" "+tablero[1][0]+" | "+tablero[1][1]+" | "+tablero[1][2]+"\n"
	     		   +"---+---+---\n"
	     		  +" "+tablero[2][0]+" | "+tablero[2][1]+" | "+tablero[2][2]+"\n");
    }
    
    /**
    ** Comprueba si la jugada es válida 
    ** @return true si es válida, false si no lo es
    **/
    public static boolean jugadaValida(int fila, int columna, char[][] tablero) {
        // aquí tu código
    	//Se puede devolver simplemente el booleano sin informar
    	//al usuario de posibles errores, pero creo conveniente
    	//hacerlo por lo que dejo comentada la posible solución.
    	//return ((fila >= 0 && fila <= 2) && (columna >= 0 && columna <= 2)) && (tablero[fila][columna] == ' ');
    	if (((fila >= 0 && fila <= 2) && (columna >= 0 && columna <= 2)) && (tablero[fila][columna] == ' ')) {
    		System.out.println("La casilla está vacía y está dentro de los límites del tablero....");
    		return true;
    	}else {
    		System.out.println("La casilla está ocupada o no está dentro de los límites del tablero....\nSe te va a solicitar otra casilla....");
    		return false;
    	}
    }
    
    
    //Función que recoje la lógica de todo el juego
    public static int[] jugarTurno(char[][] tablero, Scanner myScanner, HashMap<Integer, int[]> concordancias) {
    	int[] ret = new int[2];
    	int option = -1;
    	do {
        	System.out.print("Introduce un numero para la casilla (1-9): ");
        	option = myScanner.nextInt();
        	int[] coordenadas = new int[2];
        	if (option >= 1 && option <= 9) {
        		coordenadas = concordancias.get(option);
            	ret[0] = coordenadas[0];
            	ret[1] = coordenadas[1];
        	}else {
        		//Si la opción no está entre 1-9 entonces se asigna
        		//-1 para hacer saltar un error en el procesamiento de
        		//la jugada
        		ret[0] = -1;
        		ret[1] = -1;
        	}
        // validar jugada
        }while(!jugadaValida(ret[0], ret[1], tablero));
        return ret;
    }
    
    /**
    ** Comprueba si ya hay un ganador 
    ** @return true si hay ganador, false si no hay ganador
    **/
    public static boolean hayGanador(HashMap<Integer, int[]> concordancias, char[][] tablero) {
        // Comprobar filas, columnas y diagonales
    	int[][] posicionesGanadoras = {
    			{1,2,3},
    			{4,5,6},
    			{7,8,9},
    			{1,5,9},
    			{3,5,7},
    			{1,4,7},
    			{2,5,8},
    			{3,6,9}
		};
    	int[] a = new int[2];
    	int[] b = new int[2];
    	int[] c = new int[2];
    	for (int[] x : posicionesGanadoras) {
    		a = concordancias.get(x[0]);
    		b = concordancias.get(x[1]);
    		c = concordancias.get(x[2]);
    		char tableroFirstCheck = tablero[a[0]][a[1]];
    		char tableroSecondCheck = tablero[b[0]][b[1]];
    		char tableroThirdCheck = tablero[c[0]][c[1]];
    		if (tableroFirstCheck != ' ' && tableroFirstCheck == tableroSecondCheck && tableroSecondCheck == tableroThirdCheck) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
    ** Comprueba si hay empate 
    ** @return true si hay empate, false si todavía quedan casillas vacías
    **/
    public static boolean esEmpate(char[][] tablero) {
        // aquí tu código
    	for (char[] x : tablero) {
    		for (char y : x) {
    			if (y == ' ') {
    					return false;
    			}
    		}
    	}
    	return true;
    }

    public static boolean resetOrExit(Scanner myScanner) {
    	int option = -1;
    	boolean notOK = true;
    	boolean retorno = false;
    	do {
    		System.out.print("[?] ¿Queréis jugar otra vez?\n"
    					   + "[1] -> Si\n"
    		   		 	   + "[2] -> No, salir del programa\n"
    					   + "Introduce una opcion: ");
    		option = myScanner.nextInt();
    		switch (option) {
    		case 1:
    			retorno = false;
    			notOK = false;
    			break;
    		case 2:
    			retorno = true;
    			notOK = false;
    			break;
    		default:
    			System.out.println("No se reconoce la opcion intenta otra vez....");
    			notOK = true;
    		}
    	} while (notOK);
    	return retorno;
    }
    
    public static boolean resetGame(char[][] tablero, char caracter, boolean jugador) {
    	tablero = inicializarTablero(tablero);
		if (caracter == 'X') {
			jugador = !jugador;
		}
		return jugador;
    }

}
