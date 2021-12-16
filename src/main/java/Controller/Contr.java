package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import Exception.AlreadyExist;
import Exception.EmptyArray;
public interface Contr<T> {

    T create(T obj) throws IOException, AlreadyExist, SQLException;
    List<T> getAll() throws SQLException, IOException, EmptyArray;
    T update(T obj) throws IOException, EmptyArray, SQLException;
    boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException;
    boolean findOne(Long id) throws IOException, SQLException;
}