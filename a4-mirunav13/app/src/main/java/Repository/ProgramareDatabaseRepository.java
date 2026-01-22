package Repository;

import Domain.Programare;

import java.sql.*;
import java.util.Date;

public class ProgramareDatabaseRepository extends DatabaseRepository<Programare> {

    public ProgramareDatabaseRepository(String url) {
        super(url, "programari");
    }

    @Override
    protected void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS programari (" +
                "id INTEGER PRIMARY KEY, " +
                "pacient_id INTEGER NOT NULL, " +
                "data INTEGER NOT NULL, " +
                "scop TEXT NOT NULL)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Eroare creare tabel programari: " + e.getMessage());
        }
    }

    @Override
    protected Programare extractEntity(ResultSet rs) throws SQLException {
        return new Programare(
                rs.getInt("id"),
                rs.getInt("pacient_id"),
                new Date(rs.getLong("data")),
                rs.getString("scop")
        );
    }

    @Override
    protected PreparedStatement statementInsert(Connection conn, Programare entity) throws SQLException {
        String sql = "INSERT INTO programari (id, pacient_id, data, scop) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, entity.getID());
        stmt.setInt(2, entity.getPacientId());
        stmt.setLong(3, entity.getData().getTime());
        stmt.setString(4, entity.getScop());
        return stmt;
    }

    @Override
    protected PreparedStatement statementUpdate(Connection conn, Programare entity) throws SQLException {
        String sql = "UPDATE programari SET pacient_id = ?, data = ?, scop = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, entity.getPacientId());
        stmt.setLong(2, entity.getData().getTime());
        stmt.setString(3, entity.getScop());
        stmt.setInt(4, entity.getID());
        return stmt;
    }
}
