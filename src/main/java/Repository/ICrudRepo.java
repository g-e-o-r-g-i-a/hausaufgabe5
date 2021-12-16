package Repository;

import java.io.IOException;
import java.net.http.WebSocketHandshakeException;
import java.sql.SQLException;
import java.util.List;
import Exception.AlreadyExist;
import Exception.EmptyArray;

public interface ICrudRepo<T> {

    T create(T obj) throws IOException, AlreadyExist, SQLException;
    List<T> getAll() throws IOException, SQLException, EmptyArray;
    T update(T obj) throws IOException, EmptyArray, SQLException;
    boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException;

}
