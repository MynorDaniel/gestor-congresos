/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.CongresoInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 *
 * @author mynordma
 */
public class CongresoParametros extends Validador implements EntidadParseador<Congreso> {
    
    private final String nombre;
    private final String precioStr;
    private final String convocandoStr;
    private final String fechaStr;
    private final String fechaFinStr;
    private final String descripcion;
    private final String instalacionIdStr;
    private final String creador;
    
    public CongresoParametros(HttpServletRequest request) {
        nombre = request.getParameter("nombre");
        precioStr = request.getParameter("precio");
        convocandoStr = request.getParameter("convocando");
        fechaStr = request.getParameter("fecha");
        fechaFinStr = request.getParameter("fecha_fin");
        descripcion = request.getParameter("descripcion");
        instalacionIdStr = request.getParameter("instalacion");
        creador = ((Usuario) request.getSession(false).getAttribute("usuarioSession")).getId();
    }

    @Override
    public Congreso toEntidad() throws CongresoInvalidoException {
        if(!longitudValida(nombre, 200)) throw new CongresoInvalidoException("Verifica que el nombre tenga una longitud menor o igual a 200");
        if(!montoValido(precioStr)) throw new CongresoInvalidoException("Verifica que el precio sea un decimal positivo");
        if(!convocacionValida(convocandoStr)) throw new CongresoInvalidoException("Verifica que el valor de la convocatoria sea válido");
        if(!fechaValida(fechaStr)) throw new CongresoInvalidoException("Verifica que la fecha de inicio sea válida");
        if(!fechaValida(fechaFinStr)) throw new CongresoInvalidoException("Verifica que la fecha de finalización sea válida");
        if(!esEnteroPositivo(instalacionIdStr)) throw new CongresoInvalidoException("Verifica que la instalación sea válida");
        if(!longitudValida(creador, 30)) throw new CongresoInvalidoException("No puedes crear este congreso, vuelve a iniciar sesión");
        
        Congreso congreso = new Congreso();
        
        congreso.setNombre(nombre);
        congreso.setCreador(creador);
        congreso.setPrecio(Double.parseDouble(precioStr));
        congreso.setConvocando("true".equals(convocandoStr));
        congreso.setFechaInicio(LocalDate.parse(fechaStr));
        congreso.setFechaFin(LocalDate.parse(fechaFinStr));
        congreso.setDescripcion(descripcion);
        congreso.setActivado(true);
        congreso.setInstalacionId(Integer.parseInt(instalacionIdStr));
        
        return congreso;
    }
    
    private boolean convocacionValida(String convocandoStr) {
        return "true".equals(convocandoStr) || "false".equals(convocandoStr);
    }
}
