package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Huesped;

public class HuespedDao {

	private Connection connection;
	
	public HuespedDao(Connection connection) {
		this.connection = connection;
	}
//esta función que guarda una reserva
	public void guardar(Huesped huesped) {
		
		try {
			String comanSql = "INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, idReserva) VALUES (?, ?, ?, ?,?,?)";

			try (PreparedStatement prepareStm = connection.prepareStatement(comanSql, Statement.RETURN_GENERATED_KEYS)) {
				
			
				prepareStm.setString(1, huesped.getNombre());
				prepareStm.setString(2, huesped.getApellido());
				prepareStm.setDate(3, huesped.getFechaNacimiento());
				prepareStm.setString(4, huesped.getNacionalidad());
				prepareStm.setString(5, huesped.getTelefono());
				prepareStm.setInt(6, huesped.getIdReserva());

				prepareStm.execute();
				
				try(ResultSet rSet = prepareStm.getGeneratedKeys()){
						while (rSet.next()) {
							huesped.setId(rSet.getInt(1));
						}
				}
		}
	} 
		catch (SQLException e) {
		throw new RuntimeException(e);
		}
		
	}
	
	public List<Huesped> listarHuespedes() {
		List<Huesped> huespedes = new ArrayList<Huesped>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, idReserva FROM huespedes";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				transformarResultSetEnHuesped(huespedes, pstm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
//Esta función busca los huéspedes que tienen una reserva con un id específico
	public List<Huesped> buscarId(String id) {
		List<Huesped> huespedes = new ArrayList<Huesped>();
		try {

			String comSql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, idReserva FROM huespedes WHERE idReserva = ?";

			try (PreparedStatement prepareStm = connection.prepareStatement(comSql)) {
				prepareStm.setString(1, id);
				prepareStm.execute();

				transformarResultSetEnHuesped(huespedes, prepareStm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
//Esta función actualiza los datos de un huésped en la base de datos. 
	public void Actualizar(String nombre, String apellido, Date fechaN, String nacionalidad, String telefono, Integer idReserva, Integer id) {
		
		try (PreparedStatement stm = connection
				.prepareStatement("UPDATE huespedes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, nacionalidad = ?, telefono = ?, idReserva = ? WHERE id = ?")) {
			stm.setString(1, nombre);
			stm.setString(2, apellido);
			stm.setDate(3, fechaN);
			stm.setString(4, nacionalidad);
			stm.setString(5, telefono);
			stm.setInt(6, idReserva);
			stm.setInt(7, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
//Esta función elimina un huésped de la base de datos
	public void eliminar(Integer id) {
		try (PreparedStatement stm = connection.prepareStatement("DELETE FROM huespedes WHERE id = ?")) {
			stm.setInt(1, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
//Esta función convierte el resultado de una consulta SQL en una lista de objetos Huesped
	private void transformarResultSetEnHuesped(List<Huesped> reservas, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Huesped huespedes = new Huesped(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDate(4), rst.getString(5), rst.getString(6), rst.getInt(7));
				reservas.add(huespedes);
			}
		}				
	}
	
	
	
	
	
	
}
