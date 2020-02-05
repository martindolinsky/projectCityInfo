package sample;

/**
 * @author Martin Dolinsky
 */
public class NoSuchCityException extends Exception {

    public NoSuchCityException(String message) {
        super(message);
    }
}
