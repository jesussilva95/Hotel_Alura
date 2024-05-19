package controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import dao.ReservaDao;
import factory.ConnectionFactory;
import model.Reserva;

public class ReservasController {
	private ReservaDao reservaDao;
	
	public ReservasController() {
		Connection connection = new ConnectionFactory().RecuperarConexion();
		this.reservaDao = new ReservaDao(connection);
	}

	public void guardar(Reserva reserva) {
		this.reservaDao.guardar(reserva);
	}
		
	public List<Reserva> buscar() {
		return this.reservaDao.buscar();
	}
	
	public List<Reserva> buscarId(String id) {
		return this.reservaDao.buscarId(id);
	}
	public void actualizar(Date fechaE, Date fechaS, String valor, String formaPago, Integer id) {
		this.reservaDao.Actualizar(fechaE, fechaS, valor, formaPago, id);
	}
	
	public void Eliminar(Integer id) {
		this.reservaDao.Eliminar(id);
	}
}
