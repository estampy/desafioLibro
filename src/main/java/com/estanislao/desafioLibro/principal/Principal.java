package com.estanislao.desafioLibro.principal;

import com.estanislao.desafioLibro.model.Datos;
import com.estanislao.desafioLibro.model.DatosLibros;
import com.estanislao.desafioLibro.service.ConsumoAPI;
import com.estanislao.desafioLibro.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    public void muestraMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        // top 10 mas descargados
        System.out.println("TOP 10 mas descagados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                .limit(10)
                .map(l->l.titulo().toUpperCase())
                .forEach(System.out::println);
        // busqueda de libros por nombre
        System.out.println("Ingrese el nombre del libro que desea");
        var tituloLibro = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro
                .replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json,Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l->l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("libro encontrado");
            System.out.println(libroBuscado.get());
        }else{
            System.out.println("libro no encontrado");
        }

        //trabajando con estadisticas
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d->d.numeroDescargas()>0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDescargas));
        System.out.println("cantodad media de descargas: " + est.getAverage());
        System.out.println("cantidad maxima de descargas: "+ est.getMax());
        System.out.println("cantidad minima de descargas: "+ est.getMin());
        System.out.println("cantidad de registros evaluados: " + est.getCount());
    }
}
