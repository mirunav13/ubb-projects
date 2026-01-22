package Repository;

import Domain.Entity;
import Exceptions.AppException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseRepository<T extends Entity> implements Repository<T> {
    protected final String url;
    protected final String tableName;

    public DatabaseRepository(String url, String tableName) {
        this.url = url;
        this.tableName = tableName;
        createTable();
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    protected abstract void createTable();
    protected abstract T extractEntity(ResultSet rs) throws SQLException;
    protected abstract PreparedStatement statementInsert(Connection conn, T entity) throws SQLException;
    protected abstract PreparedStatement statementUpdate(Connection conn, T entity) throws SQLException;

    @Override
    public void add(T element) {
        String checkSql = "SELECT COUNT(*) FROM " + tableName + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, element.getID());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new AppException("ID duplicat: " + element.getID());
            }

            try (PreparedStatement stmt = statementInsert(conn, element)) {
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new AppException("Eroare la adăugare: " + e.getMessage());
        }
    }

    @Override
    public void update(T element) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = statementUpdate(conn, element)) {
            int rows = stmt.executeUpdate(); //how many rows have been modified?
            if (rows == 0) {
                throw new AppException("Nu există ID: " + element.getID());
            }
        } catch (SQLException e) {
            throw new AppException("Eroare la actualizare: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new AppException("Nu există ID: " + id);
            }
        } catch (SQLException e) {
            throw new AppException("Eroare la ștergere: " + e.getMessage());
        }
    }

    @Override
    public T findById(int id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { //moves cursor to the first line
                return extractEntity(rs);
            }
            throw new AppException("Nu există ID: " + id);
        } catch (SQLException e) {
            throw new AppException("Eroare la căutare: " + e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        List<T> result = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.add(extractEntity(rs));
            }
        } catch (SQLException e) {
            throw new AppException("Eroare la listare: " + e.getMessage());
        }
        return result;
    }
}