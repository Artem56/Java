/**
 * Created by Artem Solomatin on 10.04.17.
 * ZipComparator
 */
public interface ComparableObject <T> {
    FileStatus compareToObject(T object);

    T getComparedObject();
}
