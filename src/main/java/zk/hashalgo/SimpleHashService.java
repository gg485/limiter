package zk.hashalgo;

import java.util.Objects;

public class SimpleHashService implements HashService{
    @Override
    public long hash(String key) {
        return Objects.hash(key);
    }
}
