/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.AfiliacionBD;
import com.mynor.gestor.congresos.app.basededatos.CongresoBD;
import com.mynor.gestor.congresos.app.basededatos.InstitucionBD;
import com.mynor.gestor.congresos.app.basededatos.PagoBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.CongresoInvalidoException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Afiliacion;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosCongreso;
import com.mynor.gestor.congresos.app.modelo.Pago;

/**
 *
 * @author mynordma
 */
public class ManejadorDeCongresos extends Manejador {
    
    private final CongresoBD congresoBD;
    
    public ManejadorDeCongresos(){
        this.congresoBD = new CongresoBD();
    }

    public Congreso crearCongreso(Congreso congresoNuevo) throws CongresoInvalidoException, UsuarioInvalidoException, AccesoDeDatosException {
        //Validar duplicidad
        if(existeCongreso(congresoNuevo)) throw new CongresoInvalidoException("Ya existe un congreso con el mismo nombre");
        
        //Verificar el rol
        if(!esAdminDeCongresos(congresoNuevo.getCreador())) throw new UsuarioInvalidoException("No puedes crear congresos");
        
        //Verificar que el usuario tenga una institucion
        //...
        
        //Verificar fechas logicas
        if(!fechasLogicas(congresoNuevo.getFechaInicio(), congresoNuevo.getFechaFin())) throw new UsuarioInvalidoException("La fecha inicial debe ser anterior a la fecha final");
        
        //Verificar que la instalacion no este ocupada
        if(instalacionOcupada(congresoNuevo)) throw new UsuarioInvalidoException("La instalación esta siendo ocupada");
        
        //Asignar institucion
        AfiliacionBD afiliacionBD = new AfiliacionBD();
        Afiliacion afiliacion = afiliacionBD.leerPorUsuario(congresoNuevo.getCreador())[0];
        InstitucionBD institucionBD = new InstitucionBD();
        congresoNuevo.setInstitucion(institucionBD.leerPorId(afiliacion.getInstitucion().getId()));
        
        //Crear el congreso
        congresoBD.crear(congresoNuevo);
        
        return congresoNuevo;
    }

    private boolean instalacionOcupada(Congreso congresoNuevo) throws AccesoDeDatosException {
        FiltrosCongreso filtros = new FiltrosCongreso();
        filtros.setInstalacion(congresoNuevo.getInstalacionId());
        filtros.setFechaInicio(congresoNuevo.getFechaInicio());
        filtros.setFechaFin(congresoNuevo.getFechaFin());
        
        Congreso[] coincidencias = congresoBD.leer(filtros);
        
        return coincidencias.length > 0;
    }

    public Congreso obtenerCongreso(String nombre) throws AccesoDeDatosException {
        FiltrosCongreso filtros = new FiltrosCongreso();
        filtros.setNombre(nombre);
        Congreso congreso = congresoBD.leer(filtros)[0];
        InstitucionBD institucionBD = new InstitucionBD();
        congreso.setInstitucion(institucionBD.leerPorId(congreso.getInstitucionId()));
        
        return congreso;
    }

    public Congreso[] obtenerCongresos(String creador) throws AccesoDeDatosException {
        FiltrosCongreso filtros = new FiltrosCongreso();
        filtros.setCreador(creador);
        InstitucionBD institucionBD = new InstitucionBD();
        Congreso[] congresos = congresoBD.leer(filtros);
        for (Congreso congreso : congresos) {
            congreso.setInstitucion(institucionBD.leerPorId(congreso.getInstitucionId()));
        }
        return congresos;
    }

    public Congreso[] obtenerCongresos() throws AccesoDeDatosException {
        InstitucionBD institucionBD = new InstitucionBD();
        Congreso[] congresos = congresoBD.leer(new FiltrosCongreso());
        for (Congreso congreso : congresos) {
            congreso.setInstitucion(institucionBD.leerPorId(congreso.getInstitucionId()));
        }
        return congresos;
    }

    private boolean existeCongreso(Congreso congresoNuevo) throws AccesoDeDatosException {
        FiltrosCongreso filtros = new FiltrosCongreso();
        filtros.setNombre(congresoNuevo.getNombre());
        return congresoBD.leer(filtros).length > 0;
    }

    public Congreso[] obtenerCongresos(FiltrosCongreso filtros) throws AccesoDeDatosException {
        if(filtros.getFechaInicio() != null && filtros.getFechaFin() != null){
            if(!fechasLogicas(filtros.getFechaInicio(), filtros.getFechaFin())) throw new AccesoDeDatosException("Verifica que la fecha final sea despues de la inicial");
        }
        return congresoBD.leer(filtros);
    }

    public Pago[] obtenerPagos(String nombre) throws AccesoDeDatosException {
        return (new PagoBD()).leerPorCongreso(nombre);
    }

    public Congreso[] ordenarPorGanancias(Congreso[] congresos) {
        Congreso[] resultado = congresos.clone();

        for (int i = 0; i < resultado.length - 1; i++) {
            for (int j = 0; j < resultado.length - i - 1; j++) {
                double gananciasJ = calcularGanancia(resultado[j]);
                double gananciasJ1 = calcularGanancia(resultado[j + 1]);

                if (gananciasJ < gananciasJ1) {
                    Congreso temp = resultado[j];
                    resultado[j] = resultado[j + 1];
                    resultado[j + 1] = temp;
                }
            }
        }

        return resultado;
    }

    private double calcularGanancia(Congreso c) {
        double ganancia = 0;
        if (c.getPagos() != null) {
            for (Pago p : c.getPagos()) {
                ganancia += p.getMonto() * p.getComisionCobrada();
            }
        }
        return ganancia;
    }

    public void actualizarConvocatoria(Congreso congreso) throws AccesoDeDatosException {
        congresoBD.actualizarConvocatoria(congreso);
    }

    
}