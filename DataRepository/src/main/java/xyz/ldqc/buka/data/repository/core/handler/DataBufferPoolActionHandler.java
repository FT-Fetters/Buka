package xyz.ldqc.buka.data.repository.core.handler;

import xyz.ldqc.buka.data.repository.core.action.Action;
import xyz.ldqc.buka.data.repository.core.aware.DataBufferPoolAware;

/**
 * @author Fetters
 */
public interface DataBufferPoolActionHandler<T extends Action> extends ActionHandler<T>, DataBufferPoolAware {

}
