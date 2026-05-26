import java.lang.classfile.instruction.SwitchCase;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MiGestorTareas {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);

        int opcionSeleccionadaMenu = 0;

        HashMap<String, Boolean> tareas = new HashMap<>();

        tareas.put("Actvidad6", false);
        tareas.put("Tutoria", false);
        tareas.put("EvaluacionCurso", true);

        //System.out.println(tareas.keySet());

        do {
            menu();
            //Implemento Try - catch para evitar error si selecciona una letra en vez de un numero.
            try {
                System.out.print("\nSeleccioné una opción del menú: ");
                opcionSeleccionadaMenu = scan.nextInt();
            } catch (InputMismatchException e) {

                System.out.println("\nError: Debe ingresar solo números.");

                scan.nextLine(); // limpiar buffer

                opcionSeleccionadaMenu = 0;
            }

            switch (opcionSeleccionadaMenu){
                case 1: //Agregar tarea
                    AgregarTarea(tareas, scan);
                    break;
                case 2: //Consultar Tareas
                    int opcionSeleccionConsulta = 0;
                    System.out.print("\nCuales tareas desea consultar, indique la opción: 1. Todas las tareas, 2. Tareas Realizadas, 3. Tareas pendientes.\n");

                    try {
                        opcionSeleccionConsulta = scan.nextInt();

                        ConsultaTareas(tareas, opcionSeleccionConsulta);
                    } catch (InputMismatchException e) {

                        System.out.println("\nError: Debe ingresar un número válido.");

                        scan.nextLine(); // limpiar buffer
                    }

                    PausaEnEjecucion(scan);
                    break;
                case 3: //Completar tareas
                    CompletarTarea(tareas, scan);
                    break;
                case 4:
                    int tareasSinRealizar =0;
                    tareasSinRealizar = TareasPendientes(tareas);
                    System.out.println("\nEl numero total de tareas pendientes es: "+tareasSinRealizar);
                    PausaEnEjecucion(scan);
                    break;
                default:
                    //Creo este condicional para que separe las acciones de salir con los errores.
                    if(opcionSeleccionadaMenu != 5) {
                        System.out.println("\nEsta opcion no es valida en el menú");
                        PausaEnEjecucion(scan);
                    }
                    break;
            }

        }while (opcionSeleccionadaMenu != 5);

        System.out.println("\t\t\tSeleccionaste la opción salir, hasta pronto...");
        scan.close();
    }
    /*
    Se crea el menú principal como una función independiente para dejar el codigo mucho mas facil y legible
    */
    public static void menu(){
        System.out.println("\t\tMENÚ PRINCIPAL");
        System.out.println("1. Agregar nueva tarea");
        System.out.println("2. Ver tareas");
        System.out.println("3. Marcar tarea completada");
        System.out.println("4. Mostrar total de tareas pendientes");
        System.out.println("5. Salir del programa");
    }
    /*
    Creo este método con la intención de usar un separador entre el menú y los resultados
    */
    public static void PausaEnEjecucion(Scanner scan) {
        scan.nextLine(); // limpia el buffer
        System.out.println("\nPresione Enter para continuar...");
        scan.nextLine(); // espera al usuario
    }
    /*
    Creo este método para hacer la consulta de las tareas con tres variaciones simplificando el código.
    */
    public  static void ConsultaTareas(HashMap<String, Boolean> tareasParam, int opcionSeleccionConsultaParam){
        for(String tarea : tareasParam.keySet()) {
            boolean completada = tareasParam.get(tarea);

            String estadoTarea = "";
            if(completada){
                estadoTarea = "Completada";
            }
            else {
                estadoTarea = "Pendiente";
            }
            switch (opcionSeleccionConsultaParam) {
                //Mostrar todas
                case 1:
                    System.out.println(tarea + " - " + estadoTarea);
                    break;
                //Mostrar realizadas
                case 2:
                    if (completada) {
                        System.out.println(tarea + " - "+estadoTarea);
                    }
                    break;
                //Mostrar pendientes
                case 3:
                    if (!completada) {
                        System.out.println(tarea + " - "+estadoTarea);
                    }
                    break;
                default:
            }
        }
        /*Hago un condicional por que no quiero que valide la opción del menú y solo muestre el mensaje de opción
        invalidad una única vez cuando la opción del menú no corresponda a una de las opciones.
        */
        if(opcionSeleccionConsultaParam < 1 || opcionSeleccionConsultaParam > 3){
            System.out.println("\n\t\tOpción invalida");
        }
    }

    /*
    Creación de un método para incorporar información nueva, se requiere para simplificar el código y
    reutilizar la función en otros posibles escenarios.
    */
    public static void AgregarTarea(HashMap<String, Boolean> tareasParam,Scanner scanParam){
        scanParam.nextLine(); // limpiar buffer

        System.out.print("Ingrese la nueva tarea: ");

        String nuevaTarea = scanParam.nextLine();

        tareasParam.put(nuevaTarea, false);

        System.out.println("\n\t\tTarea agregada correctamente");
    }
    /*
    Creo este método para marcar las tareas terminadas, lo dejo como un método para que el Main quede más limpio y los
    procesos de mantenimiento sean más fáciles en el futuro
    */
    public static void CompletarTarea(HashMap<String, Boolean> tareasParam, Scanner scanParam){
        // Mostrar tareas
        System.out.println("\nTAREAS:");

        for(String tarea : tareasParam.keySet()){
            System.out.println("- " + tarea);
        }

        scanParam.nextLine(); // limpiar buffer

        // Pedir tarea
        System.out.print("\nIngrese el nombre de la tarea a completar: ");

        String tareaCompletar = scanParam.nextLine();

        // Verificar si existe
        if(tareasParam.containsKey(tareaCompletar)){

            tareasParam.put(tareaCompletar, true);

            System.out.println("Tarea marcada como completada");

        }else{

            System.out.println("La tarea no existe");
        }
    }
    /*
    Creo un método que me retorna el numero de tareas pendientes, se usara con múltiples funciones, la primera como
    limpieza del código Main, la segunda en múltiples funciones y toma de decisiones futuras.
    */
    public static  int TareasPendientes(HashMap<String, Boolean> tareasParam){

        int totalTareasPendientes = 0;

        for(String tarea : tareasParam.keySet()){
            boolean completada = tareasParam.get(tarea);

            if(!completada){
                totalTareasPendientes ++;
            }
        }

        return totalTareasPendientes;
    }
}