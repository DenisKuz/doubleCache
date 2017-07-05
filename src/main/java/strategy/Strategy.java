package strategy;

public interface Strategy<K> {

    K takeKeyOfSupplantedElement();

    void upDateRating(final K key);
}