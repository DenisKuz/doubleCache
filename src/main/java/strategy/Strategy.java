package strategy;

public interface Strategy<K> {

    K kickExtraElement();

    void upDateRating(final K key);
}