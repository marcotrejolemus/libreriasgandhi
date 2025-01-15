package com.literatura.libreriasgandhi.principal;

import com.literatura.libreriasgandhi.service.ConsumoApiGutendex;
import com.literatura.libreriasgandhi.service.ConvertirDatos;
import com.literatura.libreriasgandhi.models.Autor;
import com.literatura.libreriasgandhi.models.Libro;
import com.literatura.libreriasgandhi.models.LibrosRespuestaApi;
import com.literatura.libreriasgandhi.models.records.DatosLibro;
import com.literatura.libreriasgandhi.repository.iAutorRepository;
import com.literatura.libreriasgandhi.repository.iLibroRepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsumoApiGutendex consumoApi = new ConsumoApiGutendex();
    private ConvertirDatos convertir = new ConvertirDatos();
    private static String API_BASE = "https://gutendex.com/books/?search=";
    private List<Libro> datosLibro = new ArrayList<>();
    private iLibroRepository libroRepository;
    private iAutorRepository autorRepository;
    public Principal(iLibroRepository libroRepository, iAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void menu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    
                    ======================================================
                   |  Librerias Gandhi un mundo de generos literarios...  |
                    ======================================================                    
                    1 - Agregar libro por titulo
                    2 - Listar libros agregados
                    3 - Buscar libro por titulo
                    4 - Buscar todos los autores de libros buscados
                    5 - Buscar autores por año
                    6 - Buscar libros por idioma
                    7 - Top 10 Libros mas descargados
                    8 - Buscar Autor por nombre
                    0 - Salir 
                    Escribe una opcion y presiona enter:                    
                    """;

            try {
                System.out.println(menu);
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("=======================================");
                System.out.println(" Amablemente escriba un numero valido.    ");
                System.out.println("=======================================\n");
                sc.nextLine();
                continue;
            }
            switch (opcion){
                case 1:
                    buscarLibroEnLaWeb();
                    break;
                case 2:
                    librosBuscados();
                    break;
                case 3:
                    buscarLibroPorNombre();
                    break;
                case 4:
                    BuscarAutores();
                    break;
                case 5:
                    buscarAutoresPorAnio();
                    break;
                case 6:
                    buscarLibrosPorIdioma();
                    break;
                case 7:
                    top10LibrosMasDescargados();
                    break;
                case 8:
                    buscarAutorPorNombre();
                    break;
                case 0:
                    System.out.println("============================");
                    System.out.println("   Salio de la aplicacion.     ");
                    System.out.println("============================\n");
                    opcion = 0;
                    break;
                default:
                    System.out.println("==============================");
                    System.out.println("      Opcion no valida.  ");
                    System.out.println("==============================\n");
                    System.out.println("Intente de nuevo");
                    menu();
                    break;
            }
        }
    }

    private Libro getDatosLibro(){
        System.out.println("Escriba el título del libro: ");
        var nombreLibro = sc.nextLine().toLowerCase();
        var json = consumoApi.obtenerDatos(API_BASE + nombreLibro.replace(" ", "%20"));
        LibrosRespuestaApi datos = convertir.convertirDatosJsonAJava(json, LibrosRespuestaApi.class);

        if (datos != null && datos.getResultadoLibros() != null && !datos.getResultadoLibros().isEmpty()) {
            DatosLibro primerLibro = datos.getResultadoLibros().get(0);
            return new Libro(primerLibro);
        } else {
            System.out.println("No se ha encontrado informacion.");
            return null;
        }
    }


    private void buscarLibroEnLaWeb() {
        Libro libro = getDatosLibro();

        if (libro == null){
            System.out.println("Titulo no encontrado. busqueda regreso vacio");
            return;
        }

        try{
            boolean libroExists = libroRepository.existsByTitulo(libro.getTitulo());
            if (libroExists){
                System.out.println("Titulo ya existente en la base de datos");
            }else {
                libroRepository.save(libro);
                System.out.println(libro.toString());
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("No se puede registrar el título buscado");
        }
    }

    @Transactional(readOnly = true)
    private void librosBuscados(){
        //datosLibro.forEach(System.out::println);
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se encontraron coincidencias de titulos.");
        } else {
            System.out.println("Coincidencias encontradas de los titulos:");
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }

    private void buscarLibroPorNombre() {
        System.out.println("Escriba titulo libro que quiere buscar: ");
        var titulo = sc.nextLine();
        Libro libroBuscado = libroRepository.findByTituloContainsIgnoreCase(titulo);
        if (libroBuscado != null) {
            System.out.println("Titulo buscado fue: " + libroBuscado);
        } else {
            System.out.println("Titulo '" + titulo + "' no se encontro.");
        }
    }

    private  void BuscarAutores(){
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No se encontraron coincidencias. \n");
        } else {
            System.out.println("Coincidencias encontradass: \n");
            Set<String> autoresUnicos = new HashSet<>();
            for (Autor autor : autores) {
                if (autoresUnicos.add(autor.getNombre())){
                    System.out.println(autor.getNombre()+'\n');
                }
            }
        }
    }

    private void  buscarLibrosPorIdioma(){
        System.out.println("Escriba el idioma a buscar: \n");
        System.out.println("###########################################");
        System.out.println("Ejemplo escriba es para titulos en español. ");
        System.out.println("###########################################\n");

        var idioma = sc.nextLine();
        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron titulos.");
        } else {
            System.out.println("Coincidencias encontradas con el idioma:");
            for (Libro libro : librosPorIdioma) {
                System.out.println(libro.toString());
            }
        }

    }

    private void buscarAutoresPorAnio() {

        System.out.println("Escriba el año a consultar de autores:: \n");
        var anioBuscado = sc.nextInt();
        sc.nextLine();

        List<Autor> autoresVivos = autorRepository.findByCumpleaniosLessThanOrFechaFallecimientoGreaterThanEqual(anioBuscado, anioBuscado);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron coincidencias de autores vivos en este año " + anioBuscado + ".");
        } else {
            System.out.println("Se encontraron coincidencias de autores vivos en este año " + anioBuscado + " son:");
            Set<String> autoresUnicos = new HashSet<>();

            for (Autor autor : autoresVivos) {
                if (autor.getCumpleanios() != null && autor.getFechaFallecimiento() != null) {
                    if (autor.getCumpleanios() <= anioBuscado && autor.getFechaFallecimiento() >= anioBuscado) {
                        if (autoresUnicos.add(autor.getNombre())) {
                            System.out.println("Autor: " + autor.getNombre());
                        }
                    }
                }
            }
        }
    }

    private void top10LibrosMasDescargados(){
        List<Libro> top10Libros = libroRepository.findTop10ByTituloByCantidadDescargas();
        if (!top10Libros.isEmpty()){
            int index = 1;
            for (Libro libro: top10Libros){
                System.out.printf("Titulo %d: %s Autor: %s Descargas: %d\n",
                        index, libro.getTitulo(), libro.getAutores().getNombre(), libro.getCantidadDescargas());
                index++;
            }

        }
    }


    private void buscarAutorPorNombre() {
        System.out.println("Escriba el nombre del autor a buscar: ");
        var escritor = sc.nextLine();
        Optional<Autor> escritorBuscado = autorRepository.findFirstByNombreContainsIgnoreCase(escritor);
        if (escritorBuscado != null) {
            System.out.println("\nEl autor buscado fue: " + escritorBuscado.get().getNombre());

        } else {
            System.out.println("\nAutor con el titulo '" + escritor + "' sin coincidencias .");
        }
    }

}
