package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Reserva;

public class ReservaDao {

	private Connection connection;
	
	public ReservaDao(Connection connection) {
		this.connection = connection;
	}
	
	// metodo para guardar en el enviamos los datos que el usuario nos suministra para guardar en la bse de datos
	public void guardar(Reserva reserva) {
		try {
			String sql = "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, formaPago)"
					+ " VALUES (?, ?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, 
					Statement.RETURN_GENERATED_KEYS)) {

				pstm.setDate(1, reserva.getFechaE());
				pstm.setDate(2, reserva.getFechaS());
				pstm.setString(3, reserva.getValor());
				pstm.setString(4, reserva.getFormaPago());

				pstm.executeUpdate();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						reserva.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
//Esta función busca una reserva por su id y devuelve un objeto Reserva con los datos de la reserva.
	public List<Reserva> buscar() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String comSql = "SELECT id, fecha_entrada, fecha_salida,"
					+ " valor, formaPago FROM reservas WHERE id = ?";
			try (PreparedStatement prepareSTM = connection.prepareStatement(comSql)){
				prepareSTM.execute();
				
				transformarResultSetEnReserva(reservas, prepareSTM);
				
			} return reservas;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
//La función sigue haciendo lo mismo que antes, pero ahora es más flexible y reutilizable
	public List<Reserva> buscarId(String id) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {

			String comSql = "SELECT id, fecha_entrada, fecha_salida, valor, formaPago FROM reservas WHERE id = ?";

			try (PreparedStatement prepareSTM = connection.prepareStatement(comSql)) {
				prepareSTM.setString(1, id);
				prepareSTM.execute();

				transformarResultSetEnReserva(reservas, prepareSTM);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
//Esta función actualiza los datos de una reserva en la base de datos. Recibe los nuevos valores de los 
//atributos de la reserva y el id de la reserva que se quiere modificar. 
	
	public void Actualizar(Date fechaEntrada, Date fechaSalida, String valor, String formaPago, Integer id) {
		try (PreparedStatement prepareSTM = connection
				.prepareStatement("UPDATE reservas SET fecha_entrada = ?, fecha_salida = ?, valor = ?, formaPago = ? WHERE id = ?")) {
			prepareSTM.setDate(1, fechaEntrada);
			prepareSTM.setDate(2, fechaSalida);
			prepareSTM.setString(3, valor);
			prepareSTM.setString(4, formaPago);
			prepareSTM.setInt(5, id);
			prepareSTM.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
//Esta función elimina una reserva de la base de datos por medio de ID 
	public void Eliminar(Integer id) {
		try (PreparedStatement prepareSTM = connection.prepareStatement("DELETE FROM reservas WHERE id = ?")) {
			prepareSTM.setInt(1, id);
			prepareSTM.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
//Esta función convierte el resultado de una consulta SQL en una lista de objetos Reserva
	private void transformarResultSetEnReserva(List<Reserva> reservas, PreparedStatement pstm) 
			throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Reserva objReserva = new Reserva(rst.getInt(1), rst.getDate(2),rst.getDate(3), rst.getString(4), rst.getString(5));

				reservas.add(objReserva);
			}
		}
	}
	
	
	
	
}
