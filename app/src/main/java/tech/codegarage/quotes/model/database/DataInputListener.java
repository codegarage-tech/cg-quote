package tech.codegarage.quotes.model.database;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public interface DataInputListener<T> {
    public void InputListener(T insertedData);
}