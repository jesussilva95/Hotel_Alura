package controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import dao.HuespedDao;
import factory.ConnectionFactory;
import model.Huesped;

public class HuespedesController {
	
	HuespedDao huespedDao;
	
		
	public HuespedesController() {
		Connection connection = new ConnectionFactory().RecuperarConexion();
		this.huespedDao = new HuespedDao(connection);
	}
	
	public void Guardar(Huesped huespedes) {
		this.huespedDao.guardar(huespedes);
	}

	public List<Huesped> listarHuespedes(){
		return this.huespedDao.listarHuespedes();
	}
	
	public List<Huesped> listarHuespedesId(String id) {
		return this.huespedDao.buscarId(id);
	}
	
	public void actualizar(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, Integer IdReserva, Integer id) {
		this.huespedDao.Actualizar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, IdReserva, id);
	}
	
	
	public void Eliminar(Integer id) {
		this.huespedDao.eliminar(id);		
	}

}
