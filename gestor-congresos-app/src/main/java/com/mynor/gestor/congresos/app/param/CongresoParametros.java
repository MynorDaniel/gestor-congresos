/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.CongresoInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.modelo.Rol;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

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
    private final String comiteStr;
    private String[] ids;
    
    public CongresoParametros(HttpServletRequest request) {
        nombre = request.getParameter("nombre");
        precioStr = request.getParameter("precio");
        convocandoStr = request.getParameter("convocando");
        fechaStr = request.getParameter("fecha");
        fechaFinStr = request.getParameter("fecha_fin");
        descripcion = request.getParameter("descripcion");
        instalacionIdStr = request.getParameter("instalacion");
        creador = ((Usuario) request.getSession(false).getAttribute("usuarioSession")).getId();
        comiteStr = request.getParameter("comiteUsuarios");
    }

    @Override
    public Congreso toEntidad() throws CongresoInvalidoException {
        if(!longitudValida(nombre, 200)) throw new CongresoInvalidoException("Verifica que el nombre tenga una longitud menor o igual a 200");
        if(!montoCongresoValido(precioStr)) throw new CongresoInvalidoException("El precio mínimo es de Q35");
        if(!convocacionValida(convocandoStr)) throw new CongresoInvalidoException("Verifica que el valor de la convocatoria sea válido");
        if(!fechaValida(fechaStr)) throw new CongresoInvalidoException("Verifica que la fecha de inicio sea válida");
        if(!fechaValida(fechaFinStr)) throw new CongresoInvalidoException("Verifica que la fecha de finalización sea válida");
        if(!esEnteroPositivo(instalacionIdStr)) throw new CongresoInvalidoException("Verifica que la instalación sea válida");
        if(!longitudValida(creador, 30)) throw new CongresoInvalidoException("No puedes crear este congreso, vuelve a iniciar sesión");
        if(!comiteValido(comiteStr)) throw new CongresoInvalidoException("Comité inválido");
        
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
        
        Participacion[] comite = new Participacion[ids.length];
        
        for (int i = 0; i < ids.length; i++) {
            Participacion miembroComite = new Participacion();
            miembroComite.setCongresoNombre(congreso.getNombre());
            miembroComite.setRol(Rol.COMITE);
            miembroComite.setUsuarioId(ids[i]);
            
            comite[i] = miembroComite;
        }
        
        congreso.setComite(comite);
        
        return congreso;
    }
    
    private boolean convocacionValida(String convocandoStr) {
        return "true".equals(convocandoStr) || "false".equals(convocandoStr);
    }
    
    private boolean comiteValido(String comiteStr){
        if(StringUtils.isBlank(comiteStr.trim())) return false;
        
        ids = comiteStr.split(",");
        
        if(ids.length < 1) {
            return false;
        }
        
        for (String id : ids) {
            System.out.println(id);
            if(!longitudValida(id, 30)){
                return false;
            }
        }
        return true;
    }
    protected boolean montoCongresoValido(String montoStr) {
        try {
            double monto = Double.parseDouble(montoStr);
            return monto >= 35;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
}
