package Repository;

import Domain.Pacient;

import java.sql.*;

public class PacientDatabaseRepository extends DatabaseRepository<Pacient> {

    public PacientDatabaseRepository(String url) {
        super(url, "pacienti");
    }

    @Override
    protected void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS pacienti (" +
                "id INTEGER PRIMARY KEY, " +
                "nume TEXT NOT NULL, " +
                "prenume TEXT NOT NULL, " +
                "varsta INTEGER NOT NULL)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Eroare creare tabel pacienti: " + e.getMessage());
        }
    }

    @Override
    protected Pacient extractEntity(ResultSet rs) throws SQLException {
        return new Pacient(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("prenume"),
                rs.getInt("varsta")
        );
    }

    @Override
    protected PreparedStatement statementInsert(Connection conn, Pacient entity) throws SQLException {
        String sql = "INSERT INTO pacienti (id, nume, prenume, varsta) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, entity.getID());
        stmt.setString(2, entity.getNume());
        stmt.setString(3, entity.getPrenume());
        stmt.setInt(4, entity.getVarsta());
        return stmt;
    }

    @Override
    protected PreparedStatement statementUpdate(Connection conn, Pacient entity) throws SQLException {
        String sql = "UPDATE pacienti SET nume = ?, prenume = ?, varsta = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getPrenume());
        stmt.setInt(3, entity.getVarsta());
        stmt.setInt(4, entity.getID());
        return stmt;
    }
}