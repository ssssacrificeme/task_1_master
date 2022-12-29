package ORM;

import java.sql.SQLException;

public interface ORMInterface<T> {
    void create(T val) throws SQLException;
    T read(long id) throws SQLException;
    void update(T val) throws SQLException;
    void delete(long id) throws SQLException;
}
