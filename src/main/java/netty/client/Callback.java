package netty.client;

import netty.AbstractCommand;
@FunctionalInterface
public interface Callback {
    void call(AbstractCommand message);
}
