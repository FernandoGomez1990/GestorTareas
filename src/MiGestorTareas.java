import java.lang.classfile.instruction.SwitchCase;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class MiGestorTareas {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);

        int opcionSeleccionadaMenu = 0;

        ArrayList<Tarea> tareas = new ArrayList<>();

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
                    System.out.print("\nCuales tareas desea consultar, indique la opción:"
                                    +"\n1. Todas las tareas"
                                    +"\n2. Tareas Realizadas"
                                    +"\n3. Tareas pendientes.\n");

                    try {

                        System.out.print("Seleccioné una opción de consulta: ");
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
                case 4: // Mostrar pendientes
                    int tareasSinRealizar = TareasPendientes(tareas);

                    System.out.println("\nEl numero total de tareas pendientes es: "+tareasSinRealizar);
                    PausaEnEjecucion(scan);
                    break;
                default:
                    //Creo este condicional para que separe las acciones de salir con los errores.
                    if(opcionSeleccionadaMenu != 5) {
                        System.out.println("\nEsta opción no es valida en el menú...");
                        PausaEnEjecucion(scan);
                    }
                    break;
            }

        }while (opcionSeleccionadaMenu != 5);

        System.out.println("\t\t\tSeleccionaste la opción salir, hasta pronto...");
        scan.close();
    }

    /*
    Se crea el menú principal como una función independiente
    para dejar el codigo mucho mas facil y legible
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
    Método para pausar la ejecución y permitir
    visualizar los resultados en consola.
    */
    public static void PausaEnEjecucion(Scanner scan) {
        scan.nextLine(); // limpia el buffer
        System.out.println("\nPresione Enter para continuar...");
        scan.nextLine(); // espera al usuario
    }
    /*
    Método para agregar nuevas tareas al ArrayList.
    */
    public static void AgregarTarea(ArrayList<Tarea> tareasParam,Scanner scanParam){

        scanParam.nextLine(); // limpiar buffer
        System.out.print("\nIngrese la nueva tarea: ");

        String nuevaDescripcion = scanParam.nextLine();

        Tarea nuevaTarea = new Tarea();

        nuevaTarea.idTarea = tareasParam.size() + 1;
        nuevaTarea.descripcion = nuevaDescripcion;
        nuevaTarea.completada = false;

        tareasParam.add(nuevaTarea);

        System.out.println("\n\t\tTarea agregada correctamente");
    }

    /*
    Método para consultar tareas según el filtro seleccionado.
    */
    public static void ConsultaTareas(ArrayList<Tarea> tareasParam,int opcionSeleccionConsultaParam){

        if(tareasParam.isEmpty()){
            System.out.println("\nNo existen tareas registradas.");
            return;
        }

        for(Tarea tarea : tareasParam){
            String estadoTarea;
            if(tarea.completada){
                estadoTarea = "[X]";
            } else {
                estadoTarea = "[ ]";
            }

            switch (opcionSeleccionConsultaParam){
                case 1: // Mostrar todas
                    System.out.println(tarea.idTarea + ". "+ estadoTarea+ " "+ tarea.descripcion);
                    break;
                case 2: // Mostrar completadas
                    if(tarea.completada){
                        System.out.println(tarea.idTarea + ". "+ estadoTarea+ " "+ tarea.descripcion);
                    }
                    break;
                case 3: // Mostrar pendientes
                    if(!tarea.completada){
                        System.out.println(tarea.idTarea + ". "+ estadoTarea+ " "+ tarea.descripcion);
                    }
                    break;
                default:
                    System.out.println("\nOpción inválida.");
                    return;
            }
        }
    }

    /*
    Método para marcar una tarea como completada.
    */
    public static void CompletarTarea(ArrayList<Tarea> tareasParam,Scanner scanParam){

        if(tareasParam.isEmpty()){
            System.out.println("\nNo existen tareas registradas.");
            return;
        }
        System.out.println("\nTAREAS:");
        for(Tarea tarea : tareasParam){
            String estado;
            if(tarea.completada){
                estado = "[X]";
            } else {
                estado = "[ ]";
            }

            System.out.println(tarea.idTarea + ". "+ estado+ " "+ tarea.descripcion);
        }

        try {

            System.out.print("\nIngrese el ID de la tarea a completar: ");

            int idBuscar = scanParam.nextInt();

            boolean tareaEncontrada = false;

            for(Tarea tarea : tareasParam){
                if(tarea.idTarea == idBuscar){
                    tarea.completada = true;
                    tareaEncontrada = true;
                    System.out.println("\nTarea marcada como completada.");
                    break;
                }
            }
            if(!tareaEncontrada){
                System.out.println("\nNo existe una tarea con ese ID.");
            }
        } catch (InputMismatchException e){
            System.out.println("\nError: Debe ingresar un número válido.");
            scanParam.nextLine();
        }
    }

    /*
    Método que retorna el total de tareas pendientes.
    */
    public static int TareasPendientes(ArrayList<Tarea> tareasParam){

        int totalTareasPendientes = 0;

        for(Tarea tarea : tareasParam){
            if(!tarea.completada){
                totalTareasPendientes++;
            }
        }
        return totalTareasPendientes;
    }
}